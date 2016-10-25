package com.example.monking.viewpager;

import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.monking.domain.NewsMenu;
import com.example.monking.fragment.MenuFragment;
import com.example.monking.newsapptest.R;
import com.example.monking.newsapptest.act.MainActivity;
import com.example.monking.view.Tab.TabContentPager;
import com.viewpagerindicator.TabPageIndicator;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by MonKing on 2016/10/19.
 */

public class TwoBaseTagPager extends BaseTagPager {

    private TabPageIndicator mIndicator = new TabPageIndicator(mainActivity);
    private ViewPager mViewpager =new ViewPager(mainActivity);
    private List<TabContentPager> mPagers = new ArrayList<TabContentPager>();
    private ArrayList<NewsMenu.DataMenu> mData;
    private ArrayList<NewsMenu.Children> childrens;
    private TextView tv;
    private View view;


    public TwoBaseTagPager(MainActivity mainActivity) {
        super(mainActivity);
    }



    @Override
    public void initData() {
        textView.setText("老二的标题");
        /**
         * 获取了fragment已经能检测到数据，
         * 不知道什么原因无法直接在基类获取数据。
         */
        MainActivity mainUI = mainActivity;
        MenuFragment menuFragment = mainUI.getLeftMenuFragment();
        mData = menuFragment.getmnewsMenusData();
        childrens = mData.get(0).children;
        /**
         * 根据数量创建页面
         */
        mPagers = new ArrayList<TabContentPager>();
        for (int i = 0; i < childrens.size(); i++) {
            TabContentPager pager = new TabContentPager(mainActivity);
            mPagers.add(pager);
            System.out.println(mPagers.get(i));
        }
        view = View.inflate(mainActivity, R.layout.two_content_layout,null);
//        frameLayout.addView(view); //这个是能设置到帧布局里面的，就是不知道为什么设置完以后 适配器没办法用了。
        mViewpager= (ViewPager) view.findViewById(R.id.two_viewpager);
        mIndicator= (TabPageIndicator) view.findViewById(R.id.indicator);
        TabMenuAdapter adapter=new TabMenuAdapter();
        mViewpager.setAdapter(adapter);
        mIndicator.setViewPager(mViewpager);
    }

    /**
     * 适配器
     */
    class TabMenuAdapter extends PagerAdapter {
        @Override
        public CharSequence getPageTitle(int position) {
            String title=childrens.get(position).title;
            return title;
        }

        @Override
        public int getCount() {
            return mPagers.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view==object;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            frameLayout.addView(view);
            TabContentPager tabContentPager=mPagers.get(position);
            View view2=tabContentPager.rootView;
            frameLayout.addView(view2);
            return view;
        }
    }
}
