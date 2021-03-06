import socket
import os
from os.path import exists
from time import sleep

def get_port():
    file = open("port", "r")
    port_number = file.readline()
    file.close()
    return int(port_number)
HOST = '127.0.0.1'
PORT = get_port()
ID = 'abc'
# 1,서버 -> 클라이언트 파일 전송 모듈
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
# 2,클라이언트 -> 서버 파일 전송 모듈
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
#3.서버 내의 리스트를 보여줌
def SendList():
    num = sock.recv(1024)
    for i in range(1, (int)(num.decode())):
        filename = sock.recv(1024)
        tmp = filename.decode()
        print('%s' %tmp[:-4])


#4. 서버내의 파일 삭제
def DeleteFile(filename):
        sock.sendall(filename.encode())
        tmp = sock.recv(1024)
        print(tmp.decode())

print("서비스 받을 번호를 눌러주세요")
service_number = input('a. 서버로부터 파일 수신 b. 서버로 파일 송신 c. 서버로부터 파일 목록 수신 d. 서버의 파일 삭제: ')

sock = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
sock.connect((HOST, PORT))
sock. sendall(ID.encode())
sleep(0.000001)
sock.sendall(service_number.encode())
if (service_number == 'a'):
    filename = input('다운로드 받을 파일이름을 입력하세요: ')
    Server2Clinet(filename)
elif (service_number == 'b'):
    filename = input('업로드 할 파일이름을 입력하세요: ')
    Client2Server(filename)
elif (service_number == 'c'):
    SendList()
elif (service_number == 'd'):
    filename = input('삭제 할 파일이름을 입력하세요: ')
    DeleteFile(filename)

sock.close()