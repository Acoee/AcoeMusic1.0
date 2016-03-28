package com.app.music.app;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.lang.Thread.UncaughtExceptionHandler;
import java.net.ConnectException;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Date;

import com.app.music.common.mail.MailSenderInfo;
import com.app.music.common.mail.SimpleMailSender;
import com.app.music.ui.start.StartActivity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Environment;
import android.os.Looper;


public class AppException extends Exception implements UncaughtExceptionHandler {
	private final static boolean Debug = false; // 是否保存错误日志
	
	/** 定义异常类型 */
	public final static byte TYPE_NETWORK 	= 0x01;	//网络连接失败
	public final static byte TYPE_SOCKET	= 0x02;	//网络异常，读取数据超时
	public final static byte TYPE_HTTP_CODE	= 0x03;	//网络异常，错误码
	public final static byte TYPE_HTTP_ERROR= 0x04;	//网络异常，请求超时
	public final static byte TYPE_XML	 	= 0x05;	//数据解析异常
	public final static byte TYPE_IO	 	= 0x06;	//文件流异常
	public final static byte TYPE_RUN	 	= 0x07;	//应用程序运行时异常
	
	private byte type; //保存当前错误类型
	private int code;  //错误码
	
	/** 系统默认的UncaughtException处理类 */
	private Thread.UncaughtExceptionHandler mDefaultHandler;
	
	private AppException(){
		this.mDefaultHandler = Thread.getDefaultUncaughtExceptionHandler();
	}
	
	/**
	 * 自定义异常类构造函数
	 * @param type 异常类型
	 * @param code 错误码
	 * @param excp 具体异常
	 */
	private AppException(byte type, int code, Exception excp) {
		super(excp);
		excp.printStackTrace();

		this.type = type;
		this.code = code;		
		if(Debug){
			this.saveErrorLog(excp);
		}
	}
	
	public int getCode() {
		return this.code;
	}
	
	public int getType() {
		return this.type;
	}
	
	/**
	 * 保存异常日志
	 * @param excp
	 */
	@SuppressWarnings("deprecation")
	public void saveErrorLog(Exception excp) {
		String errorlog = "errorlog.txt";
		String savePath = "";
		String logFilePath = "";
		FileWriter fw = null;
		PrintWriter pw = null;
		try {
			//判断是否挂载了SD卡
			String storageState = Environment.getExternalStorageState();		
			if(storageState.equals(Environment.MEDIA_MOUNTED)){
				savePath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/wqk/Log/";
				File file = new File(savePath);
				if(!file.exists()){
					file.mkdirs();
				}
				logFilePath = savePath + errorlog;
			}
			//没有挂载SD卡，无法写文件
			if(logFilePath == ""){
				return;
			}
			File logFile = new File(logFilePath);
			if (!logFile.exists()) {
				logFile.createNewFile();
			}
			fw = new FileWriter(logFile,true);
			pw = new PrintWriter(fw);
			pw.println("--------------------"+(new Date().toLocaleString())+"---------------------");	
			excp.printStackTrace(pw);
			pw.close();
			fw.close();
		} catch (Exception e) {
			e.printStackTrace();		
		}finally{ 
			if(pw != null){ pw.close(); } 
			if(fw != null){ try { fw.close(); } catch (IOException e) { }}
		}

	}
	
	public static AppException http(int code) {
		return new AppException(TYPE_HTTP_CODE, code, null);
	}
	
	public static AppException http(Exception e) {
		return new AppException(TYPE_HTTP_ERROR, 0 ,e);
	}

	public static AppException socket(Exception e) {
		return new AppException(TYPE_SOCKET, 0 ,e);
	}
	
	public static AppException io(Exception e) {
		if(e instanceof UnknownHostException || e instanceof ConnectException){
			return new AppException(TYPE_NETWORK, 0, e);
		}else if(e instanceof IOException){
			return new AppException(TYPE_IO, 0 ,e);
		}
		return run(e);
	}
	
	public static AppException xml(Exception e) {
		return new AppException(TYPE_XML, 0, e);
	}
	
