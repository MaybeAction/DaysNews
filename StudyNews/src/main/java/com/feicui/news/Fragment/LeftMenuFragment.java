package com.feicui.news.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;


import com.feicui.news.R;
import com.feicui.news.activity.MainActivity;
import com.feicui.news.base.BaseFragment;


public class LeftMenuFragment extends BaseFragment implements View.OnClickListener{

    private View view;
    private RelativeLayout rl_news,rl_reading,rl_local,rl_commnet,rl_photo;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_left_menu,container,false);
        initView(view);
        initEvent();
        return view;
    }

    private void initEvent() {
        rl_news.setOnClickListener(this);
        rl_reading.setOnClickListener(this);

        rl_local.setOnClickListener(this);
        rl_commnet.setOnClickListener(this);
        rl_photo.setOnClickListener(this);
    }

    private void initView(View view) {
        rl_news=(RelativeLayout) view.findViewById(R.id.rl_news);
        rl_reading=(RelativeLayout) view.findViewById(R.id.rl_reading);
        rl_local=(RelativeLayout) view.findViewById(R.id.rl_local);
        rl_commnet=(RelativeLayout) view.findViewById(R.id.rl_commnet);
        rl_photo=(RelativeLayout) view.findViewById(R.id.rl_photo);
    }


    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.rl_news:
                MainActivity activity= (MainActivity) getActivity();
                activity.showNewsListFragment();
                break;
            case R.id.rl_reading:
                MainActivity activity1= (MainActivity) getActivity();
                activity1.showCommentFragment();
                break;
            case R.id.rl_local:
                showToast("本地");
                break;
            case R.id.rl_commnet:
                showToast("跟帖评论");

                break;
            case R.id.rl_photo:
                showToast("图片");
            break;

        }

    }
}
