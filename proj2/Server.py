import sys
from SimpleXMLRPCServer import SimpleXMLRPCServer
from SimpleXMLRPCServer import SimpleXMLRPCRequestHandler

if len(sys.argv) < 2:
	print "Invalid port number"
	exit()

portnumber = int(sys.argv[1])

class RequestHandler(SimpleXMLRPCRequestHandler):
    rpc_paths = ('/RPC2',)

server = SimpleXMLRPCServer(("localhost", portnumber), requestHandler=RequestHandler)
server.register_introspection_functions()

hashmap={}
def get(key):
    if key in hashmap:
        return hashmap[key]
    else:
        return "Key doesn't exist"

def set(key,value):
    if key is None:
        return "Key value invalid"
    else:
        hashmap.update({key:value})
        return "Successfully added "+key+":"+value

def delete(key):
    if key is None:
        return "Key value invalid"
    else:
        del hashmap[key]
        return "Successfully deleted "+key

server.register_function(get)
server.register_function(set)
server.register_function(delete)
server.serve_forever()