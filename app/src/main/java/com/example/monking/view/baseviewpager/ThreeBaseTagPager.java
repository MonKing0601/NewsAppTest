package com.example.monking.view.baseviewpager;

import android.view.Gravity;
import android.widget.TextView;

import com.example.monking.newsapptest.act.MainActivity;

/**
 * Created by MonKing on 2016/10/19.
 */

public class ThreeBaseTagPager extends BaseTagPager {
    public ThreeBaseTagPager(MainActivity mainActivity) {
        super(mainActivity);
    }
    @Override
    public void initData() {
        textView.setText("老三的标题");
        TextView tv=new TextView(mainActivity);
        tv.setText("老三的内容");
        tv.setTextSize(25);
        tv.setGravity(Gravity.CENTER);
        super.initData();
    }

}
