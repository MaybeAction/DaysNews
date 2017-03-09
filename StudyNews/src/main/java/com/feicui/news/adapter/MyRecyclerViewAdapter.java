package com.feicui.news.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.feicui.news.R;
import com.feicui.news.entiy.NewsTypeInfo;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * 适配器要继承RecyclerView.Adapter
 * 还要传递一个泛型，一般是自己写一个内部类
 * 内部类要继承RecyclerView.ViewHolder
 * 然后重新抽象方法
 */

public class MyRecyclerViewAdapter extends RecyclerView.Adapter<MyRecyclerViewAdapter.MyViewHodler>  {

    //得到一个LayoutInflater对象，用来将布局变为View
    private LayoutInflater inflater;
    //保存数据的集合
    private List<NewsTypeInfo> list;

    //声明一个item点击接口对象
    private OnItemClickListener onItemClickListener;


    //当前点击选择的位置
    public int selectedPosition=0;
    public MyRecyclerViewAdapter(Context context){
        inflater=LayoutInflater.from(context);
        list=new ArrayList<>();
    }

    /**
     *
     * @return  数据集合
     */
    public List<NewsTypeInfo> getList() {
        return list;
    }

    /**
     *
     * @param position
     * @return  对应位置数据
     */
    public NewsTypeInfo getItem(int position){
        return list.get(position);
    }
    /**
     *
     * @param info
     */
    public void add(NewsTypeInfo info){
        list.add(info);
    }



    //创建重复利用的ViewHodler对象
    @Override
    public MyViewHodler onCreateViewHolder(ViewGroup parent, int viewType) {


        View itemView = inflater.inflate(R.layout.item_rc,parent,false);

        MyViewHodler myViewHodler=new MyViewHodler(itemView);

        return myViewHodler;
    }


    //在绑定viewHodler之后，添加数据给具体控件
    @Override
    public void onBindViewHolder(MyViewHodler holder, final int position) {

        //设置itemview点击事件
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               if (onItemClickListener!=null){
                   onItemClickListener.onItemClickListener(v,position);
               }
            }
        });

        //显示textview内容以及颜色设置
        holder.tv.setText(list.get(position).getSubgroup());
        //判断当前显示的位置，来显示不同的字体颜色
        if (selectedPosition==position){
            holder.tv.setTextColor(Color.RED);
        }else {
            holder.tv.setTextColor(Color.BLACK);
        }


    }

    //item数量
    @Override
    public int getItemCount() {
        return list.size();
    }




    /**
     * 用来重复利用item布局和控件
     */
    public class MyViewHodler extends RecyclerView.ViewHolder{

        TextView tv;
        //itemview属性
        View itemView;
        public MyViewHodler(View itemView) {
            super(itemView);
            //实例化itemview
            this.itemView=itemView;
            tv= (TextView) itemView.findViewById(R.id.tv);
        }
    }


    /**
     * 声明一个item点击接口
     */
    public interface OnItemClickListener{
        void onItemClickListener(View view,int position);
    }

    /**
     * 实例化item点击接口回调的方法
     *
     */

    public void setOnItemClickListener(OnItemClickListener onItemClickListener){
        this.onItemClickListener=onItemClickListener;
    }
}
