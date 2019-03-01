import socket
import os
from os.path import exists
import threading
from time import sleep

HOST = ''
PORT = 1154
ID = ''
Path = ''


# 연결될 때 마다 생성되는 쓰레드 함수
def on_new_client(conn, addr):

    print('[%s] 연결됨' %addr[0])
    ID = conn.recv(21).decode()
    if(ID[-1]=='\n'):
        ID = ID[:-1]
    Path = './mimms/' + ID + '/'

    try:  # make directory if not exist
        if not (os.path.isdir(Path)):
            os.makedirs(os.path.join(Path))
    except OSError as e:
        if e.errno != errno.EEXIST:
            print("Failed to create directory!!!!!")
            raise

    service_number = conn.recv(1024).decode();
    if (service_number[-1] == '\n'):
        service_number = service_number[:-1]

    if (service_number == 'a'):
        print('[%s] 파일 클라이언트로 전송 서비스' % addr[0])
        Server2Client(conn, Path)
    elif (service_number == 'b'):
        print('[%s] 파일 서버로 전송 서비스' % addr[0])
        Client2Server(conn, Path)
    elif (service_number == 'c'):
        print('[%s] 파일 목록 전송 서비스' % addr[0])
        SendList(conn, Path)
    elif (service_number == 'j'):
        print('[%s] 파일 목록 전송 서비스(java)' % addr[0])
        SendList(conn, Path)
    elif (service_number == 'd'):
        print('[%s] 파일 삭제 서비스' % addr[0])
        DeleteFile(conn, Path)

    print('[%s] 연결 끊김' % addr[0])

    conn.close()

# 1,서버 -> 클라이언트 파일 전송 모듈
def Server2Client(conn, Path):
    data_transferred = 0
    filename = conn.recv(1024)  # 클라이언트로 부터 파일이름을 전달받음
    filename = filename.decode()  # 파일이름 이진 바이트 스트림 데이터를 일반 문자열로 변환

    if (filename[-1] == '\n'):
        filename = filename[:-1]
    if not exists(Path + filename):  # 파일이 해당 디렉터리에 존재하지 않으면
        return  # handle()함수를 빠져 나온다.

    with open(Path + filename, 'rb') as f:
        try:
            data = f.read(1024)  # 파일을 1024바이트 읽음
            while data:  # 파일이 빈 문자열일때까지 반복
                data_transferred += conn.send(data)
                data = f.read(1024)
        except Exception as e:
            print(e)

    print('[%s] %s 전송완료, 전송량 [%dbite]' % (addr[0], filename, data_transferred))


# 2,클라이언트 -> 서버 파일 전송 모듈
def Client2Server(conn, Path):
    data_transferred = 0
    filename = conn.recv(1024)  # 클라이언트로 부터 파일이름을 전달받음

    data = conn.recv(1024)

    if not data:
        print('[%s] %s 파일: 클라이언트에 존재하지 않거나 전송중 오류발생' % (addr[0], filename.decode()))
        return
    try:  # make directory if not exist
        if not (os.path.isdir(Path)):
            os.makedirs(os.path.join(Path))
    except OSError as e:
        if e.errno != errno.EEXIST:
            print("Failed to create directory!!!!!")
            raise

    with open(Path + filename.decode(), 'wb') as f:  # save file at directory
        try:
            while data:
                f.write(data)
                data_transferred += len(data)
                data = conn.recv(1024)
        except Exception as e:
            print(e)

    print('[%s] %s 전송완료, 전송량 [%dbite]' % (addr[0], filename.decode(), data_transferred))

# JAVA,클라이언트 -> 서버 파일 전송 모듈
def Client2ServerJava(self, Path):

    data_transferred = 0
    filename = conn.recv(1024)  # 클라이언트로 부터 파일이름을 전달받음
    data = conn.recv(1024)

    if not data:
        print('[%s] %s 파일: 클라이언트에 존재하지 않거나 전송중 오류발생' % (addr[0], filename.decode()))
        return
    try:  # make directory if not exist
        if not (os.path.isdir(Path)):
            os.makedirs(os.path.join(Path))
    except OSError as e:
        if e.errno != errno.EEXIST:
            print("Failed to create directory!!!!!")
            raise

    with open(Path + filename.decode()[:-1], 'wb') as f:  # save file at directory
        try:
            while data:
                f.write(data)
                data_transferred += len(data)
                data = conn.recv(1024)
                data = data[:-1]
        except Exception as e:
            print(e)

    print('[%s] %s 전송완료, 전송량 [%dbite]' % (addr[0], filename.decode(), data_transferred))


#3.서버 내의 리스트를 보여줌
def SendList(conn, Path):
    cnt = sum([len(files) for r, d, files in os.walk(Path)])
    conn.send(str(cnt).encode())
    sleep(0.01)
    for root, dirs, files in os.walk(Path):
        for file in files:
            sleep(0.000001) # 한번 전송할때 하나의 파일이 가게끔 sleep을 걸어줌
            conn.send(file.encode())

#4. 클라이언트가 지정한 파일 서버 내에서 삭제
def DeleteFile(conn, Path):
    filename = conn.recv(1024)  # 클라이언트로 부터 파일이름을 전달받음

    if os.path.isfile(Path + filename.decode()):
        os.remove(Path + filename.decode())
        conn.send('delete complete'.encode())
    else:
        conn.send('no file exists'.encode())


# 서버 시작
s = socket.socket(socket.AF_INET, socket.SOCK_STREAM)

host = ''
port = 1154

s.setsockopt(socket.SOL_SOCKET, socket.SO_REUSEADDR, 1)
s.bind((host, port))
s.listen(5)  # maximum number of queued connections(max 5)

print('++++++파일 서버를 시작++++++')
print("+++파일 서버를 끝내려면 'Ctrl + C'를 누르세요.")

try:
    # 멀티 커넥션을 위해 accept할 때마다 쓰레드를 생성하여 연결 요청을 처리한다.
    while True:
        conn, addr = s.accept()
        t = threading.Thread(target=on_new_client, args=(conn, addr))
        t.start()

    s.close()
except KeyboardInterrupt:
    print('++++++파일 서버를 종료합니다.++++++')