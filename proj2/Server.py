import sys
import SocketServer
from SimpleXMLRPCServer import SimpleXMLRPCServer
from SimpleXMLRPCServer import SimpleXMLRPCRequestHandler
from threading import Thread, Lock
from Log import Log
import socket

if len(sys.argv) < 2:
	print "Invalid port number"
	exit()

portnumber = int(sys.argv[1])
logfile = Log("server.log")
mutex = Lock()
hashmap={}

logfile.log("Server starts! port number:"+str(portnumber))

class AsyncXMLRPCServer(SocketServer.ThreadingMixIn,SimpleXMLRPCServer): pass


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

server = AsyncXMLRPCServer((socket.gethostbyname(socket.gethostname()), portnumber), SimpleXMLRPCRequestHandler)
server.register_introspection_functions()

server.register_function(get)
server.register_function(set)
server.register_function(delete)
server.serve_forever()
