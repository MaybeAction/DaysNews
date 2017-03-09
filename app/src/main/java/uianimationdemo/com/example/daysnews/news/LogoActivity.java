package uianimationdemo.com.example.daysnews.news;

import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import uianimationdemo.com.example.daysnews.R;

public class LogoActivity extends MyBaseActivity implements Animation.AnimationListener {
    private ImageView iv;
    private Animation am;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logo);
        initView();
    }

    private void initView() {
        iv= (ImageView) findViewById(R.id.iv);
        am= AnimationUtils.loadAnimation(this,R.anim.anim);
        //启动这个动画效果
        iv.startAnimation(am);
        am.setAnimationListener(this);


    }

    @Override
    public void onAnimationStart(Animation animation) {

    }

    @Override
    public void onAnimationEnd(Animation animation) {

                intentTo(MainActivity.class);
                finish();
    }

    @Override
    public void onAnimationRepeat(Animation animation) {

    }
}
