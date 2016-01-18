JFLAGS = -g
JC = javac
.SUFFIXES: .java .class
.java.class:
	$(JC) $(JFLAGS) $*.java

CLASSES = \
	Server.java \
	TCPServer.java \
	UDPServer.java \
	Client.java \
	TCPClient.java \
	UDPClient.java \
	Log.java \

default: classes

classes: $(CLASSES:.java=.class)

clean:
	$(RM) *.class
