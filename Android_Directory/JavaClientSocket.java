package org.androidtown.socket;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

/**
 * ǥ�� �ڹٷ� ���� Ŭ���̾�Ʈ ����
 * 
 * 1. ������ ������ ��,
 * 2. ���ڿ��� Object Ÿ������ ������,
 * 3. Object�� �޾� �����.
 * 
 * @author Mike
 *
 */
public class JavaClientSocket {
	
	public static void main(String[] args) {

		try {
			Socket aSocket = new Socket("localhost", 11001);
			System.out.println("Ŭ���̾�Ʈ ������ ����������ϴ�. ȣ��Ʈ : localhost, ��Ʈ : 11001");
			
			ObjectOutputStream outstream = new ObjectOutputStream(aSocket.getOutputStream());
			outstream.writeObject("Hello.");
			outstream.flush();
			System.out.println("���� ������ : Hello.");

			ObjectInputStream instream = new ObjectInputStream(aSocket.getInputStream());
			Object obj = instream.readObject();
			System.out.println("���� ������ : " + obj);
			
			aSocket.close();
			System.out.println("���� ����.");

		} catch(Exception ex) {
			ex.printStackTrace();
		}
	}
	
}
