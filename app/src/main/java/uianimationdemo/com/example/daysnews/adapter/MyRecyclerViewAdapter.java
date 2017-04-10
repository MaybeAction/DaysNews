package uianimationdemo.com.example.daysnews.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.List;
import uianimationdemo.com.example.daysnews.R;
import uianimationdemo.com.example.daysnews.entiy.NewsListInfo;

/**
 * Created by Steven on 2017/2/9.
 */

public class MyRecyclerViewAdapter extends RecyclerView.Adapter<MyRecyclerViewAdapter.MyViewHolder> {
    private LayoutInflater inflater;
    private List<NewsListInfo> list;
    private MyOnClickListener myOnClickListener;
    private int fragmentPosition;


    public void setMyOnClickListener(MyOnClickListener myOnClickListener) {
        this.myOnClickListener = myOnClickListener;
    }

    public MyRecyclerViewAdapter(Context context) {
        inflater = inflater.from(context);
        list = new ArrayList<>();
    }

    public void add(NewsListInfo info) {
        list.add(info);
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.recyclerview_main_item, parent, false);
        MyViewHolder myViewHolder = new MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.tv_main_item.setText(list.get(position).getSungroup());
        holder.tv_main_item.setTag(position);
        //给字体设置颜色
        if(fragmentPosition==position){
            holder.tv_main_item.setTextColor(Color.RED);
        }else{
            holder.tv_main_item.setTextColor(Color.BLACK);
        }

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public List<NewsListInfo> getList() {
        return list;
    }

    public void getFragmentPosition(int position){
            fragmentPosition=position;
    }

    public void clear() {
        list.clear();
    }


    /**
     * MyViewHolder类
     */
    class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView tv_main_item;

        public MyViewHolder(View itemView) {
            super(itemView);
            tv_main_item = (TextView) itemView.findViewById(R.id.tv_main_item);
            tv_main_item.setOnClickListener(this);
        }
        @Override
        public void onClick(View v) {
            myOnClickListener.MyOnClick((Integer) v.getTag());

        }
    }

    public interface MyOnClickListener {
        void MyOnClick(int position);
    }

}
