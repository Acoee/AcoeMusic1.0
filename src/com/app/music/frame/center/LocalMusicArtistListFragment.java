package com.app.music.frame.center;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.app.music.frame.base.BaseFragment;

import cn.com.acoe.app.music.R;

/**
 * 本地音乐歌手列表
 * Created by Administrator on 2015/9/8.
 */
public class LocalMusicArtistListFragment extends BaseFragment {
    private String TAG = "LocalMusicArtistListFragment";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return super.onCreateView(R.layout.loacal_music_fragment, TAG, inflater, container, savedInstanceState);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        initUI();
    }

    private void initUI() {

    }
}
