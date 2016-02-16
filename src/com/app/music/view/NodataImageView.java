package com.app.music.view;

import cn.com.acoe.app.music.R;
import android.content.Context;
import android.view.View;
import android.widget.ImageView;

/**
 * 暂无数据背景
 * @author zhuyb
 * @date 2015-8-20
 * @version V1.0.0
 */
public class NodataImageView extends ImageView {
	public NodataImageView(Context context) {
		super(context);
		setBackgroundResource(R.drawable.no_content_bg);
	}
	
	public void show(){
		setVisibility(View.VISIBLE);
	}
	
	public void hide(){
		setVisibility(View.GONE);
	}
}
