package com.example.monking.view.menuview;

import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import com.example.monking.domain.NewsMenu;
import com.example.monking.newsapptest.act.MainActivity;

import java.util.ArrayList;

/**
 * 新闻中心页签对象
 * Created by MonKing on 2016/10/25.
 */

public class TabDetailPager extends BaseMenuDetailPager {
    private ArrayList<NewsMenu.DataMenu> dataMenu;
    private String title;
    private TextView tv;

    public TabDetailPager(MainActivity activity, ArrayList<NewsMenu.DataMenu> dataMenus) {
        super(activity);
        dataMenu=dataMenus;
    }

    @Override
    public View initView() {
        tv = new TextView(mainActivity);

        return tv;
    }

    @Override
    public void initData() {
        tv.setText("dddddddd"+dataMenu);
        tv.setGravity(Gravity.CENTER);
        tv.setTextSize(22);
        tv.setTextColor(000);

    }
}
