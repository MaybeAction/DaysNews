package com.feicui.news.activity;

import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.RelativeLayout;


import com.feicui.news.R;
import com.feicui.news.base.BaseActivity;

public class LogoActivity extends BaseActivity implements Animation.AnimationListener {

    private RelativeLayout activity_logo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logo);
        activity_logo= (RelativeLayout) findViewById(R.id.activity_logo);
        Animation animation= AnimationUtils.loadAnimation(this,R.anim.logo);
        activity_logo.startAnimation(animation);

        //设置动画监听
        animation.setAnimationListener(this);
    }

    @Override
    public void onAnimationStart(Animation animation) {
        //动画开始
    }

    @Override
    public void onAnimationEnd(Animation animation) {
        //动画结束
        startActivity(MainActivity.class,true);
    }

    @Override
    public void onAnimationRepeat(Animation animation) {
        //动画重复
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //释放资源
        activity_logo.clearAnimation();
    }
}
