package com.example.monking.fragment;

import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.example.monking.newsapptest.R;
import com.example.monking.viewpager.BaseTagPager;
import com.example.monking.viewpager.FiveBaseTagPager;
import com.example.monking.viewpager.FourBaseTagPager;
import com.example.monking.viewpager.OneBaseTagPager;
import com.example.monking.viewpager.ThreeBaseTagPager;
import com.example.monking.viewpager.TwoBaseTagPager;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by MonKing on 2016/10/18.
 * 主界面的fragment
 */

public class MainContentFragment extends BaseFragment  {

    private ViewPager viewPager;
    private RadioGroup radioGroup;
    private RadioButton radioButton1;
    private RadioButton radioButton2;
    private RadioButton radioButton3;
    private RadioButton radioButton4;
    private RadioButton radioButton5;
    private List<BaseTagPager> pagers=new ArrayList<BaseTagPager>();


    @Override
    public View initView() {
        View root= View.inflate(mainActivity,R.layout.activity_main,null);
        viewPager= (ViewPager) root.findViewById(R.id.vp_act_main);
        radioGroup= (RadioGroup) root.findViewById(R.id.rg_act_main);
        return root;

    }

    /**
     * 设置适配器
     * 创建五个页面
     */
    @Override
    public void initData() {
        pagers.add(new OneBaseTagPager(mainActivity));
        pagers.add(new TwoBaseTagPager(mainActivity));
        pagers.add(new ThreeBaseTagPager(mainActivity));
        pagers.add(new FourBaseTagPager(mainActivity));
        pagers.add(new FiveBaseTagPager(mainActivity));
        MyAdapter adapter=new MyAdapter();
        viewPager.setAdapter(adapter);
    }
    /**
     * 得到当前页面的编号
     */
    public int getPagerPositionNumber(){
        int num =viewPager.getId();
        return num;
    }
    /**
     * 切换页面
     *
     */
    public void changedView(int position){
        viewPager.setCurrentItem(position);
    }

    /**
     * 设置radioGroup的点击事件
     *
     */
    @Override
    public void initEvent() {
    radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {
            switch (checkedId){
                case R.id.one_btn:
                    viewPager.setCurrentItem(0);
                    break;
                case R.id.two_btn:
                    viewPager.setCurrentItem(1);
                    break;
                case R.id.three_btn:
                    viewPager.setCurrentItem(2);
                    break;
                case R.id.four_btn:
                    viewPager.setCurrentItem(3);
                    break;
                case R.id.five_btn:
                    viewPager.setCurrentItem(4);
                    break;
            }
        }
    });
    }



    private class MyAdapter extends PagerAdapter{

        @Override
        public int getCount() {
            return pagers.size();
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
            BaseTagPager baseTagPager=pagers.get(position);
            View view =baseTagPager.getRoot();
            container.addView(view);
            return view;
        }
    }

    public BaseTagPager getOneTagPager(){
        OneBaseTagPager btPager= (OneBaseTagPager) pagers.get(0);
        return btPager;
    }



}
