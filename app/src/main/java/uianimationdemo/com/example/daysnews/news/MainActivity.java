package uianimationdemo.com.example.daysnews.news;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import uianimationdemo.com.example.daysnews.R;
import uianimationdemo.com.example.daysnews.fragment.CommentFragment;
import uianimationdemo.com.example.daysnews.fragment.FavoriteFragment;
import uianimationdemo.com.example.daysnews.fragment.LeftFragment;
import uianimationdemo.com.example.daysnews.fragment.NewsFragment;
import uianimationdemo.com.example.daysnews.fragment.RightFragment;
import uianimationdemo.com.example.daysnews.slidingmenu.SlidingMenu;

public class MainActivity extends MyBaseActivity implements View.OnClickListener {
    private ImageView iv_title_left;
    private ImageView iv_title_right;
    private SlidingMenu slidingMenu;
    private NewsFragment newsFragment;
    private FrameLayout main_content;
    private CommentFragment commentFragment;
    private FavoriteFragment favoriteFragment;
    private TextView tv_title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        //初始化SlidingMenu
        initSlidingMenu();
        //初始化控件
        initView();
        //初始化事件
        initEvent();

    }

    /**
     * 初始化控件
     */
    private void initView() {
        iv_title_left= (ImageView) findViewById(R.id.iv_title_left);
        iv_title_right= (ImageView) findViewById(R.id.iv_title_right);
        tv_title= (TextView) findViewById(R.id.tv_title);

        main_content= (FrameLayout) findViewById(R.id.main_content);
        newsFragment=new NewsFragment();
        commentFragment=new CommentFragment();
        favoriteFragment=new FavoriteFragment();

        showNewsListFragment();

    }

    /**
     * 初始化事件
     */
    private void initEvent() {
        iv_title_left.setOnClickListener(this);
        iv_title_right.setOnClickListener(this);


    }

    /**
     * 初始化SlidingMenu
     */
    private void initSlidingMenu() {
        //设置一个对象
        slidingMenu=new SlidingMenu(this);
        //设置菜单的模式
        slidingMenu.setMode(SlidingMenu.LEFT_RIGHT);
        //设置菜单的触发范围，全屏触发
        slidingMenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_MARGIN);
        //设置主界面最后显示的宽度
        slidingMenu.setBehindOffset(200);
        //关联Activity
        slidingMenu.attachToActivity(this,SlidingMenu.SLIDING_CONTENT);
        //设置左侧菜单布局
        slidingMenu.setMenu(R.layout.left_menu);
        //设置右侧菜单布局
        slidingMenu.setSecondaryMenu(R.layout.right_menu);
        //实例化左侧fragment
        Fragment leftMenuFragment=new LeftFragment();
        //实例化右侧fragment
        Fragment rightMenuFragment=new RightFragment();
        //将fragment添加到左侧布局中
        getSupportFragmentManager().beginTransaction().replace(R.id.ll_menu_left,leftMenuFragment).commit();
        //将fragment添加到右侧布局中
        getSupportFragmentManager().beginTransaction().replace(R.id.ll_menu_right,rightMenuFragment).commit();


    }


    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.iv_title_left:
                if(slidingMenu!=null&&slidingMenu.isMenuShowing()){
                    slidingMenu.showContent();
                }else if(slidingMenu!=null){
                    slidingMenu.showMenu();
                }
                break;
            case R.id.iv_title_right:
                if(slidingMenu!=null&&slidingMenu.isMenuShowing()){
                    slidingMenu.showContent();
                }else if(slidingMenu!=null){
                    slidingMenu.showSecondaryMenu();
                }
                break;
        }
    }


    /**
     * 显示资讯
     */
    public void showNewsListFragment(){
        if(slidingMenu.isMenuShowing()){
            //显示主界面
            slidingMenu.showContent();
        }
        tv_title.setText("资讯");
        getSupportFragmentManager().beginTransaction().replace(R.id.main_content,newsFragment).commit();
    }

    /**
     * 显示跟帖界面
     */
    public void showCommentFragment(){
        if(slidingMenu.isMenuShowing()){
            slidingMenu.showContent();
        }
        tv_title.setText("跟帖");
        getSupportFragmentManager().beginTransaction().replace(R.id.main_content,commentFragment).commit();
    }

    /**
     * 显示收藏界面
     */
    public void showFavoriteFragment(){
        if(slidingMenu.isMenuShowing()){
            slidingMenu.showContent();
        }
        tv_title.setText("收藏");

        getSupportFragmentManager().beginTransaction().replace(R.id.main_content,favoriteFragment).commit();
    }
}
