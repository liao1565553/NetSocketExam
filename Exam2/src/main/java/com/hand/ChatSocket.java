package com.hand;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.Socket;
import java.net.URL;

public class ChatSocket extends Thread {
	Socket socket;

	public ChatSocket(Socket s) {
		this.socket = s;
	}

	@Override
	public void run() {
		try {
			URL url = new URL("http://files.saas.hand-china.com/java/target.pdf");
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();

			InputStream fis = connection.getInputStream();
			FileOutputStream fos = new FileOutputStream("target.pdf");

			BufferedInputStream bis = new BufferedInputStream(fis, 200);
			BufferedOutputStream bos = new BufferedOutputStream(fos);

			byte input[] = new byte[512];
			while (bis.read(input) != -1) {
				bos.write(input);
			}
			bos.close();
			bis.close();
			fos.close();
			fis.close();
			System.out.println("下载成功");
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
