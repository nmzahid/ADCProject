import sys
import xmlrpclib
from Log import Log

if len(sys.argv) < 3:
	print "Invalid port number"
	exit()

addr = sys.argv[1]
portnumber = sys.argv[2]
logfile = Log("client.log")
logfile.log("Connecting to " + addr + ":" + portnumber)

s = xmlrpclib.ServerProxy('http://'+addr+':'+portnumber)
print s.system.listMethods()

# file=open("filename.txt",'r')
# for line in file.readline():
def commandHandler(line):
    logfile.log("Sending command: "+line)
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
    with open("file.txt", "r") as inputfile:
    	for line in inputfile:
    		command=line
    		logfile.log("Response from server: "+commandHandler(command))
    #command = raw_input("client> ")
    
