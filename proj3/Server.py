import sys
import SocketServer
from SimpleXMLRPCServer import SimpleXMLRPCServer
from SimpleXMLRPCServer import SimpleXMLRPCRequestHandler
import threading
from Log import Log
import socket

if len(sys.argv) < 2:
	print "Invalid port number"
	exit()

portnumber = int(sys.argv[1])
logfile = Log("server.log")
mutex = threading.Lock()
hashmap={}

logfile.log("Server starts! port number:"+str(portnumber))

class AsyncXMLRPCServer(SocketServer.ThreadingMixIn,SimpleXMLRPCServer): pass

def sync(message):
	for addr in server_list:
		try:
			print("TwoPC-Tx-phase-1: send data (%s) to %s"%(message, addr))
			sock = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
			sock.connect((addr, 8000))
			sock.settimeout(1000)

			for i in range(0,3):
				try:
					sock.sendall(message)
					response = sock.recv(1024)
					sock.close()
				except:
					continue
				break
		except:
			print("connection failed!")
			return False
	for addr in server_list:
		try:
			print("TwoPC-Tx-phase-2: send go (%s) to %s"%(message, addr))
			sock = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
			sock.connect((addr, 8000))
			sock.settimeout(1000)
			for i in range(0,3):
				try:
					sock.sendall("go")
					response = sock.recv(1024)
					sock.close()
				except:
					continue
				break
		except:
			print("connection failed!")
			return False
	return True

def get(key):
	logfile.log("Received request from client: get "+key)
	mutex.acquire()
	retStr = ''
	if key in hashmap:
		retStr = hashmap[key]
	else:
		retStr = "Key doesn't exist"
	mutex.release()
	logfile.log("Sending response to client: "+retStr)
	return retStr

def set(key,value):
	ret = sync("set %s %s"%(key, value))
	if ret == False:
		return "sync fail!"
	return _set(key,value)


def _set(key,value):
	logfile.log("Received request from client: set "+key+","+value)
	mutex.acquire()
	retStr = ''
	if key is None:
		retStr = "Key value invalid"
	else:
		hashmap.update({key:value})		
		retStr = "Successfully added "+key+":"+value
	mutex.release()
	logfile.log("Sending response to client: "+retStr)
	return retStr

def delete(key):
	ret = sync("del "+key)
	if ret == False:
		return "sync fail!"
	return _delete(key)

def _delete(key):
	logfile.log("Received request from client: delete "+key)
	mutex.acquire()
	retStr = ''
	if key is None:
		retStr = "Key value invalid"
	else:
		del hashmap[key]
		retStr = "Successfully deleted "+key
	mutex.release()
	logfile.log("Sending response to client: "+retStr)
	return retStr

# synchronization part
server_list = []#[['10.16.18.159', '8020']]
with open("server_ip_list.txt", "r") as myfile:
	print(socket.gethostbyname(socket.gethostname()))
	server_list = [line.rstrip('\n') for line in myfile]
	# server_list = [item.split(' ') for item in myfile]
	for line in server_list:
		print(line)
	server_list.remove(socket.gethostbyname(socket.gethostname()))

sync_data = ''
class ThreadedTCPRequestHandler(SocketServer.BaseRequestHandler):
	def handle(self):
		data = self.request.recv(1024)
		if data != "go":
			global sync_data
			sync_data = data
			print("TwoPC-Rx-phase-1: received data (%s) from %s"%(sync_data, self.client_address[0]))
			cur_thread = threading.current_thread()
			self.request.sendall("ACK(Got it!)")
		else:
			print("TwoPC-Rx-phase-2: received go (%s) from %s"%(data, self.client_address[0]))
			command = sync_data.split(" ")
			if len(command) == 3:
				_set(command[1], command[2])
			if len(command) == 2:
				_delete(command[1])
			cur_thread = threading.current_thread()
			self.request.sendall("ACK(Got it!)")

class ThreadedTCPServer(SocketServer.ThreadingMixIn, SocketServer.TCPServer):
	pass
	

sync_server = ThreadedTCPServer((socket.gethostbyname(socket.gethostname()), 8000), ThreadedTCPRequestHandler)
ip, port = sync_server.server_address

sync_server_thread = threading.Thread(target=sync_server.serve_forever)
sync_server_thread.daemon = True
sync_server_thread.start()


server = AsyncXMLRPCServer((socket.gethostbyname(socket.gethostname()), portnumber), SimpleXMLRPCRequestHandler)
server.register_introspection_functions()

server.register_function(get)
server.register_function(set)
server.register_function(delete)
server.serve_forever()