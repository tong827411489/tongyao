package com.Interface.api;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import javax.net.ssl.HttpsURLConnection;

import org.apache.log4j.Logger;
import org.jsoup.Connection.Method;

import Decoder.BASE64Encoder;



public class HttpRequester {
	
	private static final Logger logger = Logger.getLogger(HttpRequester.class);
	
	  /**
      * 向指定 URL 发送指定的(POST/GET)方法的请求
      * 
      * @param method
      *            指定请求方法：GET, POST 等
	  * @param url
      *            发送请求的 URL
      * @param param
      *            请求参数，请求参数是 name1=value1&name2=value2 的形式。
      * @param header 请求头所带参数
	  * @return result 返回结果
	  */
	public static Map<String, String> sendPost(String mothod,String url,String param,String header){
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
			if (!"".equals(header)&&header != null) {
				httpcon.setRequestProperty("apikey", header);	
			}
			//提交方式
			httpcon.setRequestMethod(mothod);
			out = new PrintWriter(httpcon.getOutputStream());
			if (!"".equals(mothod)&&param != null&&mothod.equals("POST")) {
				out.print(param);
			}
			out.flush();
			
			responseCode = httpcon.getResponseCode();
			map.put("map", String.valueOf(responseCode));
			if (HttpsURLConnection.HTTP_OK == responseCode) {
				br = new BufferedReader(new InputStreamReader(httpcon.getInputStream(),"GBK"));
				
				String strLine;
				StringBuffer sb = new StringBuffer();
				while ((strLine = br.readLine()) != null) {
					sb.append(strLine);
					
				}
				result = sb.toString();
				if (header != null && !"".equals(header)) {
					map.put("result", decodeUnicode(result));
				}else{
					map.put("result",result);
				}
				
			}
		} catch (Exception e) {
			logger.debug("发送 "+mothod+" 请求出现异常");
			
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
	
	/**
	 * unicode解析出中文
	 * @param theString
	 * @return
	 */
	 public static String decodeUnicode(String theString) {
	        char aChar;
	        int len = theString.length();
	        StringBuffer outBuffer = new StringBuffer(len);
	        for (int x = 0; x < len;) {
	            aChar = theString.charAt(x++);
	            if (aChar == '\\') {
	                aChar = theString.charAt(x++);
	                if (aChar == 'u') {
	                    // Read the xxxx
	                    int value = 0;
	                    for (int i = 0; i < 4; i++) {
	                        aChar = theString.charAt(x++);
	                        switch (aChar) {
	                            case '0':
	                            case '1':
	                            case '2':
	                            case '3':
	                            case '4':
	                            case '5':
	                            case '6':
	                            case '7':
	                            case '8':
	                            case '9':
	                                value = (value << 4) + aChar - '0';
	                                break;
	                            case 'a':
	                            case 'b':
	                            case 'c':
	                            case 'd':
	                            case 'e':
	                            case 'f':
	                                value = (value << 4) + 10 + aChar - 'a';
	                                break;
	                            case 'A':
	                            case 'B':
	                            case 'C':
	                            case 'D':
	                            case 'E':
	                            case 'F':
	                                value = (value << 4) + 10 + aChar - 'A';
	                                break;
	                            default:
	                                throw new IllegalArgumentException(
	                                        "Malformed   \\uxxxx   encoding.");
	                        }

	                    }
	                    outBuffer.append((char) value);
	                } else {
	                    if (aChar == 't')
	                        aChar = '\t';
	                    else if (aChar == 'r')
	                        aChar = '\r';
	                    else if (aChar == 'n')
	                        aChar = '\n';
	                    else if (aChar == 'f')
	                        aChar = '\f';
	                    outBuffer.append(aChar);
	                }
	            } else
	                outBuffer.append(aChar);
	        }
	        return outBuffer.toString();
	    }
	 
	 
	  /**
	     * 图片转化成base64字符串
	     * @param file 文件对象
	     * @return
	     */
	    public static String getImageBase64(File file) {
	        //将图片文件转化为字节数组字符串，并对其进行Base64编码处理
	        //String imgFile = "E:/image/001.png";//待处理的图片
	        InputStream in = null;
	        byte[] data = null;
	        //读取图片字节数组
	        try {
	            in = new FileInputStream(file);
	            data = new byte[in.available()];
	            in.read(data);
	            in.close();
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	        //对字节数组Base64编码
	        BASE64Encoder encoder = new BASE64Encoder();
	        String base64Data = encoder.encode(data);
	       
	        return base64Data;//返回Base64编码过的字节数组字符串
	    }

}
