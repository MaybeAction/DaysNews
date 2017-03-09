package com.feicui.news.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;


import com.feicui.news.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 陈刘磊 on 2017/1/15.
 */

public class MyLeadPagerAdapter extends PagerAdapter {


    //声明放数据的集合
    private List<ImageView> list;
    //声明Context对象
    private Context context;
    //声明图片id数组
    private int[] imageIds;
    public MyLeadPagerAdapter(Context context){
        //实例化
        this.context=context;
        list=new ArrayList<>();
        imageIds=new int[]{R.mipmap.bd, R.mipmap.welcome,R.mipmap.wy};
    }
    @Override
    public int getCount() {
        return imageIds.length;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        //实例化一个ImageView对象
        ImageView imageView=new ImageView(context);
        //设置资源
        imageView.setImageResource(imageIds[position]);
        //设置缩放效果
        imageView.setScaleType(ImageView.ScaleType.FIT_XY);
        //加入集合
        list.add(imageView);
        //加入viewpager
        container.addView(imageView);
        //返回imagView
        return imageView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        //移除集合中的数据
        container.removeView(list.get(position));
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view==object;
    }
}