	public static AppException network(Exception e) {
		if(e instanceof UnknownHostException || e instanceof ConnectException){
			return new AppException(TYPE_NETWORK, 0, e);
		}
		/*else if(e instanceof HttpException){
			return http(e);
		}*/
		else if(e instanceof SocketException){
			return socket(e);
		}
		return http(e);
	}
	
	public static AppException run(Exception e) {
		return new AppException(TYPE_RUN, 0, e);
	}

	/**
	 * 获取APP异常崩溃处理对象
	 * @return
	 */
	public static AppException getAppExceptionHandler(){
		return new AppException();
	}
	
	@Override
	public void uncaughtException(Thread thread, Throwable ex) {
		if(!handleException(ex) && mDefaultHandler != null) {
			mDefaultHandler.uncaughtException(thread, ex);
		}
	}
	
	/**
	 * 自定义异常处理:收集错误信息&发送错误报告
	 * @param ex 
	 * @return true:处理了该异常信息;否则返回false
	 */
	private boolean handleException(Throwable ex) {
		if(ex == null) {
			return false;
		}
		context = AppManager.getAppManager().currentActivity();
		
		if(context == null || context.isFinishing()) {
			existStart();
			return false;
		}
		crashReport = getCrashReport(context, ex); //获取异常报告
		//显示异常信息&发送报告
		new Thread() {
			public void run() {
				Looper.prepare();
				//不管异常与否，都提交报告
				MailSenderInfo info = new MailSenderInfo();
				info.subject = "APP错误报告";
				info.content = crashReport;
				SimpleMailSender sms = new SimpleMailSender();
				sms.sendTextMail(info);
				//重启一应用
				existStart();
				
				Looper.loop();
			}
		}.start();
		return true;
	}
	private Activity context;
	private String crashReport;
	
	/**
	 * 退出程序后重启 
	 */
	private void existStart(){
		Intent intent = new Intent(context.getApplicationContext(), StartActivity.class);  
        PendingIntent restartIntent = PendingIntent.getActivity(context.getApplicationContext(), 
        		0, intent, Intent.FLAG_ACTIVITY_NEW_TASK);                                                 
        //退出程序后重启                                          
        AlarmManager mgr = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);    
        mgr.set(AlarmManager.RTC, System.currentTimeMillis() + 100, restartIntent); // 0.1秒钟后重启应用   
		//退出应用
		AppManager.getAppManager().AppExit();
	}
	
	/**
	 * 获取APP崩溃异常报告
	 * @param ex
	 * @return
	 */
	@SuppressLint("NewApi")
	private String getCrashReport(Activity context, Throwable ex) {
		StringBuffer exceptionStr = new StringBuffer();
		/*try {
			ApplicationInfo info = context.getApplicationContext().getPackageManager().getApplicationInfo(context.getApplicationContext().getPackageName(), PackageManager.GET_META_DATA);
			String user_source = info.metaData.get("UMENG_CHANNEL").toString();
			if(SystemUtils.isEmpty(user_source)){
				user_source = "maisulang";
			}
			exceptionStr.append("Chinal: "+user_source+"\n");
		} catch (NameNotFoundException e) {
			e.printStackTrace();
		}*/
		exceptionStr.append("Version: "+ClientInfo.getInstance().versionName+"("+ClientInfo.getInstance().versionCode+")\n");
		exceptionStr.append("Android: "+android.os.Build.VERSION.RELEASE+"("+android.os.Build.MODEL+")\n");
		exceptionStr.append("Activity: "+context.getClass().getName()+"\n");
		/*exceptionStr.append("Exception: "+ex.getMessage()+"\n");
		StackTraceElement[] elements = ex.getStackTrace();
		for (int i = 0; i < elements.length; i++) {
			exceptionStr.append(elements[i].toString()+"\n");
		}*/
		Writer writer = new StringWriter();
		PrintWriter pw = new PrintWriter(writer);
		ex.printStackTrace(pw);
		pw.close();
		String error= writer.toString();
		exceptionStr.append("Exception: "+error+"\n");
		return exceptionStr.toString();
	}
}
