package uianimationdemo.com.example.daysnews.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import uianimationdemo.com.example.daysnews.entiy.NewsListShow;

/**
 * Created by Steven on 2017/2/17.
 */

public class MySqliteDataBase {

    private static MySqliteOpenHelper mySqliteOpenHelper;

    public static void insertNews(Context context, NewsListShow.News news){
        //实例化MySqliteOpenHelper对象，并不是创建数据库
        mySqliteOpenHelper=new MySqliteOpenHelper(context);

        //实例化SQLiteDatabase对象 并创建数据库
        SQLiteDatabase db=mySqliteOpenHelper.getWritableDatabase();

        if(isNewsSaved(context,news)){
                return;
        }
        //数据值
        ContentValues valus=new ContentValues();
        valus.put("nid",news.getNid());
        valus.put("icon",news.getIcon());
        valus.put("stamp",news.getStamp());
        valus.put("summary",news.getSummary());
        valus.put("title",news.getTitle());
        valus.put("link",news.getLink());
        //向数据库中插入一条数据
        db.insert(MySqliteOpenHelper.getCreateSaveNewsTable(),null,valus);

    }

    public static boolean isNewsSaved(Context context, NewsListShow.News news){
        //实例化MySqliteOpenHelper对象，并不是创建数据库
        mySqliteOpenHelper=new MySqliteOpenHelper(context);
        //实例化SQLiteDatabase对象 并创建数据库
        SQLiteDatabase db=mySqliteOpenHelper.getWritableDatabase();

        //判断news是否已经在数据库中了
        String str="select * from "+MySqliteOpenHelper.getCreateSaveNewsTable()+" where nid="+news.getNid();
        Cursor c = db.rawQuery(str, null);
        if(c.moveToFirst()){//如果新闻已经收藏直接返回
            c.close();
            return true;
        }else{//否则只关闭游标
            c.close();

        }
        return  false;
    }

    public static void deleteNews(Context context,NewsListShow.News news){
            //实例化MySqliteOpenHelper对象，并不是创建数据库
        mySqliteOpenHelper=new MySqliteOpenHelper(context);
        SQLiteDatabase db=mySqliteOpenHelper.getReadableDatabase();

        String str="delete from lovenews where nid="+news.getNid();

        db.execSQL(str);
    }

    public static void deleteAllNews(Context context,NewsListShow.News news){
        //实例化MySqliteOpenHelper对象，并不是创建数据库
        mySqliteOpenHelper=new MySqliteOpenHelper(context);

        SQLiteDatabase db=mySqliteOpenHelper.getReadableDatabase();

        String str="delete from lovenews ";

        db.execSQL(str);
    }
}
