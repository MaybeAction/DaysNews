package uianimationdemo.com.example.daysnews.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by maybe on 2017/3/6.
 */

public class Util {
    public static String TAG="UTIL";
    public static Bitmap getBitmap(String imageUrl){
        Log.e(TAG,"getbitmap"+imageUrl);
        Bitmap bitmap=null;
        try {
            URL url=new URL(imageUrl);
            HttpURLConnection connection= (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream is=connection.getInputStream();
            bitmap= BitmapFactory.decodeStream(is);
            is.close();
            connection.disconnect();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bitmap;
    }
}
