package com.app.music.frame.base;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.app.music.app.AppManager;

import cn.com.acoe.app.music.R;

/**
 * Created by Administrator on 2015/9/2.
 */
public class BaseTitleFragment extends BaseFragment {
    private TextView txtTitle;
    private Button btnBack, btnRight;

    public View onCreateView(int layoutResID, String fragmentName, LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        return super.onCreateView(layoutResID, fragmentName, inflater, container, savedInstanceState);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        initUI(); //初始化界面
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    private void initUI() {

        this.txtTitle = (TextView) this.context.findViewById(R.id.title_textview);
        this.btnBack = (Button) this.context.findViewById(R.id.title_back_button);
        this.btnRight = (Button) this.context.findViewById(R.id.title_right_button);
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

    protected void setRightButtonBgStyle(int drawableResource) {
        this.btnRight.setBackgroundResource(drawableResource);
    }
    protected void setRightButtonVisible(int visiblity) {
        this.btnRight.setVisibility(visiblity);
    }

    protected void setBtnRightButtonClicklistener(View.OnClickListener listener) {
        this.btnRight.setOnClickListener(listener);
    }

    // 返回按钮点击事件
    private View.OnClickListener backClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            AppManager.getAppManager().finishActivity(context);
        }
    };
}
