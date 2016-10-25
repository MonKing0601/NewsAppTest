package com.example.monking.view.menuview;

import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;

import com.example.monking.domain.NewsMenu;
import com.example.monking.fragment.MenuFragment;
import com.example.monking.newsapptest.R;
import com.example.monking.newsapptest.act.MainActivity;

import java.util.ArrayList;

/**
 * Created by MonKing on 2016/10/22.
 */

public class NewsMenuPager extends BaseMenuDetailPager {
    private ViewPager mViewPager;

    private ArrayList<NewsMenu.DataMenu> dataMenus;
    private ArrayList<TabDetailPager> mPagers;

    public NewsMenuPager(MainActivity activity) {
        super(activity);
    }

    //初始化页签
    @Override
    public void initData() {
        MainActivity mainUI = mainActivity;
        MenuFragment menuFragment = mainUI.getLeftMenuFragment();
        dataMenus = menuFragment.getmnewsMenusData();
        mPagers = new ArrayList<TabDetailPager>();
        System.out.println(dataMenus);
        for (int i = 0; i < dataMenus.get(0).children.size(); i++) {
            TabDetailPager tabDetailPager = new TabDetailPager(mainActivity, dataMenus);
            mPagers.add(tabDetailPager);
            System.out.println(mPagers.get(i));
        }
        NewsMenuDetailApapter adapter=new NewsMenuDetailApapter();
        mViewPager.setAdapter(adapter);
        System.out.println("asdasdsdhfasdhjkdfhaskdjfhaksjdfhlas");
    }

    @Override
    public View initView() {
        View view = View.inflate(mainActivity, R.layout.two_content_layout, null);
        mViewPager = (ViewPager) view.findViewById(R.id.two_viewpager_222);
        return view;
    }

    public class NewsMenuDetailApapter extends PagerAdapter {

        @Override
        public int getCount() {
            return mPagers.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            TabDetailPager pager = mPagers.get(position);
            View view = pager.rootView;
            System.out.println("444444444444444444444444444444444"+view);
            container.addView(view);
            pager.initData();
            return view;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }
    }
}
