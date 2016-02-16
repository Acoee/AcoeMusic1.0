package com.app.music.frame.center;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.app.music.frame.base.BaseFragment;
import com.fortysevendeg.swipelistview.SwipeListView;

import cn.com.acoe.app.music.R;

/**用户个人中心界面
 * Created by Administrator on 2015/9/2.
 */
public class CenterFragment extends BaseFragment implements View.OnClickListener{
    private static final String TAG = "CenterFragment";
    // 控件
    private RelativeLayout rlLocalMusic, rlFavoriteMusic, rlDownloadMusic;
    private TextView txtUserName, txtUserAutograph, txtRecordInfo, txtLocalNum, txtFavoriteNum, txtDownloadNum, txtCreateList;
    private SwipeListView userMusicList;
    private ImageView imgUserPhoto;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return super.onCreateView(R.layout.user_center_fragment, TAG, inflater, container, savedInstanceState);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        if (rlLocalMusic == null) initUI();
    }

    /**
     * 初始化界面
     */
    private void initUI() {
        // 初始化控件
    	this.rlLocalMusic = (RelativeLayout) this.context.findViewById(R.id.user_local_music_rl);
        this.rlFavoriteMusic = (RelativeLayout) this.context.findViewById(R.id.user_favorite_music_rl);
        this.rlDownloadMusic = (RelativeLayout) this.context.findViewById(R.id.user_download_music_rl);
        this.txtUserName = (TextView) this.context.findViewById(R.id.user_center_nickname_textview);
        this.txtUserAutograph = (TextView) this.context.findViewById(R.id.user_center_sign_textview);
        this.txtRecordInfo = (TextView) this.context.findViewById(R.id.user_center_record_textview);
        this.txtLocalNum = (TextView) this.context.findViewById(R.id.user_local_music_amount_textview);
        this.txtFavoriteNum = (TextView) this.context.findViewById(R.id.user_favorite_music_amount_textview);
        this.txtDownloadNum = (TextView) this.context.findViewById(R.id.user_download_music_amount_textview);
        this.txtCreateList = (TextView) this.context.findViewById(R.id.user_create_list_textview);
        this.userMusicList = (SwipeListView) this.context.findViewById(R.id.user_center_list);
        this.imgUserPhoto = (ImageView) this.context.findViewById(R.id.user_center_photo_imageview);
        // 绑定点击事件
        this.rlLocalMusic.setOnClickListener(this);
        this.rlFavoriteMusic.setOnClickListener(this);
        this.rlDownloadMusic.setOnClickListener(this);
        this.txtCreateList.setOnClickListener(this);
        this.imgUserPhoto.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.user_local_music_rl:
                Intent localIntent = new Intent(this.getActivity(), LocalMusicActivity.class);
                startActivity(localIntent);
                break;
            case R.id.user_favorite_music_rl:
                // TODO
                break;
            case R.id.user_download_music_rl:
                // TODO
                break;
            case R.id.user_create_list_textview:
                // TODO
                break;
            case R.id.user_center_photo_imageview:
                // TODO
                break;
        }
    }

}
