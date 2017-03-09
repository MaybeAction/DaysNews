package com.feicui.news.base;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.feicui.news.R;


/**
 * Created by Administrator on 2017/1/16.
 */

public class BaseFragment extends Fragment {
    private final String TAG=BaseFragment.this.getClass().getSimpleName();
    @Override
    public void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.e(TAG,"onCreate");
    }
    /**
     * @param mClass
     *            要跳转到的activity
     */
    protected void startActivity(Class<?> mClass) {
        Intent intent = new Intent(getActivity(), mClass);
        startActivity(intent);
        // 添加动画
        getActivity().overridePendingTransition(R.anim.screen_in,
                R.anim.screen_out);
    }

//    private RequestQueue queue;
//
//    /**
//     * 得到一个RequestQueue对象
//     * @return
//     */
//    protected RequestQueue getQueue(){
//        if (queue==null){
//             queue= Volley.newRequestQueue(getActivity());
//        }
//        return queue;
//    }

    /**
     * Toast方法
     * @param text
     */
    protected void showToast(String text){

        Toast.makeText(getActivity(),text,Toast.LENGTH_SHORT).show();

    }

    @Override
    public View onCreateView(LayoutInflater inflater,  ViewGroup container,  Bundle savedInstanceState) {
        Log.e(TAG,"onCreateView");
        return super.onCreateView(inflater, container, savedInstanceState);

    }

    @Override
    public void onActivityCreated( Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Log.e(TAG,"onActivityCreated");
    }

    @Override
    public void onViewCreated(View view,  Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Log.e(TAG,"onActivityCreated");
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.e(TAG,"onStart");
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.e(TAG,"onResume");
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.e(TAG,"onPause");
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.e(TAG,"onStop");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Log.e(TAG,"onDestroyView");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.e(TAG,"onDestroy");
    }

    @Override
    public void onDetach() {
        super.onDetach();
        Log.e(TAG,"onDetach");
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Log.e(TAG,"onAttach");
    }

}
