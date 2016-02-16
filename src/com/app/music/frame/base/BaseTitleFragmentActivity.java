package com.app.music.frame.base;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.app.music.app.AppManager;

import cn.com.acoe.app.music.R;

/**
 * Created by Administrator on 2015/9/2.
 */
public class BaseTitleFragmentActivity extends BaseFragmentActivity{
    private TextView txtTitle;
    private Button btnBack, btnRight;

    @Override
    public void setContentView(int layoutResID, String activityName) {
        super.setContentView(layoutResID, activityName);

        getWindow().setBackgroundDrawable(null);
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

    /**
     * 设置标题文字
     * @param resid
     */
    protected void setTitleText(int resid) {
        this.txtTitle.setText(resid);
    }

    /**
     * 设置右边按钮背景
     * @param drawableResource
     */
    protected void setRightButtonBgStyle(int drawableResource) {
        this.btnRight.setBackgroundResource(drawableResource);
    }

    /**
     * 设置右边按钮可见性
     * @param visiblity
     */
    protected void setRightButtonVisible(int visiblity) {
        this.btnRight.setVisibility(visiblity);
    }

    /**
     * 设置右边按钮点击事件
     * @param listener
     */
    protected void setRightButtonClicklistener(View.OnClickListener listener) {
        this.btnRight.setOnClickListener(listener);
    }

    /**
     * 获取右边按钮
     * @return
     */
    protected Button getRightButton() {
        return this.btnRight;
    }

    /**
     * 设置返回按钮可见性
     * @param visiblity
     */
    protected void setBackButtonVisible(int visiblity) {
        this.btnBack.setVisibility(visiblity);
    }

    // 返回按钮点击事件
    private View.OnClickListener backClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            AppManager.getAppManager().finishActivity(BaseTitleFragmentActivity.this);
        }
    };
}
