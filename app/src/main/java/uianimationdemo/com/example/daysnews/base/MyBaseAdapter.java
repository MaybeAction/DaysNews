package uianimationdemo.com.example.daysnews.base;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.LruCache;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by Steven on 2017/2/13.
 */

public class MyBaseAdapter<E> extends BaseAdapter {
    protected  boolean isFling=false;

    //集合用来保存数据
    protected List<E> list;

    //Context 对象
    protected Context context;
    //LayoutInflater对象
    protected LayoutInflater inflater;

    public MyBaseAdapter(Context context) {
        this.context=context;
        inflater=inflater.from(context);
        list=new ArrayList<E>();
    }

    public void clear(){
        list.clear();
        notifyDataSetChanged();
    }

    protected static LruCache<String, Bitmap> lruCache = new LruCache<String, Bitmap>(
            5 * 1024 * 1024) {
        protected int sizeOf(String key, Bitmap value) {

            return value.getByteCount();
        }
    };


    public void setIsFling(boolean isFling){
        this.isFling=isFling;
    }

    //添加数据方法
    public void add(E e){
        list.add(e);
        notifyDataSetChanged();
    }
    public void add(List<E> eList){
        list.addAll(eList);
        notifyDataSetChanged();
    }
    public void addReverse(){
        Collections.reverse(list);
        notifyDataSetChanged();
    }

    public List<E> getList() {
        return list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public E getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return null;
    }
}
