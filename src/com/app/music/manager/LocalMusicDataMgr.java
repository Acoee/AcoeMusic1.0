package com.app.music.manager;

import android.content.Context;

import com.app.music.common.threads.WorkThreadExecutor;
import com.app.music.dao.MusicDao;
import com.app.music.entity.Mp3Bean;
import com.app.music.manager.base.AbstractDataManager;
import com.app.music.manager.base.DataManagerCallBack;
import com.app.music.manager.base.NetSourceListener;

import java.util.ArrayList;

/**
 * 本地音乐管理接口
 * Created by Administrator on 2015/9/9.
 */
public class LocalMusicDataMgr extends AbstractDataManager{
    private DataManagerListener listener = new DataManagerListener();

    public static final int LOCAL_MUSIC_QUERY_SUCCESS = 10001; // 本地音乐查询成功
    public static final int LOCAL_MUSIC_QUERY_FAILURE = 10002; // 本地音乐查询失败
    public static final int LOCAL_MUSIC_AMOUNT_QUERY_SUCCESS = 10003; // 本地音乐数量查询成功
    public static final int LOCAL_MUSIC_AMOUNT_QUERY_FAILURE = 10004; // 本地音乐数量查询失败

    public LocalMusicDataMgr(DataManagerCallBack callBack) {
        super(callBack);
    }

    /**
     * 获取本地音乐列表
     * @param context
     */
    public void getLocalMusic(final Context context) {
        WorkThreadExecutor.getInstance().execute(new Runnable() {
            @Override
            public void run() {
                ArrayList<Mp3Bean> result = MusicDao.getLocalMusic(context);
                if (result == null) {
                    listener.sendMessage(NetSourceListener.WHAT_ERROR, LOCAL_MUSIC_QUERY_FAILURE, result, "获取本地音乐失败");
                } else {
                    listener.sendMessage(NetSourceListener.WHAT_SUCCESS, LOCAL_MUSIC_QUERY_SUCCESS, result, "获取本地音乐成功");
                }
            }
        });
    }

    /**
     * 获取本地音乐数量
     * @param context
     * @return
     */
    public void getLocalMusicAmount(final Context context) {
        WorkThreadExecutor.getInstance().execute(new Runnable() {
            @Override
            public void run() {
                String result = MusicDao.getLocalMusicAmount(context);
                if (result == null) {
                    listener.sendMessage(NetSourceListener.WHAT_ERROR, LOCAL_MUSIC_AMOUNT_QUERY_FAILURE, result, "获取本地音乐数量失败");
                } else {
                    listener.sendMessage(NetSourceListener.WHAT_SUCCESS, LOCAL_MUSIC_AMOUNT_QUERY_SUCCESS, result, "获取本地音乐数量成功");
                }
            }
        });
    }
}
