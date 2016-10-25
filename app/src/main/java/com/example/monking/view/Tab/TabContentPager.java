package com.example.monking.view.Tab;

import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import com.example.monking.newsapptest.act.MainActivity;
import com.example.monking.view.menuview.BaseMenuDetailPager;

/**
 * Created by MonKing on 2016/10/24.
 */

public class TabContentPager extends BaseMenuDetailPager {


    private TextView tv;

    public TabContentPager(MainActivity activity) {
        super(activity);

    }

    @Override
    public void initData() {
        super.initData();
    }

    @Override
    public View initView() {
        tv = new TextView(mainActivity);
        tv.setText("就是看看");
        tv.setTextSize(22);
        tv.setGravity(Gravity.CENTER);

        return tv;
    }
}
