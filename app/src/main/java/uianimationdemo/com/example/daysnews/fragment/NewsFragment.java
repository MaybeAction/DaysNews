package uianimationdemo.com.example.daysnews.fragment;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Toast;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import uianimationdemo.com.example.daysnews.R;
import uianimationdemo.com.example.daysnews.adapter.MyRecyclerViewAdapter;
import uianimationdemo.com.example.daysnews.adapter.MyXListViewAdapter;
import uianimationdemo.com.example.daysnews.entiy.NewsListInfo;
import uianimationdemo.com.example.daysnews.entiy.NewsListShow;
import uianimationdemo.com.example.daysnews.news.WebViewActivity;
import uianimationdemo.com.example.daysnews.view.xlistview.XListView;


public class NewsFragment extends Fragment implements MyRecyclerViewAdapter.MyOnClickListener, AdapterView.OnItemClickListener {
    private MyRecyclerViewAdapter adapter;
    private RecyclerView recyclerView;
    private View view;
    private LinearLayoutManager linearLayoutManager;
    private NewsListShow.News news;
    private NewsListInfo info;
    private XListView xListView;
    private MyXListViewAdapter xListViewAdapter;
    private NewsListShow infoShow;

    //当前显示新闻列表所属的新闻类型
    private int contentNewsTypeSubid;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_news, container, false);
        initView();
        initEvent();
        initData();
        return view;
    }

    /**
     * 初始化控件
     */
    private void initView() {
        xListView = (XListView) view.findViewById(R.id.xListView);
        recyclerView = (RecyclerView) view.findViewById(R.id.rc_newslist);
        adapter = new MyRecyclerViewAdapter(getActivity());
        //实例化XListView适配器
        xListViewAdapter = new MyXListViewAdapter(getActivity());
        linearLayoutManager = new LinearLayoutManager(getActivity());
        requestNewsType();
        //让XListView可以刷新
        xListView.setPullRefreshEnable(true);
        xListView.setPullLoadEnable(true);
        //给XListViwe设置适配器
        xListView.setAdapter(xListViewAdapter);
    }


    /**
     * 加载新闻列表数据
     */
    private void requestNewsList(int subid, int dir) {
        //news_sort?ver=版本号&subid=分类名&dir=1&nid=新闻id&stamp=20140321&cnt=2
        //地址与参数之间用？链接，参数之间用&链接
        int nid = 0;
        String stamp = null;
        final int cnt = 20;

        if(dir==2){
            nid= xListViewAdapter.getItem(xListViewAdapter.getCount()-1).getNid();
        }
        String url = "http://118.244.212.82:9092/newsClient/news_list?ver=2&subid=" + subid +
                "&dir=" + dir + "&nid=" + nid + "&stamp=" + stamp + "&cnt=" + cnt;
        //Volley使用步骤
        //1；得到一个请求队列
        RequestQueue queue = Volley.newRequestQueue(getActivity());
        Response.Listener<String> listener = new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                //得到Gson对象
                Gson gson = new Gson();
                 infoShow = gson.fromJson(s, NewsListShow.class);
                if(infoShow.getMessage().equals("OK")){
                xListViewAdapter.add(infoShow.getData());
                }else{
                    Toast.makeText(getActivity(), "没有更多新闻", Toast.LENGTH_SHORT).show();
                }
            }
        };
        Response.ErrorListener errorListener = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Toast.makeText(getActivity(), "网络数据请求失败，", Toast.LENGTH_SHORT).show();
            }
        };
        //2：创建请求 参数1：网络参数，参数2：请求服务器数据成功后的接口监听，参数3：请求服务器数据失败
        StringRequest request = new StringRequest(url, listener, errorListener);
        //3：将请求加入请求队列
        queue.add(request);
    }

    /**
     * 初始化事件
     */
    private void initEvent() {
        //设置RecyclerView的方向
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        xListView.setOnItemClickListener(this);

        //xListView的刷新，以及显示更多
        xListView.setXListViewListener(new XListView.IXListViewListener() {
            @Override
            public void onRefresh() {
                xListViewAdapter.clear();
                requestNewsList(contentNewsTypeSubid,1);
                xListView.stopRefresh();
            }

            @Override
            public void onLoadMore() {//加载更多的监听
                requestNewsList(contentNewsTypeSubid,2);
                //请求完最新数据之后，停止加载更多动画
                xListView.stopLoadMore();
            }
        });
    }

    /**
     * 初始化数据
     */
    private void initData() {
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);
        adapter.setMyOnClickListener(this);

    }

    /**
     * 请求RecyclerView数据并添加数据
     */
    private void requestNewsType() {
        //news_sort?ver=版本号&imei=手机标识符
        //地址与参数之间用？链接，参数之间用&链接
        String url = "http://118.244.212.82:9092/newsClient/news_sort?ver=2&imei=2";
        //Volley使用步骤
        //1；得到一个请求队列
        RequestQueue queue = Volley.newRequestQueue(getActivity());
        Response.Listener<String> listener = new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                try {
                    JSONObject obj = new JSONObject(s);
                    if (obj.getString("message").equals("OK")) {//数据正常返回
                        JSONArray array = obj.getJSONArray("data");
                        for (int i = 0; i < array.length(); i++) {
                            obj = array.getJSONObject(i);
                            JSONArray subarray = obj.getJSONArray("subgrp");
                            //遍历子分组数据
                            for (int j = 0; j < subarray.length(); j++) {
                                obj = subarray.getJSONObject(j);
                                //实例化实例类对象
                                info = new NewsListInfo(obj.getString("subgroup"), obj.getInt("subid"));
                                adapter.add(info);
                            }
                        }
                                adapter.notifyDataSetChanged();
                                //默认加载第一种类型的新闻列表
                        contentNewsTypeSubid=adapter.getList().get(0).getSubid();
                                //加载新闻列表数据
                                requestNewsList(contentNewsTypeSubid, 1);
                    } else {
                        Toast.makeText(getActivity(), s + "", Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };

        Response.ErrorListener errorListener = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Toast.makeText(getActivity(), "网络数据请求失败，", Toast.LENGTH_SHORT).show();
            }
        };
        //2：创建请求 参数1：网络参数，参数2：请求服务器数据成功后的接口监听，参数3：请求服务器数据失败
        StringRequest request = new StringRequest(url, listener, errorListener);
        //3：将请求加入请求队列
        queue.add(request);
    }


    @Override
    public void MyOnClick(int position) {
        NewsListInfo newInfo=adapter.getList().get(position);
        //将标记的位置传回Adapter
        adapter.getFragmentPosition(position);
        adapter.notifyDataSetChanged();
        //加载对应类型的新闻列表
        xListViewAdapter.clear();
        contentNewsTypeSubid= newInfo.getSubid();
        requestNewsList(contentNewsTypeSubid, 1);
    }


    /**
     *  点击跳转，并传值，在下一个Activitiy中得到Link
     * @param parent
     * @param view
     * @param position
     * @param id
     */
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        news= xListViewAdapter.getList().get(position-1);
        Intent intent=new Intent(getActivity(), WebViewActivity.class);
        intent.putExtra("info",news);
        startActivity(intent);
    }
}
