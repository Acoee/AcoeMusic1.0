package com.app.music.ui;

import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.app.music.ui.base.BaseTitleFragmentActivity;
import com.app.music.ui.center.CenterFragment;
import com.app.music.ui.current.CurrentFragment;
import com.app.music.ui.media.MediaFragment;
import com.app.music.ui.playlist.PlaylistFragment;

import cn.com.acoe.app.music.R;

/**
 * 主界面
 * Created by Administrator on 2015/9/2.
 */
public class MainActivity extends BaseTitleFragmentActivity implements View.OnClickListener{
    private String TAG = "MainActivity";
    // 动画
    private AnimationDrawable animPlaying;
    // 控件
    private LinearLayout llNavContainer;
    private RelativeLayout rlCenter, rlCurrent, rlList, rlMedia;
    // 数据
    private boolean isPlaying;

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putInt("lastClickPos", lastClickPos);
        outState.putString("fragment", nowFragment);
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity, TAG);

        initUI();
        if(savedInstanceState != null) {
            changeFragment(savedInstanceState.getString("fragment", CenterFragment.class.getName()));
            setNavStyle(savedInstanceState.getInt("lastClickPos", 0));
        } else {
            setFirstFragment(CenterFragment.class.getName());
            setNavStyle(0);
        }
    }

    private void initUI() {
        // 设置标题
        this.setBackButtonVisible(View.GONE);
        this.setRightButtonClicklistener(this);
        // 初始化动画
        this.animPlaying = (AnimationDrawable) getRightButton().getBackground();
        // 初始化控件
        this.llNavContainer = (LinearLayout) findViewById(R.id.bottom_nav_container_ll);
        this.rlCenter = (RelativeLayout) findViewById(R.id.main_nav_center_rl);
        this.rlCurrent = (RelativeLayout) findViewById(R.id.main_nav_current_rl);
        this.rlList = (RelativeLayout) findViewById(R.id.main_nav_list_rl);
        this.rlMedia = (RelativeLayout) findViewById(R.id.main_nav_media_rl);
        // 初始化Fragment，将类名作为key
        this.fragments.put(CenterFragment.class.getName(), new CenterFragment());
        this.fragments.put(CurrentFragment.class.getName(), new CurrentFragment());
        this.fragments.put(PlaylistFragment.class.getName(), new PlaylistFragment());
        this.fragments.put(MediaFragment.class.getName(), new MediaFragment());
        // 绑定点击事件
        this.rlCenter.setOnClickListener(this);
        this.rlCurrent.setOnClickListener(this);
        this.rlList.setOnClickListener(this);
        this.rlMedia.setOnClickListener(this);
    }

    private int lastClickPos = -1;
    private int[] selectedIds = { R.drawable.nav_center_selected, R.drawable.nav_current_selected, R.drawable.nav_list_selected, R.drawable.nav_media_selected };
    private int[] unselectedIds = { R.drawable.nav_center_unselected, R.drawable.nav_current_unselected, R.drawable.nav_list_unselected, R.drawable.nav_media_unselected };
    private int[] titleIds = { R.string.main_title_center_text, R.string.main_title_current_text, R.string.main_title_list_text, R.string.main_title_media_text };
    @Override
    public void setNavStyle(int clickPos) {
        if (clickPos == lastClickPos) {
            return;
        }
        lastClickPos = clickPos;
        setTitleText(titleIds[clickPos]);
        for(int i = 0; i < llNavContainer.getChildCount(); i++) {
            ViewGroup groupNav = (ViewGroup) llNavContainer.getChildAt(i);
            ImageView imgNav = (ImageView) groupNav.getChildAt(0);
            TextView txtNav = (TextView) groupNav.getChildAt(1);
            if (i == clickPos) {
                imgNav.setBackgroundResource(selectedIds[i]);
                txtNav.setTextColor(getResources().getColor(R.color.main_color));
            } else {
                imgNav.setBackgroundResource(unselectedIds[i]);
                txtNav.setTextColor(getResources().getColor(R.color.main_nav_text_color));
            }
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.title_right_button: // 点击标题栏音乐播放按钮
                if (isPlaying) {
                    animPlaying.stop();
                } else {
                    animPlaying.start();
                }
                isPlaying = !isPlaying;
                break;
            case R.id.main_nav_center_rl: // 点击个人中心
                changeFragment(CenterFragment.class.getName());
                setNavStyle(0);
                break;
            case R.id.main_nav_current_rl: // 点击当前音乐
                changeFragment(CurrentFragment.class.getName());
                setNavStyle(1);
                break;
            case R.id.main_nav_list_rl: // 点击播放列表
                changeFragment(PlaylistFragment.class.getName());
                setNavStyle(2);
                break;
            case R.id.main_nav_media_rl: // 点击媒体库
                changeFragment(MediaFragment.class.getName());
                setNavStyle(3);
                break;
        }
    }
}
