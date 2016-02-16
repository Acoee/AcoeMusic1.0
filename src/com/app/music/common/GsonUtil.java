package com.app.music.common;

import java.lang.reflect.Type;

import com.google.gson.Gson;

/**
 * Gson转换和解析json数据工具类
 * @author songqy
 * @version 1.0.0
 * @time 2015-05-05
 *
 */
public class GsonUtil {
	private static Gson gson = new Gson(); //Gson对象
	
	/**
	 * 将json字符串转换为对象(+1)
	 * @param json
	 * @param type
	 * @return
	 */
	public static <T> T getObjectFromJson(String json, Type type){
		if(SystemUtils.isEmpty(json)){
			return null;
		}
		return gson.fromJson(json, type);
	}
	
	/**
	 * 将json字符串转换为对象(+2)
	 * @param json
	 * @param c
	 * @return
	 */
	public static <T> T getObjectFromJson(String json, Class<T> c){
		if(SystemUtils.isEmpty(json)){
			return null;
		}
		return gson.fromJson(json, c);
	}
	
	/**
	 * 将对象转换成字符串(+1)
	 * @param t
	 * @return
	 */
	public static <T> String formatObjectToJson(T t){
		return gson.toJson(t);
	}
	
	/**
	 * 将对象转换成字符串(+2)
	 * @param t
	 * @return
	 */
	public static <T> String formatObjectToJson(T t, Type type){
		return gson.toJson(t, type);
	}
	
	
}
