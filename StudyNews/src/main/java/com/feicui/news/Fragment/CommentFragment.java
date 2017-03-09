package com.feicui.news.Fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.feicui.news.R;
import com.feicui.news.base.BaseFragment;

/**
 * Created by Administrator on 2017/2/9.
 */

public class CommentFragment extends BaseFragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
       View view = inflater.inflate(R.layout.fragment_comment,container,false);
        return view;
    }
}
