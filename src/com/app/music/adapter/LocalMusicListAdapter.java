package com.app.music.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.app.music.app.AppContext;
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
        ViewHolder holder = null;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = LayoutInflater.from(AppContext.appContext).inflate(R.layout.local_music_list_item_view, null);
            holder.txtCatalog = (TextView) convertView.findViewById(R.id.local_music_list_item_catalog_txt);
            holder.txtTitle = (TextView) convertView.findViewById(R.id.local_music_list_item_title_txt);
            holder.txtArtist = (TextView) convertView.findViewById(R.id.local_music_list_item_artist_txt);
            holder.imgLine = (ImageView) convertView.findViewById(R.id.local_music_list_item_line_img);
            holder.imgExtend = (ImageView) convertView.findViewById(R.id.local_music_list_item_extend_img);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        Mp3Bean item = datas.get(position);
        // 根据position获取分类的首字母的Char ascii值
        int section = getSectionForPosition(position);
        // 如果当前位置等于该分类首字母的Char的位置 ，则认为是第一次出现
        if (position == getPositionForSection(section)) {
            holder.imgLine.setVisibility(View.VISIBLE);
            holder.txtCatalog.setVisibility(View.VISIBLE);
            holder.txtCatalog.setText(item.firstWord);
        } else {
            holder.imgLine.setVisibility(View.GONE);
            holder.txtCatalog.setVisibility(View.GONE);
        }
        if (item != null) {
            holder.txtTitle.setText(item.title == null ? "未知歌曲" : item.title);
            holder.txtArtist.setText(item.artist == null ? "未知艺术家" : item.artist);
            holder.imgExtend.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // TODO
                }
            });
        }
        return convertView;
    }

    private static class ViewHolder {
        TextView txtCatalog, txtTitle, txtArtist;
        ImageView imgLine, imgExtend;
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
