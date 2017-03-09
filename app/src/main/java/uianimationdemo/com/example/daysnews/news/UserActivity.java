package uianimationdemo.com.example.daysnews.news;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Xfermode;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.content.SharedPreferencesCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import uianimationdemo.com.example.daysnews.R;
import uianimationdemo.com.example.daysnews.adapter.UserBaseAdapter;
import uianimationdemo.com.example.daysnews.db.MainUrlManager;
import uianimationdemo.com.example.daysnews.entiy.UserInfomation;
import uianimationdemo.com.example.daysnews.utils.LoadImage;

/**
 * 用户中心界面
 */
public class UserActivity extends AppCompatActivity implements View.OnClickListener {
    private ListView lv_user;
    private ImageView iv_web_title_left,iv_web_title_right,iv_user_photo;
    private Button bt_user;
    private UserBaseAdapter adapter;
    private UserInfomation info;
    private TextView tv_user_uid,tv_user_integration,tv_user_comnum,tv_web_title;
    private String token;
    private PopupWindow pop;
    private View popView;
    private TextView tv_pop_user_photo,tv_pop_user_takePhoto;
    private RelativeLayout re;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);
        token=getSharedPreferences("userToken",MODE_PRIVATE).getString("token","000");
        initView();
        initEvent();
        iniData();
        String qqEntry=getSharedPreferences("qqPhoto",MODE_PRIVATE).getString("qqEntry",null);
        if (qqEntry != null) {
            re.setVisibility(View.GONE);
        }else{
            re.setVisibility(View.VISIBLE);
        requestInfomation(token);

        }
    }

    private void iniData() {
        tv_web_title.setText("用户中心");
    }

    private void initEvent() {
        iv_web_title_right.setVisibility(View.INVISIBLE);
        iv_web_title_left.setOnClickListener(this);
        bt_user.setOnClickListener(this);
        iv_user_photo.setOnClickListener(this);

    }


    private void initView() {
        tv_web_title= (TextView) findViewById(R.id.tv_web_title);
        re= (RelativeLayout) findViewById(R.id.re);
        lv_user= (ListView) findViewById(R.id.lv_user);
        iv_web_title_left= (ImageView) findViewById(R.id.iv_web_title_left);
        iv_web_title_right= (ImageView) findViewById(R.id.iv_web_title_right);
        bt_user= (Button) findViewById(R.id.bt_user);
        iv_user_photo= (ImageView) findViewById(R.id.iv_user_photo);
        tv_user_integration= (TextView) findViewById(R.id.tv_user_integration);
        tv_user_uid= (TextView) findViewById(R.id.tv_user_uid);
        tv_user_comnum= (TextView) findViewById(R.id.tv_user_comnum);
        adapter=new UserBaseAdapter(this);
        lv_user.setAdapter(adapter);
    }

    @Override
    public void onClick(View v) {
            switch (v.getId()){
                case R.id.iv_web_title_left:
                    setResult(7);
                    finish();
                    break;
                case R.id.bt_user:
                    getSharedPreferences("userToken",MODE_PRIVATE).edit().putString("token","000").commit();
                    getSharedPreferences("userToken",MODE_PRIVATE).edit().putString("explain","失败").commit();
                    getSharedPreferences("qqPhoto",MODE_PRIVATE).edit().putString("qqEntry",null).commit();
                    Intent intent=new Intent();
                    setResult(6,intent);
                    finish();
                    break;
                case R.id.iv_user_photo:
                    showPop();
                    break;
                case R.id.tv_pop_user_photo:
                    selectPhoto();
                    break;
                case R.id.tv_pop_user_takePhotp:
                    takePhoto();
                    break;

            }
    }

    /**
     * 请求信息
     * @param token
     */
    public void requestInfomation(String token){

        String url= MainUrlManager.mainUrl+"user_home?ver=2&imei=2&token="+token;

        //创建请求队列
        RequestQueue queue= Volley.newRequestQueue(this);

        Response.Listener<String> listener=new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                try {
                    JSONObject obj=new JSONObject(s);
                    if(obj.getString("message").equals("OK")){
                            obj=obj.getJSONObject("data");
                        Gson gson=new Gson();
                        info=gson.fromJson(obj.toString(),UserInfomation.class);
                        adapter.add(info.getLoginlog());
                        adapter.addReverse();
                        //设置图片
                        setUserImage();
                        tv_user_uid.setText(info.getUid());
                        tv_user_comnum.setText(info.getComnum()+"");
                        tv_user_integration.setText("积分："+info.getIntegration()+"");
                        re.setVisibility(View.GONE);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            private void setUserImage() {
                String path=getSharedPreferences("photoPath",MODE_PRIVATE).getString("userPhoto",null);
                if(path!=null){
                    File f=new File(path);
                    if(f.exists()){
                        Bitmap b= BitmapFactory.decodeFile(path);
                        iv_user_photo.setImageBitmap(b);
                    }
                }else{
                LoadImage.getLoadImage(UserActivity.this,info.getPortrait(),iv_user_photo,60,60);
                }
            }
        };
        Response.ErrorListener errorListener=new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Toast.makeText(UserActivity.this, "网络连接失败", Toast.LENGTH_SHORT).show();
            }
        };
        StringRequest request=new StringRequest(url,listener,errorListener);

        //添加到队列
        queue.add(request);
    }

    public void showPop(){
        pop=new PopupWindow(this);

        popView= LayoutInflater.from(this).inflate(R.layout.item_pop_user,null);

        //设置宽高
        pop.setWidth(ViewGroup.LayoutParams.FILL_PARENT);
        pop.setHeight(ViewGroup.LayoutParams.FILL_PARENT);
        //设置要显示的View
        pop.setContentView(popView);
        //实例化POP中的控件
        initPopView(popView);
        //设置事件
        initPopEvent();

        //设置外部可点击
        pop.setOutsideTouchable(true);

        //设置显示的View
        View view=LayoutInflater.from(UserActivity.this).inflate(R.layout.activity_user,null);
        //显示PopupWindow
        pop.showAtLocation(view, Gravity.CENTER,0,0);

        //设置触摸父布局，让pop消失
        popView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(pop.isShowing()){
                    pop.dismiss();
                }
                return false;
            }
        });



    }

    private void initPopEvent() {
        tv_pop_user_photo.setOnClickListener(this);
        tv_pop_user_takePhoto.setOnClickListener(this);
    }

    private void initPopView(View popView) {
        tv_pop_user_photo= (TextView) popView.findViewById(R.id.tv_pop_user_photo);
        tv_pop_user_takePhoto= (TextView) popView.findViewById(R.id.tv_pop_user_takePhotp);

    }

    /**
     * 选择图片
     */
    private void selectPhoto(){
        Intent intent=new Intent(Intent.ACTION_PICK,null);
        intent.setType("image/+");
        intent.putExtra("crop","true");//设置剪裁功能
        intent.putExtra("aspectX",1);//设置宽高比例
        intent.putExtra("aspectY",1);
        intent.putExtra("outputX",80);//宽高的值
        intent.putExtra("outputY",80);
        intent.putExtra("return-data",true);//返回裁剪结果
        startActivityForResult(intent,OPPHOTO);
    }

    /**
     * 拍照
     */
    protected  void takePhoto(){
        Intent intent=new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent,OPCAMERA);
    }

    private static final int OPCAMERA=1;
    private static final int OPPHOTO=2;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
                if(requestCode==OPCAMERA){
                    if(resultCode== Activity.RESULT_OK){
                        Bundle bundle=data.getExtras();
                        Bitmap bitmap= (Bitmap) bundle.get("data");
                        Bitmap b=getClipBitmap(bitmap);
                        iv_user_photo.setImageBitmap(b);
                        File file=savaBitmap(b);
                        uploading(file);
                    }
                }
                if(requestCode==OPPHOTO){
                    if(resultCode==Activity.RESULT_OK){
                        Bundle bundle=data.getExtras();
                        Bitmap bitmap= (Bitmap) bundle.get("data");
                        Bitmap b=getClipBitmap(bitmap);
                        iv_user_photo.setImageBitmap(b);
                        File file=savaBitmap(b);
                        uploading(file);

                    }
                }
                pop.dismiss();

    }

    /**
     *
     * @param bitmap
     * @return  返回剪切后的图片
     */
    private Bitmap getClipBitmap(Bitmap bitmap){
        //用来做模板的bitmap，圆形
            Bitmap bitmapType= BitmapFactory.decodeResource(getResources(),R.mipmap.userbg);
            int h=bitmapType.getHeight();
            int w=bitmapType.getWidth();
        //将拍照或者相册中选择的bitmap进行压缩
        Bitmap b=Bitmap.createScaledBitmap(bitmap,w,h,true);
        Canvas canvas=new Canvas(b);
        Paint paint=new Paint();
        paint.setAntiAlias(true);
        //在画布上绘制选择的bitmap
        canvas.drawBitmap(b,new Matrix(),paint);
        //j两层图片相交时的显示模式
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_IN));

        canvas.drawBitmap(bitmapType,new Matrix(),paint);
        return b;
    }

    /**
     * 上传头像
     * @param f 用户将要上传的头像文件
     */
    private void uploading(File f){
        //user_image?token=用户令牌& portrait=头像
        String url=MainUrlManager.mainUrl+"user_image?token="+token+"&portrait="+f.getAbsolutePath();
        RequestQueue queue=Volley.newRequestQueue(this);

        Response.Listener<String> listener=new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                        Log.e("uuuuuuuuuuu",s);
            }
        };
        Response.ErrorListener errorListener=new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Toast.makeText(UserActivity.this, "服务器连接失败", Toast.LENGTH_SHORT).show();
            }
        };
        StringRequest request=new StringRequest(url,listener,errorListener);

        queue.add(request);

    }

    /**
     *
     * @param b 用户截取的头像文件的bitmap对象
     * @return  返回一个File文件 I/O流写入SD卡
     */
    private File savaBitmap(Bitmap b){
        String path=Environment.getExternalStorageDirectory().getAbsolutePath()+"/userphoto";
        Log.e("ssssssssssss",path);
            File file=new File(path);
            if(!file.exists()){
                file.mkdirs();
            }
            File f=new File(file,"myphoto.jpg");
        try {
            FileOutputStream out=new FileOutputStream(f);
            b.compress(Bitmap.CompressFormat.PNG,90,out);
        getSharedPreferences("photoPath",MODE_PRIVATE).edit().putString("userPhoto",f.getAbsolutePath()).commit();
            out.flush();
            out.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
       return  f;
    }
}
