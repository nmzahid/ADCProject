import datetime

class Log:
	def __init__(self, fileName):
		self.fileName = fileName

	def log(self, data):
		logStr = datetime.datetime.now().strftime("%Y-%m-%d %H:%M:%S")
		with open(self.fileName, "a") as myfile:
			myfile.write("["+ logStr + "] " + data + "\n")
