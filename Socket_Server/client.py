import socket
import os
from os.path import exists

def get_port():
    file = open("port", "r")
    port_number = file.readline()
    file.close()
    return int(port_number)
HOST = 'localhost'
PORT = get_port()

def Server2Clinet(filename):
    data_transferred = 0

    sock.sendall(filename.encode())
    data = sock.recv(1024)
    if not data:
        print('파일[%s]: 서버에 존재하지 않거나 전송중 오류발생' % filename)
        return
    try:  # make directory if not exist
        if not (os.path.isdir("client/")):
            os.makedirs(os.path.join("client/"))
    except OSError as e:
        if e.errno != errno.EEXIST:
            print("Failed to create directory!!!!!")
            raise

    with open('client/'+ filename, 'wb') as f:  # save file at directory
        try:
            while data:
                f.write(data)
                data_transferred += len(data)
                data = sock.recv(1024)
        except Exception as e:
            print(e)

    print('파일[%s] 전송종료. 전송량 [%d]' % (filename, data_transferred))

def Client2Server(filename):
    data_transferred = 0
    sock.sendall(filename.encode())
    if not exists('client/' + filename):  # 파일이 해당 디렉터리에 존재하지 않으면
        return  # handle()함수를 빠져 나온다.

    print('파일[%s] 전송 시작...' % filename)
    with open('client/' + filename, 'rb') as f:
        try:
            data = f.read(1024)  # 파일을 1024바이트 읽음
            while data:  # 파일이 빈 문자열일때까지 반복
                data_transferred += sock.send(data)
                data = f.read(1024)
        except Exception as e:
            print(e)

    print('전송완료[%s], 전송량[%d]' % (filename, data_transferred))


print("서비스 받을 번호를 눌러주세요")
service_number = input('a. 서버로부터 파일 수신 b. 서버로 파일 송신 c. 서버로부터 파일 목록 수신: ')

sock = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
sock.connect((HOST, PORT))

if (service_number == 'a'):
    sock.sendall(service_number.encode())
    filename = input('다운로드 받은 파일이름을 입력하세요: ')
    Server2Clinet(filename)
elif (service_number == 'b'):
    sock.sendall(service_number.encode())
    filename = input('업로드 할 파일이름을 입력하세요: ')
    Client2Server(filename)
