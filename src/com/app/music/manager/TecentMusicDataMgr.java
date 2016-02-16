package com.app.music.manager;

import com.app.music.common.http.BusinessResult;
import com.app.music.common.http.TecentMusicResult;
import com.app.music.common.threads.WorkThreadExecutor;
import com.app.music.dao.TecentMusicDao;
import com.app.music.entity.QQMusicBean;
import com.app.music.manager.base.AbstractDataManager;
import com.app.music.manager.base.DataManagerCallBack;
import com.app.music.manager.base.NetSourceListener;


/**
 * 腾讯音乐网络请求接口
 * Created by Acoe on 2016/1/30.
 */
public class TecentMusicDataMgr extends AbstractDataManager {
    private DataManagerListener listener = new DataManagerListener();

    public static final int TECENT_MUSIC_NEW_SONG_QUERY_SUCESS = 20001; // 新歌榜查询成功
    public static final int TECENT_MUSIC_NEW_SONG_QUERY_FAILURE = 20002;
    public static final int TECENT_MUSIC_ALL_SONG_QUERY_SUCESS = 20003; // 总榜查询成功
    public static final int TECENT_MUSIC_ALL_SONG_QUERY_FAILURE = 20004;

    public TecentMusicDataMgr(DataManagerCallBack callBack) {
        super(callBack);
    }

    /**
     * 新歌榜单查询
     */
    public void newSongQuery() {
        WorkThreadExecutor.getInstance().execute(new Runnable() {
            @Override
            public void run() {
                TecentMusicResult<QQMusicBean> result = TecentMusicDao.newSongQuery();
                if (result.success) {
                    listener.sendMessage(NetSourceListener.WHAT_SUCCESS, TECENT_MUSIC_NEW_SONG_QUERY_SUCESS, result, getToastMsg(result.message));
                } else {
                    listener.sendMessage(NetSourceListener.WHAT_ERROR, TECENT_MUSIC_NEW_SONG_QUERY_FAILURE, null, getToastMsg(result.message));
                }
            }
        });
    }

    /**
     * 歌曲总榜单查询
     */
    public void allSongQuery() {
        WorkThreadExecutor.getInstance().execute(new Runnable() {
            @Override
            public void run() {
                TecentMusicResult<QQMusicBean> result = TecentMusicDao.allSongQuery();
                if (result.success) {
                    listener.sendMessage(NetSourceListener.WHAT_SUCCESS, TECENT_MUSIC_ALL_SONG_QUERY_SUCESS, result, getToastMsg(result.message));
                } else {
                    listener.sendMessage(NetSourceListener.WHAT_ERROR, TECENT_MUSIC_ALL_SONG_QUERY_FAILURE, null, getToastMsg(result.message));
                }
            }
        });
    }
}