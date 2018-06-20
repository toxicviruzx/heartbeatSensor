#Imported libraries used in this project:
#LoraConnection imports the methods from the LoraConnection class.
from LoraConnection import LoraConnection
import network, socket, machine

#Creates a LoreConnection object and make it connect to "The Things Network".
connection = LoraConnection()
connected = connection.connectToTTN()

s = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
s.bind(('', 80))
s.listen(10)

#While loop to keep the LoPy running forever.
#Creates new variables for the read data.
#Passes the data to the "sendData" method in the LoraConnection class.
while True:
    conn, addr = s.accept()
    request = conn.recv(1024)
    request = str(request).split("b")
    requestString = request[1].replace("'","")
    id,bpm = requestString.split(":")
    bpmInt, bpmFloat = bpm.split(".")
    conn.close()
    print(id + " " + bpmInt)
    connection.sendData(id,bpmInt)
