package com.app.music.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.app.music.app.AppContext;
import com.app.music.common.ViewHolder;
import com.app.music.entity.MediaBillboardBean;

import java.util.ArrayList;

import cn.com.acoe.app.music.R;

/**
 * 媒体库榜单列表适配器
 * Created by Acoe on 2016/2/1.
 */
public class MediaBillboardAdapter extends BaseAdapter {
    private ArrayList<MediaBillboardBean> datas;

    public MediaBillboardAdapter() {
        datas = new ArrayList<MediaBillboardBean>();
        MediaBillboardBean newBean = new MediaBillboardBean();
        newBean.rsId = R.drawable.bill_board_new_icon;
        newBean.name = "新歌榜";
        newBean.desc = "最新的歌曲尽数搜罗";
        datas.add(newBean);
        MediaBillboardBean allBean = new MediaBillboardBean();
        allBean.rsId = R.drawable.bill_board_all_icon;
        allBean.name = "总歌榜";
        allBean.desc = "看看大家最爱听的歌";
        datas.add(allBean);
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
            convertView = LayoutInflater.from(AppContext.appContext).inflate(R.layout.media_list_item_view, null);
        }
        ImageView imgView = ViewHolder.get(convertView, R.id.media_list_item_imageview);
        TextView txtName = ViewHolder.get(convertView, R.id.media_list_item_name_textview);
        TextView txtDesc = ViewHolder.get(convertView, R.id.media_list_item_desc_textview);
        MediaBillboardBean item = datas.get(position);
        if (item != null) {
            imgView.setBackgroundResource(item.rsId);
            txtName.setText(item.name);
            txtDesc.setText(item.desc);
        }
        return convertView;
    }
}
