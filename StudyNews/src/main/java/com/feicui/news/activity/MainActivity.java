package com.feicui.news.activity;

import android.app.Fragment;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.feicui.news.Fragment.CommentFragment;
import com.feicui.news.Fragment.LeftMenuFragment;
import com.feicui.news.Fragment.NewsListFragment;
import com.feicui.news.Fragment.RightMenuFragment;
import com.feicui.news.R;
import com.feicui.news.base.BaseActivity;
import com.feicui.news.view.slidingmenu.SlidingMenu;


public class MainActivity extends BaseActivity {


    private RelativeLayout layout_content;
    //声明SlidingMenu对象
    public static SlidingMenu slidingMenu;

    private Fragment newsListFragment;

    private TextView tv_title;
    private ImageView iv_set,iv_user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initSlidingMenu();
        initView();
        initEvent();
        setTitle("资讯");

    }


    @Override
    protected void onDestroy() {
        super.onDestroy();

    }




    private void initEvent() {

        iv_set.setOnClickListener(listener);
        iv_user.setOnClickListener(listener);
    }

    //标题栏两个按钮的点击事件
    View.OnClickListener listener=new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.iv_set:

                    if (slidingMenu != null && slidingMenu.isMenuShowing()) {//菜单不为空，菜单正在显示

                        //显示主界面
                        slidingMenu.showContent();
                    } else if (slidingMenu != null) {//如果菜单没有显示
                        //显示左菜单
                        slidingMenu.showMenu();
                    }
                    break;
                case R.id.iv_user:
                    if (slidingMenu != null && slidingMenu.isMenuShowing()) {
                        slidingMenu.showContent();
                    } else if (slidingMenu != null) {
                        //显示右菜单
                        slidingMenu.showSecondaryMenu();
                    }
                    break;
            }
        }
    };

    private void initView() {
        tv_title= (TextView) findViewById(R.id.tv_title);
        iv_set= (ImageView) findViewById(R.id.iv_set);
        iv_user= (ImageView) findViewById(R.id.iv_user);
        layout_content= (RelativeLayout) findViewById(R.id.layout_content);

        showNewsListFragment();

    }

    /**
     * 初始化菜单
     */
    private void initSlidingMenu() {
        //实例化对象
        slidingMenu = new SlidingMenu(this);
        //模式设置
        slidingMenu.setMode(SlidingMenu.LEFT_RIGHT);
        //触发模式
        slidingMenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
        //设置偏移距离
        slidingMenu.setBehindOffset(200);
        //slidingMenu.setBehindOffsetRes(R.dimen.slidingmenu_offset);
        //设置activity
        slidingMenu.attachToActivity(this, SlidingMenu.SLIDING_CONTENT);
        //设置左侧布局
        slidingMenu.setMenu(R.layout.menu_left);

        //设置右侧布局
        slidingMenu.setSecondaryMenu(R.layout.menu_right);

        //实例化左侧fragment
        Fragment leftMenuFragment = new LeftMenuFragment();
        //实例化右侧fragment
        Fragment rightMenuFragment=new RightMenuFragment();
        //将fragment添加到左侧布局中
        getFragmentManager().beginTransaction().replace(R.id.ll_menu_left,
                leftMenuFragment).commit();
        //将fragment添加到右侧布局中
        getFragmentManager().beginTransaction().replace(R.id.ll_menu_right,
                rightMenuFragment).commit();

    }



    /**
     * 更换当前界面的标题
     */
    private void setTitle(String title){
        tv_title.setText(title);
    }


    @Override
    public void onBackPressed() {
        if (slidingMenu.isMenuShowing()) {
            slidingMenu.showContent();
        } else {
            exitTwice();
        }
    }

    //两次退出
    private boolean isFirstExit=true;
    private void exitTwice(){
        if(isFirstExit){
          showToast("再按一次退出");
            isFirstExit=false;
            new Thread(){
                public void run() {
                    try {
                        Thread.sleep(3000);
                        isFirstExit=true;
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                };
            }.start();
        }else{
            finish();
        }
    }


    public void showNewsListFragment(){
        if (slidingMenu.isMenuShowing()){//如果菜单在显示
            //显示主界面
            slidingMenu.showContent();
        }
        getFragmentManager().beginTransaction().replace(R.id.layout_content,new NewsListFragment()).commit();
    }

    public void showCommentFragment(){
        if (slidingMenu.isMenuShowing()){
            //显示主界面
            slidingMenu.showContent();
        }
        getFragmentManager().beginTransaction().replace(R.id.layout_content,new CommentFragment()).commit();
    }
}
