package com.app.music.dao;

import com.app.music.common.GsonUtil;
import com.app.music.common.SystemUtils;
import com.app.music.common.URLs;
import com.app.music.common.http.TecentMusicResult;
import com.app.music.entity.QQMusicBean;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

/**
 * 腾讯音乐请求接口
 * Created by Acoe on 2016/2/1.
 */
public class TecentMusicDao extends BaseDao {

    /**
     * 新歌榜单查询
     * @return
     */
    public static TecentMusicResult<QQMusicBean> newSongQuery() {
        TecentMusicResult<QQMusicBean> result = new TecentMusicResult<QQMusicBean>();
        try {
            String resultJson = callWebMethod(URLs.TecentMusic.NEW_SONG_BILL_BOARD, null, null);
            resultJson = resultJson.substring("JsonCallBack(".length(), resultJson.lastIndexOf(")"));
            if (!SystemUtils.isEmpty(resultJson)) {
                Type typeObject = new TypeToken<TecentMusicResult<QQMusicBean>>(){}.getType();
                result = GsonUtil.getObjectFromJson(resultJson, typeObject);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return getResult(result);
    }

    /**
     * 歌曲总榜单查询
     * @return
     */
    public static TecentMusicResult<QQMusicBean> allSongQuery() {
        TecentMusicResult<QQMusicBean> result = new TecentMusicResult<QQMusicBean>();
        try {
            String resultJson = callWebMethod(URLs.TecentMusic.ALL_SONG_BILL_BOARD, null, null);
            resultJson = resultJson.substring("JsonCallBack(".length(), resultJson.lastIndexOf(")"));
            if (!SystemUtils.isEmpty(resultJson)) {
                Type typeObject = new TypeToken<TecentMusicResult<QQMusicBean>>(){}.getType();
                result = GsonUtil.getObjectFromJson(resultJson, typeObject);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return getResult(result);
    }
}
