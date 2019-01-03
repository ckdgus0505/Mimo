# File : server.py
# Brief : 라즈베리파이에서 실행 시키는 파일

import socket   # 소켓 모듈

# port 번호를 가져오는 함수
def get_port():
    file = open("port", "r")
    port_number = file.readline()
    file.close()
    return int(port_number)


socket = socket.socket()
host = ''
port = get_port()
socket.bind((host, port))
socket.listen(5)

while True:
    client, addr = socket.accept()
    print('Conn : ', addr)
    client.send("test".encode())
    client.close()
