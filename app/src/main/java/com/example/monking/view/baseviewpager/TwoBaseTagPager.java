package com.example.monking.view.baseviewpager;

import android.view.Gravity;
import android.widget.TextView;

import com.example.monking.newsapptest.act.MainActivity;

/**
 * Created by MonKing on 2016/10/19.
 */

public class TwoBaseTagPager extends BaseTagPager {


    public TwoBaseTagPager(MainActivity mainActivity) {
        super(mainActivity);
    }

    @Override
    public void initData() {
        textView.setText("老二的标题");
        TextView tv=new TextView(mainActivity);
        tv.setText("老二的内容");
        tv.setTextSize(25);
        tv.setGravity(Gravity.CENTER);
        super.initData();
    }

}
