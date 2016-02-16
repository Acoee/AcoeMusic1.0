package com.app.music.manager.base;

/**
 * 网络访问回调接口
 * @author Acoe
 * @date 2015-8-20
 * @version V1.0.0
 */
public interface DataManagerCallBack {
	/**
	 * 网络访问后回调函数
	 * @param what
	 * @param arg1
	 * @param arg2
	 * @param obj
	 */
	public void onBack(int what, int arg1, int arg2, Object obj);
}
