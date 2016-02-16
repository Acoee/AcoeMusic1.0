package com.app.music.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.media.MediaPlayer.OnPreparedListener;
import android.os.IBinder;
import android.os.RemoteException;

import com.app.music.app.AppContext;
import com.app.music.common.SystemUtils;
import com.app.music.entity.Mp3Bean;
import com.app.music.service.IServicePlayer.Stub;

/**
 * 音乐后台播放服务
 * Created by Acoe on 2015/9/28.
 */
public class MusicService extends Service{
	private ArrayList<Mp3Bean> datas;
	private MediaPlayer mediaPlayer;
	private int currPosition; // 正在被播放歌曲在列表中的位置
	private int playMode = 0;  //播放模式： 0 单曲循环；1 列表循环；2 随机播放；3 列表播放
	private int playStatus = 0;  //播放状态：0 未开始；1 播放中；2 暂停中
	private Random random;
	
	@Override
	public void onCreate() {
		super.onCreate();
		
		init();
	}
	
	/**
	 * 初始化操作
	 */
	private void init() {
		mediaPlayer = new MediaPlayer();
		if (playMode == 0) {
			mediaPlayer.setLooping(true);
		} else {
			mediaPlayer.setLooping(false);
		}
		mediaPlayer.reset(); // 恢复到初始状态
		setMusicCompleteListener(); // 设置音乐播放完成的监听
	}
	
	/**
	 * 同步service数据
	 */
	public void syncData() {
		this.datas = AppContext.datas;
	}

    @Override
    public IBinder onBind(Intent intent) {
        return stub;
    }
    
    @Override
    public boolean onUnbind(Intent intent) {
    	return super.onUnbind(intent);
    }

    private Stub stub = new Stub(){

		@Override
		public void play() throws RemoteException {
			playMusic();
		}

		@Override
		public void pause() throws RemoteException {
			pauseMusic();
		}

		@Override
		public void stop() throws RemoteException {
			stopMusic();
		}

		@Override
		public void playNext() throws RemoteException {
			playNextMusic();
		}

		@Override
		public void playLast() throws RemoteException {
			playLastMusic();
		}

		@Override
		public int getDuration() throws RemoteException {
			return (int) datas.get(currPosition).duration;
		}

		@Override
		public int getPosition() throws RemoteException {
			return mediaPlayer.getCurrentPosition();
		}

		@Override
		public int getPlayMode() throws RemoteException {
			return playMode;
		}

		@Override
		public void setPlayMode(int mode) throws RemoteException {
			playMode = mode;
		}

		@Override
		public boolean getIsPlaying() throws RemoteException {
			return playStatus == 1;
		}

		@Override
		public void seekTo(int position) throws RemoteException {
			mediaPlayer.seekTo(position);
		}

		@Override
		public void seetLoop(boolean isLoop) throws RemoteException {
			mediaPlayer.setLooping(isLoop);
		}

		@Override
		public String getTitle() throws RemoteException {
			return datas.get(currPosition).title;
		}

		@Override
		public String getArtist() throws RemoteException {
			return datas.get(currPosition).artist;
		}
	};
	
	/**
	 * 播放
	 */
	private void playMusic() {
		try {
			mediaPlayer.setDataSource(datas.get(currPosition).url);
			if (playStatus == 0) {
				mediaPlayer.prepare();
				mediaPlayer.setOnPreparedListener(new PreparedListener());
			} else {
				mediaPlayer.start();
			}
			playStatus = 1;
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	 //播放模式： 0 单曲循环；1 列表循环；2 随机播放；3 列表播放
	private void playNextMusic() {
		switch (playMode) {
		case 0: // 单曲循环
		case 1: // 列表循环
			if (currPosition == datas.size() - 1) { // 播放到最后一首了
				currPosition = 0;
			} else {
				++currPosition;
			}
			break;
		case 2: // 随机播放
			if (random == null) random = new Random();
			currPosition = random.nextInt(datas.size());
			break;
		case 3: // 列表播放
			if (currPosition == datas.size() - 1) { // 播放到最后一首了
				SystemUtils.ToastShow("已经播放到最后一首了", false);
				return;
			} else {
				++currPosition;
			}
			break;
		}
		playMusic();
		playStatus = 1;
	}
	
	private void playLastMusic() {
		switch (playMode) {
		case 0: // 单曲循环
		case 1: // 列表循环
			if (currPosition == 0) { // 已经是第一首
				currPosition = datas.size() - 1;
			} else {
				--currPosition;
			}
			break;
		case 2: // 随机播放
			if (random == null) random = new Random();
			currPosition = random.nextInt(datas.size());
			break;
		case 3: // 列表播放
			if (currPosition == 0) { // 已经是第一首
				SystemUtils.ToastShow("已经播放是第一首了", false);
				return;
			} else {
				--currPosition;
			}
			break;
		}
		playStatus = 1;
	}
	
	/**
	 * 暂停播放
	 */
	private void pauseMusic() {
		mediaPlayer.pause();
		playStatus = 2;
	}
	
	/**
	 * 停止播放
	 */
	private void stopMusic() {
		mediaPlayer.stop();
		playStatus = 0;
	}
	
	/**
	 * 设置音乐播放完成时的监听器
	 */
	private void setMusicCompleteListener() {
		mediaPlayer.setOnCompletionListener(new OnCompletionListener() {

			@Override
			public void onCompletion(MediaPlayer mp) {
				if (playMode == 0) { // 单曲循环
					mediaPlayer.start();
				} else if (playMode == 1) { // 列表循环
					playNextMusic();
				} 
			}
		});
	}
	
	
	/**
	 * 
	 * 实现一个OnPrepareLister接口,当音乐准备好的时候开始播放
	 * 
	 */
	private final class PreparedListener implements OnPreparedListener {

		@Override
		public void onPrepared(MediaPlayer mp) {
			mediaPlayer.start(); // 开始播放
		}
	}

	@Override
	public void onDestroy() {
		if (mediaPlayer != null) {
			mediaPlayer.stop();
			mediaPlayer.release();
			mediaPlayer = null;
		}
	}
}
