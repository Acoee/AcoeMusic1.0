package com.app.music.ui.current;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout.LayoutParams;

import com.app.music.adapter.ChoosePageAdapter;
import com.app.music.ui.base.BaseFragment;

import cn.com.acoe.app.music.R;

/**
 * 当前音乐Fragment
 * Created by Administrator on 2015/9/2.
 */
public class CurrentFragment extends BaseFragment{
    private String TAG = "CurrentFragment";
    
    private AssetManager assetMgr;
    private String[] imgUrls;
    private ViewPager choosePager;
	private ChoosePageAdapter chooseAdapter;
	private	ArrayList<ImageView> chooseList;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return super.onCreateView(R.layout.current_music_fragment, TAG, inflater, container, savedInstanceState);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        if(chooseList == null) {
        	initUI();
        }
    }

    private void initUI() {
    	Log.i(TAG, "initUI()");
		this.chooseList = new ArrayList<ImageView>();
		loadImg();
    	choosePager = (ViewPager) this.context.findViewById(R.id.current_music_bg_viewpager);
    	chooseAdapter = new ChoosePageAdapter(chooseList);
		choosePager.setAdapter(chooseAdapter);
    }
    
    private void loadImg() {
		assetMgr = this.getActivity().getAssets();
		InputStream is = null;
		ArrayList<Bitmap> bitmapList = new ArrayList<Bitmap>();
		LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
		try {
			imgUrls = assetMgr.list("img");
			for (String str : imgUrls) {
				Log.i("Main", "str: " + "img/" + str);
				if (str.endsWith(".png") || str.endsWith(".jpg") || str.endsWith(".gif")) {
					try {
						is = assetMgr.open("img/" + str);
						if (is == null) continue;
						Bitmap bitmap = BitmapFactory.decodeStream(is);
						bitmapList.add(bitmap);
						ImageView imgView = new ImageView(getActivity().getApplicationContext());
						imgView.setLayoutParams(params);
//						imgView.setBackgroundDrawable(new BitmapDrawable(bitmap));
						imgView.setImageBitmap(bitmap);
						chooseList.add(imgView);
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
