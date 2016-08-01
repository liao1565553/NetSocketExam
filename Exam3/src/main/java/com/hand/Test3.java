package com.hand;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;

import com.google.gson.JsonObject;

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
		String coms[] = { "name", "open", "close", "current", "high", "low" };
		try {
			HttpResponse response = null;
			try {
				response = client.execute(get);
			} catch (ClientProtocolException e1) {
				e1.printStackTrace();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			HttpEntity entity = response.getEntity();
			String result = null;
			try {
				result = EntityUtils.toString(entity, "utf-8");
			} catch (ParseException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			// 解析文本文件

			// 获取引号内的内容
			result = getUseResult(result);

			// 获取信息
			Map<String, String> maps = getMap(result,coms);
			System.out.println(maps.toString());

			// 存储为xml文件
			if (saveAsXML(maps,coms)) {
				System.out.println("存储XML文件成功！");
			} else {
				System.out.println("存储XML文件失败！");
			}

			// 存储为json数据
			if (saveAsJSON(maps,coms)) {
				System.out.println("存储JSON文件成功！");
			} else {
				System.out.println("存储JSON文件失败！");
			}

			System.out.println(maps.get("name"));

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private boolean saveAsJSON(Map<String, String> maps,String[] coms) {
		JsonObject object=new JsonObject();
		for (int i = 0; i < coms.length; i++) {
			object.addProperty(coms[i],maps.get(coms[i]) );
		}
		System.out.println(object.toString());
		// 将数据写入文件
		try {
			FileWriter fw = new FileWriter("josn.txt");
			fw.write(object.toString());
			fw.flush();
			fw.close();
		} catch (IOException ee) {
			ee.printStackTrace();
			return false;
		}
		return true;
	}

	public String getUseResult(String s) {
		int frist = 0, last = 0;
		for (int i = 0; i < s.length(); i++) {
			if (s.charAt(i) == '"') {
				frist = i + 1;
				break;
			}
		}
		for (int i = frist; i < s.length(); i++) {
			if (s.charAt(i) == '"') {
				last = i;
			}
		}
		return s.substring(frist, last);
	}

	private Map<String, String> getMap(String s, String coms[]) {
		
		Map<String, String> maps = new HashMap<String, String>();
		int count = 0;
		int frist = 0;
		for (int i = 0; i < s.length(); i++) {
			if (s.charAt(i) != ',') {
			} else {
				String test = s.substring(frist, i);
				frist = i + 1;
				if (count < 6) {
					maps.put(coms[count], test);
					count++;
				} else {
					break;
				}
			}
		}
		return maps;
	}

	public boolean saveAsXML(Map<String, String> maps,String[] coms) {
		// 创建文档及设置根元素节点的方式
		Element root = DocumentHelper.createElement("xml");
		Document document = DocumentHelper.createDocument(root);
		// 给跟元素添加属性
		// 给根节点添加孩子节点
		for (int i = 0; i < maps.size(); i++) {
			Element element1 = root.addElement(coms[i]);
			element1.addElement(maps.get(coms[i]));
		}

		// 把生成的xml文档存放在硬盘上 true代表是否换行
		OutputFormat format = new OutputFormat("    ", true);
		format.setEncoding("utf-8");// 设置编码格式
		XMLWriter xmlWriter = null;
		try {
			xmlWriter = new XMLWriter(new FileOutputStream("XML.xml"), format);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			return false;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return false;
		} finally {
			try {
				xmlWriter.write(document);
				xmlWriter.close();
			} catch (IOException e) {
				e.printStackTrace();
			}

		}
		return true;
	}

}
