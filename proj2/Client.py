import sys
import xmlrpclib
from Log import Log

if len(sys.argv) < 3:
	print "Invalid port number"
	exit()

addr = sys.argv[1]
portnumber = sys.argv[2]

s = xmlrpclib.ServerProxy('http://'+addr+':'+portnumber)
print s.system.listMethods()

# file=open("filename.txt",'r')
# for line in file.readline():
def commandHandler(line):
    command=line.split( )
    if command[0] == 'set':
        if len(command) == 3:
            return s.set(command[1],command[2])
        else:
            return "Invalid input for set!"
    elif command[0] == 'get':
        if len(command) == 2:
            return s.get(command[1])
        else:
            return "Invalid input for get!"

    elif command[0] == 'del':
        if len(command) == 2:
            return s.delete(command[1])
        else:
            return "Invalid input for del!"
    else:
    	return "Invalid input!"



while True:
    command = raw_input("client> ")
    print commandHandler(command)
