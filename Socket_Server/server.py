import socketserver
import os
from os.path import exists


def get_port():
    file = open("port", "r")
    port_number = file.readline()
    file.close()
    return int(port_number)


HOST = ''
PORT = get_port()


class MyTcpHandler(socketserver.BaseRequestHandler):
    def handle(self):
        print('[%s] 연결됨' % self.client_address[0])

        service_number = self.request.recv(1024);
        if (service_number.decode() == 'a'):
            Server2Client(self)
        elif (service_number.decode() == 'b'):
            Client2Server(self)
        elif (service_number.decode() == 'c'):
            SendList(self)
        elif (service_number.decode() == 'd'):
            DeleteFile(self)


# 1,서버 -> 클라이언트 파일 전송 모듈
def Server2Client(self):
    data_transferred = 0
    filename = self.request.recv(1024)  # 클라이언트로 부터 파일이름을 전달받음
    filename = filename.decode()  # 파일이름 이진 바이트 스트림 데이터를 일반 문자열로 변환

    if not exists('server/' + filename):  # 파일이 해당 디렉터리에 존재하지 않으면
        return  # handle()함수를 빠져 나온다.

    print('파일[%s] 전송 시작...' % filename)
    with open('server/' + filename, 'rb') as f:
        try:
            data = f.read(1024)  # 파일을 1024바이트 읽음
            while data:  # 파일이 빈 문자열일때까지 반복
                data_transferred += self.request.send(data)
                data = f.read(1024)
        except Exception as e:
            print(e)

    print('전송완료[%s], 전송량[%d]' % (filename, data_transferred))


# 2,클라이언트 -> 서버 파일 전송 모듈
def Client2Server(self):
    data_transferred = 0
    filename = self.request.recv(1024)  # 클라이언트로 부터 파일이름을 전달받음
    print(filename.decode())
    data = self.request.recv(1024)
    if not data:
        print('파일[%s]: 클라이언트에 존재하지 않거나 전송중 오류발생' % filename)
        return
    try:  # make directory if not exist
        if not (os.path.isdir("server/")):
            os.makedirs(os.path.join("server/"))
    except OSError as e:
        if e.errno != errno.EEXIST:
            print("Failed to create directory!!!!!")
            raise

    with open('server/' + filename.decode(), 'wb') as f:  # save file at directory
        try:
            while data:
                f.write(data)
                data_transferred += len(data)
                data = self.request.recv(1024)
        except Exception as e:
            print(e)

    print('파일[%s] 전송종료. 전송량 [%d]' % (filename, data_transferred))

#3.서버 내의 리스트를 보여줌
def SendList(self):
    for root, dirs, files in os.walk('./server'):
        for file in files:
            print(file[:-4])
            self.request.send(file.encode())
        print('send complete')
#4. 클라이언트가 지정한 파일 서버 내에서 삭제
def DeleteFile(self):
    filename = self.request.recv(1024)  # 클라이언트로 부터 파일이름을 전달받음
    if os.path.isfile('server/'+filename.decode()):
        os.remove('server/'+filename.decode())
        self.request.send('delete complete'.encode())

def runServer():
    print('++++++파일 서버를 시작++++++')
    print("+++파일 서버를 끝내려면 'Ctrl + C'를 누르세요.")

    try:
        server = socketserver.TCPServer((HOST, PORT), MyTcpHandler)
        server.serve_forever()
    except KeyboardInterrupt:
        print('++++++파일 서버를 종료합니다.++++++')



runServer()