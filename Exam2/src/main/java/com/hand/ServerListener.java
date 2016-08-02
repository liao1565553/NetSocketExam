package com.hand;

import java.awt.HeadlessException;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerListener extends Thread {
	@Override
	public void run() {

		try {
			@SuppressWarnings("resource")
			ServerSocket serverSocket = new ServerSocket(12345);

			while(true){
				System.out.println("服务器已开启");
				Socket socket = serverSocket.accept();
				System.out.println("有客户端请求连接！");
				ChatSocket chatSocket=new ChatSocket(socket);
				chatSocket.start();
			}
		} catch (HeadlessException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
	}

}
