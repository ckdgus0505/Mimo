#include<stdio.h>
#include<WinSock2.h>
#include<string.h>
#include<string.h>

#define SERVERIP "127.0.0.1"
#define  BUFF_SIZE   1024
int readBytes;
void Server2Client(char *filename, SOCKET *soc);
void Client2Server(char *filename);
void SendList();
void DeleteFile(char *filename);
char buff[BUFF_SIZE + 1];

int get_port()
{
	char serverport[5];
	FILE *fp = fopen("PORT", "r");
	fgets(serverport, 5 ,fp);
	return atoi(serverport);
}

int main(int argc, char *argv[])
{
	int retval;
	char service_number[1];

	char filename[BUFF_SIZE];
	SOCKET * socptr;
	// 윈속 초기화 
	WSADATA wsa;
	if (WSAStartup(MAKEWORD(2, 2), &wsa) != 0) return 1;

	// 소켓 생성 
	SOCKET sock = socket(PF_INET, SOCK_STREAM, 0);
	socptr = &sock;
	if (sock == INVALID_SOCKET) exit(1);

	// 소켓 설정
	SOCKADDR_IN serveraddr;
	ZeroMemory(&serveraddr, sizeof(serveraddr));
	serveraddr.sin_family = PF_INET;
	serveraddr.sin_addr.s_addr = inet_addr(SERVERIP);
	serveraddr.sin_port = htons(get_port());

	printf("a. 서버로부터 파일 수신 b. 서버로 파일 송신 c. 서버로부터 파일 목록 수신 d. 서버의 파일 삭제:");
	scanf("%c", &service_number);

	// 연결 요청
	retval = connect(sock, (SOCKADDR *)&serveraddr, sizeof(serveraddr));
	if (retval == SOCKET_ERROR) exit(1);

	send(sock, service_number, sizeof(service_number), 0);



  //1, 서버->클라이언트 파일 전송 모듈
	if (service_number[0] == 'a')
	{
		printf("다운로드 받을 파일이름을 입력하세요: ");
		scanf("%s", filename);
		send(sock, filename, strlen(filename), 0);

		FILE *fp = fopen(filename, "w");
		try
		{
			while ((readBytes = recv(sock, buff, BUFF_SIZE, 0)) != NULL)
			{
				fputs(buff, fp);
				readBytes = recv(sock, buff, BUFF_SIZE, 0);
				memset(buff, 0, BUFF_SIZE);
			}
		}
		catch (int i)
		{
			printf("%s",i);
		}

		fclose(fp);

	}
	//2, 클라이언트->서버 파일 전송 모듈    => 개행 문제 해결해야함...
	else if (service_number[0] == 'b')
	{
		printf("업로드 할 파일이름을 입력하세요: ");
		scanf("%s", filename);
		send(sock, filename, strlen(filename), 0);
		if (FILE *fp = fopen(filename, "r"))
		{
			try
			{
				while (fgets(buff, BUFF_SIZE, fp) != NULL)
				{ 
					send(sock, buff, strlen(buff), 0);
					//send(sock, "\r", 1, 0);
					memset(buff, 0, BUFF_SIZE);
				}
			}
			catch (int i)
			{
				printf("%s", i);
			}

			fclose(fp);
		}
		else
		{
			printf("파일 존재하지 않음\n");
		}

	}
//3.서버 내의 리스트를 보여줌
	else if (service_number[0] == 'c')
	{
		char n[1];
		recv(sock, n, 1, 0);
		for (int i = 0; i < atoi(n); i++)
		{
			readBytes = recv(sock, filename, BUFF_SIZE, 0);
			for (int i = 0; i < readBytes; i++)
				printf("%c", filename[i]);
			printf("\n");
		}

	}
//4. 서버내의 파일 삭제
	else if (service_number[0] == 'd')
	{
		printf("삭제 할 파일이름을 입력하세요: ");
		scanf("%s", filename);
		send(sock, filename, strlen(filename), 0);

		readBytes = recv(sock, buff, BUFF_SIZE, 0);
		for (int i = 0; i < readBytes; i++)
		{
			printf("%c", buff[i]);
		}
	}


	// 소켓 종료 
	closesocket(sock);

	// 윈속 종료 
	WSACleanup();
	return 0;
}
