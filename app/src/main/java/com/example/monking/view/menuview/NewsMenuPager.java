package com.example.monking.view.menuview;

import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import com.example.monking.newsapptest.act.MainActivity;

/**
 * Created by MonKing on 2016/10/22.
 */

public class NewsMenuPager extends BaseMenuDetailPager {
    public NewsMenuPager(MainActivity activity) {
        super(activity);
    }

    @Override
    public View initView() {
        TextView tv=new TextView(mainActivity);
        tv.setText("侧边栏新闻页面");
        tv.setGravity(Gravity.CENTER);
        tv.setTextSize(22);
        return tv;
    }
}
