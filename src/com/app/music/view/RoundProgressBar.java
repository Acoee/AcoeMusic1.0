package com.app.music.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.view.View;

import com.app.music.common.SystemUtils;

import cn.com.acoe.app.music.R;

/**
 * 自定义play进度按钮
 * Created by Administrator on 2015/9/2.
 */
public class RoundProgressBar extends View {
    private Paint paint;

    /**
     * 圆环的颜色
     */
    private int roundColor;

    /**
     * 圆环进度的颜色
     */
    private int roundProgressColor;

    /**
     * 圆环的宽度
     */
    private float roundWidth;

    /**
     * 最大进度
     */
    private int max;

    /**
     * 当前进度
     */
    private int progress;

    /**
     * 艺术家名称、字体颜色、字体大小
     */
    private String artistText;
    private int artistTextColor;
    private float artistTextSize;

    /**
     * 音乐名称、字体颜色、字体大小
     */
    private String musicText;
    private int musicTextColor;
    private float musicTextSize;

    /**
     * 音乐进度、字体颜色、字体大小
     */
    private String playedTime;
    private int playedTimeColor;
    private float playedTimeSize;

    private RectF oval = new RectF();
    /**
     * 播放状态
     */
    private boolean isPlaying = false;

    private Path path = new Path();

    public RoundProgressBar(Context context) {
        this(context, null);
    }

    public RoundProgressBar(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RoundProgressBar(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);

        paint = new Paint();


        TypedArray mTypedArray = context.obtainStyledAttributes(attrs,
                R.styleable.RoundProgressBar);

        //获取自定义属性和默认值
        roundColor = mTypedArray.getColor(R.styleable.RoundProgressBar_roundColor, Color.WHITE);
        roundProgressColor = mTypedArray.getColor(R.styleable.RoundProgressBar_roundProgressColor, 0xff9ac718);
        roundWidth = mTypedArray.getDimension(R.styleable.RoundProgressBar_roundWidth, 12);
        max = mTypedArray.getInteger(R.styleable.RoundProgressBar_max, 100);
        artistText = "未知艺术家";
        artistTextColor = mTypedArray.getColor(R.styleable.RoundProgressBar_artistTextColor, 0xffd2d2d2);
        artistTextSize = mTypedArray.getDimension(R.styleable.RoundProgressBar_artistTextSize, 12);
        musicText = "未知";
        musicTextColor = mTypedArray.getColor(R.styleable.RoundProgressBar_musicTextColor, Color.WHITE);
        musicTextSize = mTypedArray.getDimension(R.styleable.RoundProgressBar_musicTextSize, 16);
        playedTime = "00:00/00:00";
        playedTimeColor = mTypedArray.getColor(R.styleable.RoundProgressBar_playedTimeColor, Color.WHITE);
        playedTimeSize = mTypedArray.getDimension(R.styleable.RoundProgressBar_playedTimeSize, 16);

        mTypedArray.recycle();
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //设定坐标系，以控件左上角为原点
        int centerX = getWidth() / 2; //获取圆心的x坐标

        /**
         * 画最外层的大圆环
         */
        int radius = (int) (centerX - roundWidth / 2); //圆环的半径
        paint.setColor(roundColor); //设置圆环的颜色
        paint.setStyle(Paint.Style.STROKE); //设置空心
        paint.setStrokeWidth(roundWidth); //设置圆环的宽度
        paint.setAntiAlias(true);  //消除锯齿
        canvas.drawCircle(centerX, centerX, radius, paint); //画出圆环

        /**
         * 画圆弧 ，画圆环的进度 ，及圆环内背景
         */
        paint.setStrokeWidth(roundWidth); //设置圆环的宽度
        paint.setColor(roundProgressColor);  //设置进度的颜色
        oval.set(centerX - radius, centerX - radius, centerX + radius, centerX + radius);  //用于定义的圆弧的形状和大小的界限
        paint.setStyle(Paint.Style.STROKE);
        canvas.drawArc(oval, 270, 360 * progress / max, false, paint);  //根据进度画圆弧
        paint.setStyle(Paint.Style.FILL_AND_STROKE);
        paint.setColor(Color.GRAY);
        canvas.drawCircle(centerX, centerX, radius - roundWidth, paint);

        /**
         * 画播放状态图形
         */
//		Log.i(tag, "This is -> onDraw() -> isPlaying: " + isPlaying);
        paint.setColor(Color.GRAY);
        canvas.drawRect(centerX - centerX / 12, centerX - centerX / 12, centerX + centerX / 12, centerX + centerX / 12, paint);
        paint.setColor(Color.WHITE);
        paint.setStyle(Paint.Style.FILL_AND_STROKE);
        if (!isPlaying) {
            path.moveTo(centerX - centerX / 12, centerX - centerX / 12);
            path.lineTo(centerX - centerX / 12, centerX + centerX / 12);
            path.lineTo(centerX + centerX / 18, centerX);
            path.close();
            canvas.drawPath(path, paint);
        } else {
            canvas.drawRect(centerX - centerX / 12, centerX - centerX / 12, centerX - centerX / 13, centerX + centerX / 12, paint);
            canvas.drawRect(centerX + centerX / 13, centerX - centerX / 12, centerX + centerX / 12, centerX + centerX / 12, paint);
        }

        /**
         * 画艺术家名称、歌曲名称、进度信息
         */
        paint.setStrokeWidth(0);
        paint.setColor(artistTextColor);
        paint.setTextSize(artistTextSize);
        paint.setTypeface(Typeface.DEFAULT); //设置字体
        canvas.drawText(artistText, centerX - paint.measureText(artistText) / 2, centerX / 2 - artistTextSize, paint); //画出进度百分比
        paint.setColor(musicTextColor);
        paint.setTextSize(musicTextSize);
        paint.setTypeface(Typeface.DEFAULT_BOLD); //设置字体
        canvas.drawText(musicText, centerX - paint.measureText(musicText) / 2, centerX / 2 + musicTextSize, paint); //画出进度百分比
        paint.setColor(playedTimeColor);
        paint.setTextSize(playedTimeSize);
        playedTime = SystemUtils.MillisecondToString(progress) + "/" + SystemUtils.MillisecondToString(max);
        canvas.drawText(playedTime, centerX - paint.measureText(playedTime) / 2, centerX + centerX / 2 + artistTextSize, paint); //画出进度百分比
    }


