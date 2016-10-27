package com.example.monking.view.baseviewpager;

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

    }

}
