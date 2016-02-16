package com.app.music.common.http;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;

import org.apache.commons.httpclient.DefaultHttpMethodRetryHandler;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.cookie.CookiePolicy;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.multipart.FilePart;
import org.apache.commons.httpclient.methods.multipart.MultipartRequestEntity;
import org.apache.commons.httpclient.methods.multipart.Part;
import org.apache.commons.httpclient.methods.multipart.StringPart;
import org.apache.commons.httpclient.params.HttpMethodParams;

import android.util.Log;

import com.app.music.app.AppContext;
import com.app.music.app.AppException;
import com.app.music.app.ClientInfo;
import com.app.music.common.SystemUtils;
import com.app.music.common.URLs;

/**
 * API客户端接口：用于访问网络数据（采用HttpClient）
 * @author songqy
 * @version 1.0.0
 * @created 2015-04-27
 */
public class ApiClient {
	public static final String UTF_8 = "UTF-8"; //UTF-8编码

	private final static int TIMEOUT_CONNECTION = 10000; // 连接超时
	private final static int TIMEOUT_SOCKET = 20000; // 读取超时
	private final static int RETRY_TIME = 2; // 失败尝试次数

	/**
	 * 获取HttpClient对象
	 * @return
	 */
	private static HttpClient getHttpClient() {
		HttpClient httpClient = new HttpClient();
		// 设置 HttpClient 接收 Cookie,用与浏览器一样的策略
		httpClient.getParams().setCookiePolicy(CookiePolicy.BROWSER_COMPATIBILITY);
		// 设置 默认的超时重试处理策略
		httpClient.getParams().setParameter(HttpMethodParams.RETRY_HANDLER, new DefaultHttpMethodRetryHandler());
		// 设置 连接超时时间
		httpClient.getHttpConnectionManager().getParams().setConnectionTimeout(TIMEOUT_CONNECTION);
		// 设置 读数据超时时间
		httpClient.getHttpConnectionManager().getParams().setSoTimeout(TIMEOUT_SOCKET);
		// 设置 字符集
		httpClient.getParams().setContentCharset(UTF_8);
		return httpClient;
	}

	/**
	 * 获取Get请求方式的Method对象
	 * @param url 请求的url
	 * @param isEncoding 编码与否
	 * @return
	 */
	private static GetMethod getHttpGet(String url, boolean isEncoding) {
		GetMethod httpGet = new GetMethod(url);
		// 设置 请求超时时间
		httpGet.getParams().setSoTimeout(TIMEOUT_SOCKET);
		httpGet.setRequestHeader("Host", URLs.HOST);
		httpGet.setRequestHeader("Connection", "Keep-Alive");
		httpGet.setRequestHeader("User-Agent", ClientInfo.getInstance().userAgent);
		if(isEncoding){
			httpGet.setRequestHeader("Content-Encoding", "gzip");
		}
		httpGet.setRequestHeader("Proxy-Client-IP", SystemUtils.getIP(AppContext.appContext));
		
		return httpGet;
	}

	/**
	 * 
	 * 获取Post请求方式的Method对象
	 * @param url 请求的url
	 * @param isEncoding 缓存信息
	 * @return
	 */
	private static PostMethod getHttpPost(String url, boolean isEncoding) {
		PostMethod httpPost = new PostMethod(url);
		// 设置 请求超时时间
		httpPost.getParams().setSoTimeout(TIMEOUT_SOCKET);
		httpPost.setRequestHeader("Host", URLs.HOST);
		httpPost.setRequestHeader("Connection", "Keep-Alive");
		httpPost.setRequestHeader("User-Agent", ClientInfo.getInstance().userAgent);
		if(isEncoding){
			httpPost.setRequestHeader("Content-Encoding", "gzip");
		}
		httpPost.setRequestHeader("Proxy-Client-IP", SystemUtils.getIP(AppContext.appContext));
		
		return httpPost;
	}

	/**
	 * 在url尾部添加参数
	 * @param p_url 要转换的url
	 * @param params 参数集合
	 * @return
	 */
	public static String _MakeURL(String p_url, HashMap<String, Object> params) {
		StringBuilder url = new StringBuilder(p_url);
		if (url.indexOf("?") < 0)
			url.append('?');

		for (String name : params.keySet()) {
			url.append('&');
			url.append(name);
			url.append('=');
			url.append(String.valueOf(params.get(name)));
		}
		return url.toString().replace("?&", "?");
	}

