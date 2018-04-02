from socket import *

host = '192.168.0.103'
port = 25970
addr = (host, port)
##hostask=input("hostname or IP: ")
##if hostask != "none":
##    host=hostask

def getInfo(id):
    information = "This is information about building in front of you"
    return information

def getTheBuildingID(long, lat, angl):
    ID=000000
    return ID

tcp_socket = socket(AF_INET, SOCK_STREAM)

tcp_socket.bind(addr)
tcp_socket.listen(1)
print("server is started on:", " ", host, ":", port,)

while True:

    # Если мы захотели выйти из программы
##    if input('Do you want to quit? y\\n: ') == 'y': break

    print('wait connection...')

    # accept - принимает запрос и устанавливает соединение, (по умолчанию работает в блокирующем режиме)
    # устанавливает новый сокет соединения в переменную conn и адрес клиента в переменную addr
    conn, addr = tcp_socket.accept()
    print('client addres: ', addr)

    # recv - получает сообщение TCP
    longtitude = float(bytes.decode(conn.recv(1024)))
    conn.send(str.encode("got longtitude"))
    latitude = float(bytes.decode(conn.recv(1024)))
    conn.send(str.encode("got latitude"))
    angle = float(bytes.decode(conn.recv(1024)))
    conn.send(str.encode("got angle"))
    
    print(longtitude)
    print(latitude)
    print(angle)

##    conn.send(b'thanks for data, wait while server work over data and searching information')
    
    conn.send(str.encode(getInfo(getTheBuildingID(longtitude, latitude, angle))))

    conn.close()

tcp_socket.close()
