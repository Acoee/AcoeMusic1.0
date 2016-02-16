package com.app.music.frame.base;

import java.util.ArrayList;
import java.util.HashMap;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.FrameLayout.LayoutParams;


import cn.com.acoe.app.music.R;

import com.app.music.app.AppManager;
import com.app.music.manager.base.DataManagerCallBack;
import com.app.music.view.LoadingView;
import com.app.music.view.NodataImageView;

/**
 * FragmentActivity基类，不带标题
 * Created by Administrator on 2015/9/2.
 */
public class BaseFragmentActivity extends FragmentActivity implements DataManagerCallBack {
	/** 加载提示框 */
	private LoadingView loadingView;
	/** 无数据背景 */
	private NodataImageView imgNodata;
	/** 当前Activity名称 */
	public String activityName;
	/**主页面内容*/
	public HashMap<String, Fragment> fragments = new HashMap<String, Fragment>();
	public ArrayList<Boolean> isShowNodatas = new ArrayList<Boolean>(); //是否显示暂无数据背景
	public String nowFragment;
	
	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
	}
	
	protected void setContentView(int layoutResID, String activityName) {
		super.setContentView(layoutResID);
		setContentView(layoutResID);
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
		/** 将当前activity加入到app管理器中 **/
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
	 * 设置初始显示页面
	 * @param key
	 */
	public void setFirstFragment(String key){
		nowFragment = key;
		FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
		transaction.add(R.id.fragment_container, fragments.get(key)).commitAllowingStateLoss();
	}
	
	/**
	 * 切换Fragment
	 * @param key 要切换的Fragment类名
	 */
	public void changeFragment(String key){
		nowFragment = key;
		//切换Fragment时把加载框消失掉
		hideLoadingView();
		//获取FragmentManager管理类
		FragmentManager fragmentManager = getSupportFragmentManager();
		//通过获取默认的FragmentManager来获取Fragment的事务类
		FragmentTransaction transaction = fragmentManager.beginTransaction();
		//切换动画
		transaction.setCustomAnimations(R.anim.fragment_fading_in, R.anim.fragment_fading_out);
		//页面切换，并加载到中间主体内容中
		transaction.replace(R.id.fragment_container, fragments.get(key));
		//被切换的子页面添加的栈底，否则被替换的子页面将完全销毁，下次调用会重新绘制
		transaction.addToBackStack(null);
		//切换后需要事务提交才能生效
		transaction.commitAllowingStateLoss();
		/**
         * 在用FragmentTransaction.commit()方法提交FragmentTransaction对象后
         * 会在进程的主线程中，用异步的方式来执行。
         * 如果想要立即执行这个等待中的操作，就要调用这个方法（只能在主线程中调用）。
         * 要注意的是，所有的回调和相关的行为都会在这个调用中被执行完成，因此要仔细确认这个方法的调用位置。
         */
        fragmentManager.executePendingTransactions();
	}

	/**
	 * 设置顶部或者底部样式
	 * @param clickPos
	 */
	public void setNavStyle(int clickPos){
	}

	/**
	 * 显示加载框
	 * @return
	 */
	public void showLoadingView(String loadingText) {
		if(isFinishing()) return;
		if(this.loadingView == null){
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
	 * @return
	 */
	public void hideLoadingView() {
		if(isFinishing()) return;
		if(this.loadingView != null){
			this.loadingView.hide();
		}
	}
	
	/**
	 * 加载框是否显示
	 * @return
	 */
	public boolean isLoadingShowing(){
		if(this.loadingView != null && this.loadingView.getVisibility() == View.VISIBLE){
			return true;
		}
		return false;
	}
	
	
	/**
	 * 显示暂无数据背景
	 */
	public void showNodataImageBg(int pos){
		if(isFinishing()) return;
		if(this.imgNodata == null){
			this.imgNodata = new NodataImageView(getApplicationContext());
			LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
			params.gravity = Gravity.CENTER;
			addContentView(this.imgNodata, params);
		}
		this.imgNodata.show();
		this.isShowNodatas.set(pos, true);
	}
	
	/**
	 * 隐藏斩无数据背景
	 */
	public void hideNodataImageBg(int pos){
		if(isFinishing()) return;
		if(this.imgNodata != null){
			this.imgNodata.hide();
			this.isShowNodatas.set(pos, false);
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
		// TODO Auto-generated method stub
		
	}
}
