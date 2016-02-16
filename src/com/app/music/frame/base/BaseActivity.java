package com.app.music.frame.base;

import android.app.Activity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.FrameLayout.LayoutParams;

import com.app.music.app.AppManager;
import com.app.music.common.SystemUtils;
import com.app.music.manager.base.AbstractDataManager;
import com.app.music.manager.base.DataManagerCallBack;
import com.app.music.view.LoadingView;
import com.app.music.view.NodataImageView;

public class BaseActivity extends Activity implements DataManagerCallBack {
	/** 当前Activity名称 */
	private String activityName;
	/** 加载提示框 */
	private LoadingView loadingView;
	/** 暂无数据背景 */
	private NodataImageView imgNodata;
	/** 当前Activity是否可见 */
	public boolean isVisible;
	
	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
	}
	
	protected void setContentView(int layoutResID, String activityName) {
		setContentView(layoutResID);
		this.activityName = activityName;
	}
	
	protected void setContentView(View view, String activityName) {
		setContentView(view);
		this.activityName = activityName;
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}
	
	@Override
	public void setContentView(int layoutResID) {
		super.setContentView(layoutResID);
		getWindow().setBackgroundDrawable(null);
		/** 将当前Activity加入到app管理器中 */
		AppManager.getAppManager().addActivity(this);
		// 初始化界面
		initUI();
	}

	@Override
	protected void onStart() {
		super.onStart();
		isVisible = true;
	}

	@Override
	protected void onStop() {
		super.onStop();
		isVisible = false;
	}

	@Override
	public void setContentView(View view) {
		super.setContentView(view);
		getWindow().setBackgroundDrawable(null);
		/** 将当前Activity加入到app管理器中 */
		AppManager.getAppManager().addActivity(this);
		// 初始化界面
		initUI();
	}

	/**
	 * 初始化界面
	 */
	private void initUI() {
	}
	
	/**
	 * 显示加载框
	 * @param loadingText
	 */
	protected void showLoadingView(String loadingText) {
		if (isFinishing()) return;
		if (this.loadingView == null) {
			this.loadingView = new LoadingView(getApplicationContext());
			LayoutParams params = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
			params.gravity = Gravity.CENTER;
			this.addContentView(this.loadingView, params);
		}
		this.loadingView.setLoadText(loadingText);
		this.loadingView.show();
	}
	
	/**
	 * 隐藏加载框
	 */
	protected void hideLoadingView() {
		if (isFinishing()) return;
		if (this.loadingView != null) {
			this.loadingView.hide();
		}
	}
	
	/**
	 * 加载框显示状态
	 * @return
	 */
	protected boolean isLoadingShowing() {
		if (this.loadingView != null && this.loadingView.getVisibility() == View.VISIBLE) {
			return true;
		}
		return false;
	}
	
	/**
	 * 显示暂无数据背景
	 */
	public void showNodataImageBg(){
		if(isFinishing()) return;
		if(this.imgNodata == null){
			this.imgNodata = new NodataImageView(getApplicationContext());
			LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
			params.gravity = Gravity.CENTER;
			addContentView(this.imgNodata, params);
		}
		this.imgNodata.show();
	}
	
	/**
	 * 隐藏斩无数据背景
	 */
	public void hideNodataImageBg(){
		if(isFinishing()) return;
		if(this.imgNodata != null){
			this.imgNodata.hide();
		}
	}
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			if (loadingView != null && loadingView.getVisibility() == View.VISIBLE) {
				loadingView.hide();
				return true;
			}
			AppManager.getAppManager().finishActivity(this);
		}
		return super.onKeyDown(keyCode, event);
	}
	
	public void finishActvity() {
		AppManager.getAppManager().finishActivity(this);
	}
	
	@Override
	public void onBack(int what, int arg1, int arg2, Object obj) {
		switch (what) {
		case AbstractDataManager.USER_UNLOGIN: //用户未登录
			if(!isFinishing()) SystemUtils.showUnloginDialog(this);
			break;
		}
	}

}
