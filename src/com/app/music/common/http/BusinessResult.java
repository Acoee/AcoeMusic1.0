package com.app.music.common.http;

import java.util.ArrayList;
import java.util.HashMap;

/**   
 * 接口访问返回结果类
 * @author songqy  
 * @date 2016-1-4 下午1:29:29 
 * @version V2.1.0 
 */
public class BusinessResult<T> {

	public String message; //提示信息，APP端或WX端直接展示给用户
	public boolean success; //结果标识，true或false
	public long sysTime; //服务器时间
	public T one; //查询详情数据
	public ArrayList<T> list; //查询列表数据
	public int code; //结果码 0成功  4失败 8888用户未登录
	public HashMap<String, Object> map; //扩展的其它结果
	
}