package com.example.monking.adapter;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

/**
 * 引导页面的适配器封装
 * Created by MonKing on 2016/10/16.
 */

public class GuideAdapter extends PagerAdapter {
    private List<ImageView> mImageLists = new ArrayList<ImageView>();

    public GuideAdapter(ArrayList<ImageView> mImageLists) {
        this.mImageLists = mImageLists;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }


    public Object instantiateItem(ViewGroup container, int position) {
        container.addView(mImageLists.get(position));
        return mImageLists.get(position);
    }

    @Override
    public int getCount() {
        return mImageLists.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }
}
