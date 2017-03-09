package uianimationdemo.com.example.daysnews.news;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import uianimationdemo.com.example.daysnews.R;
import uianimationdemo.com.example.daysnews.adapter.MyPagerAdapter;

/**
 * 引导界面
 */
public class LeadActivity extends MyBaseActivity implements ViewPager.OnPageChangeListener, RadioGroup.OnCheckedChangeListener, View.OnClickListener {
    private ViewPager vp;
    private MyPagerAdapter adapter;
    private RadioGroup rg;
    private TextView tv;
    private boolean isFirst;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        isFirst = getSharedPreferences("la_isshow", MODE_PRIVATE).getBoolean("isFirst", true);
        if (!isFirst) {
            intentTo(LogoActivity.class);
            finish();
        }
        if (isFirst) {
            getSharedPreferences("la_isshow",MODE_PRIVATE).edit().putBoolean("isFirst",false).commit();
            setContentView(R.layout.activity_lead);

            initView();
            initEvent();
            initData();


        }
    }
    private void initData() {
        vp.setAdapter(adapter);
    }

    private void initEvent() {
        vp.addOnPageChangeListener(this);
        rg.setOnCheckedChangeListener(this);
        tv.setOnClickListener(this);

    }

    private void initView() {
        tv= (TextView) findViewById(R.id.tv);
        vp= (ViewPager) findViewById(R.id.vp);
        rg= (RadioGroup) findViewById(R.id.rg);
        adapter=new MyPagerAdapter(this);

    }


    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        if(position==3){
            tv.setVisibility(View.VISIBLE);
        }else{
            tv.setVisibility(View.GONE);
        }
        RadioButton rb=null;

        switch (position){
            case 0:rb= (RadioButton) findViewById(R.id.radio0);
                break;
            case 1:rb= (RadioButton) findViewById(R.id.radio1);
                break;
            case 2:rb= (RadioButton) findViewById(R.id.radio2);
                break;
            case 3:rb= (RadioButton) findViewById(R.id.radio3);
                break;

        }
            rb.setChecked(true);
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        switch (checkedId){
            case R.id.radio0:vp.setCurrentItem(0);
                break;
            case R.id.radio1:vp.setCurrentItem(1);
                break;
            case R.id.radio2:vp.setCurrentItem(2);
                break;
            case R.id.radio3:vp.setCurrentItem(3);
        }
    }

    @Override
    public void onClick(View v) {
        intentTo(LogoActivity.class);
        finish();
    }
}
