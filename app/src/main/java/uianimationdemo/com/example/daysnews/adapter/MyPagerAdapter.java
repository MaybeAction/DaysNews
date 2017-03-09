package uianimationdemo.com.example.daysnews.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

import uianimationdemo.com.example.daysnews.R;

/**
 * Created by Steven on 2017/2/6.
 */

public class MyPagerAdapter extends PagerAdapter{
    private List<ImageView> imageViewList;
    private Context context;
    private int imageIds[]=new int[]{R.mipmap.wy,R.mipmap.welcome,R.mipmap.small,R.mipmap.bd};

    public MyPagerAdapter(Context context) {
        this.context=context;
        imageViewList=new ArrayList<>();
        //添加图片


    }

    @Override
    public int getCount() {
        return imageIds.length;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view==object;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        //移除将要移出的ImageView
        container.removeView(imageViewList.get(position));
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        ImageView imageView=new ImageView(context);
        //imageView设置ID
        imageView.setImageResource(imageIds[position]);
        imageView.setScaleType(ImageView.ScaleType.FIT_XY);
        //将ImageView加入要显示的ViewGroup中
        container.addView(imageView);
        //添加进集合
        imageViewList.add(imageView);
        return imageView;
    }
}
