package com.feicui.news.Fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.feicui.news.R;
import com.feicui.news.adapter.MyRecyclerViewAdapter;
import com.feicui.news.adapter.MyXLsitViewAdapter;
import com.feicui.news.base.BaseFragment;
import com.feicui.news.entiy.NewsTypeInfo;
import com.feicui.news.view.slidingmenu.xlistview.XListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Administrator on 2017/2/9.
 */

public class NewsListFragment extends BaseFragment {
    private View view;
    private RecyclerView rc_newslist;
    private XListView xListView;
    private MyRecyclerViewAdapter adapter;
    private MyXLsitViewAdapter xLsitViewAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_newslist, container, false);
        initView();
        initEvent();
        return view;
    }

    /**
     * 控件事件
     */
    private void initEvent() {

        adapter.setOnItemClickListener(onItemClickListener);


    }

    MyRecyclerViewAdapter.OnItemClickListener onItemClickListener = new MyRecyclerViewAdapter.OnItemClickListener() {
        @Override
        public void onItemClickListener(View view, int position) {
            //通过position得到当前位置的实体类对象
           NewsTypeInfo info=adapter.getItem(position);
            showToast(info.getSubgroup()+info.getSubid());
            //设置当前选中的位置
            adapter.selectedPosition=position;
            //刷新适配器
            adapter.notifyDataSetChanged();


        }
    };

    /**
     * 初始化控件
     */
    private void initView() {
        rc_newslist = (RecyclerView) view.findViewById(R.id.rc_newslist);
        xListView= (XListView) view.findViewById(R.id.xListView);

        //recyclerview使用流程
        //设置布局管理器
        rc_newslist.setLayoutManager(new LinearLayoutManager(getActivity(),
                LinearLayoutManager.HORIZONTAL, false));

        //实例化适配器
        adapter = new MyRecyclerViewAdapter(getActivity());
        //添加新闻标题的数据
        requestNewsType();
        //设置适配器
        rc_newslist.setAdapter(adapter);

        //xListview能够刷新
        xListView.setPullRefreshEnable(true);
        //加载新闻列表数据
        requestNewsList(1,1);
        //给xlistview设置适配器
        xListView.setAdapter(xLsitViewAdapter);



    }

    /**
     * 加载新闻列表数据
     */
    private void requestNewsList(int subid,int dir) {
        //news_list?ver=版本号&subid=分类名&dir=1&nid=新闻id&stamp=20140321&cnt=2
        //地址与参数之间用？链接，参数之间用&链接

        int nid=0;
        String stamp=null;
        String url = "http://118.244.212.82:9092/newsClient/news_list?ver=2&subid="+subid+
                "&dir=" +dir+"&nid="+nid+"&stamp="+stamp+"&cnt="+20;

        //Volley使用步骤
        //1：得到一个请求队列
        RequestQueue queue = Volley.newRequestQueue(getActivity());


        Response.Listener<String> listener = new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                Log.e("sssss===", s);


            }
        };
        Response.ErrorListener errorListener = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                showToast("网络数据请求失败，请检查你的网络");
            }
        };
        //2:创建请求,参数1：网络链接，参数2：请求服务器数据成功后的接口监听，参数3：请求服务器数据失败
        StringRequest request = new StringRequest(url, listener, errorListener);
        //3:将请求加入请求队列

        queue.add(request);
    }

    /**
     * 加载新闻类型数据
     */
    private void requestNewsType() {
        //news_sort?ver=版本号&imei=手机标识符
        //地址与参数之间用？链接，参数之间用&链接
        String url = "http://118.244.212.82:9092/newsClient/news_sort?ver=2&imei=2";

        //Volley使用步骤
        //1：得到一个请求队列
        RequestQueue queue = Volley.newRequestQueue(getActivity());


        Response.Listener<String> listener = new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {

                try {
                    //通过一个Json字符串得到一个JSONObject对象
                    JSONObject obj = new JSONObject(s);
                    if (obj.getString("message").equals("OK")) {//数据正常返回
                        //JSONArray对象
                        JSONArray array = obj.getJSONArray("data");
                        for (int i = 0; i < array.length(); i++) {
                            obj = array.getJSONObject(i);
                            //子分组中的JSONArray
                            JSONArray subarray = obj.getJSONArray("subgrp");
                            //遍历子分组数据
                            for (int j = 0; j < subarray.length(); j++) {
                                obj = subarray.getJSONObject(j);
                                //实例化实体类对象
                                NewsTypeInfo info = new NewsTypeInfo(obj.getInt("subid"),
                                        obj.getString("subgroup"));
                                adapter.add(info);
                                adapter.notifyDataSetChanged();
                            }
                        }


                    } else {
                        showToast(s);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };
        Response.ErrorListener errorListener = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                showToast("网络数据请求失败，请检查你的网络");
            }
        };
        //2:创建请求,参数1：网络链接，参数2：请求服务器数据成功后的接口监听，参数3：请求服务器数据失败
        StringRequest request = new StringRequest(url, listener, errorListener);
        //3:将请求加入请求队列

        queue.add(request);
    }
}
