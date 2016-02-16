package com.app.music.dao;

import android.util.Log;

import java.io.File;
import java.util.HashMap;

import com.app.music.app.AppContext;
import com.app.music.common.SystemUtils;
import com.app.music.common.URLs;
import com.app.music.common.http.BusinessResult;
import com.app.music.common.http.TecentMusicResult;
import com.app.music.manager.base.NetSourceListener;
import com.app.music.server.ApiClient;

public class BaseDao {
	/** 网络访问方式，true:httpclient;false:webservice **/
	public static final boolean isCallByHttpClient = true;
	/** 公共参数 */
	public static final String RESPONSE_TYPE = "json";
	/**请求超时或者失败*/
	public static final String REQUEST_FAILURE_MSG = "请求响应失败 , 请重试！";
	
	/**
	 * 访问网络接口
	 * @param method
	 * @param params
	 * @param files
	 * @return
	 * @throws Exception
	 */
	public static String callWebMethod(String method, HashMap<String, Object> params, HashMap<String, File> files) throws Exception {
		//拼接访问url
		String requestUrl = (method.startsWith("http:")?"":URLs.HOST) + method;
		// 加上公共的参数
		if (params == null) {
			params = new HashMap<String, Object>();
		}
		Log.d("music", "访问的URL=========>" + requestUrl);
		Log.d("music", "访问的参数=========>" + params);
		String result = null;
		if (isCallByHttpClient) { // 用httpclient方式
			try {
				// 访问
				result = ApiClient.convertStreamToString(ApiClient.doPost(requestUrl, params, files));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		Log.i("music", "返回的URL=========>" + requestUrl);
		Log.i("music", "返回的结果=========>" + result);
		return result;
	}

	/**
	 * 返回公共结果和处理公共参数
	 * @param result
	 * @return
	 */
	public static <T> BusinessResult<T> getResult(BusinessResult<T> result){
		if(result == null){
			result = new BusinessResult<T>();
			result.success = false;
			result.message = REQUEST_FAILURE_MSG;
		}else if(result.sysTime > 0){
			AppContext.sysTime = result.sysTime; //保存服务器系统时间
		}
		return result;
	}

	/**
	 * Tecent音乐榜单返回公共结果和处理公共参数
	 * @param result
	 * @return
	 */
	public static <T> TecentMusicResult<T> getResult(TecentMusicResult<T> result){
		if(result == null){
			result = new TecentMusicResult<T>();
			result.success = false;
			result.message = REQUEST_FAILURE_MSG;
		}else if(result.sysTime > 0){
			AppContext.sysTime = result.sysTime; //保存服务器系统时间
		} else {
			result.success = true;
			result.message = "SUCCESS";
			result.sysTime = System.currentTimeMillis();
		}
		return result;
	}
}
