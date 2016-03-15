package com.app.music.view;

import android.content.Context;
import android.media.Image;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.app.music.common.BitmapManager;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Timer;
import java.util.TimerTask;

import cn.com.acoe.app.music.R;

/**
 * 自动轮播广告栏组件
 * Created by Acoe on 2016/1/27.
 */
public class AdViewPager extends ViewPager {
    /** 显示的Activity */
    private Context context;
    /** 条目单击事件接口 */
    private MyOnItemClickListener myOnItemClickListener;
    /** 图片切换时间 */
    private int switchTime;
    /** 自动轮播的定时器 */
    private Timer timer;
    /** 小圆点容器 */
    private LinearLayout ovalLayout;
    /** 当前选择的条目索引 */
    private static int curIndex;
    /** 上次选中的条目索引 */
    private int lastIndex;
    /** 小圆点选中时的背景资源id */
    private int ovalFocusedId;
    /** 小圆点未选中时的背景资源id */
    private int ovalNormalId;
    /** 图片资源id数组 */
    private int[] adsId;
    /** 网络图片路径列表 */
    private ArrayList<String> uris;
    /** ImageView数组 */
    private ArrayList<ImageView> imgViews;
    /** 图片异步加载工具 */
    private BitmapManager bitmapManager;

    public AdViewPager(Context context) {
        super(context);
    }

    public AdViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    /**
     *
     * @param context 当前的Activity
     * @param uris 网络图片的路径数组
     * @param adsId 图片资源id数组
     * @param switchTime 广告切换时间
     * @param ovalLayout 小圆点Layout
     * @param ovalFocusedId 小圆点选中状态的背景资源id
     * @param ovalNormalId 小圆点非选中状态的背景资源id
     */
    public void start(Context context, ArrayList<String> uris, int[] adsId, int switchTime, final LinearLayout ovalLayout, final int ovalFocusedId, final int ovalNormalId) {
        this.context = context;
        this.uris = uris;
        this.adsId = adsId;
        if (this.adsId == null) {
            this.adsId = new int[]{ R.drawable.load_default_icon };
        }
        this.switchTime = switchTime;
        this.ovalLayout = ovalLayout;
        this.ovalFocusedId = ovalFocusedId;
        this.ovalNormalId = ovalNormalId;
        if (imgViews != null) {
            imgViews.clear();
        }
        initImages();// 初始化图片组
        this.setAdapter(new ImageAdapter(imgViews));
        initOvalLayout();// 初始化圆点
        this.setOnPageChangeListener(new OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {
            }

            @Override
            public void onPageSelected(int i) {
                if (ovalLayout != null && imgViews.size() > 1) { // 切换圆点
                    ovalLayout.getChildAt(lastIndex).setBackgroundResource(ovalNormalId); // 圆点取消
                    ovalLayout.getChildAt(i % 4).setBackgroundResource(ovalFocusedId); // 圆点选中
                    lastIndex = i % 4;
                }
            }

            @Override
            public void onPageScrollStateChanged(int i) {
            }
        });
        startCycle();// 开始自动滚动任务
    }

    /**
     * 初始化广告图片
     */
    private void initImages() {
        this.bitmapManager = new BitmapManager(getContext());
        this.imgViews = new ArrayList<ImageView>();
        int len = uris != null ? uris.size() : adsId.length;
        for (int i = 0; i < len; i++) {
            ImageView imageView = new ImageView(context);
            imageView.setScaleType(ImageView.ScaleType.FIT_XY); // 设置缩放方式
            imageView.setLayoutParams(new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT));
            if (uris != null) {
                bitmapManager.loadBackgroundBitmap(uris.get(i), imageView);
            } else {
                imageView.setBackgroundResource(adsId[i]);
            }
            imgViews.add(imageView);
        }
    }

    private void initOvalLayout() {
        if (ovalLayout == null) {
            return;
        }
            ovalLayout.removeAllViews();
        if (imgViews.size() < 2) {
            ovalLayout.getLayoutParams().height = 0;
        } else {
            ovalLayout.getLayoutParams().height = getResources().getDimensionPixelSize(R.dimen.dimen_12);
            // 圆点的高度
            int Ovalheight = (int) (ovalLayout.getLayoutParams().height * 0.8);
            // 圆点的左右外边距
            int Ovalmargin = (int) (ovalLayout.getLayoutParams().height * 0.4);
            android.widget.LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(Ovalheight, Ovalheight);
            layoutParams.setMargins(Ovalmargin, 0, Ovalmargin, 0);
            for (int i = 0; i < imgViews.size(); i++) {
                View v = new View(context); // 员点
                v.setLayoutParams(layoutParams);
                v.setBackgroundResource(ovalNormalId);
                ovalLayout.addView(v);
            }
            // 选中第一个
            ovalLayout.getChildAt(0).setBackgroundResource(ovalFocusedId);
        }
    }

    public interface MyOnItemClickListener {
        /**
         * @param curIndex
         *            //当前条目在数组中的下标
         */
        void onItemClick(int curIndex);
    }

    /**
     * 停止轮播
     */
    public void stopCycle() {
        if (timer != null) {
            timer.cancel();
            timer = null;
        }
    }

    /**
     * 开始轮播，图片数量大于1才轮播
     */
    public void startCycle() {
        if (imgViews.size() > 1 && switchTime > 0) {
          timer = new Timer();
          timer.schedule(new TimerTask() {
              @Override
              public void run() {
            	  curIndex++;
                  handler.removeMessages(CycleHandler.PAGE);
                  handler.sendMessage(handler.obtainMessage(CycleHandler.PAGE));
              }
          }, switchTime, switchTime);
        }
    }
    
    private CycleHandler handler = new CycleHandler(new WeakReference<AdViewPager>(this));

    private static class CycleHandler extends Handler {
    	private WeakReference<AdViewPager> wk; // 使用弱引用，避免Handler泄露
    	public static int PAGE = 9527;

    	protected CycleHandler(WeakReference<AdViewPager> wk) {
    		this.wk = wk;
    	}
    	@Override
    	public void handleMessage(Message msg) {
    		super.handleMessage(msg);
    		AdViewPager pager = wk.get();
    		if (pager == null) {
    			return;
    		}
    		pager.setCurrentItem(curIndex, true);
    	}
    }

    private class ImageAdapter extends PagerAdapter {
        private ArrayList<ImageView> imgViews;

        public ImageAdapter(ArrayList<ImageView> imgViews) {
            this.imgViews = imgViews;
        }

        @Override
        public int getCount() {
            return (imgViews == null || imgViews.size() == 0) ? 1 : Integer.MAX_VALUE;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
        }

        @Override
        public boolean isViewFromObject(View view, Object o) {
            return view == o;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            position %= imgViews.size();
            if (position<0){
                position = imgViews.size() + position;
            }
            ImageView view = imgViews.get(position);
            // 如果View已经在之前添加到了一个父组件，则必须先remove，否则会抛出IllegalStateException。
            ViewParent vp = view.getParent();
            if (vp != null) {
                ViewGroup parent = (ViewGroup) vp;
                parent.removeView(view);
            }
            container.addView(view);
            return view;
        }
    }
}
