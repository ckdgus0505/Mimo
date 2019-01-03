package org.androidtown.socket;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * ǥ�� �ڹٷ� ���� ���� ����
 * 
 * 1. Ŭ���̾�Ʈ�κ����� ���� ��û�� ������,
 * 2. Object �����͸� ���� ��,
 * 3. Object �����Ϳ� ���ڿ��� �ٿ��� ����
 * 
 * @author Mike
 *
 */
public class JavaServerSocket {
	public static void main(String[] args) {
		
		try {
			ServerSocket server = new ServerSocket(11001);
			System.out.println("���� ������ ����������ϴ�. ��Ʈ : 11001");
			
			while(true) {
				Socket aSocket = server.accept();
				System.out.println("Ŭ���̾�Ʈ�� �����.");
				
				ObjectInputStream instream = new ObjectInputStream(aSocket.getInputStream());
				Object obj = instream.readObject();
				System.out.println("���� ������ : " + obj);

				ObjectOutputStream outstream = new ObjectOutputStream(aSocket.getOutputStream());
				outstream.writeObject(obj + " from Server.");
				outstream.flush();
				System.out.println("���� ������ : " + obj + " from Server.");
				
				aSocket.close();
				System.out.println("���� ����.");
				
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
}
