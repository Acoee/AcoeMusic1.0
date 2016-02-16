package com.app.music.app;

import com.app.music.common.SystemUtils;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.net.ConnectivityManager;
import android.net.NetworkInfo.State;
import android.provider.Settings;

public class ClientInfo {
	private static ClientInfo instance;

	public int mNetWorkState; // 当前网络状态

	public String userAgent; //当前手机客户端信息

	/** 系统版本号 */
	public int versionCode;
	/** 系统版本名 */
	public String versionName;

	/** android设备ID(android_id + imei) **/
	public String android_id;

	public String terminalCode;

	public static final int NETWORN_NONE = 0; // 无网络
	public static final int NETWORN_WIFI = 1; // wifi网络
	public static final int NETWORN_MOBILE = 2; // 3G网络

	public static ClientInfo getInstance() {
		if (instance == null) {
			instance = new ClientInfo();
			instance.init();
		}
		return instance;
	}

	public void init() {
		Context context = AppContext.appContext;
		// 获取android_id
		android_id = "Android_" + Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID)
				+ "_" + SystemUtils.getIMEI(context);
		setVersionAndName(context);
		setUserAgent(context);
		getNetworkState();
	}

	/**
	 * 获取用户手机标识信息和应用的版本信息
	 * @param context
	 * @return
	 */
	private void setUserAgent(Context context) {
		StringBuilder ua = new StringBuilder("WQK.NET");
		ua.append('/' + versionName + '_' + versionCode);// App版本
		ua.append("/Android");// 手机系统平台
		ua.append("/" + android.os.Build.VERSION.RELEASE);// 手机系统版本
		ua.append("/" + android.os.Build.MODEL); // 手机型号
		ua.append("/" + android_id);// 客户端唯一标识
		userAgent = ua.toString();
	}

	/**
	 * 获取App安装包信息
	 * 
	 * @return
	 */
	private void setVersionAndName(Context context) {
		PackageInfo info = null;
		try {
			info = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
			versionName = info.versionName;
			versionCode = info.versionCode;
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 判断当前网络状态
	 * 
	 * @param context
	 * @return 0：无网络 1：wifi网络 2：3G网络
	 */
	public int getNetworkState() {
		try {
			ConnectivityManager connManager = (ConnectivityManager) AppContext.appContext
					.getSystemService(Context.CONNECTIVITY_SERVICE);
			// Wifi
			State state = connManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState();
			if (state == State.CONNECTED || state == State.CONNECTING) {
				mNetWorkState = NETWORN_WIFI;
				return NETWORN_WIFI;
			}
			// 3G
			state = connManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState();
			if (state == State.CONNECTED || state == State.CONNECTING) {
				mNetWorkState = NETWORN_MOBILE;
				return NETWORN_MOBILE;
			}
			mNetWorkState = NETWORN_NONE;
			return NETWORN_NONE;
		} catch (Exception e) {
			mNetWorkState = NETWORN_NONE;
			return NETWORN_NONE;
		}
	}

}
