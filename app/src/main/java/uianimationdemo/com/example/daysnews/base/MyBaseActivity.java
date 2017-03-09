package uianimationdemo.com.example.daysnews.base;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

/**
 * Created by Steven on 2017/2/16.
 */

public class MyBaseActivity extends Activity {


    /**
     *
     * @param toclass 跳转到目标Activtiy
     */
    protected void toClass(Context context,Class toclass) {
        Intent intent=new Intent(context,toclass);
        startActivity(intent);
    }

    /**
     *
     * @param context 当前的context对象
     * @param str 需要吐司的字符串
     */
    protected void showToast(Context context,String str){
        Toast.makeText(context, str+"", Toast.LENGTH_SHORT).show();
    }
}
