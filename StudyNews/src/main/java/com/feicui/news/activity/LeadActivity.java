package com.feicui.news.activity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.feicui.news.R;
import com.feicui.news.adapter.MyLeadPagerAdapter;
import com.feicui.news.base.BaseActivity;


public class LeadActivity extends BaseActivity implements ViewPager.OnPageChangeListener, RadioGroup.OnCheckedChangeListener {

    //声明控件
    private ViewPager vp;
    private RadioGroup rg;
    //声明适配器
    private MyLeadPagerAdapter adapter;
    //声明一个sharedPreferences对象
    private SharedPreferences sp;
    //声明一个boolean值，来确定是否是第一次启动
    private boolean isFirst;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sp = getSharedPreferences("leadshared", MODE_PRIVATE);
        isFirst = sp.getBoolean("isFirst", false);
        if (isFirst) {
          //  getSharedPreferences("isLogin", Context.MODE_PRIVATE).edit().putBoolean("isLogined",false).commit();
            startActivity(LogoActivity.class, true);
        } else {
            sp.edit().putBoolean("isFirst", true).commit();
            setContentView(R.layout.activity_lead);
            initView();
            initEvent();
            //实例化适配器
            adapter = new MyLeadPagerAdapter(this);

            //设置适配器
            vp.setAdapter(adapter);
        }
    }

    /**
     * 控件事件
     */
    private void initEvent() {
        //viewPager的滑动监听
        vp.addOnPageChangeListener(this);
        //RadioGroup的选择监听
        rg.setOnCheckedChangeListener(this);
    }

    /**
     * 初始化控件
     */
    private void initView() {
        vp = (ViewPager) findViewById(R.id.vp);
        rg = (RadioGroup) findViewById(R.id.rg);
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        RadioButton radioButton = null;
        switch (position) {
            case 0:
                radioButton = (RadioButton) findViewById(R.id.rb1);
                break;
            case 1:
                radioButton = (RadioButton) findViewById(R.id.rb2);
                break;
            case 2:
                radioButton = (RadioButton) findViewById(R.id.rb3);

                break;
        }
        radioButton.setChecked(true);
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        switch (checkedId) {
            case R.id.rb1:
                vp.setCurrentItem(0);
                break;
            case R.id.rb2:
                vp.setCurrentItem(1);
                break;
            case R.id.rb3:
                vp.setCurrentItem(2);
                startActivity(LogoActivity.class, true);
                break;
        }
    }
}
