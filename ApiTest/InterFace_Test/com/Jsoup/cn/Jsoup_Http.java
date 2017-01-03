package com.Jsoup.cn;

import java.io.IOException;
import java.util.Map;

import org.apache.log4j.Logger;
import org.jsoup.Connection;
import org.jsoup.Connection.Method;
import org.jsoup.Connection.Response;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public class Jsoup_Http {
	
	private static Logger logger = Logger.getLogger(Jsoup_Http.class);
	
	/**
	 * Jsoup get 请求 不带参数
	 */
	public Document JsoupGet(String Url){
		Document doc = null;
		try {
			doc = Jsoup.connect(Url).get();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return doc;
	}

	/**
	 * @param 获取Cookies
	 * @return 返回Cookies
	 */
	
	public Map<String, String> JsoupCookies(String account,String password){
		Map<String, String> cookies = null;
		try {
		Connection conn = Jsoup.connect("http://192.168.0.186:9092/login/doLogin.html");
		conn.data("account", account);
		conn.data("password", password);
		conn.timeout(20000);
		conn.method(Method.POST);
		//返回数据
		Response response;
		response = conn.execute();
		cookies = response.cookies();
		return cookies;
		} catch (IOException e) {
			logger.error("请求错误");
			e.printStackTrace();
			return cookies;
		}
	}
}
