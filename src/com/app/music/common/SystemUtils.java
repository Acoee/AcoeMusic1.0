package com.app.music.common;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Enumeration;
import java.util.regex.Pattern;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.telephony.TelephonyManager;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import com.app.music.app.AppContext;

import cn.com.acoe.app.music.R;

public class SystemUtils {

	private final static Pattern letterPattern = Pattern.compile("^[a-zA-Z]+$");
	
	/**
	 * 获取手机IMEI号
	 * @param context
	 * @return
	 */
	public static String getIMEI(Context context) {
	    return ((TelephonyManager)context.getSystemService(Context.TELEPHONY_SERVICE)).getDeviceId();
	}
	
	/**
	 * 获取手机当前IP地址
	 * @param context
	 * @return
	 */
	public static String getIP(Context context){
        try {
        	//WIFI
        	WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE); 
        	if(wifiManager.isWifiEnabled()){
        		WifiInfo wifiInfo = wifiManager.getConnectionInfo(); 
        		int ipAddress = wifiInfo.getIpAddress(); 
        		Log.i("maisulang", "Wifi_IP----------->" + intToIp(ipAddress));
        		return intToIp(ipAddress); 
        	}
        	//GPRS
			for (Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces(); en.hasMoreElements();){  
				NetworkInterface intf = en.nextElement();  
				for(Enumeration<InetAddress> enumIpAddr = intf.getInetAddresses(); enumIpAddr.hasMoreElements();){  
					InetAddress inetAddress = enumIpAddr.nextElement();  
					if (!inetAddress.isLoopbackAddress()){  
						Log.i("maisulang", "GPRS_IP----------->" + inetAddress.getHostAddress().toString());
						return inetAddress.getHostAddress().toString();  
					}  
				}  
	         }
		} catch (SocketException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * ip转化
	 * @param i
	 * @return
	 */
	public static String intToIp(int i) { 
		return ( i & 0xFF) + "." + ((i >> 8 ) & 0xFF) + "." + ((i >> 16 ) & 0xFF) + "." + ((i >> 24 ) & 0xFF );
	}
	
	/**
	 * 未登录对话框
	 * @param context
	 * @return
	 */
	public static AlertDialog showUnloginDialog(final Activity context){
		View alertView = new View(context);
		final AlertDialog dialog = showDialog(context, alertView);
		return dialog;
	}
	
	/**
	 * 创建自定义弹出对话框
	 * @param context 当前上下文对象
	 * @param alertView 自定义布局
	 * @return
	 */
	public static AlertDialog showDialog(Activity context, View alertView){
		AlertDialog dialog = new AlertDialog.Builder(context).create();
		dialog.show();
		dialog.getWindow().setContentView(alertView);
		//设置显示宽高
		WindowManager.LayoutParams params = dialog.getWindow().getAttributes();    
		params.width = getPhoneWidth(context) * 5 / 6;  //屏幕的6/7
		dialog.getWindow().setAttributes(params);
		dialog.setCanceledOnTouchOutside(true);
		dialog.setCancelable(true);
		dialog.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM); //解决不弹出软键盘
		return dialog;
	}
	
	/**
	 * 获取屏幕宽度
	 * @param context
	 * @return
	 */
	public static int getPhoneWidth(Context context){
		WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
		DisplayMetrics dm = new DisplayMetrics();
		wm.getDefaultDisplay().getMetrics(dm);
		return dm.widthPixels;
	}

	/**
	 * 获取屏幕宽度
	 * @param activity
	 * @return
	 */
	public static int getPhoneWidth(Activity activity) {
		DisplayMetrics dm = new DisplayMetrics();
		activity.getWindowManager().getDefaultDisplay().getMetrics(dm);
		return dm.widthPixels;// 屏幕宽度
	}
	
	/**
	 * 获取屏幕高度
	 * @param context
	 * @return
	 */
	public static int getPhoneHeight(Context context) {
		WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
		DisplayMetrics dm = new DisplayMetrics();
		wm.getDefaultDisplay().getMetrics(dm);
		return dm.heightPixels;
	}

