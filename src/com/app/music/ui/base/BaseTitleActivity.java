package com.app.music.ui.base;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import com.app.music.app.AppManager;

import cn.com.acoe.app.music.R;

/**
 * 带Title的Activity基类
 * @author Acoe
 * @date 2015-8-20
 * @version V1.0.0
 */
public class BaseTitleActivity extends BaseActivity {
	private TextView txtTitle;
	private Button btnBack, btnRight;
	
	@Override
	protected void onSaveInstanceState(Bundle outState) {
		//TODO 要保存的值
		
		super.onSaveInstanceState(outState);
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
	}

	@Override
	public void setContentView(int layoutResID, String activityName) {
		super.setContentView(layoutResID, activityName);
		
		initUI(); // 初始化界面
	}

	private void initUI() {
		this.txtTitle = (TextView) findViewById(R.id.title_textview);
		this.btnBack = (Button) findViewById(R.id.title_back_button);
		this.btnRight = (Button) findViewById(R.id.title_right_button);
		this.btnBack.setOnClickListener(backClickListener);
	}

	/**
	 * 设置标题文字
	 * @param title
	 */
	protected void setTitleText(String title) {
		this.txtTitle.setText(title);
	}

	protected void setTitleText(int resid) {
		this.txtTitle.setText(resid);
	}

	/**
	 * 设置右边按钮可见性
	 * @param visiblity
	 */
	protected void setRightButtonVisible(int visiblity) {
		this.btnRight.setVisibility(visiblity);
	}

	// 返回按钮点击事件
	private OnClickListener backClickListener = new OnClickListener() {
		@Override
		public void onClick(View v) {
			AppManager.getAppManager().finishActivity(BaseTitleActivity.this);
		}
	};
}
