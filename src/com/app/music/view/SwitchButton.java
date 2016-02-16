package com.app.music.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import cn.com.acoe.app.music.R;

/**
 * 滑动开关控件
 * Created by Acoe on 2015/9/21.
 */
public class SwitchButton extends View implements View.OnClickListener {
    private Bitmap switchBottom, switchThumb, switchFrame, switchMask;
    private float xOffset; // x轴偏移量
    private boolean switchStatus; // 开关状态
    private int maxMoveDistance; // 最大移动距离
    private float onPressX; // 按下的有效X轴坐标

    private Rect rDest; // 绘制的目标区域
    private Rect rSrc; // 截取的源图片大小
    private int deltX; // 移动的偏移量
    private Paint paint;
    private OnChangeListener onChangeListener;
    private boolean flag;

    public SwitchButton(Context context) {
        this(context, null);
    }

    public SwitchButton(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SwitchButton(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(); // 初始化
    }

    private void init() {
        switchBottom = BitmapFactory.decodeResource(getResources(), R.drawable.switch_bottom);
        switchThumb = BitmapFactory.decodeResource(getResources(), R.drawable.switch_btn_normal);
        switchFrame = BitmapFactory.decodeResource(getResources(), R.drawable.switch_frame);
        switchMask = BitmapFactory.decodeResource(getResources(), R.drawable.switch_mask);
        setOnClickListener(this);
        setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                return false;
            }
        });
        maxMoveDistance = switchBottom.getWidth() - switchFrame.getWidth();
        rDest = new Rect(0, 0, switchFrame.getWidth(), switchFrame.getHeight());
        rSrc = new Rect();
        paint = new Paint();
        paint.setAntiAlias(true);
        paint.setAlpha(255);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_IN));
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        setMeasuredDimension(switchFrame.getWidth(), switchFrame.getHeight());
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (deltX > 0 || deltX == 0 && switchStatus) {
            if (rSrc != null) {
                rSrc.set(maxMoveDistance - deltX, 0, switchBottom.getWidth() - deltX, switchFrame.getHeight());
            }
        } else if (deltX < 0 || deltX == 0 && !switchStatus) {
            if (rSrc != null) {
                rSrc.set(-deltX, 0, switchFrame.getWidth() - deltX, switchFrame.getHeight());
            }
        }
        int count = canvas.saveLayer(new RectF(rDest), null, Canvas.MATRIX_SAVE_FLAG | Canvas.CLIP_SAVE_FLAG
                | Canvas.HAS_ALPHA_LAYER_SAVE_FLAG | Canvas.FULL_COLOR_LAYER_SAVE_FLAG | Canvas.CLIP_TO_LAYER_SAVE_FLAG);
        canvas.drawBitmap(switchBottom, rSrc, rDest, null);
        canvas.drawBitmap(switchThumb, rSrc, rDest, null);
        canvas.drawBitmap(switchFrame, 0, 0, null);
        canvas.drawBitmap(switchMask, 0, 0, paint);
        canvas.restoreToCount(count);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                onPressX = event.getX();
                break;
            case MotionEvent.ACTION_MOVE:
                xOffset = event.getX();
                deltX = (int) (xOffset - onPressX);
                // 如果开关已经滑到底部，不再继续滑了
                if ((switchStatus && deltX < 0) || (!switchStatus && deltX > 0)) {
                    flag = true;
                    deltX = 0;
                }
                if (Math.abs(deltX) > maxMoveDistance) {
                    deltX = deltX > 0 ? maxMoveDistance : -maxMoveDistance;
                }
                invalidate();
                return true;
            case MotionEvent.ACTION_UP:
                if (Math.abs(deltX) > 0 && Math.abs(deltX) < maxMoveDistance / 2) {
                    deltX = 0;
                    invalidate();
                    return true;
                } else if (Math.abs(deltX) > maxMoveDistance / 2 && Math.abs(deltX) <= maxMoveDistance) {
                    deltX = deltX > 0 ? maxMoveDistance : -maxMoveDistance;
                    switchStatus = !switchStatus;
                    if (onChangeListener != null) {
                        onChangeListener.onChange(this, switchStatus);
                    }
                    invalidate();
                    deltX = 0;
                    return true;
                } else if (deltX == 0 && flag) {
                    // 这时候得到的是不需要进行处理的，因为已经move过了
                    deltX = 0;
                    flag = false;
                    return  true;
                }
                return super.onTouchEvent(event);
            default:
                break;
        }
        invalidate();
        return  super.onTouchEvent(event);
    }

    public void setOnChangeListener(OnChangeListener onChangeListener) {
        onChangeListener = onChangeListener;
    }

    public interface OnChangeListener {
        void onChange(SwitchButton sb, boolean status);
    }

    @Override
    public void onClick(View view) {
        deltX = switchStatus ? maxMoveDistance : -maxMoveDistance;
        switchStatus = !switchStatus;
        if (onChangeListener != null) {
            onChangeListener.onChange(this, switchStatus);
        }
        invalidate();
        deltX = 0;
    }
}
