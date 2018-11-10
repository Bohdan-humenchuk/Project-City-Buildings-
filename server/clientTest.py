from socket import *
import sys

host = '0.tcp.ngrok.io'
port = 11625
addr = (host,port)
tcp_socket = socket(AF_INET, SOCK_STREAM)
tcp_socket.connect(addr)

data = input("enter what you want to send: ")

#str.encode - перекодирует введенные данные в байты, bytes.decode - обратно
data = str.encode(data)
tcp_socket.send(data)

data = bytes.decode(tcp_socket.recv(1024))
print(data)

tcp_socket.close()
