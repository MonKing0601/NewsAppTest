package com.example.monking.view.menuview;

import android.view.View;

import com.example.monking.newsapptest.act.MainActivity;


/**
 * Created by MonKing on 2016/10/22.
 */

public abstract class BaseMenuDetailPager {
    public MainActivity mainActivity;
    public View rootView;

    public BaseMenuDetailPager(MainActivity activity) {
        mainActivity = activity;
        rootView = initView();
        initData();
    }

    public abstract View initView();

    public void initData() {

    }

    public View getRootView(){
        return rootView;
    }

}
