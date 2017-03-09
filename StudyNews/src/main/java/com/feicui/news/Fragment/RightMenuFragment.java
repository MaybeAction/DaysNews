package com.feicui.news.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.feicui.news.R;
import com.feicui.news.base.BaseFragment;




public class RightMenuFragment extends BaseFragment implements View.OnClickListener {

    private View view;
    private TextView tv_update_version;
    private ImageView iv_share_wx, iv_share_qq, iv_share_friends, iv_share_weibo;
    private RelativeLayout rl_unlogin;
    private RelativeLayout relativelayout_logined;
    private ImageView iv_photo;
    private TextView tv_name, tv_oneKeyShare;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_right_menu, container, false);

        initView(view);
        initEvent();
        return view;
    }

    /**
     * 控件的一些事件
     */
    private void initEvent() {
        tv_update_version.setOnClickListener(this);
        iv_share_wx.setOnClickListener(this);
        tv_oneKeyShare.setOnClickListener(this);
        iv_share_qq.setOnClickListener(this);
        iv_share_friends.setOnClickListener(this);
        iv_share_weibo.setOnClickListener(this);
        rl_unlogin.setOnClickListener(this);
        relativelayout_logined.setOnClickListener(this);

    }



    /**
     * 初始化控件
     * @param view  显示的view
     */
    private void initView(View view) {
        tv_update_version = (TextView) view.findViewById(R.id.tv_update_version);
        tv_oneKeyShare = (TextView) view.findViewById(R.id.tv_oneKeyShare);
        iv_share_wx = (ImageView) view.findViewById(R.id.iv_share_wx);
        iv_share_qq = (ImageView) view.findViewById(R.id.iv_share_qq);
        iv_share_friends = (ImageView) view.findViewById(R.id.iv_share_friends);
        iv_share_weibo = (ImageView) view.findViewById(R.id.iv_share_weibo);
        rl_unlogin = (RelativeLayout) view.findViewById(R.id.rl_unlogin);
        relativelayout_logined = (RelativeLayout) view.findViewById(R.id.relativelayout_logined);
        iv_photo = (ImageView) view.findViewById(R.id.iv_photo);
        tv_name = (TextView) view.findViewById(R.id.tv_name);


    }

    @Override
    public void onClick(View v) {
        Intent intent = null;
        switch (v.getId()) {
            case R.id.tv_update_version:

                break;
            case R.id.iv_share_wx:
                showToast("登录微信");
                break;
            case R.id.iv_share_qq:
                showToast("登录qq");
                break;
            case R.id.iv_share_friends:
                showToast("登录朋友圈");
                break;
            case R.id.tv_oneKeyShare:


                break;
            case R.id.iv_share_weibo:
                showToast("登录微博");

                break;
            case R.id.rl_unlogin:

                break;
            case R.id.relativelayout_logined:


                break;
        }
    }
}
