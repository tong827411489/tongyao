package com.HttpClient.cn;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

import org.apache.log4j.Logger;

public class HttpUrlConnection {
	
	private static Logger logger = Logger.getLogger(HttpUrlConnection.class);
	httpClientCookies hcc = new httpClientCookies();
	/**
	 * @param HttpUrlConnection
	 * @param 获取参数,发送方式 GET
	 * @param rawBdoy存放参数
	 * @return response
	 */
	public String HttpUrlConnectionGet(String Url,String rawBody){
		HttpURLConnection conn = null;
		PrintWriter pw = null;
		BufferedReader br = null;
		StringBuffer sb = null;
		String line = null;
		String response = null;
	
			try {
				conn = (HttpURLConnection) new URL(Url).openConnection();
				conn.setDoOutput(true);
				conn.setReadTimeout(20000);
				conn.setConnectTimeout(20000);
				conn.setRequestProperty("accept", "*/*");
				conn.setRequestProperty("connection", "Keep-Alive");
				conn.setRequestProperty("user-agent",
		                    "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
				conn.setUseCaches(false);
				conn.setRequestMethod("GET");
				conn.connect();
				pw = new PrintWriter(conn.getOutputStream());
				pw.print(rawBody);
				pw.close();
				int responseCode = conn.getResponseCode();
				if (responseCode == HttpsURLConnection.HTTP_OK) {
					//读取返回数据
					br = new BufferedReader(new InputStreamReader(conn.getInputStream(),"UTF-8"));
					while ((line = br.readLine()) != null) {
						sb.append(line);
					}
					response = sb.toString();
				}else{
					response = String.valueOf(responseCode);
				}
			} catch (MalformedURLException e) {
				logger.error("61");
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			finally{
				try {
					if (pw != null) {
						pw.close();
					}
					if (br != null) {
						br.close();
					}
				} catch (Exception e2) {
					e2.printStackTrace();
				}
				
			}
		return response;
	}
	

	/**
	 * @param HttpUrlConnection
	 * @param 获取参数,发送方式 GET
	 * @param rawBdoy存放参数
	 * 			获取Cookies，获取帐号权限
	 * @return response
	 */
	public String HttpUrlConnectionGet(String Url,String rawBody,String cookiesUrl,String cookiesParam){
		HttpURLConnection conn = null;
		PrintWriter pw = null;
		BufferedReader br = null;
		
		StringBuffer sb = null;
		String line = null;
		String response = null;
	
			try {
				conn = (HttpURLConnection) new URL(Url).openConnection();
				conn.setDoOutput(true);
				conn.setReadTimeout(20000);
				conn.setConnectTimeout(20000);
				conn.setRequestProperty("accept", "*/*");
				conn.setRequestProperty("connection", "Keep-Alive");
				conn.setRequestProperty("user-agent",
		                    "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
				conn.setRequestProperty("Cookie",hcc.HttpUrlConnectionCookies(cookiesUrl, cookiesParam));
				conn.setUseCaches(false);
				conn.setRequestMethod("Get");
				conn.connect();
				pw = new PrintWriter(conn.getOutputStream());
				pw.print(rawBody);
				pw.close();
				int responseCode = conn.getResponseCode();
				if (responseCode == HttpsURLConnection.HTTP_OK) {
					//读取返回数据
					br = new BufferedReader(new InputStreamReader(conn.getInputStream(),"UTF-8"));
					while ((line = br.readLine()) != null) {
						sb.append(line);
					}
					response = sb.toString();
				}else{
					response = String.valueOf(responseCode);
				}
			} catch (MalformedURLException e) {
				logger.error("53");
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			finally{
				try {
					if (pw != null) {
						pw.close();
					}
					if (br != null) {
						br.close();
					}
				} catch (Exception e2) {
					e2.printStackTrace();
				}
				
			}
		return response;
	}
	/**
	 * @param HttpUrlConnection
	 * @param 获取参数,发送方式 POST
	 * @param rawBdoy存放参数
	 * @return response
	 */
	public String HttpUrlConnectionPost(String Url,String rawBody){
		HttpURLConnection conn = null;
		PrintWriter pw = null;
		BufferedReader br = null;
		
		StringBuffer sb = null;
		String line = null;
		String response = null;
	
			try {
				conn = (HttpURLConnection) new URL(Url).openConnection();
				conn.setDoOutput(true);
				conn.setReadTimeout(20000);
				conn.setConnectTimeout(20000);
				conn.setRequestProperty("accept", "*/*");
				conn.setRequestProperty("connection", "Keep-Alive");
				conn.setRequestProperty("user-agent",
		                    "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
				conn.setUseCaches(false);
				conn.setRequestMethod("POST");
				conn.connect();
				pw = new PrintWriter(conn.getOutputStream());
				pw.print(rawBody);
				pw.close();
				int responseCode = conn.getResponseCode();
				if (responseCode == HttpsURLConnection.HTTP_OK) {
					//读取返回数据
					br = new BufferedReader(new InputStreamReader(conn.getInputStream(),"UTF-8"));
					while ((line = br.readLine()) != null) {
						sb.append(line);
					}
					response = sb.toString();
				}else{
					response = String.valueOf(responseCode);
				}
			} catch (MalformedURLException e) {
				logger.error("53");
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			finally{
				try {
					if (pw != null) {
						pw.close();
					}
					if (br != null) {
						br.close();
					}
				} catch (Exception e2) {
					e2.printStackTrace();
				}
				
			}
		return response;
	}
	

	/**
	 * @param HttpUrlConnection
	 * @param 获取参数,发送方式 POST
	 * @param rawBdoy存放参数
	 * 			获取Cookies，获取帐号权限
	 * @return response
	 */
	public String HttpUrlConnectionPost(String Url,String rawBody,String cookiesUrl,String cookiesParam){
		HttpURLConnection conn = null;
		PrintWriter pw = null;
		BufferedReader br = null;
		
		StringBuffer sb = null;
		String line = null;
		String response = null;
		
	
			try {
				conn = (HttpURLConnection) new URL(Url).openConnection();
				conn.setDoOutput(true);
				conn.setReadTimeout(20000);
				conn.setConnectTimeout(20000);
				conn.setRequestProperty("accept", "*/*");
				conn.setRequestProperty("connection", "Keep-Alive");
				conn.setRequestProperty("user-agent",
		                    "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
				conn.setRequestProperty("Cookie",hcc.HttpUrlConnectionCookies(cookiesUrl, cookiesParam));
				conn.setUseCaches(false);
				conn.setRequestMethod("POST");
				conn.connect();
				pw = new PrintWriter(conn.getOutputStream());
				pw.print(rawBody);
				pw.close();
				int responseCode = conn.getResponseCode();
				if (responseCode == HttpsURLConnection.HTTP_OK) {
					//读取返回数据
					br = new BufferedReader(new InputStreamReader(conn.getInputStream(),"UTF-8"));
					while ((line = br.readLine()) != null) {
						sb.append(line);
					}
					response = sb.toString();
				}else{
					response = String.valueOf(responseCode);
				}
			} catch (MalformedURLException e) {
				logger.error("53");
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			finally{
				try {
					if (pw != null) {
						pw.close();
					}
					if (br != null) {
						br.close();
					}
				} catch (Exception e2) {
					e2.printStackTrace();
				}
				
			}
		return response;
	}
}
