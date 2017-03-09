package uianimationdemo.com.example.daysnews.fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Base64;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.tencent.connect.UserInfo;
import com.tencent.connect.auth.QQAuth;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.Tencent;
import com.tencent.tauth.UiError;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.ByteArrayOutputStream;
import java.io.File;
import uianimationdemo.com.example.daysnews.R;
import uianimationdemo.com.example.daysnews.db.MainUrlManager;
import uianimationdemo.com.example.daysnews.news.EntryActivity;
import uianimationdemo.com.example.daysnews.news.MainActivity;
import uianimationdemo.com.example.daysnews.news.UserActivity;
import uianimationdemo.com.example.daysnews.utils.LoadImage;
import uianimationdemo.com.example.daysnews.utils.Util;

public class RightFragment extends Fragment implements View.OnClickListener {
    private ImageView entry,entry_qq;
    private TextView tv_rightfragment_entry,tv_rightfragment_update;
    private Intent intent;
    private String userToken;
    private String message;
    private Tencent mTencent;
    private static final String TAG= MainActivity.class.getName();
    private String mAppid;
    private static QQAuth mQQAuth;
    private final String APP_ID="222222";
    private UserInfo mInfo;
    private boolean isQQEntry=false;
    //由于 传递对象不成功  使用静态变量
    public static String qqName;
    public static Bitmap qqLogo;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_right,container,false);
        userToken=getActivity().getSharedPreferences("userToken",Context.MODE_PRIVATE).getString("token","000");
        initView(view);
        initEvent();
        return view;
    }

    private void initEvent() {
        entry.setOnClickListener(this);
        entry_qq.setOnClickListener(this);
        tv_rightfragment_entry.setOnClickListener(this);
        tv_rightfragment_update.setOnClickListener(this);
    }

    private void initView(View view) {
        tv_rightfragment_update= (TextView) view.findViewById(R.id.tv_rightfragment_update);
        entry= (ImageView) view.findViewById(R.id.entry);
        entry_qq= (ImageView) view.findViewById(R.id.entry_qq);
        tv_rightfragment_entry= (TextView) view.findViewById(R.id.tv_rightfragment_entry);
        tv_rightfragment_entry.setText("立刻登录");
        entry.setImageResource(R.mipmap.biz_pc_main_info_profile_avatar_bg_dark);
        Bitmap b=getQQImage();
        //判断是否用第三方登录过
        if(b==null){
            return;
        }else{
            entry.setImageBitmap(b);
            tv_rightfragment_entry.setText("12345.");
        }
        //判断是否能读取到账号
        if(userToken.equals("000")){
            return;
        }else{
            requestIcon(userToken);
        }
    }

    /**
     * 向QQ请求数据
     */
    private void updateUserInfo(){
        if (mQQAuth != null && mQQAuth.isSessionValid()) {
            IUiListener listener = new IUiListener() {

                @Override
                public void onError(UiError e) {
                    // TODO Auto-generated method stub

                }

                @Override
                public void onComplete(final Object response) {
                    Message msg = new Message();
                    msg.obj = response;
                    msg.what = 0;
                    mHandler.sendMessage(msg);
                    new Thread() {
                        @Override
                        public void run() {
                            JSONObject json = (JSONObject) response;
                            Log.e("eeeeeeeeeeee",response+"");
                            if (json.has("figureurl")) {
                                Bitmap bitmap = null;
                                try {
                                    bitmap = Util.getBitmap(json
                                            .getString("figureurl_qq_2"));
                                } catch (JSONException e) {

                                }
                                Message msg = new Message();
                                Bundle b=new Bundle();
                                b.putString("r",response+"");
                                msg.setData(b);
                                msg.obj = bitmap;
                                msg.what = 1;
                                mHandler.sendMessage(msg);
                            }
                        }

                    }.start();
                }

                @Override
                public void onCancel() {
                }
            };
            mInfo = new UserInfo(getActivity(), mQQAuth.getQQToken());
            mInfo.getUserInfo(listener);

        } else {


        }
    }

    //handler处理消息
    Handler mHandler = new Handler() {

        @Override
        public void handleMessage(Message msg) {

            Bitmap bitmap = null ;
            //消息0是获取qq名称
            if (msg.what == 0) {
                JSONObject response = (JSONObject) msg.obj;
                if (response.has("nickname")) {
                    try {
                        tv_rightfragment_entry.setText(response.getString("nickname"));
                        qqName=response.getString("nickname");
                    } catch (Exception e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }
            } else if (msg.what == 1) {//发送1消息是 获取qq头像
                bitmap = (Bitmap) msg.obj;
                saveQQImage(bitmap);
                entry.setImageBitmap(bitmap);
                qqLogo=bitmap;

                Toast.makeText(getActivity(),  msg.getData().getString("r").toString(), Toast.LENGTH_SHORT).show();
                getActivity().getSharedPreferences("userToken",getActivity().MODE_PRIVATE).edit().putString("token","000").commit();
                getActivity().getSharedPreferences("userToken",getActivity().MODE_PRIVATE).edit().putString("explain","失败").commit();

            }
        }
    };


    /**
     *
     * @param bitmap 第三方登录后获得的QQ头像,用64位字节流存储到本地
     */
    private void saveQQImage(Bitmap bitmap){
        SharedPreferences sharedPreferences=getActivity().getSharedPreferences("qqPhoto", getActivity().MODE_PRIVATE);
        SharedPreferences.Editor editor=sharedPreferences.edit();
        ByteArrayOutputStream byteArrayOutputStream=new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 50, byteArrayOutputStream);
        String headPicBase64=new String(Base64.encodeToString(byteArrayOutputStream.toByteArray(),Base64.DEFAULT));
        editor.putString("qqEntry",headPicBase64);
        editor.commit();

    }


    /**
     *
     * @return bitmap 用SharedPreference读取之前存的图像
     */
    private Bitmap getQQImage(){
        SharedPreferences sharedPreferences=getActivity().getSharedPreferences("qqPhoto",getActivity().MODE_PRIVATE);
        String headPic=sharedPreferences.getString("qqEntry",null);
        if(headPic==null){
            return null;
        }
        Bitmap bitmap=null;
        if (headPic!="") {
            byte[] bytes = Base64.decode(headPic.getBytes(), 1);
            //  byte[] bytes =headPic.getBytes();
            bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
        }
            return bitmap;
    }





    /**
     * 点击登录
     */
    private void onClickLogin() {

        if (!mQQAuth.isSessionValid()) {
            IUiListener listener = new BaseUiListener() {
                @Override
                protected void doComplete(JSONObject values) {
                    updateUserInfo();
                }
            };
            mQQAuth.login(getActivity(), "all", listener);
            mTencent.login(getActivity(), "all", listener);
        } else {
            mQQAuth.logout(getContext());
            updateUserInfo();

        }
    }


    /**
     * 腾讯自带接口
     */
    private class BaseUiListener implements IUiListener {

        @Override
        public void onComplete(Object response) {

            doComplete((JSONObject) response);
        }

        protected void doComplete(JSONObject values) {

        }

        @Override
        public void onError(UiError e) {

        }

        @Override
        public void onCancel() {

        }
    }





    @Override
    public void onClick(View v) {
            switch (v.getId()){
                case R.id.entry:
                case R.id.tv_rightfragment_entry:
                    //读取本地存储的账号
                    userToken=getActivity().getSharedPreferences("userToken",Context.MODE_PRIVATE).getString("token","000");
                    //读取本地的数据，判断是否是第三方登录
                    Bitmap b= getQQImage();
                    if(b!=null){
                        isQQEntry=true;
                    }else{
                        isQQEntry=false;
                    }
                    if(!isQQEntry&&userToken.equals("000")){
                        intent = new Intent(getActivity(), EntryActivity.class);
                        startActivityForResult(intent, 3);
                        break;
                    }
                    //判断是否是QQ登录
                    if(isQQEntry){
                        intent = new Intent(getActivity(), UserActivity.class);
                        startActivityForResult(intent, 4);
                        break;
                    }

                    //判断是否是本地登录
                    if(!userToken.equals("000")) {
                        if(message.equals("OK")) {
                            intent = new Intent(getActivity(), UserActivity.class);
                            startActivityForResult(intent, 4);

                        }else{
                            intent = new Intent(getActivity(), EntryActivity.class);
                            startActivityForResult(intent, 3);
                        }
                    }
                    break;
                case R.id.tv_rightfragment_update:
                    updateVersion();
                    break;
                case R.id.entry_qq:
                    onClickLogin();
                    break;
            }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
                //用户中心（UerActivity）返回键返回码==7
                if(resultCode==7){
                    String path=getActivity().getSharedPreferences("photoPath",Context.MODE_PRIVATE).getString("userPhoto",null);
                    if(path!=null){
                        File f=new File(path);
                        if(f.exists()){
                            Bitmap b= BitmapFactory.decodeFile(path);
                            entry.setImageBitmap(b);
                        }
                    }
                }
        //登录界面（EntryActivity）登录按钮返回码==4
                if(resultCode==4){
                    if (data!=null){
                            String token=data.getStringExtra("token");
                            getActivity().getSharedPreferences("userToken", Context.MODE_PRIVATE).edit().putString("token",token).commit();
                        requestIcon(token);
                    }
                }
        //用户中心（UerActivity）退出登录按钮返回码==6
                if(resultCode==6){
                    tv_rightfragment_entry.setText("立刻登录");
                    entry.setImageResource(R.mipmap.biz_pc_main_info_profile_avatar_bg_dark);
                }
        }

        public void requestIcon(String token){
            //user_home?ver=版本号&imei=手机标识符& token =用户令牌
            String url= MainUrlManager.mainUrl+"user_home?ver=2&imei=2&token="+token;
            //1得到一个请求队列
            RequestQueue queue= Volley.newRequestQueue(getActivity());

            //2创建请求
            Response.Listener<String> listener=new Response.Listener<String>() {
                @Override
                public void onResponse(String s) {
                    try {
                        JSONObject obj=new JSONObject(s);
                        message=obj.getString("message");
                        if(obj.getString("message").equals("OK")){
                            obj=obj.getJSONObject("data");
                            String url=obj.getString("portrait");
                            Log.e("uuuuuuuuu",url);
                            tv_rightfragment_entry.setText(obj.getString("uid"));
                            //设置图片
                            String path=getActivity().getSharedPreferences("photoPath",Context.MODE_PRIVATE).getString("userPhoto",null);
                            if(path!=null){
                                File f=new File(path);
                                if(f.exists()){
                                    Bitmap b= BitmapFactory.decodeFile(path);
                                    entry.setImageBitmap(b);
                                }else{
                                    LoadImage.getLoadImage(getActivity(),url,entry,40,30);
                                }
                            }else{
                            LoadImage.getLoadImage(getActivity(),url,entry,40,30);
                            }

                        }else{
                            Toast.makeText(getActivity(), "与服务器连接有误,请重新登录", Toast.LENGTH_SHORT).show();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            };
            Response.ErrorListener errorListener=new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError volleyError) {
                    Toast.makeText(getActivity(), "网络数据请求失败，", Toast.LENGTH_SHORT).show();
                }
            };

            StringRequest request=new StringRequest(url,listener,errorListener);
            //3请求加入请求队列
            queue.add(request);
        }


    /**
     * 更新版本的接口
     */
    private void updateVersion(){
            //update?imei=唯一识别号&pkg=包名&ver=版本
        String url=MainUrlManager.mainUrl+"update?imei=2&pkg="+getActivity().getPackageName()+"&ver=2";
        RequestQueue queue=Volley.newRequestQueue(getActivity());

        Response.Listener<String> listener=new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                try {
                    JSONObject obj=new JSONObject(s);

                Toast toast=Toast.makeText(getActivity(), "更新地址为: "+obj.getString("link"), Toast.LENGTH_SHORT);
                    onClickTopToast(toast);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        };
        Response.ErrorListener errorListener=new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Toast.makeText(getActivity(), "与服务器连接失败", Toast.LENGTH_SHORT).show();
            }
        };
        StringRequest request=new StringRequest(url,listener,errorListener);
        queue.add(request);

    }

    public void onClickTopToast(Toast toast) {

        toast.setGravity(Gravity.RIGHT, 20,0);
        toast.show();
    }


    /**
     * 第三方登录
     */
    @Override
    public void onStart() {
        mAppid = APP_ID;
        mQQAuth = QQAuth.createInstance(mAppid, getActivity().getApplicationContext());
        mTencent = Tencent.createInstance(mAppid, getActivity());
        super.onStart();
    }
}
