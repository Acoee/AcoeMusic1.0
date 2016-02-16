package com.app.music.frame.playlist;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.app.music.frame.base.BaseFragment;

import cn.com.acoe.app.music.R;

/**
 * 播放列表Fragment
 * Created by Administrator on 2015/9/2.
 */
public class PlaylistFragment extends BaseFragment {
    private String TAG = "PlaylistFragment";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return super.onCreateView(R.layout.play_list_fragment, TAG, inflater, container, savedInstanceState);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        initUI();
    }

    private void initUI() {

    }
}
