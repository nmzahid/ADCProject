import xmlrpclib

class Client:
	def __init__(self):

client = xmlrpclib.ServerProxy("http://localhost:8000/")

file=open("filename.txt",'r')
for line in file.readline():
    command=line.split( )
    length=len(command)
    if command[0] is 'set':
        if length is 3:
            response=client.set(command[1],command[2])
        else:
            response="Invalid input for set!"
    elif command[0] is 'get':
        if length is 2:
            response=client.get(command[1])
        else:
            response="Invalid input for get!"

    elif command[0] is 'del':
        if length is 2:
            response=client.delete(command[1])
        else:
            response="Invalid input for del!"


print(response)
