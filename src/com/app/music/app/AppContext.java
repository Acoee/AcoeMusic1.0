package com.app.music.app;

import com.app.music.db.base.AssistantDatabaseHelper;
import com.app.music.entity.Mp3Bean;

import android.app.Application;
import java.util.ArrayList;

public class AppContext extends Application {
	public static AppContext appContext;
	public static boolean isLogined;
	public static ArrayList<Mp3Bean> datas;
	
	public static long sysTime; //服务器当前系统时间
	public static long getSysTime(){
		if(sysTime <= 0){
			return System.currentTimeMillis();
		}else{
			return sysTime;
		}
	}

	@Override
	public void onCreate() {
		super.onCreate();
		appContext = this;
		
		// SQLite数据库初始化
		AssistantDatabaseHelper.initSQLiteDatabase();
	}
}
