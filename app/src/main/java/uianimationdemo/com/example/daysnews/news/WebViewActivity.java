package uianimationdemo.com.example.daysnews.news;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.mob.commons.SHARESDK;

import org.json.JSONException;
import org.json.JSONObject;

import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.onekeyshare.OnekeyShare;
import uianimationdemo.com.example.daysnews.R;
import uianimationdemo.com.example.daysnews.db.MySqliteDataBase;
import uianimationdemo.com.example.daysnews.entiy.NewsListShow;


public class WebViewActivity extends uianimationdemo.com.example.daysnews.base.MyBaseActivity implements View.OnClickListener {
    private WebView wb;
    private String url;
    private ImageView iv_web_title_left,iv_web_title_right;
    private TextView tv_web_title;
    private NewsListShow.News news;
    private LinearLayout activity_web_view;
    private TextView tv_pop_comment,tv_pop_local,tv_pop_share;
    private String str;
    private ProgressBar pb;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);
        ShareSDK.initSDK(this);
        Intent intent=getIntent();
        news= (NewsListShow.News) intent.getSerializableExtra("info");
        url=news.getLink();

        initView();
        initEvent();
        initData();
        requestComment();
    }

    private void initData() {
        tv_web_title.setText(news.getTitle());
    }

    private void initEvent() {
        //设置WebView属性，能够执行Javascript脚本
        wb.getSettings().setJavaScriptEnabled(true);
        //加载需要显示的网页
        wb.loadUrl(url);
        //设置web视图
        wb.setWebViewClient(new HelloWebViewClient());
        iv_web_title_left.setOnClickListener(this);
        iv_web_title_right.setOnClickListener(this);
        activity_web_view.setOnClickListener(this);
        wb.setWebChromeClient(new WebChromeClient(){
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);
                        pb.setProgress(newProgress);
                        if(newProgress>=100){
                            pb.setVisibility(View.GONE);
                        }
            }
        });
    }


    private void initView() {
        wb= (WebView) findViewById(R.id.wb);
        iv_web_title_left= (ImageView) findViewById(R.id.iv_web_title_left);
        iv_web_title_right= (ImageView) findViewById(R.id.iv_web_title_right);
        tv_web_title= (TextView) findViewById(R.id.tv_web_title);
        activity_web_view= (LinearLayout) findViewById(R.id.activity_web_view);
        pb= (ProgressBar) findViewById(R.id.pb);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.iv_web_title_left:
                finish();
                break;
            case R.id.iv_web_title_right:
                showPop();
                tv_web_title.setFocusable(true);
                break;
            case R.id.tv_pop_comment:
                Intent intent=new Intent(WebViewActivity.this,WebViewCommentActivity.class);
                popupWindow.dismiss();
                intent.putExtra("webNews",news);
                startActivity(intent);
                break;
            case R.id.tv_pop_local:
                // 向数据库中添加收藏新闻
                MySqliteDataBase.insertNews(this,news);
                tv_pop_local.setText("已收藏");
                    showToast(this,"收藏成功");
                break;
            case R.id.tv_pop_share:
                showShare();
                break;
        }
    }


    private PopupWindow popupWindow;
    private View popView;

    private void showPop() {
        popupWindow=new PopupWindow(this);
        //设置宽高
        popupWindow.setWidth(activity_web_view.getWidth()/3);
        popupWindow.setHeight(popupWindow.getHeight());
        //实例化View
        popView= LayoutInflater.from(this).inflate(R.layout.pop_newsdetaile_activity,null);
        //设置要显示的view
        popupWindow.setContentView(popView);

        //实例化popupwindow中的控件
        initPop();
        tv_pop_comment.setText("("+str+")"+"评论");
        //设置事件
        initPopEvent();
        //让外部可点击
        popupWindow.setOutsideTouchable(true);
        //设置背景，必须要有
        popupWindow.setBackgroundDrawable(getResources().getDrawable(R.color.black));
        popupWindow.showAsDropDown(iv_web_title_right,0,10);
        //设置评论数量
    }

    private void initPopEvent() {
        tv_pop_comment.setOnClickListener(this);
        tv_pop_local.setOnClickListener(this);
        tv_pop_share.setOnClickListener(this);

        //判断该新闻是否已经被收藏
        if(MySqliteDataBase.isNewsSaved(this,news)){
                tv_pop_local.setText("已收藏");
        }else{
            tv_pop_local.setText("加入收藏");
        }
    }

    private void initPop() {
        //实例化popwindow中的控件
        tv_pop_comment= (TextView) popView.findViewById(R.id.tv_pop_comment);
        tv_pop_local= (TextView) popView.findViewById(R.id.tv_pop_local);
        tv_pop_share= (TextView) popView.findViewById(R.id.tv_pop_share);
    }


    private class HelloWebViewClient extends WebViewClient{
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }
    }

    /**
     * 解析评论数据
     */
    public void requestComment(){
        //cmt_num?ver=版本号& nid=新闻编号
        String url = "http://118.244.212.82:9092/newsClient/cmt_num?ver=2&nid="+news.getNid();
        //Volley使用
        //1.得到一个请求队列
        RequestQueue queue= Volley.newRequestQueue(this);
        Response.Listener<String> listener=new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {

                try {
                    JSONObject obj=new JSONObject(s);
                    if(obj.getString("message").equals("OK")){//数据正常返回
                        str=obj.getString("data");

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };

        Response.ErrorListener errorListener = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Toast.makeText(WebViewActivity.this, "网络数据请求失败，", Toast.LENGTH_SHORT).show();
            }
        };
        //2：创建请求 参数1：网络参数，参数2：请求服务器数据成功后的接口监听，参数3：请求服务器数据失败
        StringRequest request = new StringRequest(url, listener, errorListener);
        //3：将请求加入请求队列
        queue.add(request);
    }


    private void showShare() {
        OnekeyShare oks = new OnekeyShare();
        //关闭sso授权
        oks.disableSSOWhenAuthorize();
        // title标题，印象笔记、邮箱、信息、微信、人人网、QQ和QQ空间使用
        oks.setTitle(news.getTitle());
        // titleUrl是标题的网络链接，仅在Linked-in,QQ和QQ空间使用
        oks.setTitleUrl(news.getLink());
        // text是分享文本，所有平台都需要这个字段
        oks.setText(news.getSummary());
        //分享网络图片，新浪微博分享网络图片需要通过审核后申请高级写入接口，否则请注释掉测试新浪微博
        oks.setImageUrl(news.getIcon());
        // imagePath是图片的本地路径，Linked-In以外的平台都支持此参数
        //oks.setImagePath("/sdcard/test.jpg");//确保SDcard下面存在此张图片
        // url仅在微信（包括好友和朋友圈）中使用
        oks.setUrl(news.getLink());
        // comment是我对这条分享的评论，仅在人人网和QQ空间使用
        oks.setComment("");
        // site是分享此内容的网站名称，仅在QQ空间使用
        oks.setSite(getString(R.string.app_name));
        // siteUrl是分享此内容的网站地址，仅在QQ空间使用
        oks.setSiteUrl(news.getLink());

// 启动分享GUI
        oks.show(this);
    }
}