	/**
	 * 获取屏幕高度
	 * @param activity
	 * @return
	 */
	public static int getPhoneHeight(Activity activity) {
		DisplayMetrics dm = new DisplayMetrics();
		activity.getWindowManager().getDefaultDisplay().getMetrics(dm);
		return dm.heightPixels;// 屏幕高度
	}

	/**
	 * 毫秒转化方法，将毫秒转化为时间格式的文本字符串
	 * 用于显示媒体文件时长，其他需要时间显示的场合等
	 * @param millisecond 毫秒
	 * @return 小时数:分钟数:秒数
	 */
	public static String MillisecondToString(int millisecond) {
		String time;
		int hours = millisecond / 1000 / 3600;
		int minutes = millisecond / 1000 % 3600 / 60;
		int seconds = millisecond / 1000 % 60;

		if (hours == 0 ) {
			time = "";
		} else if (hours < 10) {
			time = "0" + hours + ":";
		} else {
			time = "" + hours + ":";
		}

		if (minutes == 0) {
			time = time + "00:";
		} else if (minutes < 10) {
			time = time + "0" + minutes + ":";
		} else {
			time = time + minutes + ":";
		}

		if (seconds == 0) {
			time = time + "00";
		} else if (seconds <10) {
			time = time + "0" + seconds;
		} else {
			time = time + seconds;
		}
		return time;
	}

	/**
	 * 判断给定字符串是否空白串。 空白串是指由空格、制表符、回车符、换行符组成的字符串 若输入字符串为null或空字符串，返回true
	 * @param input 要判断的字符串
	 * @return boolean 空串：true 非空串：false
	 */
	public static boolean isEmpty(String input) {
		if (input == null || "".equals(input))
			return true;

		for (int i = 0; i < input.length(); i++) {
			char c = input.charAt(i);
			if (c != ' ' && c != '\t' && c != '\r' && c != '\n') {
				return false;
			}
		}
		return true;
	}

	/**
	 * 自定义Toast显示
	 * @param msg 提示的内容
	 * @param isLongShow 是否长时间显示
	 */
	public static void ToastShow(String msg, boolean isLongShow){
		Toast toast = null;
		if(isLongShow)
			toast = Toast.makeText(AppContext.appContext, msg, Toast.LENGTH_LONG);
		else
			toast = Toast.makeText(AppContext.appContext, msg, Toast.LENGTH_SHORT);

		TextView toastView = (TextView) LayoutInflater.from(AppContext.appContext).inflate(R.layout.toast_view, null);
		toastView.setText(msg);
		toast.setView(toastView);
		//toast.setGravity(Gravity.CENTER, 0, 0);
		toast.show();
	}

	/**
	 * 自定义Toast显示
	 * @param msg 提示的内容
	 * @param isLongShow 是否长时间显示
	 * @param isVisible 是否可以显示
	 */
	public static void ToastShow(String msg, boolean isLongShow, boolean isVisible){
		if (!isVisible) return;
		Toast toast = null;
		if(isLongShow)
			toast = Toast.makeText(AppContext.appContext, msg, Toast.LENGTH_LONG);
		else
			toast = Toast.makeText(AppContext.appContext, msg, Toast.LENGTH_SHORT);

		TextView toastView = (TextView) LayoutInflater.from(AppContext.appContext).inflate(R.layout.toast_view, null);
		toastView.setText(msg);
		toast.setView(toastView);
		//toast.setGravity(Gravity.CENTER, 0, 0);
		toast.show();
	}

	/**
	 * 判断是否是字母
	 * @param lettern
	 * @return
	 */
	public static boolean isLetternRight(String lettern){
		if(isEmpty(lettern))
			return false;
		return letterPattern.matcher(lettern).matches();
	}

	/**
	 * 命名文件名(yyyyMMddHHmmss+4位随机字母数字)
	 */
	public static String file_name() {
		String systime;
		Calendar c = Calendar.getInstance();
		SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
		Date date = c.getTime();
		systime = df.format(date);
		return systime + RandomUtil.getRandomStringByLength(4);
	}
}
