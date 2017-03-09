package uianimationdemo.com.example.daysnews.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import uianimationdemo.com.example.daysnews.R;
import uianimationdemo.com.example.daysnews.base.MyBaseAdapter;
import uianimationdemo.com.example.daysnews.entiy.NewsCommentInfo;
import uianimationdemo.com.example.daysnews.utils.LoadImage;

/**
 * Created by Steven on 2017/2/16.
 */

public class CommentXListViewAdapter extends MyBaseAdapter<NewsCommentInfo.Comment>{
    private Context context;


    public CommentXListViewAdapter(Context context) {
        super(context);
        this.context=context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder vh=null;
        if(convertView==null){
            vh=new ViewHolder();
            convertView=inflater.inflate(R.layout.item_webcomment,null);
            vh.iv_wbcomment= (ImageView) convertView.findViewById(R.id.iv_wbcomment);
            vh.tv_wbcomment_cotent= (TextView) convertView.findViewById(R.id.tv_wbcomment_cotent);
            vh.tv_wbcomment_uid= (TextView) convertView.findViewById(R.id.tv_wbcomment_uid);
            vh.tv_wbcomment_stamp= (TextView) convertView.findViewById(R.id.tv_wbcomment_stamp);
            convertView.setTag(vh);
        }else{
            vh= (ViewHolder) convertView.getTag();
        }

        NewsCommentInfo.Comment comment=getItem(position);
        vh.tv_wbcomment_stamp.setText(comment.getStamp());
        vh.tv_wbcomment_cotent.setText(comment.getContent());
        vh.tv_wbcomment_uid.setText(comment.getUid());
        //加载图片
        LoadImage.getLoadImage(context, comment.getPortrait(),vh.iv_wbcomment,80,60);
        return convertView;
    }

    public class ViewHolder{
        ImageView iv_wbcomment;
        TextView tv_wbcomment_cotent,tv_wbcomment_uid,tv_wbcomment_stamp;
    }
}
