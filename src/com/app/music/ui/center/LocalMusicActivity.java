package com.app.music.ui.center;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.app.music.ui.base.BaseTitleFragmentActivity;

import cn.com.acoe.app.music.R;

/**
 * 本地音乐列表界面
 * Created by Administrator on 2015/9/8.
 */
public class LocalMusicActivity extends BaseTitleFragmentActivity implements View.OnClickListener {
    private String TAG = "LocalMusicActivity";

    // 控件
    private LinearLayout llNavContainer;
    private TextView txtNavMusic, txtNavArtist, txtNavAlbum;
    // 数据
    private int lastClickPos = -1;

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putInt("lastClickPos", lastClickPos);
        outState.putString("fragment", nowFragment);
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.local_music_activity, TAG);

        initUI();
        if(savedInstanceState != null) {
            changeFragment(savedInstanceState.getString("fragment", LocalMusicListFragment.class.getName()));
            setNavStyle(savedInstanceState.getInt("lastClickPos", 0));
        } else {
            setFirstFragment(LocalMusicListFragment.class.getName());
            setNavStyle(0);
        }
    }

    private void initUI() {
        // 设置标题
        setTitleText("本地音乐");
        // 初始化控件
        this.llNavContainer = (LinearLayout) findViewById(R.id.local_music_top_nav_ll);
        this.txtNavMusic = (TextView) findViewById(R.id.local_music_nav_music_txt);
        this.txtNavArtist = (TextView) findViewById(R.id.local_music_nav_artist_txt);
        this.txtNavAlbum = (TextView) findViewById(R.id.local_music_nav_album_txt);
        // 初始化Fragment，将类名作为key
        this.fragments.put(LocalMusicListFragment.class.getName(), new LocalMusicListFragment());
        this.fragments.put(LocalMusicArtistListFragment.class.getName(), new LocalMusicArtistListFragment());
        this.fragments.put(LocalMusicAlbumListFragment.class.getName(), new LocalMusicAlbumListFragment());
        // 绑定点击事件
        this.txtNavMusic.setOnClickListener(this);
        this.txtNavArtist.setOnClickListener(this);
        this.txtNavAlbum.setOnClickListener(this);
    }

    @Override
    public void setNavStyle(int clickPos) {
        if (clickPos == lastClickPos) {
            return;
        }
        lastClickPos = clickPos;
        for(int i = 0; i < llNavContainer.getChildCount(); i++) {
            TextView txtNav = (TextView) llNavContainer.getChildAt(i);
            if (i == clickPos) {
                txtNav.setBackgroundResource(R.drawable.top_nav_selected_bg);
                txtNav.setTextColor(getResources().getColor(android.R.color.white));
            } else {
                txtNav.setBackgroundResource(R.drawable.top_nav_unselected_bg);
                txtNav.setTextColor(getResources().getColor(R.color.main_color));
            }
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.local_music_nav_music_txt:
                setNavStyle(0);
                break;
            case R.id.local_music_nav_artist_txt:
                setNavStyle(1);
                break;
            case R.id.local_music_nav_album_txt:
                setNavStyle(2);
                break;
        }
    }
}
