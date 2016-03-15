package com.app.music.ui.media;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;

import com.app.music.adapter.SongListAdapter;
import com.app.music.common.SystemUtils;
import com.app.music.common.http.TecentMusicResult;
import com.app.music.entity.QQMusicBean;
import com.app.music.ui.base.BaseTitleActivity;
import com.app.music.manager.TecentMusicDataMgr;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

import java.util.ArrayList;

import cn.com.acoe.app.music.R;

/**
 * 总歌榜界面
 * Created by Acoe on 2016/2/16.
 */
public class AllSongBillboardActivity extends BaseTitleActivity {
    private static String TAG = "AllSongBillboardActivity";
    private TecentMusicDataMgr musicDataMgr;
    // 控件
    private ImageView imgAlbum;
    private PullToRefreshListView listView;
    // 列表适配器
    private SongListAdapter adapter;
    private ArrayList<QQMusicBean> datas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.new_song_billboard_activity, TAG);
        initUI();
        listView.setCurrentMode(Mode.PULL_FROM_START);
        listView.setRefreshing(true);
    }

    /**
     * 初始化界面
     */
    private void initUI() {
        musicDataMgr = new TecentMusicDataMgr(this);
        // 设置标题
        setTitleText("总歌榜");
        setRightButtonVisible(View.GONE);
        // 控件
        imgAlbum = (ImageView) findViewById(R.id.album_imageview);
        listView = (PullToRefreshListView) findViewById(R.id.listview);
        imgAlbum.setBackgroundResource(R.drawable.all_song_banner);
        // 初始化列表
        adapter = new SongListAdapter();
        listView.setAdapter(adapter);
        listView.setOnRefreshListener(onRefreshListener);
    }

    private PullToRefreshBase.OnRefreshListener<ListView> onRefreshListener = new PullToRefreshBase.OnRefreshListener<ListView>() {
        @Override
        public void onRefresh(PullToRefreshBase<ListView> refreshView) {
            Mode mode = refreshView.getCurrentMode();
            if (mode == Mode.PULL_FROM_START) { // 刷新
                listView.setMode(Mode.PULL_FROM_START);
                //跳转到头部
                listView.getRefreshableView().setSelection(0);
                datas = new ArrayList<QQMusicBean>();
                loadData();
            }
        }
    };

    /**
     * 加载数据
     */
    private void loadData() {
        musicDataMgr.allSongQuery();
    }

    @Override
    public void onBack(int what, int arg1, int arg2, Object obj) {
        super.onBack(what, arg1, arg2, obj);
        listView.onRefreshComplete();
        switch (what) {
            case TecentMusicDataMgr.TECENT_MUSIC_ALL_SONG_QUERY_SUCESS:
                TecentMusicResult<QQMusicBean> result = (TecentMusicResult<QQMusicBean>) obj;
                datas = result.songlist;
                adapter.updateListView(datas);
                break;
            case TecentMusicDataMgr.TECENT_MUSIC_ALL_SONG_QUERY_FAILURE:
                SystemUtils.ToastShow(obj.toString(), false, isVisible);
                break;
        }
    }
}