    public synchronized int getMax() {
        return max;
    }

    /**
     * 设置进度的最大值
     *
     * @param max
     */
    public synchronized void setMax(int max) {
        if (max < 0) {
            throw new IllegalArgumentException("max not less than 0");
        }
        this.max = max;
    }

    /**
     * 获取进度.需要同步
     *
     * @return
     */
    public synchronized int getProgress() {
        return progress;
    }

    /**
     * 设置进度，此为线程安全控件，由于考虑多线的问题，需要同步
     * 刷新界面调用postInvalidate()能在非UI线程刷新
     *
     * @param progress
     */
    public synchronized void setProgress(int progress) {
        if (progress < 0) {
            throw new IllegalArgumentException("progress not less than 0");
        }
        if (progress > max) {
            progress = max;
        }
        if (progress <= max) {
            this.progress = progress;
            postInvalidate();
        }

    }


    public int getCricleColor() {
        return roundColor;
    }

    public void setCricleColor(int cricleColor) {
        this.roundColor = cricleColor;
    }

    public int getCricleProgressColor() {
        return roundProgressColor;
    }

    public void setCricleProgressColor(int cricleProgressColor) {
        this.roundProgressColor = cricleProgressColor;
    }

    public float getRoundWidth() {
        return roundWidth;
    }

    public void setRoundWidth(float roundWidth) {
        this.roundWidth = roundWidth;
    }

    public String getArtistText() {
        return artistText;
    }

    public void setArtistText(String artistText) {
        this.artistText = artistText;
    }

    public int getArtistTextColor() {
        return artistTextColor;
    }

    public void setArtistTextColor(int artistTextColor) {
        this.artistTextColor = artistTextColor;
    }

    public float getArtistTextSize() {
        return artistTextSize;
    }

    public void setArtistTextSize(float artistTextSize) {
        this.artistTextSize = artistTextSize;
    }

    public String getMusicText() {
        return musicText;
    }

    public void setMusicText(String musicText) {
        this.musicText = musicText;
    }

    public int getMusicTextColor() {
        return musicTextColor;
    }

    public void setMusicTextColor(int musicTextColor) {
        this.musicTextColor = musicTextColor;
    }

    public float getMusicTextSize() {
        return musicTextSize;
    }

    public void setMusicTextSize(float musicTextSize) {
        this.musicTextSize = musicTextSize;
    }

    public int getPlayedTimeColor() {
        return playedTimeColor;
    }

    public void setPlayedTimeColor(int playedTimeColor) {
        this.playedTimeColor = playedTimeColor;
    }

    public float getPlayedTimeSize() {
        return playedTimeSize;
    }

    public void setPlayedTimeSize(float playedTimeSize) {
        this.playedTimeSize = playedTimeSize;
    }

    public boolean getIsPlaying() {
        return isPlaying;
    }

    public void setIsPlaying(boolean isPlaying) {
        this.isPlaying = isPlaying;
    }
}