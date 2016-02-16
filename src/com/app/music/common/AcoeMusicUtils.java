package com.app.music.common;

import android.content.Context;
import android.database.Cursor;
import android.provider.MediaStore;
import android.provider.MediaStore.Audio.Media;

import com.app.music.entity.Mp3Bean;

import java.util.ArrayList;

/**
 * 歌曲工具类
 * Created by Administrator on 2015/9/9.
 */
public class AcoeMusicUtils {
    public static ArrayList<Mp3Bean> getMp3Beans(Context context) {
        Cursor cursor = context.getContentResolver().query(
                Media.EXTERNAL_CONTENT_URI, null, null, null,
                Media.DEFAULT_SORT_ORDER);
        ArrayList<Mp3Bean> datas = new ArrayList<Mp3Bean>();
        Mp3Bean item;
        for (int i = 0; i < cursor.getCount(); i++) {
            cursor.moveToNext();
            item = new Mp3Bean();
            long id = cursor.getLong(cursor
                    .getColumnIndex(Media._ID));	//音乐id
            String title = cursor.getString((cursor
                    .getColumnIndex(Media.TITLE))); // 音乐标题
            String artist = cursor.getString(cursor
                    .getColumnIndex(Media.ARTIST)); // 艺术家
            String album = cursor.getString(cursor
                    .getColumnIndex(Media.ALBUM));	//专辑
            String displayName = cursor.getString(cursor
                    .getColumnIndex(Media.DISPLAY_NAME));
            long albumId = cursor.getInt(cursor.getColumnIndex(Media.ALBUM_ID));
            long duration = cursor.getLong(cursor
                    .getColumnIndex(Media.DURATION)); // 时长
            long size = cursor.getLong(cursor
                    .getColumnIndex(Media.SIZE)); // 文件大小
            String url = cursor.getString(cursor
                    .getColumnIndex(Media.DATA)); // 文件路径
            int isMusic = cursor.getInt(cursor
                    .getColumnIndex(Media.IS_MUSIC)); // 是否为音乐
            if (isMusic != 0 && (url.endsWith(".mp3") || url.endsWith(".awv"))) { // 只把音乐添加到集合当中
                item.id = id;
                item.title = title;
                item.artist = artist;
                item.album = album;
                item.displayName = displayName;
                item.albumId = albumId;
                item.duration = duration;
                item.size = size;
                item.url = url;
                item.firstWord = PinyinUtil.getFirstPinYin(displayName);
                datas.add(item);
            }
        }
        if (!cursor.isClosed()) {
            cursor.close();
        }
        cursor = context.getContentResolver().query(
                Media.INTERNAL_CONTENT_URI, null, null, null,
                Media.DEFAULT_SORT_ORDER);
        for (int i = 0; i < cursor.getCount(); i++) {
            cursor.moveToNext();
            item = new Mp3Bean();
            long id = cursor.getLong(cursor
                    .getColumnIndex(Media._ID));	//音乐id
            String title = cursor.getString((cursor
                    .getColumnIndex(Media.TITLE))); // 音乐标题
            String artist = cursor.getString(cursor
                    .getColumnIndex(Media.ARTIST)); // 艺术家
            String album = cursor.getString(cursor
                    .getColumnIndex(Media.ALBUM));	//专辑
            String displayName = cursor.getString(cursor
                    .getColumnIndex(Media.DISPLAY_NAME));
            long albumId = cursor.getInt(cursor.getColumnIndex(Media.ALBUM_ID));
            long duration = cursor.getLong(cursor
                    .getColumnIndex(Media.DURATION)); // 时长
            long size = cursor.getLong(cursor
                    .getColumnIndex(Media.SIZE)); // 文件大小
            String url = cursor.getString(cursor
                    .getColumnIndex(Media.DATA)); // 文件路径
            int isMusic = cursor.getInt(cursor
                    .getColumnIndex(Media.IS_MUSIC)); // 是否为音乐

            if (isMusic != 0  && (url.endsWith(".mp3") || url.endsWith(".awv"))) { // 只把音乐添加到集合当中
                item.id = id;
                item.title = title;
                item.artist = artist;
                item.album = album;
                item.displayName = displayName;
                item.albumId = albumId;
                item.duration = duration;
                item.size = size;
                item.url = url;
                item.firstWord = PinyinUtil.getFirstPinYin(displayName);
                datas.add(item);
            }
        }
        if (!cursor.isClosed()) {
            cursor.close();
        }
        return datas;
    }
}
