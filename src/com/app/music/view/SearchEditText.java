package com.app.music.view;


import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnFocusChangeListener;
import android.view.View.OnKeyListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import cn.com.acoe.app.music.R;

/**
 * 待删除按钮的搜索编辑框
 * 无内容时搜索图片在中间，有内容时搜索图标在左边
 * 使用方法：在布局文件中声明时，需要指定三个图标，删除按钮正常和按下两种情况下的图标，搜索图标
 * @author Acoe
 * @date 2016-1-21
 * @version V1.0.0
 */
public class SearchEditText extends EditText implements OnFocusChangeListener, OnKeyListener, TextWatcher{
	private static final String TAG = "SearchEditText";
	/**
	 * 图标是否默认在左边
	 */
	private boolean isIconLeft = false;
	/**
	 * 是否点击软键盘搜索
	 */
	private boolean pressSearch = false;
	/**
	 * 软键盘搜索键监听
	 */
	private OnSearchClickListener listener;
	
	private Drawable drawableLeft, drawableDel, drawableDelNormal, drawableDelPressed; // 搜索图标和删除按钮图标
	private int eventX, eventY; // 记录点击坐标
	private Rect rect; // 控件区域
	
	public void setOnSearchClickListener(OnSearchClickListener listener) {
		this.listener = listener;
	}
	
	public interface OnSearchClickListener {
		void onSearchClick(View view);
	}
	

	public SearchEditText(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(context, attrs);
	}
	
	@SuppressWarnings("deprecation")
	private void init(Context context, AttributeSet attrs) {
		TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.SearchEditText);
		if (array.hasValue(R.styleable.SearchEditText_delNormalIcon)) {
			drawableDelNormal = array.getDrawable(R.styleable.SearchEditText_delNormalIcon);
		} else {
			drawableDelNormal = getResources().getDrawable(R.drawable.edit_delete_normal_icon);
		}
		if (array.hasValue(R.styleable.SearchEditText_delPressedIcon)) {
			drawableDelPressed = array.getDrawable(R.styleable.SearchEditText_delPressedIcon);
		} else {
			drawableDelPressed = getResources().getDrawable(R.drawable.edit_delete_pressed_icon);
		}
		if (array.hasValue(R.styleable.SearchEditText_searchInIcon)) {
			drawableLeft = array.getDrawable(R.styleable.SearchEditText_searchInIcon);
		} else {
			drawableLeft = getResources().getDrawable(R.drawable.edit_search_icon);
		}
		array.recycle();
		// 绑定事件
		setOnFocusChangeListener(this);
		setOnKeyListener(this);
		addTextChangedListener(this);
	}

	@Override
	protected void onDraw(Canvas canvas) {
		if (isIconLeft) { // 如果是默认样式，直接绘制
			if (length() < 1) {
				drawableDel = null;
			}
			this.setCompoundDrawablesWithIntrinsicBounds(drawableLeft, null, drawableDel, null);
		} else { // 如果不是默认样式，需要将图标绘制在中间
			float textWidth = getHint() == null ? 0 :getPaint().measureText(getHint().toString());
			int drawablePadding = getCompoundDrawablePadding();
			int drawableWidth = drawableLeft.getIntrinsicWidth();
			float bodyWidth = textWidth + drawableWidth + drawablePadding;
			canvas.translate((getWidth() - bodyWidth - getPaddingLeft() - getPaddingRight()) / 2, 0);
		}
		super.onDraw(canvas);
	}
	
	
	@Override
	public void onFocusChange(View v, boolean hasFocus) {
		// 被点击时，恢复默认样式
		if (!pressSearch && TextUtils.isEmpty(getText().toString())) {
			isIconLeft = hasFocus;
		}
	}
	
	@Override
	public boolean onKey(View v, int keyCode, KeyEvent event) {
		pressSearch = (keyCode ==KeyEvent.KEYCODE_ENTER);
		if (pressSearch && listener != null) {
			/*隐藏软键盘*/
			InputMethodManager imm = (InputMethodManager) v.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
			if (imm.isActive()) {
				imm.hideSoftInputFromWindow(v.getApplicationWindowToken(), 0);
			}
			listener.onSearchClick(v);
		}
		return false;
	}
	
	@SuppressLint("ClickableViewAccessibility")
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		// 清空edit内容
		if (drawableDel != null && event.getAction() == MotionEvent.ACTION_UP) {
			eventX = (int) event.getRawX();
            eventY = (int) event.getRawY();
            Log.i(TAG, "eventX = " + eventX + "; eventY = " + eventY);
            if (rect == null) rect = new Rect();
            getGlobalVisibleRect(rect);
            rect.left = rect.right - drawableDel.getIntrinsicWidth();
            if (rect.contains(eventX, eventY)) {
            	setText("");
            }
		}
		// 删除按钮被按下时改变图标样式
		if (drawableDel != null && event.getAction() == MotionEvent.ACTION_DOWN) {
			eventX = (int) event.getRawX();
            eventY = (int) event.getRawY();
            Log.i(TAG, "eventX = " + eventX + "; eventY = " + eventY);
            if (rect == null) rect = new Rect();
            getGlobalVisibleRect(rect);
            rect.left = rect.right - drawableDel.getIntrinsicWidth();
            if (rect.contains(eventX, eventY))
            	drawableDel = this.drawableDelPressed;
		} else {
			drawableDel = this.drawableDelNormal;
		}
		return super.onTouchEvent(event);
	}

	@Override
	public void afterTextChanged(Editable arg0) {
		if (this.length() < 1) {
			drawableDel = null;
		} else {
			drawableDel = this.drawableDelNormal;
		}
	}

	@Override
	public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,
			int arg3) {
	}
	
	@Override
	public void onTextChanged(CharSequence arg0, int arg1, int arg2,
            int arg3) {
    }
}
