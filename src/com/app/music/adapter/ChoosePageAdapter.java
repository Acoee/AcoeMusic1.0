package com.app.music.adapter;

import java.util.ArrayList;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

public class ChoosePageAdapter extends PagerAdapter {
	private ArrayList<ImageView> mListViews;  
	
    
    public ChoosePageAdapter(ArrayList<ImageView> mListViews) {  
        this.mListViews = mListViews; // 构造方法，参数是我们的页卡，这样比较方便。  
    } 

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
    	container.removeView(mListViews.get(position));
    }
    
    @Override
    public Object instantiateItem(ViewGroup container, int position) {
    	container.addView(mListViews.get(position), 0);
    	return mListViews.get(position);
    }
    
	@Override
	public int getCount() {
		return mListViews.size();
	}

	@Override
	public boolean isViewFromObject(View arg0, Object arg1) {
		return arg0 == arg1;
	}

}
