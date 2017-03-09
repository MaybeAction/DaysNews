package uianimationdemo.com.example.daysnews.news;

import android.support.v7.app.AppCompatActivity;
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
import uianimationdemo.com.example.daysnews.db.MainUrlManager;

public class ForgetPasswordActivity extends AppCompatActivity implements View.OnClickListener {
    private TextView tv_web_title;
    private ImageView iv_web_title_left,iv_web_title_right;
    private EditText et_forget_rewrite;
    private String email;
    private Button bt_resetting_password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password);

        initView();

        initEvent();

        initData();

    }

    private void initData() {
            tv_web_title.setText("忘记密码");
    }

    private void initEvent() {
        iv_web_title_left.setOnClickListener(this);
        bt_resetting_password.setOnClickListener(this);
        iv_web_title_right.setVisibility(View.INVISIBLE);
    }
    private void initView() {
        bt_resetting_password= (Button) findViewById(R.id.bt_resetting_password);
        tv_web_title= (TextView) findViewById(R.id.tv_web_title);
        iv_web_title_left= (ImageView) findViewById(R.id.iv_web_title_left);
        iv_web_title_right= (ImageView) findViewById(R.id.iv_web_title_right);
        et_forget_rewrite= (EditText) findViewById(R.id.et_forget_rewrite);
}

    @Override
    public void onClick(View v) {
                switch (v.getId()){
                    case R.id.iv_web_title_left:
                        this.finish();
                        break;
                    case R.id.bt_resetting_password:
                        email=et_forget_rewrite.getText().toString().trim();
                        if(email.length()==0){
                            Toast.makeText(this, "请输入邮箱 ", Toast.LENGTH_SHORT).show();
                            return;
                        }

                        requestRepassWord(email);
                        break;
                }
    }


    private void requestRepassWord(String email){
                //user_forgetpass?ver=版本号&email=邮箱
        String url= MainUrlManager.mainUrl+"user_forgetpass?ver=版本号&email="+email;
        //创建队列
        RequestQueue queue= Volley.newRequestQueue(this);
        //创建请求
        Response.Listener<String> listener=new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                try {
                    JSONObject obj=new JSONObject(s);
                        obj=obj.getJSONObject("data");
                        Toast.makeText(ForgetPasswordActivity.this,obj.getString("explain"),Toast.LENGTH_SHORT).show();
                    if(obj.getString("result").equals("0")){
                        finish();
                    }
                    } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        };
        Response.ErrorListener errorListener=new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Toast.makeText(ForgetPasswordActivity.this, "与服务器连接失败", Toast.LENGTH_SHORT).show();
            }
        };
        StringRequest request=new StringRequest(url,listener,errorListener);

        queue.add(request);
    }

}
