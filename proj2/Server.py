class Server:
	def __init__(self):
		
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
        hashmap[key]=value
        return "Successfully added "+key+":"+value

def delete(key):
     if key is None:
        return "Key value invalid"
     else:
        del hashmap[key]
        return "Successfully deleted "+key
