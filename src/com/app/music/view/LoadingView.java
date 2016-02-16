package com.app.music.view;

import cn.com.acoe.app.music.R;
import android.content.Context;
import android.graphics.Color;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

public class LoadingView extends LinearLayout{
	private TextView txtContent;
	private ProgressBar progressBar;
	
	public LoadingView(Context context) {
		super(context);
		// 初始化Text
		txtContent = new TextView(context);
		txtContent.setText("加载中...");
		txtContent.setTextColor(Color.WHITE);
		txtContent.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 15);
		// 初始化进度条
		progressBar = new ProgressBar(context);
		int width = getResources().getDimensionPixelSize(R.dimen.dimen_25);
		LayoutParams params = new LayoutParams(width, width);
		params.setMargins(0, 0, getResources().getDimensionPixelSize(R.dimen.dimen_5), 0);
		//初始化布局
		setPadding(getResources().getDimensionPixelSize(R.dimen.dimen_15), getResources().getDimensionPixelSize(R.dimen.dimen_10), 
				getResources().getDimensionPixelSize(R.dimen.dimen_15), getResources().getDimensionPixelSize(R.dimen.dimen_10));
		progressBar.setLayoutParams(params);
		setBackgroundResource(R.drawable.toast_bg);
		setOrientation(LinearLayout.HORIZONTAL);
		setGravity(Gravity.CENTER);
		//添加子布局
		addView(progressBar);
		addView(txtContent);
	}

	public void setLoadText(String loadingText){
		txtContent.setText(loadingText);
	}
	
	public void show(){
		setVisibility(View.VISIBLE);
	}
	
	public void hide(){
		setVisibility(View.GONE);
	}
}
