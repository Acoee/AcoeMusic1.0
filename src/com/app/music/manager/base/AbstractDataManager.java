package com.app.music.manager.base;

import android.os.Handler;
import android.os.Message;

import com.app.music.common.SystemUtils;
import com.app.music.dao.BaseDao;

import java.lang.ref.SoftReference;

/**
 * 网络访问抽象类，所有的网络访问接口都必须继承该类
 * @author zhuyb
 * @date 2015-8-20
 * @version V1.0.0
 */
public abstract class AbstractDataManager extends Handler {
	public static String TAG = "AbstractDataManager";
	
	public final static int USER_UNLOGIN = 10000; // 用户未登录
	/**
	 * 返回提示信息
	 * @param resutlMsg
	 * @return
	 */
	public static String getToastMsg(String resutlMsg){
		if(SystemUtils.isEmpty(resutlMsg)){
			resutlMsg = BaseDao.REQUEST_FAILURE_MSG;
		}
		return resutlMsg;
	}

	/**
	 * 数据管理用的Listener<br/>
	 * 可在此处理数据
	 */
	protected class DataManagerListener implements NetSourceListener {
		public DataManagerListener() {
		}

		@Override
		public void sendMessage(int resultCode, int what, Object data, String toast) {
			sendMessage(resultCode, what, NetSourceListener.ISNOT_CACHE_LOAD, data, toast);
		}

		@Override
		public final void sendMessage(int resultCode, int what, int arg1, Object data, String toast) {
			sendMessage(resultCode, what, arg1, 0, data, toast);
		}

		@Override
		public final void sendMessage(int resultCode, int what, int arg1, int arg2, Object data, String toast) {
			Message msg = null;
			switch (resultCode) {
				case WHAT_NOT_LOGIN:
					msg = onSessionOutDate(what);
					break;
				case WHAT_MAIL_LOGIN_ERROR:
					msg = onSessionOutDate(what);
					break;
				case WHAT_SUCCESS:
					msg = onSuccess(what, data);
					break;
				case WHAT_ERROR:
					msg = onFailed(what, toast);
					break;
			}
			if (msg != null) {
				// 防止请求响应太快 ,对话框无法消失
				AbstractDataManager.this.sendMessageDelayed(msg, 200);
			}
		}

		/**
		 * Session 过期
		 */
		protected Message onSessionOutDate(int what) {
			return Message.obtain(AbstractDataManager.this, what);
		}

		/**
		 * 响应的具体处理过程,需子类实现<br/>
		 * @param what
		 * @param data
		 */
		protected Message onSuccess(int what, Object data) {
			return Message.obtain(AbstractDataManager.this, what, data);
		}

		/**
		 * 获取数据失败, 若需要可 Override
		 */
		protected Message onFailed(int what, String toast) {
			return Message.obtain(AbstractDataManager.this, what, toast);
		}
	}

	private SoftReference<DataManagerCallBack> callback = null; //回调使用软引用保存

	/**
	 * 有对话框时必须使用此构造函数
	 *
	 * @param callback
	 */
	public AbstractDataManager(DataManagerCallBack callback) {
		this.callback = new SoftReference<DataManagerCallBack>(callback);
	}

	/**
	 * 最终Handler消息处理
	 */
	@Override
	public final void handleMessage(Message msg) {
		handleMessageOnUIThread(msg.what, msg.arg1, msg.arg2, msg.obj);
		DataManagerCallBack callback = getCallBack();
		if (callback != null) { //回调函数处理
			callback.onBack(msg.what, msg.arg1, msg.arg2, msg.obj);
		}
	}

	/**
	 * 处理返回结果<br/>
	 * 不要做耗时操作
	 *
	 * @param what
	 * @param arg1
	 * @param arg2
	 * @param obj
	 */
	protected void handleMessageOnUIThread(int what, int arg1, int arg2, Object obj) {

	}

	/**
	 * 获取Callback 实例
	 *
	 * @return
	 */
	private DataManagerCallBack getCallBack() {
		return callback == null ? null : callback.get();
	}
}
