package com.app.music.ui.center;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.app.music.app.AppContext;
import com.app.music.common.BitmapManager;
import com.app.music.common.SystemUtils;
import com.app.music.entity.user.UserBean;
import com.app.music.manager.LocalMusicDataMgr;
import com.app.music.ui.base.BaseFragment;
import com.app.music.ui.start.LoginActivity;
import com.fortysevendeg.swipelistview.SwipeListView;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.GetListener;
import cn.com.acoe.app.music.R;

/**用户个人中心界面
 * Created by Administrator on 2015/9/2.
 */
public class CenterFragment extends BaseFragment implements View.OnClickListener{
    private static final String TAG = "CenterFragment";
    private LocalMusicDataMgr localMusicDataMgr;
    private BitmapManager bitmapManager;
    // 控件
    private RelativeLayout rlLocalMusic, rlFavoriteMusic, rlDownloadMusic;
    private TextView txtUserName, txtUserSignature, txtRecordInfo, txtLocalNum, txtFavoriteNum, txtDownloadNum, txtCreateList;
    private SwipeListView userMusicList;
    private ImageView imgUserPhoto;
    // 数据
    private UserBean nowUser; // 当前用户

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return super.onCreateView(R.layout.user_center_fragment, TAG, inflater, container, savedInstanceState);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        Bmob.initialize(context, "c5c2a5a9fe8d7d54dc71cbbb37a6b6d2");
        if (rlLocalMusic == null) initUI();
        login();
        loadData();
    }

    /**
     * 初始化界面
     */
    private void initUI() {
        localMusicDataMgr = new LocalMusicDataMgr(this);
        // 初始化控件
    	this.rlLocalMusic = (RelativeLayout) this.context.findViewById(R.id.user_local_music_rl);
        this.rlFavoriteMusic = (RelativeLayout) this.context.findViewById(R.id.user_favorite_music_rl);
        this.rlDownloadMusic = (RelativeLayout) this.context.findViewById(R.id.user_download_music_rl);
        this.txtUserName = (TextView) this.context.findViewById(R.id.user_center_nickname_textview);
        this.txtUserSignature = (TextView) this.context.findViewById(R.id.user_center_sign_textview);
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
                if (AppContext.isLogined) {
                    // TODO
                } else {
                    Intent intent = new Intent(context, LoginActivity.class);
                    startActivity(intent);
                }
                break;
        }
    }

    /**
     * 用户登录
     */
    private void login() {

        if (AppContext.isLogined) {
            nowUser = AppContext.loginUser;
            BmobQuery<UserBean> result = new BmobQuery<UserBean>();
            result.getObject(context, nowUser.getObjectId(), new GetListener<UserBean>() {
                @Override
                public void onSuccess(UserBean userBean) {
                    bitmapManager.loadBackgroundBitmap(nowUser.photoUrl, imgUserPhoto);
                    txtUserName.setText(nowUser.userName);
                    if ("男".equals(nowUser.gender)) {
                        txtUserName.setCompoundDrawables(null, null, getResources().getDrawable(R.drawable.user_gender_male), null);
                    } else {
                        txtUserName.setCompoundDrawables(null, null, getResources().getDrawable(R.drawable.user_gender_female), null);
                    }
                    txtUserSignature.setText(nowUser.signature);
                    txtRecordInfo.setText("等级    " + nowUser.memberLevel + "级  |  播放    " + nowUser.listenAmount); // 等级    0级  |  播放    102
                }

                @Override
                public void onFailure(int i, String s) {
                    SystemUtils.ToastShow("登录失败，请重新登录", false, getUserVisibleHint());
                    imgUserPhoto.setBackgroundResource(R.drawable.default_user_photo);
                    txtUserName.setText("还未登录喔~");
                    txtUserName.setCompoundDrawables(null, null, null, null);
                    txtUserSignature.setText("");
                    txtRecordInfo.setText("等级    0级");
                }
            });
        } else {
            imgUserPhoto.setBackgroundResource(R.drawable.default_user_photo);
            txtUserName.setText("还未登录喔~");
            txtUserName.setCompoundDrawables(null, null, null, null);
            txtUserSignature.setText("");
            txtRecordInfo.setText("等级    0级");
        }
    }

    /**
     * 加载数据
     */
    private void loadData() {
        this.localMusicDataMgr.getLocalMusicAmount(context);
    }

    @Override
    public void onBack(int what, int arg1, int arg2, Object obj) {
        super.onBack(what, arg1, arg2, obj);
        switch (what) {
            case LocalMusicDataMgr.LOCAL_MUSIC_AMOUNT_QUERY_SUCCESS:
                String result = (String) obj;
                txtLocalNum.setText(result);
                break;
            case LocalMusicDataMgr.LOCAL_MUSIC_AMOUNT_QUERY_FAILURE:
                SystemUtils.ToastShow(obj.toString(), false);
                break;
        }
    }
}
