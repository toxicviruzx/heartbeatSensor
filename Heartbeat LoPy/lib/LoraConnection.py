from lopyConstants import *
from network import LoRa
import binascii
import pycom
import struct
import machine
import time
import socket

#This class creates a connection with "The Things Network", and sends the given data to it.
class LoraConnection:
    #The init method sets a few variables for the class to use. It sets the heartbeat LED to false,
    #so we can control it ourselves. Also sets the app_eui and the app_key for the conection.
    def __init__(self):
        pycom.heartbeat(False)
        self.lora = LoRa(mode=LoRa.LORAWAN)
        self.app_eui = binascii.unhexlify(app_eui)
        self.app_key = binascii.unhexlify(app_key)

    #The connectToTTN method is called from within the main function. It calls the join function from the LoRa library,
    #sets the connection type to OTAA, and uses all the variables set in the init method.
    #The method will keep looping if it hasn't joined the network yet, and flash a red LED.
    #Once the LoPy has connected to "The Things Network", it will flash blue once to show it has connected.
    #Also, the loPy calls the createSocket function from this class, and returns true so that we know it is done.
    def connectToTTN(self):
        self.lora.join(activation=LoRa.OTAA, auth=(self.app_eui, self.app_key), timeout=0)
        # Loop until joined
        while not self.lora.has_joined():
            print('Not joined yet...')
            pycom.rgbled(red)
            time.sleep(1)
            pycom.rgbled(off)
        print('Joined')
        pycom.rgbled(blue)
        time.sleep(1)
        pycom.rgbled(off)
        self._createSocket()
        return True

    #This function gets called after the LoPy has connected to "The Things Network".
    #This method creates a new socket for the data to send through.
    def _createSocket(self):
        self.socket = socket.socket(socket.AF_LORA, socket.SOCK_RAW)
        self.socket.setsockopt(socket.SOL_LORA, socket.SO_DR, 5)
        self.socket.setblocking(True)

    #The sendData method of our LoPy expects the values read from the main function.
    #Firstly, it will print the values with their respective units.
    #Next, we convert our values to integers for later purpose.
    #After we cast our values, we use a build in function called pack from the struct library.
    #This function will pack our data in hexadecimal bytes.
    #At last, we send the packed data through the previously created socket, and flash a led green to show that data was sent.
    def sendData(self, id, bpm):
        print("------------------------------")
        print("ID: ", id)
        print("BPM:    ", bpm)

        data = struct.pack(">hhhh",int(id), int(bpm))

        self.socket.send(data)
        print('Data send: ' + str(data))
        pycom.rgbled(green)
        time.sleep(0.1)
        pycom.rgbled(off)
        time.sleep(duty_cycle)
