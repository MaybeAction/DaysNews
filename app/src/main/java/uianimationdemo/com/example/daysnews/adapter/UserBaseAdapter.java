package uianimationdemo.com.example.daysnews.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import uianimationdemo.com.example.daysnews.R;
import uianimationdemo.com.example.daysnews.base.MyBaseAdapter;
import uianimationdemo.com.example.daysnews.entiy.UserInfomation;

/**
 * Created by Steven on 2017-2-20.
 */

public class UserBaseAdapter extends MyBaseAdapter<UserInfomation.User>{

    public UserBaseAdapter(Context context) {
        super(context);
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder vh=null;
        if(convertView==null){
            vh=new ViewHolder();
            convertView=inflater.inflate(R.layout.item_user,null);
            vh.tv_lv_time= (TextView) convertView.findViewById(R.id.tv_lv_time);
            vh.tv_lv_address= (TextView) convertView.findViewById(R.id.tv_lv_address);
            vh.tv_lv_device= (TextView) convertView.findViewById(R.id.tv_lv_device);
            convertView.setTag(vh);
        }else{
            vh= (ViewHolder) convertView.getTag();
        }

        UserInfomation.User userinfo=getItem(position);
        vh.tv_lv_time.setText(userinfo.getTime().substring(0,10));
        vh.tv_lv_address.setText(userinfo.getAddress());
        if(userinfo.getDevice()==0){
        vh.tv_lv_device.setText("手机");
        }else{
            vh.tv_lv_device.setText("PC网页端");
        }


        return convertView;
    }

    public class ViewHolder{
            TextView tv_lv_time,tv_lv_address,tv_lv_device;
    }
}
