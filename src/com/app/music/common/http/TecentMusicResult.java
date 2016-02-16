package com.app.music.common.http;

import java.util.ArrayList;

/**
 * 腾讯音乐接口返回结果类
 * Created by Acoe on 2016/2/2.
 */
public class TecentMusicResult<T> {
    public String message; //提示信息，APP端或WX端直接展示给用户
    public boolean success; //结果标识，true或false
    public long sysTime; //服务器时间
    public String retcode; // 结果码
    public T one;
    public ArrayList<T> songlist;
}
