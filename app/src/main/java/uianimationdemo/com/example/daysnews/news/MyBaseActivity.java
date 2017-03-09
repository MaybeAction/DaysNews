package uianimationdemo.com.example.daysnews.news;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class MyBaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    protected void intentTo(Class<?> myClass) {
        Intent intent=new Intent(this,myClass);
        startActivity(intent);
    }
}
