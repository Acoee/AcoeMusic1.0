package com.app.music.service;

interface IServicePlayer{
    void play();
	void pause();
	void stop();
	void playNext();
	void playLast();
	int getDuration();
	int getPosition();
	int getPlayMode();
	void setPlayMode(int mode);
	boolean getIsPlaying();
	void seekTo(int position);
	void seetLoop(boolean isLoop);
	String getTitle();
	String getArtist();
}