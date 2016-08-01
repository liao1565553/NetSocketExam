package com.hand;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

public class Test3 {
	public static void main(String[] args) {
		new Get().start();
	}
}

class Get extends Thread {

	HttpClient client = HttpClients.createDefault();

	@Override
	public void run() {

		HttpGet get = new HttpGet("http://hq.sinajs.cn/list=sz300170");
		try {
			HttpResponse response = client.execute(get);
			HttpEntity entity = response.getEntity();
			String result = EntityUtils.toString(entity, "utf-8");
			System.out.println(result);
			// 解析文本文件
			Map<String, String> maps = new HashMap<String, String>();
			StringBuffer temp = new StringBuffer();
			String line[] = { "name", "open", "close", "current", "high", "low" };
			int i = 0;
			int j=0;
			char item = result.charAt(i);
			while (item != '"') {
				item = result.charAt(++i);
			}
			item = result.charAt(++i);
			while (item != '"') {
				while (item != ',') {
					temp.append(item);
					item = result.charAt(++i);
					System.out.println(item);
				}
				maps.put(line[j], temp.toString());
				//temp=null;
			}
			System.out.println(maps.toString());
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
}
