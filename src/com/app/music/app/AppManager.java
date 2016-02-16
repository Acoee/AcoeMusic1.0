package com.app.music.app;

import java.util.Stack;

import com.app.music.db.base.AssistantDatabaseHelper;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;

/**
 * 应用程序Activity管理类，用于Activity管理和应用程序退出
 * @author Acoe
 * @date 2015-8-20
 * @version V1.0.0
 */
public class AppManager {
	private static Stack<Activity> activityStack; // 保存Activity的栈
	private static AppManager instance; // 当前类的实例
	
	private AppManager() {
	}
	
	/**
	 * 返回管理类的实例（单例模式）
	 * @return
	 */
	public static AppManager getAppManager() {
		if (instance == null) {
			instance = new AppManager();
		}
		return instance;
	}
	
	/**
	 * 添加Activity到堆栈
	 * @param activity
	 */
	public void addActivity(Activity activity) {
		if (activityStack == null) {
			activityStack = new Stack<Activity>();
		}
		activityStack.add(activity);
	}
	
	/**
	 * 获取当前Activity（堆栈中最后一个存入的）
	 * @return
	 */
	public Activity currentActivity() {
		Activity activity = activityStack.lastElement();
		return activity;
	}
	
	/**
	 * 结束当前Activity（堆栈中最后一个存入的）
	 */
	public void finishActivity() {
		Activity activity = activityStack.lastElement();
		finishActivity(activity);
	}
	
	/**
	 * 结束指定的Activity
	 * @param activity
	 */
	public void finishActivity(Activity activity) {
		if (activity != null) {
			activityStack.remove(activity);
			activity.finish();
			activity = null;
		}
	}
	
	/**
	 * 结束指定类名的Activity
	 * @param cls
	 */
	public void finishActivity(Class<?> cls) {
		for (Activity activity : activityStack) {
			if (activity.getClass().equals(cls)) {
				finishActivity(activity);
			}
		}
	}
	
	/**
	 * 返回指定类名的Activity
	 * @param cls
	 * @return
	 */
	public Activity getActivity(Class<?> cls) {
		for (Activity activity : activityStack) {
			if (activity.getClass().equals(cls)) {
				return activity;
			}
		}
		return null;
	}
	
	/**
	 * 结束所有的Activity
	 */
	public void finishAllActivity() {
		for (Activity activity : activityStack) {
			if (activity != null) {
				activity.finish();
			}
		}
		activityStack.clear();
	}
	
	/**
	 * 退出应用程序
	 */
	@SuppressWarnings("deprecation")
	public void AppExit() {
		try {
			AssistantDatabaseHelper.destoryInstance();
			finishAllActivity();
			ActivityManager activityMgr = (ActivityManager) AppContext.appContext.getSystemService(Context.ACTIVITY_SERVICE);
			if( android.os.Build.VERSION.SDK_INT < 8){
				// 需要权限android.permission.RESTART_PACKAGES
				activityMgr.restartPackage(AppContext.appContext.getPackageName());
			}else{
				// 必须在androidmanifest.xml文件中加入KILL_BACKGROUND_PROCESSES这个权限
				activityMgr.killBackgroundProcesses(AppContext.appContext.getPackageName());
			}
			System.exit(0);
		} catch (Exception e) {
		}
	}
}
