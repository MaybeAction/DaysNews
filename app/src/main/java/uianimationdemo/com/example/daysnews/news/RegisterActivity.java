package uianimationdemo.com.example.daysnews.news;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import org.json.JSONException;
import org.json.JSONObject;

import uianimationdemo.com.example.daysnews.R;
import uianimationdemo.com.example.daysnews.utils.CommonUtil;

public class RegisterActivity extends uianimationdemo.com.example.daysnews.base.MyBaseActivity implements View.OnClickListener, CompoundButton.OnCheckedChangeListener {
    private CheckBox cb_register;
    private Button bt_register;
    private TextView tv_web_title;
    private ImageView iv_web_title_left,iv_web_title_right;
    private boolean isFlag=true;
    private EditText et_register_account,et_register_name,et_register_password;
    private String email,name,password,explain;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
            initView();

            initEvent();

            initData();
    }

    private void initData() {
            cb_register.setText(R.string.register_promise);

    }

    private void initEvent() {
            bt_register.setOnClickListener(this);
            iv_web_title_left.setOnClickListener(this);
            cb_register.setOnCheckedChangeListener(this);
            iv_web_title_right.setVisibility(View.INVISIBLE);
            tv_web_title.setText("注册");

    }

    private void initView() {
        et_register_account= (EditText) findViewById(R.id.et_register_account);
        et_register_name= (EditText) findViewById(R.id.et_register_name);
        et_register_password= (EditText) findViewById(R.id.et_register_password);
        cb_register= (CheckBox) findViewById(R.id.cb_register);
        bt_register= (Button) findViewById(R.id.bt_register);
        tv_web_title= (TextView) findViewById(R.id.tv_web_title);
        iv_web_title_left= (ImageView) findViewById(R.id.iv_web_title_left);
        iv_web_title_right= (ImageView) findViewById(R.id.iv_web_title_right);
        //初始化CheckBox状态
        cb_register.setChecked(true);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.bt_register:
                //得到注册的数据
                initAccount();
                break;
            case R.id.iv_web_title_left:
                    this.finish();
                break;
        }
    }

    /**
     * 得到注册数据的值，并判断他们的格式是否正确，并向上个界面返回值
     */
    private void initAccount() {

        email = et_register_account.getText().toString().trim();
        name = et_register_name.getText().toString().trim();
        password = et_register_password.getText().toString().trim();
        if(!isFlag){
            showToast(this,"请阅读服务条款和信用政策，并同意后再注册");
            return;
        }
        if(email.length()==0){
            showToast(this,"请输入邮箱 ");
            return;
        }
        if(name.length()==0){
            showToast(this,"请输入昵称");
            return;
        }
        if(password.length()==0){
            showToast(this,"请输入密码");
            return;
        }
        if(!CommonUtil.verifyEmail(email)){
            showToast(this,"请输入正确的邮箱格式");
            return;
        }
        if(password.length()<6||password.length()>16){
            showToast(this,"密码长度在6到16位之间");
            return;
        }
        if(!CommonUtil.verifyPassword(password)){
            showToast(this,"请输入正确的格式的密码");
            return;
        }


        requestComment(email,name,password);

    }


    @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                            if(isChecked){
                                isFlag=true;
                            }else{
                                isFlag=false;
                            }
        }

    public void requestComment(String email, String uid, final String pwd) {
        //user_register?ver=版本号&uid=用户名&email=邮箱&pwd=登陆密码
        String url = "http://118.244.212.82:9092/newsClient/user_register?ver=2&uid="+uid+"&email="+email+"&pwd="+pwd;
        //Volley使用
        //1.得到一个请求队列
        RequestQueue queue = Volley.newRequestQueue(this);
        Response.Listener<String> listener = new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                try {
                    JSONObject obj=new JSONObject(s);
                    if(obj.getString("message").equals("OK")){
                        obj=obj.getJSONObject("data");
                        explain=obj.getString("explain");
                        showToast(RegisterActivity.this,explain);
                        if(obj.getString("explain").equals("注册成功")) {
                            Intent data=new Intent();
                            data.putExtra("name",name);
                            data.putExtra("password",password);
                            setResult(2,data);
                            finish();
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };

        Response.ErrorListener errorListener = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Toast.makeText(RegisterActivity.this, "网络数据请求失败，", Toast.LENGTH_SHORT).show();
            }
        };
        //2：创建请求 参数1：网络参数，参数2：请求服务器数据成功后的接口监听，参数3：请求服务器数据失败
        StringRequest request = new StringRequest(url, listener, errorListener);
        //3：将请求加入请求队列
        queue.add(request);
    }
}
