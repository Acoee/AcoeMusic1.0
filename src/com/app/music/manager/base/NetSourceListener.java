package com.app.music.manager.base;

/**
 * 网络数据监听接口
 *
 * @author Acoe
 * @version V1.0.0
 * @date 2015-8-20
 */
public interface NetSourceListener {
    /**
     * 成功
     */
    public static final int WHAT_SUCCESS = 100;
    /**
     * 错误
     */
    public static final int WHAT_ERROR = 110;
    /**
     * 邮箱登录失败
     */
    public static final int WHAT_MAIL_LOGIN_ERROR = 120;
    /**
     * 未登录
     */
    public static final int WHAT_NOT_LOGIN = 130;

    /**
     * 缓存加载成功
     */
    public static final int IS_CACHE_LOAD = 10;
    /**
     * 不是缓存加载成功
     */
    public static final int ISNOT_CACHE_LOAD = 11;

    // 以下为服务端定义不可改变
    /**
     * 请求成功
     */
    public static final int RESP_SUCESS = 4;
    /**
     * 请求失败
     */
    public static final int RESP_ERROR = 0;

    /**
     * 网络层由此向上发送消息 <br/>
     * {@link #WHAT_SUCCESS} 成功<br/>
     * {@link #WHAT_ERROR} 失败 <br/>
     * @param resultCode
     * @param what
     * @param data
     * @param toast
     */
    void sendMessage(int resultCode, int what, Object data, String toast);

    void sendMessage(int resultCode, int what, int arg1, Object data, String toast);

    void sendMessage(int resultCode, int what, int arg1, int arg2, Object data, String toast);
}
