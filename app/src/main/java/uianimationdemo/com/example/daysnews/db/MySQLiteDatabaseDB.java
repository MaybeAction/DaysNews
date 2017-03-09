package uianimationdemo.com.example.daysnews.db;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Environment;
import java.util.ArrayList;
import java.util.List;
import uianimationdemo.com.example.daysnews.entiy.NewsListShow;

/**
 * Created by Steven on 2017/2/17.
 */

public class MySQLiteDatabaseDB {
    private NewsListShow.News news;
    private List<NewsListShow.News> list;
    private MySqliteOpenHelper mySqliteOpenHelper;


    /**
     *
     * @param context   从外部传入
     * @return          查询数据库获取数据
     */
    public List<NewsListShow.News> qureSQL(Context context){
        list=new ArrayList<>();
        //如果有数据就先清空集合
        if(list!=null){
            list.clear();
        }
        mySqliteOpenHelper=new MySqliteOpenHelper(context);
        SQLiteDatabase db=mySqliteOpenHelper.getWritableDatabase();
//        //路径
//        String path= Environment.getDataDirectory()+"/data/"+context.getPackageName()+"/databases/"+"newsdb";
//
//        SQLiteDatabase db=SQLiteDatabase.openOrCreateDatabase(path,null);
        //查找数据库lovenews表格数据
        //select * from lovenews
        String str="select * from lovenews";
        //查询方法
        Cursor c = db.rawQuery(str, null);

        //遍历
        if(c.moveToFirst()){
                news= new NewsListShow.News();
            do{
                String icon=c.getString(c.getColumnIndex("icon"));
                String title=c.getString(c.getColumnIndex("title"));
                String stamp=c.getString(c.getColumnIndex("stamp"));
                String link=c.getString(c.getColumnIndex("link"));
                String summary=c.getString(c.getColumnIndex("summary"));
                int nid=c.getInt(c.getColumnIndex("nid"));

                news.setIcon(icon);
                news.setLink(link);
                news.setNid(nid);
                news.setStamp(stamp);
                news.setSummary(summary);
                news.setTitle(title);
                list.add(news);

            }while(c.moveToNext());
                c.close();
        }
        return list;
    }



}
