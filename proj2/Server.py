import sys
import SocketServer
from SimpleXMLRPCServer import SimpleXMLRPCServer
from SimpleXMLRPCServer import SimpleXMLRPCRequestHandler
from threading import Thread

if len(sys.argv) < 2:
	print "Invalid port number"
	exit()

portnumber = int(sys.argv[1])

class AsyncXMLRPCServer(SocketServer.ThreadingMixIn,SimpleXMLRPCServer): pass

hashmap={}
def get(key):
	retStr = ''
	if key in hashmap:
		retStr = hashmap[key]
	else:
		retStr = "Key doesn't exist"
	return retStr

def set(key,value):
	retStr = ''
	if key is None:
		retStr = "Key value invalid"
	else:
		hashmap.update({key:value})
		
		retStr = "Successfully added "+key+":"+value
	return retStr

def delete(key):
	retStr = ''
	if key is None:
		retStr = "Key value invalid"
	else:
		del hashmap[key]
		retStr = "Successfully deleted "+key
	return retStr

server = AsyncXMLRPCServer(("localhost", portnumber), SimpleXMLRPCRequestHandler)
server.register_introspection_functions()

server.register_function(get)
server.register_function(set)
server.register_function(delete)
server.serve_forever()