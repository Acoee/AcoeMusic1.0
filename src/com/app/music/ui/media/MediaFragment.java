package com.app.music.ui.media;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.app.music.adapter.MediaBillboardAdapter;
import com.app.music.app.AppContext;
import com.app.music.ui.base.BaseFragment;
import com.app.music.view.AdViewPager;
import com.app.music.view.SearchEditText;

import cn.com.acoe.app.music.R;

/**
 * 媒体库Fragment
 * Created by Administrator on 2015/9/2.
 */
public class MediaFragment extends BaseFragment {
    private String TAG = "MediaFragment";
    // 控件
    private SearchEditText edtSearch;
    private ListView listView;
    private AdViewPager adViewPager;
    private LinearLayout llOval;
    private MediaBillboardAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return super.onCreateView(R.layout.media_fragment, TAG, inflater, container, savedInstanceState);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        context.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN); // 隐藏软键盘
        initUI();
    }

    private void initUI() {
        edtSearch = (SearchEditText) context.findViewById(R.id.media_search_edittext);
        listView = (ListView) context.findViewById(R.id.media_listview);
        adViewPager = (AdViewPager) context.findViewById(R.id.media_ad_viewpager);
        llOval = (LinearLayout) context.findViewById(R.id.media_oval_ll);
        int[] ids = new int[] { R.drawable.load_default_icon, R.drawable.center_tob_bg, R.drawable.load_default_icon, R.drawable.center_tob_bg };
        // 初始化广告pager
        adViewPager.start(AppContext.appContext, null, ids, 5000, llOval, R.drawable.oval_focus_icon, R.drawable.oval_normal_icon);
        // 初始化列表
        adapter = new MediaBillboardAdapter();
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(itemClickListener);
    }

    private AdapterView.OnItemClickListener itemClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            Intent intent = new Intent();
            if (position == 0) {
                intent = new Intent(context, NewSongBillboardActivity.class);
            } else {
                intent = new Intent(context, AllSongBillboardActivity.class);
            }
            startActivity(intent);
        }
    };
}
