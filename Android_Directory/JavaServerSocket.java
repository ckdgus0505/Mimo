package org.androidtown.socket;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class JavaServerSocket {
	public static void main(String[] args) {
		
		try {
			ServerSocket server = new ServerSocket(1154);
			System.out.println("ServerSocket is created. port number: 1154");
			
			while(true) {
				Socket aSocket = server.accept();
				System.out.println("Client is connected");
				
				ObjectInputStream instream = new ObjectInputStream(aSocket.getInputStream());
				Object obj = instream.readObject();
				System.out.println("received data: " + obj);
				ObjectOutputStream outstream = new ObjectOutputStream(aSocket.getOutputStream());
				outstream.writeObject(obj + " from Server.");
				outstream.flush();
				System.out.println("sending data: " + obj + " from Server.");
				
				aSocket.close();
				System.out.println("Socket closed.");
				
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
}