	/**
	 * 流转为字符串方法
	 * @param inputStream 要转换的流
	 * @return 转换后的字符串
	 */
	public static String convertStreamToString(InputStream inputStream) {
		BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
		StringBuilder sb = new StringBuilder();
		String line = null;
		try {
			while ((line = reader.readLine()) != null) {
				sb.append(line);
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				inputStream.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return sb.toString();
	}

	/**
	 * 公用get请求URL
	 * @param url
	 * @throws AppException
	 */
	public static InputStream doGet(AppContext appContext, String url) throws AppException {
		HttpClient httpClient = null;
		GetMethod httpGet = null;

		String responseBody = "";
		int time = 0;
		do {
			try {
				//获取httpClient对象
				httpClient = getHttpClient(); 
				//获取Get方式的Method对象
				httpGet = getHttpGet(url, true);
				//执行访问
				int statusCode = httpClient.executeMethod(httpGet);
				Log.i("acoe_music", "statusCode: " + statusCode);
				if (statusCode != HttpStatus.SC_OK) {
					//throw AppException.http(statusCode);
					break;
				}
				responseBody = httpGet.getResponseBodyAsString();
				break;
			} catch (HttpException e) {
				time++;
				if (time < RETRY_TIME) {
					try {
						Thread.sleep(300);
					} catch (InterruptedException e1) {
					}
					continue;
				}
				// 发生致命的异常，可能是协议不对或者返回的内容有问题
				e.printStackTrace();
				//throw AppException.http(e);
			} catch (IOException e) {
				time++;
				if (time < RETRY_TIME) {
					try {
						Thread.sleep(300);
					} catch (InterruptedException e1) {
					}
					continue;
				}
				// 发生网络异常
				e.printStackTrace();
				//throw AppException.network(e);
			} catch (Exception e) {
				time++;
				e.printStackTrace();
				//throw AppException.network(e);
			} finally {
				// 释放连接
				httpGet.releaseConnection();
				httpClient = null;
			}
		} while (time < RETRY_TIME);
		return new ByteArrayInputStream(responseBody.getBytes());
	}

	/**
	 * 公用post方法
	 * @param url 访问的地址
	 * @param params 参数集合
	 * @param files 文件集合
	 * @throws AppException 抛出的异常
	 */
	public static InputStream doPost( String url, HashMap<String, Object> params, HashMap<String, File> files) throws AppException {
		HttpClient httpClient = null;
		PostMethod httpPost = null;
		// post表单参数处理
		int length = (params == null ? 0 : params.size()) + (files == null ? 0 : files.size());
		Part[] parts = new Part[length];
		int i = 0;
		if (params != null){
			for (String name : params.keySet()) {
				parts[i++] = new StringPart(name, String.valueOf(params.get(name)), UTF_8);
			}
		}
		if (files != null){
			for (String file : files.keySet()) {
				try {
					parts[i++] = new FilePart(file, files.get(file));
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				}
			}
		}
		String responseBody = "";
		int time = 0;
		do {
			try {
				Log.i("acoe_music", "request server num-------->" + time);
				//获取httpClient对象
				httpClient = getHttpClient();
				//获取Post方式的Method对象
				httpPost = getHttpPost(url, true);
				httpPost.setRequestEntity(new MultipartRequestEntity(parts, httpPost.getParams()));
				//执行访问
				int statusCode = httpClient.executeMethod(httpPost);
				Log.i("acoe_music", "HttpStatusCode-------->" + statusCode);
				if (statusCode != HttpStatus.SC_OK) {
					//throw AppException.http(statusCode);
					break;
				} 
				responseBody = httpPost.getResponseBodyAsString();
				break;
			} catch (HttpException e) {
				time++;
				if (time < RETRY_TIME) {
					try {
						Thread.sleep(300);
					} catch (InterruptedException e1) {
					}
					continue;
				}
				// 发生致命的异常，可能是协议不对或者返回的内容有问题
				e.printStackTrace();
				//throw AppException.http(e);
				Log.i("acoe_music", "+++++++++++++++++++++++++++>HttpException");
			} catch (IOException e) {
				time++;
				if (time < RETRY_TIME) {
					try {
						Thread.sleep(300);
					} catch (InterruptedException e1) {
					}
					continue;
				}
				// 发生网络异常
				e.printStackTrace();
				Log.i("acoe_music", "+++++++++++++++++++++++++++>IOException");
				//throw AppException.network(e);
			} catch(Exception e){
				time++;
				e.printStackTrace();
				//throw AppException.run(e);
			} finally {
				// 释放连接
				httpPost.releaseConnection();
				httpClient = null;
			}
		} while (time < RETRY_TIME);
		return new ByteArrayInputStream(responseBody.getBytes());
	}
	
	

	/**
	 * 发送XML数据
	 * @param urlStr
	 * @param xml
	 * @return
	 */
	public static String sendXmlData(String urlStr, String xml){
		DataOutputStream out = null;
		HttpURLConnection connection = null;
		BufferedReader reader = null;
		try {
			URL postUrl = new URL(urlStr);
	        // 打开连接
	        connection = (HttpURLConnection) postUrl.openConnection();
	        connection.setDoOutput(true);
	        connection.setDoInput(true);
	        connection.setRequestMethod("POST");
	        connection.setUseCaches(false);
	        connection.setInstanceFollowRedirects(true);
	        connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
	        connection.connect();
	        out = new DataOutputStream(connection.getOutputStream());
	        out.write(xml.getBytes("utf-8")); 
	        reader = new BufferedReader(new InputStreamReader(connection.getInputStream(),"utf-8"));//设置编码,否则中文乱码
	        String line="";
	        StringBuilder sb = new StringBuilder();
	        while ((line = reader.readLine()) != null){
	        	sb.append(line);
	        }
            return sb.toString();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
        	e.printStackTrace();
        } finally {
        	try {
        		if(out != null){
        			out.flush();
        			out.close();
        		}
        		if(reader != null) reader.close();
        		if(connection != null) connection.disconnect();
			} catch (IOException e) {
				e.printStackTrace();
			}
        }
		return null;
	}
	
}