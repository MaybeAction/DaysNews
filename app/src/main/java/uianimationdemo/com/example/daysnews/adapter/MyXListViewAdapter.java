package uianimationdemo.com.example.daysnews.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import uianimationdemo.com.example.daysnews.R;
import uianimationdemo.com.example.daysnews.base.MyBaseAdapter;
import uianimationdemo.com.example.daysnews.entiy.NewsListShow;
import uianimationdemo.com.example.daysnews.utils.LoadImage;

/**
 * 新闻列表的适配器
 */

public class MyXListViewAdapter extends MyBaseAdapter<NewsListShow.News> {
    private ViewHolder vh;
    private Context context;

    public MyXListViewAdapter(Context context) {
        super(context);
        this.context=context;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        vh=null;
        if(convertView==null){
            vh=new ViewHolder();
            convertView=inflater.inflate(R.layout.item_xlistview,null);
            vh.iv_XListView= (ImageView) convertView.findViewById(R.id.iv_XListView);
            vh.tv_XListView_news= (TextView) convertView.findViewById(R.id.tv_XListView_news);
            vh.tv_XListView_time= (TextView) convertView.findViewById(R.id.tv_XListView_time);
            vh.tv_XListView_title= (TextView) convertView.findViewById(R.id.tv_XListView_title);
            vh.ll_xListView= (LinearLayout) convertView.findViewById(R.id.ll_xListView);
            //当使用两个参数的标记时，前面的Id要在values里的ids给他指定
            convertView.setTag(R.id.tag_first,vh);
        }else{
            vh= (ViewHolder) convertView.getTag(R.id.tag_first);
        }
        NewsListShow.News news= (NewsListShow.News) getItem(position);
        vh.ll_xListView.setTag(R.id.tag_second,position);
        vh.tv_XListView_news.setText(news.getSummary());
        vh.tv_XListView_time.setText(news.getStamp());
        vh.tv_XListView_title.setText(news.getTitle());
        //调用加载图片的方法
        LoadImage.getLoadImage(context,news.getIcon(),vh.iv_XListView,80,60);
        return convertView;
    }


    class ViewHolder{
        TextView tv_XListView_news,tv_XListView_time,tv_XListView_title;
        ImageView iv_XListView;
        LinearLayout ll_xListView;
    }

}
