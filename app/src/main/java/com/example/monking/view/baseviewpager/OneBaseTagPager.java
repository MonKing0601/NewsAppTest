package com.example.monking.view.baseviewpager;

import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import com.example.monking.global.GlobalConstants;
import com.example.monking.newsapptest.act.MainActivity;
import com.example.monking.utils.CacheUtils;
import com.example.monking.view.leftmenuview.BaseMenuDetailPager;
import com.example.monking.view.leftmenuview.InteractMenuPager;
import com.example.monking.view.leftmenuview.NewsMenuPager;
import com.example.monking.view.leftmenuview.PotosMenuPager;
import com.example.monking.view.leftmenuview.SubjectMenuPager;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by MonKing on 2016/10/19.
 */

public class OneBaseTagPager extends BaseTagPager {


    private List<BaseMenuDetailPager> baseMenuDetailPagers;
    private View view;
    private TextView tv;

    public OneBaseTagPager(MainActivity mainActivity) {
        super(mainActivity);
    }

    @Override
    public void initData() {

        //更换标题
        textView.setText("老大的标题");
        //创建内容
        tv = new TextView(mainActivity);
        tv.setText("老大的内容");
        tv.setTextSize(25);
        tv.setGravity(Gravity.CENTER);
        //替换内容
        frameLayout.addView(tv);
    /**
    *没有缓存则缓存数据，有缓存则获取缓存
    */
        String cache = CacheUtils.getCache(mainActivity, GlobalConstants.CATEGORY_URL);
        if (!TextUtils.isEmpty(cache)) {
            processJson(cache);
        } else {
            getDataFromServer();
        }
        //初始化侧边栏的内容页面
        baseMenuDetailPagers = new ArrayList<BaseMenuDetailPager>();
        baseMenuDetailPagers.add(new NewsMenuPager(mainActivity));
        baseMenuDetailPagers.add(new SubjectMenuPager(mainActivity));
        baseMenuDetailPagers.add(new PotosMenuPager(mainActivity));
        baseMenuDetailPagers.add(new InteractMenuPager(mainActivity));
    }

    public void setNewMenuPager(int position) {
        BaseMenuDetailPager pager = baseMenuDetailPagers.get(position);
        View view = pager.rootView;
        if (position == 0) {
            frameLayout.removeAllViews();
            frameLayout.addView(view);
        } else {
            frameLayout.removeAllViews();
            frameLayout.addView(view);
        }
        pager.initData();
    }

}
