package com.feicui.news.base;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.Window;
import android.widget.Toast;

import com.feicui.news.R;

public class BaseActivity extends FragmentActivity {


    protected void showToast(String text) {
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
    }

    Fragment currentFragment;

    /**
     * 新建没用过的，显示隐藏的Fragment(要被替换的ID,将要显示的Fragment)
     */
    public void showFragment(int target, Fragment toFragment) {
        FragmentManager manager = getFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        // 如果当前显示的不为空，隐藏起来

        if (currentFragment != null) {
            transaction.hide(currentFragment);
        }
        String toClassName = toFragment.getClass().getSimpleName();
        // toFragment如果存在就显示出来
        if (manager.findFragmentByTag(toClassName) != null) {
            transaction.show(toFragment);
        } else {// toFragment替换
            transaction.add(target, toFragment, toClassName);
        }
        transaction.commit();
        // 把将要显示的赋值给当前
        currentFragment = toFragment;
    }

    /**
     * @param mClass   要跳转到的activity
     * @param isFinish 是否结束当前的activity
     */
    protected void startActivity(Class<?> mClass, boolean isFinish) {
        Intent intent = new Intent(this, mClass);
        startActivity(intent);
        // 添加动画
        overridePendingTransition(R.anim.screen_in,
                R.anim.screen_out);
        if (isFinish) {
            this.finish();
        }

    }



    /**
     * @param mClass 要跳转到的activity
     */
    protected void startActivity(Class<?> mClass) {
        Intent intent = new Intent(this, mClass);
        startActivity(intent);
        // 添加动画
        overridePendingTransition(R.anim.screen_in,
                R.anim.screen_out);
    }

    /**
     * @param mClass 要跳转到的activity
     */
    protected void startActivity(Class<?> mClass, Bundle bundle) {
        Intent intent = new Intent(this, mClass);
        if (bundle != null) {// 如果bundle对象存在
            // 添加bundle对象
            intent.putExtras(bundle);
        }
        startActivity(intent);
        // 添加动画
        overridePendingTransition(R.anim.screen_in,
                R.anim.screen_out);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        Log.e(getClass().getSimpleName(), "onCreate");
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.e(getClass().getSimpleName(), "onStart");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.e(getClass().getSimpleName(), "onRestart");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.e(getClass().getSimpleName(), "onResume");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.e(getClass().getSimpleName(), "onPause");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.e(getClass().getSimpleName(), "onStop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.e(getClass().getSimpleName(), "onDestroy");
    }

}
