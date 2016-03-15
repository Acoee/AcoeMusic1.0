package com.app.music.ui.base;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.app.music.common.SystemUtils;
import com.app.music.manager.base.AbstractDataManager;
import com.app.music.manager.base.DataManagerCallBack;

/**
 * Fragment基类
 * @author Acoe
 * @date 2015-8-20
 * @version V1.0.0
 */
public class BaseFragment extends Fragment implements DataManagerCallBack {
	protected BaseFragmentActivity context; //当前上下文对象
	public View nowView; // 缓存页面
	/** 当前Fragment名称 */
	private String fragmentName;

	/**
	 * 创建界面
	 * @param layoutResID 当前布局ID
	 * @param fragmentName
	 * @param inflater
	 * @param container
	 * @param savedInstanceState
	 * @return
	 */
	public View onCreateView(int layoutResID, String fragmentName, LayoutInflater inflater,
			ViewGroup container, Bundle savedInstanceState) {
		this.fragmentName = fragmentName;
		if(nowView == null) { //反射当前页面布局
			nowView = inflater.inflate(layoutResID, container, false);
		}
		//查看本页面view的父view是否存在，若存在，则移除父容器的所有布局
		ViewGroup p = (ViewGroup) nowView.getParent();
		if(p != null) p.removeAllViewsInLayout();
		
		return nowView; 
	}
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);

		this.context = (BaseFragmentActivity) getActivity();
	}
	
	@Override
	public void onBack(int what, int arg1, int arg2, Object obj) {
		switch (what) {
		case AbstractDataManager.USER_UNLOGIN: //用户未登录
			if(!context.isFinishing()) SystemUtils.showUnloginDialog(context);
			break;
		}
	}
	
	@Override
	public void onResume() {
		super.onResume();
	}
	
	@Override
	public void onPause() {
		super.onPause();
	}

}
