package com.app.music.entity;

/**
 * Created by Administrator on 2015/9/9.
 */
public class Mp3Bean {
    public long id; // 歌曲ID 3
    public String title; // 歌曲名称 0
    public String album; // 专辑 7
    public long albumId;//专辑ID 6
    public String displayName; //显示名称 4
    public String artist; // 歌手名称 2
    public long duration; // 歌曲时长 1
    public long size; // 歌曲大小 8
    public String url; // 歌曲路径 5
    public String lrcTitle; // 歌词名称
    public String lrcSize; // 歌词大小
    public String firstWord; // 歌曲名首字母

    public Mp3Bean() {
        super();
    }


    public Mp3Bean(long id, String title, String album, long albumId,
                   String displayName, String artist, long duration, long size,
                   String url, String lrcTitle, String lrcSize, String firstWord) {
        super();
        this.id = id;
        this.title = title;
        this.album = album;
        this.albumId = albumId;
        this.displayName = displayName;
        this.artist = artist;
        this.duration = duration;
        this.size = size;
        this.url = url;
        this.lrcTitle = lrcTitle;
        this.lrcSize = lrcSize;
        this.firstWord = firstWord;
    }
}
