package uianimationdemo.com.example.daysnews.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Steven on 2017/2/17.
 */

public class MySqliteOpenHelper extends SQLiteOpenHelper{
    public MySqliteOpenHelper(Context context) {
        super(context, "newsdb", null, 1);
    }

    private static final String CREATE_SAVE_NEWS_TABLE="create table lovenews("+
            "id integer primary key autoincrement,"+
            "nid integer," +
            "stamp text," +
            "icon text," +
            "title text," +
            "summary text," +
            "link text)";

    @Override
    public void onCreate(SQLiteDatabase db) {
            //创建数据库
                db.execSQL(CREATE_SAVE_NEWS_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }


    public static String getCreateSaveNewsTable(){
        return "lovenews";
    }
}
