package com.Interface.api;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import javax.net.ssl.HttpsURLConnection;

import org.apache.log4j.Logger;



public class HttpRequester {
	
	private static final Logger logger = Logger.getLogger(HttpRequester.class);
	
	  /**
      * 向指定 URL 发送POST方法的请求
      * 
      * @param method
      *            指定请求方法：GET, POST 等
	  * @param url
      *            发送请求的 URL
      * @param param
      *            请求参数，请求参数是 name1=value1&name2=value2 的形式。
	  * @return result 返回结果
	  */
	public static Map<String, String> sendPost(String mothod,String url,String param){
		PrintWriter out = null;
		BufferedReader br = null;
		String result = "";
		int responseCode = 0;
		Map<String, String> map = new HashMap<String, String>();
		
		try {
			HttpURLConnection httpcon = (HttpURLConnection) new URL(url).openConnection();
			
			 // 发送POST请求必须设置如下两行
			httpcon.setDoOutput(true);
			httpcon.setDoInput(true);
			
			httpcon.setReadTimeout(15000);
			httpcon.setConnectTimeout(15000);
			httpcon.setInstanceFollowRedirects(false);
			httpcon.setRequestProperty("accept", "*/*");
			httpcon.setRequestProperty("connection", "Keep-Alive");
			httpcon.setRequestProperty("user-agent",
	                    "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
			//提交方式
			httpcon.setRequestMethod(mothod);
			out = new PrintWriter(httpcon.getOutputStream());
			out.print(param);
			out.flush();
			
			responseCode = httpcon.getResponseCode();
			map.put("map", String.valueOf(responseCode));
			if (HttpsURLConnection.HTTP_OK == responseCode) {
				br = new BufferedReader(new InputStreamReader(httpcon.getInputStream(),"utf-8"));
				String strLine;
				StringBuffer sb = new StringBuffer();
				while ((strLine = br.readLine()) != null) {
					sb.append(strLine);
					
				}
				result = sb.toString();
				map.put("result", result);
			}
		} catch (Exception e) {
			logger.debug("发送 POST 请求出现异常！");
			e.printStackTrace();
		}
		 //使用finally块来关闭输出流、输入流
        finally{
            try{
                if(out!=null){
                    out.close();
                }
                if(br!=null){
                    br.close();
                }
            }
            catch(IOException ex){
                ex.printStackTrace();
            }
        }
		return map;
	}
	
}
