package uianimationdemo.com.example.daysnews.fragment;


import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Environment;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import uianimationdemo.com.example.daysnews.R;
import uianimationdemo.com.example.daysnews.adapter.MyXListViewAdapter;
import uianimationdemo.com.example.daysnews.db.MySQLiteDatabaseDB;
import uianimationdemo.com.example.daysnews.db.MySqliteDataBase;
import uianimationdemo.com.example.daysnews.entiy.NewsListShow;
import uianimationdemo.com.example.daysnews.news.UserActivity;
import uianimationdemo.com.example.daysnews.news.WebViewActivity;

/**
 * A simple {@link Fragment} subclass.
 */
public class FavoriteFragment extends Fragment implements AdapterView.OnItemLongClickListener,View.OnClickListener, AdapterView.OnItemClickListener {
    private ListView lv_favorite;
    private MyXListViewAdapter adapter;
    private MySQLiteDatabaseDB msq;
    private LinearLayout fragment_favorite;
    private TextView tv_popfavorite_delete,tv_popfavorite_deleteAll;
    private NewsListShow.News news;
    private LinearLayout ll_XListView;
    private List list;



    public FavoriteFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =inflater.inflate(R.layout.fragment_favorite,container,false);
        initView(view);
        initEvent();
        initData();
        return view;
    }

    private void initData() {
        adapter.add(msq.qureSQL(getActivity()));
    }

    private void initEvent() {
        lv_favorite.setOnItemLongClickListener(this);
        lv_favorite.setOnItemClickListener(this);

    }

    private void initView(View view) {
        ll_XListView= (LinearLayout) view.findViewById(R.id.ll_xListView);
        fragment_favorite= (LinearLayout) view.findViewById(R.id.fragment_favorite);
        lv_favorite= (ListView) view.findViewById(R.id.lv_favorite);
        list=new ArrayList();
        msq=new MySQLiteDatabaseDB();
        adapter=new MyXListViewAdapter(getActivity());
        lv_favorite.setAdapter(adapter);
    }




    private PopupWindow popupWindow;
    private View popView;
    private void showPop(int position) {
        popupWindow=new PopupWindow(getActivity());
        news=adapter.getItem(position);
        //设置宽高
        popupWindow.setWidth(ViewGroup.LayoutParams.FILL_PARENT);
        popupWindow.setHeight(ViewGroup.LayoutParams.FILL_PARENT);
        //实例化View
        popView= LayoutInflater.from(getActivity()).inflate(R.layout.item_pop_favorite,null);
        //设置要显示的view
        popupWindow.setContentView(popView);
        //实例化popupwindow中的控件
        initPop();
        //设置事件
        initPopEvent();
        //让外部可点击
        popupWindow.setOutsideTouchable(true);
        //设置显示的View
        View view=LayoutInflater.from(getActivity()).inflate(R.layout.fragment_favorite,null);
        //显示PopupWindow
        popupWindow.showAtLocation(view, Gravity.CENTER,0,0);

        popView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(popupWindow.isShowing()){
                    popupWindow.dismiss();
                }
                return false;
            }
        });
    }

    private void initPopEvent() {
        tv_popfavorite_delete.setOnClickListener(this);
        tv_popfavorite_deleteAll.setOnClickListener(this);
    }

    private void initPop() {
        tv_popfavorite_delete= (TextView) popView.findViewById(R.id.tv_popfavorite_delete);
        tv_popfavorite_deleteAll= (TextView) popView.findViewById(R.id.tv_popfavorite_deleteAll);
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.tv_popfavorite_deleteAll:
                MySqliteDataBase.deleteAllNews(getActivity(),news);
                adapter.clear();
                popupWindow.dismiss();
                break;
            case R.id.tv_popfavorite_delete:
                MySqliteDataBase.deleteNews(getActivity(),news);
                adapter.clear();
                adapter.add(msq.qureSQL(getActivity()));
                popupWindow.dismiss();
                break;
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        NewsListShow.News news=msq.qureSQL(getActivity()).get(position);
        Intent intent=new Intent(getActivity(), WebViewActivity.class);
        intent.putExtra("info",news);
        startActivity(intent);
    }


    /**
     * 长按点击事件
     * @param parent
     * @param view
     * @param position
     * @param id
     * @return
     */
    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
        showPop(position);
        return true;
    }
}
