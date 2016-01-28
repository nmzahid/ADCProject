import sys
import SocketServer
from SimpleXMLRPCServer import SimpleXMLRPCServer
from SimpleXMLRPCServer import SimpleXMLRPCRequestHandler
from threading import Thread, Lock
from Log import Log

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
	mutex.acquire()
	retStr = ''
	if key in hashmap:
		retStr = hashmap[key]
	else:
		retStr = "Key doesn't exist"
	mutex.release()
	return retStr

def set(key,value):
	mutex.acquire()
	retStr = ''
	if key is None:
		retStr = "Key value invalid"
	else:
		hashmap.update({key:value})		
		retStr = "Successfully added "+key+":"+value
	mutex.release()
	return retStr

def delete(key):
	mutex.acquire()
	retStr = ''
	if key is None:
		retStr = "Key value invalid"
	else:
		del hashmap[key]
		retStr = "Successfully deleted "+key
	mutex.release()
	return retStr

server = AsyncXMLRPCServer(("localhost", portnumber), SimpleXMLRPCRequestHandler)
server.register_introspection_functions()

server.register_function(get)
server.register_function(set)
server.register_function(delete)
server.serve_forever()