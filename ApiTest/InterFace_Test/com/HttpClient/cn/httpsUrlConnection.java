package com.HttpClient.cn;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.apache.http.client.utils.URLEncodedUtils;

public class httpsUrlConnection{
	
	private static TrustManager myx509TrustManager = new X509TrustManager() {
		
		public X509Certificate[] getAcceptedIssuers() {
			// TODO Auto-generated method stub
			return null;
		}
		
		public void checkServerTrusted(X509Certificate[] chain, String authType)
				throws CertificateException {
			// TODO Auto-generated method stub
			
		}
		
		public void checkClientTrusted(X509Certificate[] chain, String authType)
				throws CertificateException {
			// TODO Auto-generated method stub
			
		}
	};
	
	public String HttpUrlConnectionGet(String Url){
		
		
		HttpsURLConnection conn = null;
		PrintWriter pw = null;
		BufferedReader br = null;
		
		StringBuffer sb = new StringBuffer();
		String line = null;
		String response = null;
	
			try {
				TrustManager[] tm = {myx509TrustManager };
				 //设置SSLContext
				SSLContext sslContext = SSLContext.getInstance("SSL","SunJSSE");
				sslContext.init(null, tm,new java.security.SecureRandom());
				//从上述SSLContext对象中得到SSLSocketFactory对象
				SSLSocketFactory ssf = sslContext.getSocketFactory();
				
				conn = (HttpsURLConnection) new URL(Url).openConnection();
				conn.setSSLSocketFactory(ssf);
				conn.setDoOutput(true);
	            conn.setDoInput(true);
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
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (KeyManagementException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (NoSuchAlgorithmException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (NoSuchProviderException e) {
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
	
	public String HttpUrlConnectionPost(String Url,String rawBody){
		HttpsURLConnection conn = null;
		PrintWriter pw = null;
		BufferedReader br = null;
		
		StringBuffer sb = new StringBuffer();
		String line = null;
		String response = null;
	
			try {
				TrustManager[] tm = {myx509TrustManager };
				 //设置SSLContext
				SSLContext sslContext = SSLContext.getInstance("SSL","SunJSSE");
				sslContext.init(null, tm,new java.security.SecureRandom());
				//从上述SSLContext对象中得到SSLSocketFactory对象
				SSLSocketFactory ssf = sslContext.getSocketFactory();
				
				conn = (HttpsURLConnection) new URL(Url).openConnection();
				conn.setSSLSocketFactory(ssf);
				conn.setDoOutput(true);
	            conn.setDoInput(true);
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
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (KeyManagementException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (NoSuchAlgorithmException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (NoSuchProviderException e) {
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
	
	public static void main(String[] args){
		httpsUrlConnection hs = new httpsUrlConnection();
		String m = hs.HttpUrlConnectionGet("https://passport.kesucorp.com/captcha_image?type=register&0.5502958530560136");
		System.out.println(m);
	}
}