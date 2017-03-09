package uianimationdemo.com.example.daysnews.news;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
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


/**
 * 登录界面
 */
public class EntryActivity extends uianimationdemo.com.example.daysnews.base.MyBaseActivity implements View.OnClickListener {
    private Button bt_entry,bt_register,bt_forgetPassword;
    private TextView tv_wb_title;
    private ImageView iv_wb_title_left,iv_wb_title_right;
    private EditText et_entry_account,et_entry_password;
    private String explain;
    private String token;
    private String name;
    private String password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_entry);

        initView();

        initEvent();

        initData();

    }

    private void initData() {
        tv_wb_title.setText("登录");
    }

    private void initEvent() {
        bt_entry.setOnClickListener(this);
        bt_forgetPassword.setOnClickListener(this);
        bt_register.setOnClickListener(this);
        iv_wb_title_left.setOnClickListener(this);
        iv_wb_title_right.setVisibility(View.INVISIBLE);


    }

    private void initView() {
        bt_entry= (Button) findViewById(R.id.bt_entry);
        bt_forgetPassword= (Button) findViewById(R.id.bt_forgetPassword);
        bt_register= (Button) findViewById(R.id.bt_register);

        iv_wb_title_left= (ImageView) findViewById(R.id.iv_web_title_left);
        iv_wb_title_right= (ImageView) findViewById(R.id.iv_web_title_right);
        tv_wb_title= (TextView) findViewById(R.id.tv_web_title);

        et_entry_account= (EditText) findViewById(R.id.et_entry_account);
        et_entry_password= (EditText) findViewById(R.id.et_entry_password);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.bt_entry:

                String name=et_entry_account.getText().toString();
                String password=et_entry_password.getText().toString();
                if(name.length()==0){
                    showToast(this,"请输入昵称");
                    return;
                }
                if(password.length()==0){
                    showToast(this,"请输入密码");
                    return;
                }
                getSharedPreferences("qqPhoto",MODE_PRIVATE).edit().putString("qqEntry",null).commit();
                requestEntry(name,password);
                break;
            case R.id.bt_register:
                //跳转
                Intent intent=new Intent(EntryActivity.this,RegisterActivity.class);
                startActivityForResult(intent,1);
                break;
            case R.id.bt_forgetPassword:
                    toClass(EntryActivity.this,ForgetPasswordActivity.class);
                break;
            case R.id.iv_web_title_left:
                finish();
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

            if (data != null) {
                 name = data.getStringExtra("name");
                 password = data.getStringExtra("password");
                et_entry_account.setText(name);
                et_entry_password.setText(password);

            } else {

            }

    }


    public void requestEntry(String uid,String pwd){
        //user_login?ver=版本号&uid=用户名&pwd=密码&device=0
        String url = "http://118.244.212.82:9092/newsClient/user_login?ver=2&uid="+uid+"&pwd="+pwd+"&device=0";
        //Volley使用
        //1.得到一个请求队列
        RequestQueue queue = Volley.newRequestQueue(this);
        Response.Listener<String> listener = new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {

                try {
                    JSONObject obj=new JSONObject(s);
                    showToast(EntryActivity.this,obj.getString("message"));
                    if(obj.getString("message").equals("OK")){
                        obj=obj.getJSONObject("data");
                        if(obj.getString("explain").equals("登录成功")) {
                            explain=obj.getString("explain");
                            token=obj.getString("token");
                            name=et_entry_account.getText().toString();
                            Intent intent = new Intent();
                            intent.putExtra("token", token);
                            intent.putExtra("explain",explain);
                            setResult(4, intent);
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
                Toast.makeText(EntryActivity.this, "网络数据请求失败，", Toast.LENGTH_SHORT).show();
            }
        };
        //2：创建请求 参数1：网络参数，参数2：请求服务器数据成功后的接口监听，参数3：请求服务器数据失败
        StringRequest request = new StringRequest(url, listener, errorListener);
        //3：将请求加入请求队列
        queue.add(request);
    }

}
