package uianimationdemo.com.example.daysnews.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;
import uianimationdemo.com.example.daysnews.R;
import uianimationdemo.com.example.daysnews.news.MainActivity;


public class LeftFragment extends Fragment implements View.OnClickListener {
    private LinearLayout ll_news;
    private LinearLayout ll_favorite;
    private LinearLayout ll_local;
    private LinearLayout ll_comment;
    private LinearLayout ll_photo;
    private  MainActivity mainActivity;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_left,container,false);
        initView(view);
        initEvent();
        initData();
        return view;
    }

    private void initData() {
        ll_news.setSelected(true);
    }

    private void initEvent() {
        ll_news.setOnClickListener(this);
        ll_local.setOnClickListener(this);
        ll_comment.setOnClickListener(this);
        ll_favorite.setOnClickListener(this);
        ll_photo.setOnClickListener(this);

    }

    private void initView(View view) {
        ll_news= (LinearLayout) view.findViewById(R.id.ll_news);
        ll_favorite= (LinearLayout) view.findViewById(R.id.ll_favorite);
        ll_comment= (LinearLayout) view.findViewById(R.id.ll_comment);
        ll_local= (LinearLayout) view.findViewById(R.id.ll_local);
        ll_photo= (LinearLayout) view.findViewById(R.id.ll_photo);
        mainActivity= (MainActivity) getActivity();
    }

    @Override
    public void onClick(View v) {
                ll_comment.setSelected(false);
                ll_news.setSelected(false);
                ll_photo.setSelected(false);
                ll_local.setSelected(false);
                ll_favorite.setSelected(false);
        switch (v.getId()) {
            case R.id.ll_comment:
                mainActivity.showCommentFragment();
                ll_comment.setSelected(true);
                break;
            case R.id.ll_favorite:
                mainActivity.showFavoriteFragment();
                ll_favorite.setSelected(true);
                break;
            case R.id.ll_local:
                Toast.makeText(getActivity(), "this is local", Toast.LENGTH_SHORT).show();
                ll_local.setSelected(true);
                break;
            case R.id.ll_photo:
                Toast.makeText(getActivity(), "this is photo", Toast.LENGTH_SHORT).show();
                ll_photo.setSelected(true);
                break;
            case R.id.ll_news:
                mainActivity.showNewsListFragment();
                ll_news.setSelected(true);
                break;
        }

    }
}

