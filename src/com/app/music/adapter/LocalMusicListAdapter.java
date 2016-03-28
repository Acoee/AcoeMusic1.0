package com.app.music.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.app.music.app.AppContext;
import com.app.music.common.ViewHolder;
import com.app.music.entity.Mp3Bean;

import java.util.ArrayList;

import cn.com.acoe.app.music.R;

/**
 * 本地音乐列表适配器
 * Created by Administrator on 2015/9/14.
 */
public class LocalMusicListAdapter extends BaseAdapter {
    private ArrayList<Mp3Bean> datas;

    public LocalMusicListAdapter(ArrayList<Mp3Bean> datas) {
        this.datas = datas;
    }

    public void updateListView(ArrayList<Mp3Bean> datas) {
        this.datas = datas;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return datas == null ? 0 : datas.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(AppContext.appContext).inflate(R.layout.local_music_list_item_view, null);
        }
        TextView txtCatalog = ViewHolder.get(convertView,R.id.local_music_list_item_catalog_txt );
        TextView txtTitle = ViewHolder.get(convertView,R.id.local_music_list_item_title_txt);
        TextView txtArtist = ViewHolder.get(convertView,R.id.local_music_list_item_artist_txt);
        ImageView imgLine = ViewHolder.get(convertView,R.id.local_music_list_item_line_img);
        ImageView imgExtend = ViewHolder.get(convertView,R.id.local_music_list_item_extend_img);
        Mp3Bean item = datas.get(position);
        // 根据position获取分类的首字母的Char ascii值
        int section = getSectionForPosition(position);
        // 如果当前位置等于该分类首字母的Char的位置 ，则认为是第一次出现
        if (position == getPositionForSection(section)) {
            imgLine.setVisibility(View.VISIBLE);
            txtCatalog.setVisibility(View.VISIBLE);
            txtCatalog.setText(item.firstWord);
        } else {
            imgLine.setVisibility(View.GONE);
            txtCatalog.setVisibility(View.GONE);
        }
        if (item != null) {
            txtTitle.setText(item.title == null ? "未知歌曲" : item.title);
            txtArtist.setText(item.artist == null ? "未知艺术家" : item.artist);
            imgExtend.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // TODO
                }
            });
        }
        return convertView;
    }
    /**
     * 根据ListView的当前位置获取分类的首字母的Char ascii值
     */
    public int getSectionForPosition(int position) {
        return datas.get(position).firstWord.charAt(0);
    }

    /**
     * 根据分类的首字母的Char ascii值获取其第一次出现该首字母的位置
     */
    public int getPositionForSection(int section) {
        for (int i = 0; i < getCount(); i++) {
            String sortStr = datas.get(i).firstWord;
            char firstChar = sortStr.toUpperCase().charAt(0);
            if (firstChar == section) {
                return i;
            }
        }
        return -1;
    }
}
