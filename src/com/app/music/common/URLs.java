package com.app.music.common;

public class URLs {
	
	public final static String HTTP = "http://";
	public final static String HTTPS = "https://";
	
	public final static String IP = "xxxx.com";
	public final static String HOST = HTTP + IP; //生产环境主机地址

	public static class TecentMusic {
		public final static String HOST = "http://music.qq.com/musicbox/shop/v3/data/hit/";
		public final static String NEW_SONG_BILL_BOARD = HOST + "hit_newsong.js";
		public final static String ALL_SONG_BILL_BOARD = HOST + "hit_all.js";
		public final static String MUSIC_SEARCH = "";
	}

	public static class BaiduMusic {
		public final static String HOST = "";
	}

}
