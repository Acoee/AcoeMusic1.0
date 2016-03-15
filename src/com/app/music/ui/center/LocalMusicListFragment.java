package com.app.music.ui.center;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;

import com.app.music.adapter.LocalMusicListAdapter;
import com.app.music.common.SystemUtils;
import com.app.music.entity.Mp3Bean;
import com.app.music.ui.base.BaseFragment;
import com.app.music.manager.LocalMusicDataMgr;
import com.app.music.view.SideBar;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

import java.util.ArrayList;

import cn.com.acoe.app.music.R;

/**
 * 本地音乐列表Fragment
 * Created by Administrator on 2015/9/8.
 */
public class LocalMusicListFragment extends BaseFragment {
    private String TAG = "LocalMusicListFragment";
    // 控件
    private PullToRefreshListView listView;
    private SideBar sbMusic;
    private TextView txtSelected;
    // 数据
    private ArrayList<Mp3Bean> datas;
    private LocalMusicDataMgr localMusicDataMgr;
    private LocalMusicListAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return super.onCreateView(R.layout.loacal_music_fragment, TAG, inflater, container, savedInstanceState);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        if (datas == null || datas.size() == 0) {
            initUI();
            datas = new ArrayList<Mp3Bean>();
            this.listView.setRefreshing(true);
            loadData();
        }
    }

    private void initUI() {
        if (localMusicDataMgr == null) this.localMusicDataMgr = new LocalMusicDataMgr(this);
        // 初始化控件
        this.listView = (PullToRefreshListView) this.context.findViewById(R.id.local_music_list);
        this.sbMusic = (SideBar) this.context.findViewById(R.id.local_music_sidebar);
        this.txtSelected = (TextView) this.context.findViewById(R.id.local_music_selected_txt);
        // SideBar设置
        this.sbMusic.setTextView(txtSelected);
        // 列表初始化
        this.adapter = new LocalMusicListAdapter(datas);
        this.listView.setAdapter(adapter);
        this.listView.setOnItemClickListener(itemClickListener);
    }

    /**
     * 列表单击事件
     */
    private AdapterView.OnItemClickListener itemClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            SystemUtils.ToastShow("OnItem " + position + " is clicked.", false);
        }
    };

    private void loadData() {
        localMusicDataMgr.getLocalMusic(context);
    }

    @Override
    public void onBack(int what, int arg1, int arg2, Object obj) {
        super.onBack(what, arg1, arg2, obj);
        listView.onRefreshComplete();
        switch (what) {
            case LocalMusicDataMgr.LOCAL_MUSIC_QUERY_SUCCESS: // 获取本地音乐成功
                ArrayList<Mp3Bean> result = (ArrayList<Mp3Bean>) obj;
                if (result != null || result.size() != 0) {
                    datas.addAll(result);
                }
                this.adapter.updateListView(datas);
                break;
            case LocalMusicDataMgr.LOCAL_MUSIC_QUERY_FAILURE: // 获取本地音乐失败
                SystemUtils.ToastShow(obj.toString(), false);
                break;
        }
    }
}
