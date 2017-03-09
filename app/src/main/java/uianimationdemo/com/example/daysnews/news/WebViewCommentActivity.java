package uianimationdemo.com.example.daysnews.news;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Xml;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
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

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import uianimationdemo.com.example.daysnews.R;
import uianimationdemo.com.example.daysnews.adapter.CommentXListViewAdapter;
import uianimationdemo.com.example.daysnews.db.MainUrlManager;
import uianimationdemo.com.example.daysnews.entiy.NewsCommentInfo;
import uianimationdemo.com.example.daysnews.entiy.NewsListShow;
import uianimationdemo.com.example.daysnews.view.xlistview.XListView;

public class WebViewCommentActivity extends AppCompatActivity implements View.OnClickListener {
    private XListView xListView;
    private NewsListShow.News news;
    private NewsCommentInfo info;
    private NewsCommentInfo.Comment comment;
    private CommentXListViewAdapter xListViewAdapter;
    private EditText et;
    private ImageView iv_send,iv_web_title_left;
    private String token,sendComment,message;
    private TextView tv_web_title;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view_comment);
        Intent intent=getIntent();
        news= (NewsListShow.News) intent.getSerializableExtra("webNews");
        requestNewsComment(1,1);
        initView();
        initEvent();

    }

    private void initEvent() {
        //让XListView可以刷新
        xListView.setPullRefreshEnable(true);
        xListView.setPullLoadEnable(true);
        xListViewAdapter=new CommentXListViewAdapter(this);
        xListView.setAdapter(xListViewAdapter);
        tv_web_title.setText("评论");
        iv_send.setOnClickListener(this);
        iv_web_title_left.setOnClickListener(this);

        /**
         * edit中，评论时靠左，hit显示居中
         */
        et.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus){
                    et.setGravity(Gravity.LEFT);

                }else {
                    if (TextUtils.isEmpty(et.getText())) {
                        et.setGravity(Gravity.CENTER);
                    }else{
                        et.setGravity(Gravity.LEFT);
                    }
                }
            }
        });

        /**
         * 下拉刷新和上拉加载
         */
        xListView.setXListViewListener(new XListView.IXListViewListener() {
            @Override
            public void onRefresh() {
                //上拉刷新
                xListViewAdapter.clear();
                requestNewsComment(1,1);
                xListView.stopRefresh();
            }

            @Override
            public void onLoadMore() {
                //下拉加载
                requestNewsComment(2,1);
                //请求完最新数据之后，停止加载更多动画
                xListView.stopLoadMore();
            }
        });
    }

    /**
     * 初始化控件
     */
    private void initView() {
        xListView= (XListView) findViewById(R.id.xlistView_comment);
        et= (EditText) findViewById(R.id.et);
        iv_send= (ImageView) findViewById(R.id.iv_send);
        iv_web_title_left= (ImageView) findViewById(R.id.iv_web_title_left);
        tv_web_title= (TextView) findViewById(R.id.tv_web_title);
        //获得token
        token=getSharedPreferences("userToken",MODE_PRIVATE).getString("token","000");
    }

    /**
     * 请求新闻评论
     * @param dir
     */
    private void requestNewsComment(int dir,int cid){

        if(dir==2){
        cid=xListViewAdapter.getItem(xListViewAdapter.getCount()-1).getCid();
        }
        //cmt_list?ver=版本号&nid=新闻id&type=1&stamp=yyyyMMdd&cid=评论id&dir=0&cnt=20
        String url = "http://118.244.212.82:9092/newsClient/cmt_list?ver=2&nid="+news.getNid()+"&type=1&stamp=yyyyMMdd&cid="+cid+"&dir="+dir+"&cnt=20";
        //Volley使用
        //1.得到一个请求队列
        RequestQueue queue= Volley.newRequestQueue(this);
        Response.Listener<String> listener=new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                //得到Gson对象
                Gson gson = new Gson();
                info = gson.fromJson(s, NewsCommentInfo.class);
                if (info.getMessage().equals("OK")&&info.getData()!=null) {
                    xListViewAdapter.add(info.getData());
                }else{
                    Toast.makeText(WebViewCommentActivity.this, "没有更多评论", Toast.LENGTH_SHORT).show();
                }
            }
        };
        Response.ErrorListener errorListener = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Toast.makeText(WebViewCommentActivity.this, "网络数据请求失败，", Toast.LENGTH_SHORT).show();
            }
        };
        //2：创建请求 参数1：网络参数，参数2：请求服务器数据成功后的接口监听，参数3：请求服务器数据失败
        StringRequest request = new StringRequest(url, listener, errorListener);
        //3：将请求加入请求队列
        queue.add(request);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.iv_send:
                if(!token.equals("000")){
                    sendComment=et.getText().toString().trim();
                    token=getSharedPreferences("userToken",MODE_PRIVATE).getString("token","不");
                    if(!sendComment.equals("")) {
                        sendComment(token, sendComment);
                    }else{
                    }
                }else{
                    Toast.makeText(this, "请先登录", Toast.LENGTH_SHORT).show();
                }
                et.setText("");
                break;
            case R.id.iv_web_title_left:
                this.finish();
                break;
        }
    }

    public void sendComment(String token,String comment){
        String url= MainUrlManager.mainUrl+"cmt_commit?ver=2&nid="+news.getNid()+"&token="+token+"&imei=2&ctx="+comment;
        //1。创建一个队列
        RequestQueue queue=Volley.newRequestQueue(this);
        //创建一个申请
        Response.Listener<String> listener=new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {

                try {
                    JSONObject obj=new JSONObject(s);
                    message=obj.getString("message").toString();
                    if(message.equals("OK")){
                        obj=obj.getJSONObject("data");
                        chooseComment(obj);
                        et.setText("");
                    }else{
                        Toast.makeText(WebViewCommentActivity.this, "用户认证失败，请重新登录", Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            private void chooseComment(JSONObject obj) throws JSONException {
                switch(obj.getInt("result")){
                    case 0:
                        Toast.makeText(WebViewCommentActivity.this, obj.getString("explain"), Toast.LENGTH_SHORT).show();
                        xListViewAdapter.clear();
                        requestNewsComment(1,1);
                        xListViewAdapter.notifyDataSetChanged();
                        break;
                    case -1:
                        Toast.makeText(WebViewCommentActivity.this, "非法关键字", Toast.LENGTH_SHORT).show();
                        break;
                    case -2:
                        Toast.makeText(WebViewCommentActivity.this, "禁止评论(政治敏感新闻)", Toast.LENGTH_SHORT).show();
                        break;
                    case -3:
                        Toast.makeText(WebViewCommentActivity.this, "禁止评论(用户被禁言)", Toast.LENGTH_SHORT).show();
                        break;
                }
            }
        };
        Response.ErrorListener errorListener=new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Toast.makeText(WebViewCommentActivity.this, "连接失败", Toast.LENGTH_SHORT).show();
            }
        };
        StringRequest request=new StringRequest(url,listener, errorListener);

        //添加到队列
        queue.add(request);
    }
}
