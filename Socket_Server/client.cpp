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
	// ���� �ʱ�ȭ 
	WSADATA wsa;
	if (WSAStartup(MAKEWORD(2, 2), &wsa) != 0) return 1;

	// ���� ���� 
	SOCKET sock = socket(PF_INET, SOCK_STREAM, 0);
	socptr = &sock;
	if (sock == INVALID_SOCKET) exit(1);

	// ���� ����
	SOCKADDR_IN serveraddr;
	ZeroMemory(&serveraddr, sizeof(serveraddr));
	serveraddr.sin_family = PF_INET;
	serveraddr.sin_addr.s_addr = inet_addr(SERVERIP);
	serveraddr.sin_port = htons(get_port());

	printf("a. �����κ��� ���� ���� b. ������ ���� �۽� c. �����κ��� ���� ��� ���� d. ������ ���� ����:");
	scanf("%c", &service_number);

	// ���� ��û
	retval = connect(sock, (SOCKADDR *)&serveraddr, sizeof(serveraddr));
	if (retval == SOCKET_ERROR) exit(1);

	send(sock, service_number, sizeof(service_number), 0);



  //1, ����->Ŭ���̾�Ʈ ���� ���� ���
	if (service_number[0] == 'a')
	{
		printf("�ٿ�ε� ���� �����̸��� �Է��ϼ���: ");
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
	//2, Ŭ���̾�Ʈ->���� ���� ���� ���    => ���� ���� �ذ��ؾ���...
	else if (service_number[0] == 'b')
	{
		printf("���ε� �� �����̸��� �Է��ϼ���: ");
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
			printf("���� �������� ����\n");
		}

	}
//3.���� ���� ����Ʈ�� ������
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
//4. �������� ���� ����
	else if (service_number[0] == 'd')
	{
		printf("���� �� �����̸��� �Է��ϼ���: ");
		scanf("%s", filename);
		send(sock, filename, strlen(filename), 0);

		readBytes = recv(sock, buff, BUFF_SIZE, 0);
		for (int i = 0; i < readBytes; i++)
		{
			printf("%c", buff[i]);
		}
	}


	// ���� ���� 
	closesocket(sock);

	// ���� ���� 
	WSACleanup();
	return 0;
}
