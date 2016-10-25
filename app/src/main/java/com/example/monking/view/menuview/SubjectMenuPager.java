package com.example.monking.view.menuview;

import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import com.example.monking.newsapptest.act.MainActivity;

/**
 * Created by MonKing on 2016/10/22.
 */

public class SubjectMenuPager extends BaseMenuDetailPager {
    public SubjectMenuPager(MainActivity activity) {
        super(activity);
    }

    @Override
    public View initView() {
        TextView tv=new TextView(mainActivity);
        tv.setText("侧边栏专题页面");
        tv.setGravity(Gravity.CENTER);
        tv.setTextSize(22);
        return tv;
    }
}
