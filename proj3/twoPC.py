import socket

class twoPC:
	def __init__(self, server_ip_list):
		with open(server_ip_list, "r") as myfile:
			self.server_list = [line.rstrip('\n') for line in myfile]
			self.server_list.remove(socket.gethostbyname(socket.gethostname()))

	def (self):
		for ip in self.server_list:
			print(ip)
	def recv(self):
		print(self.server_list)