package com.app.music.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.app.music.app.AppContext;
import com.app.music.common.ViewHolder;
import com.app.music.entity.QQMusicBean;

import java.util.ArrayList;

import cn.com.acoe.app.music.R;

/**
 * 歌曲列表适配器
 * Created by Acoe on 2016/2/2.
 */
public class SongListAdapter extends BaseAdapter{
    private ArrayList<QQMusicBean> datas;

    public void updateListView(ArrayList<QQMusicBean> datas) {
        this.datas = datas;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return datas == null ? 0 : datas.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(AppContext.appContext).inflate(R.layout.song_list_item_view, null);
        }
        TextView txtNum = ViewHolder.get(convertView, R.id.number_textview);
        TextView txtTitle = ViewHolder.get(convertView, R.id.title_textview);
        TextView txtArtist = ViewHolder.get(convertView, R.id.artist_textview);
        final CheckBox cbxExtend = ViewHolder.get(convertView, R.id.extend_checkbox);
        final LinearLayout llExtend = ViewHolder.get(convertView, R.id.extend_ll);
        TextView txtLike = ViewHolder.get(convertView, R.id.like_textview);
        TextView txtAddTo = ViewHolder.get(convertView, R.id.add_to_textview);
        TextView txtDownload = ViewHolder.get(convertView, R.id.download_textview);
        TextView txtAddToQueue = ViewHolder.get(convertView, R.id.add_to_queue_textview);
        TextView txtShare = ViewHolder.get(convertView, R.id.share_textview);
        final QQMusicBean item = datas.get(position);
        if (item != null) {
            txtNum.setText(position + 1 + "");
            txtTitle.setText(item.songName);
            txtArtist.setText(item.singerName);
            if (item.isExtend) {
                cbxExtend.setChecked(true);
            } else {
                cbxExtend.setChecked(false);
            }
            cbxExtend.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (isChecked) {
                        llExtend.setVisibility(View.VISIBLE);
                        clearExtend();
                    } else {
                        llExtend.setVisibility(View.GONE);
                    }
                    item.isExtend = isChecked;
                }
            });
            View.OnClickListener clickListener = new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    switch (v.getId()) {
                        case R.id.like_textview:
                            // TODO
                            cbxExtend.setChecked(false);
                            break;
                        case R.id.add_to_textview:
                            // TODO
                            cbxExtend.setChecked(false);
                            break;
                        case R.id.download_textview:
                            // TODO
                            cbxExtend.setChecked(false);
                            break;
                        case R.id.add_to_queue_textview:
                            // TODO
                            cbxExtend.setChecked(false);
                            break;
                        case R.id.share_textview:
                            // TODO
                            cbxExtend.setChecked(false);
                            break;
                    }
                }
            };
            txtLike.setOnClickListener(clickListener);
            txtAddTo.setOnClickListener(clickListener);
            txtDownload.setOnClickListener(clickListener);
            txtAddToQueue.setOnClickListener(clickListener);
            txtShare.setOnClickListener(clickListener);
        }
        return convertView;
    }

    /**
     * 清除所有的item展开情况
     */
    private void clearExtend(){
        for (QQMusicBean item : datas) {
            item.isExtend = false;
        }
        notifyDataSetChanged();
    }

}
