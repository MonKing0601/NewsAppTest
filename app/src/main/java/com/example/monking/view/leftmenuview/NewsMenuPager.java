package com.example.monking.view.leftmenuview;

import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;

import com.example.monking.domain.NewsMenu;
import com.example.monking.newsapptest.R;
import com.example.monking.newsapptest.act.MainActivity;
import com.example.monking.view.fragment.MenuFragment;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.viewpagerindicator.TabPageIndicator;

import java.util.ArrayList;

/**
 * Created by MonKing on 2016/10/22.
 */

public class NewsMenuPager extends BaseMenuDetailPager {
    private ViewPager mViewPager;
    private TabPageIndicator mIndicator;
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
        for (int i = 0; i < dataMenus.get(0).children.size(); i++) {
            TabDetailPager tabDetailPager = new TabDetailPager(mainActivity, dataMenus.get(0).children.get(i).title);
            mPagers.add(tabDetailPager);
            System.out.println(mPagers.get(i));
        }

        NewsMenuDetailApapter adapter = new NewsMenuDetailApapter();
        mViewPager.setAdapter(adapter);
        mIndicator.setViewPager(mViewPager);
    }

    @Override
    public View initView() {
        View view = View.inflate(mainActivity, R.layout.two_content_layout, null);
        mViewPager = (ViewPager) view.findViewById(R.id.two_viewpager_222);
        mIndicator = (TabPageIndicator) view.findViewById(R.id.indicator);
        ViewUtils.inject(this,view);
        return view;
    }

    public class NewsMenuDetailApapter extends PagerAdapter {
        @Override
        public CharSequence getPageTitle(int position) {
            String x = dataMenus.get(0).children.get(position).title;
            return x;
        }

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
            container.addView(view);
            pager.initData();
            return view;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }


    }
    @OnClick(R.id.indicator_ImageBtn)
    public void nextPager(View view){
        int currentItem=mViewPager.getCurrentItem();
        currentItem++;
        mViewPager.setCurrentItem(currentItem);
    }
}
