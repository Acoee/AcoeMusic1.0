package com.app.music.dao;

import android.content.Context;

import com.app.music.common.AcoeMusicUtils;
import com.app.music.entity.Mp3Bean;

import java.util.ArrayList;

/**
 * Created by Administrator on 2015/9/9.
 */
public class MusicDao extends BaseDao {

    public static ArrayList<Mp3Bean> getLocalMusic(Context context) {
        return AcoeMusicUtils.getMp3Beans(context);
    }
}
