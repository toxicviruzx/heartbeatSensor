#Imported libraries used in this project:
#SI7006A20 imports the sensor library for the humidity sensor.
#MPL3115A2 imports the sensor library for the temperature sensor.
#LTR329ALS01 imports the ensor library for the light sensor.
#LoraConnection imports the methods from the LoraConnection class.
# from SI7006A20 import SI7006A20
# from MPL3115A2 import MPL3115A2
# from LTR329ALS01 import LTR329ALS01
from LoraConnection import LoraConnection
import network, socket, machine
#Creates new objects and defines them as their respective names.
# temperatureSensor = MPL3115A2()
# humiditySensor = SI7006A20()
# pressureSensor = MPL3115A2()
# lightSensor = LTR329ALS01()

#Creates a LoreConnection object and make it connect to "The Things Network".
connection = LoraConnection()
connected = connection.connectToTTN()

s = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
s.bind(('', 80))
s.listen(10)



#While loop to keep the LoPy running forever.
#Creates new variables for the read data. Temperature sensor data will be assigned to the "temperature" variable.
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
