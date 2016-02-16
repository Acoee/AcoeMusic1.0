package com.app.music.frame.start;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.FrameLayout.LayoutParams;

import com.app.music.frame.MainActivity;
import com.app.music.frame.base.BaseActivity;

import cn.com.acoe.app.music.R;

/**
 * 启动界面
 * Created by Administrator on 2015/9/2.
 */
public class StartActivity extends BaseActivity{
	private static String TAG = "StartActivity";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// 反射启动布局页面
		View startView = new View(getApplicationContext());
		LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
		startView.setLayoutParams(params);
		startView.setBackgroundResource(R.drawable.start_bg);
		// 加载启动页面
		setContentView(startView, TAG);
		// 渐变动画
		AlphaAnimation aa = new AlphaAnimation(0.3f, 1.0f);
		aa.setDuration(2000); // 动画保存3秒时间
		startView.startAnimation(aa); // 启动动画
		aa.setAnimationListener(new Animation.AnimationListener() {
			@Override
			public void onAnimationStart(Animation animation) {
			}
			@Override
			public void onAnimationEnd(Animation animation) {
				redirectTo(); // 动画结束后跳转
			}
			@Override
			public void onAnimationRepeat(Animation animation) {
			}
		});
	}

	private void redirectTo() {
		Intent intent = new Intent(StartActivity.this, MainActivity.class);
		startActivity(intent);
		finishActvity();
	}
}
