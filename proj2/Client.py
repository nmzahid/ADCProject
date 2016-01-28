import sys
import xmlrpclib
from Log import Log

if len(sys.argv) < 3:
	print "Invalid port number"
	exit()

addr = sys.argv[1]
portnumber = sys.argv[2]
testfile = ''
if len(sys.argv) == 4:
	testfile = sys.argv[3]

logfile = Log("client.log")
logfile.log("Connecting to " + addr + ":" + portnumber)

s = xmlrpclib.ServerProxy('http://'+addr+':'+portnumber)
print s.system.listMethods()

def commandHandler(line):
    logfile.log("Sending command: "+line)
    command=line.split( )
    if command[0].lower() == 'set':
        if len(command) == 3:
            return s.set(command[1],command[2])
        else:
            return "Invalid input for set!"
    elif command[0].lower() == 'get':
        if len(command) == 2:
            return s.get(command[1])
        else:
            return "Invalid input for get!"

    elif command[0].lower() == 'del':
        if len(command) == 2:
            return s.delete(command[1])
        else:
            return "Invalid input for del!"
    else:
    	return "Invalid input!"


if len(testfile) > 0:
    with open(testfile, "r") as inputfile:
        for line in inputfile:
            command=line.strip('\n\r')
            if len(command) > 0 :
                logfile.log("Response from server: "+commandHandler(command))
else:
    while True:
        command = raw_input("client> ")
        response = commandHandler(command)
        print(response)
        logfile.log("Response from server: "+response)
    
