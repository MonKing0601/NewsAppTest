package com.example.monking.newsapptest.act;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.example.monking.adapter.GuideAdapter;
import com.example.monking.newsapptest.R;
import com.example.monking.utils.SharedPreferencesUtils;
import com.example.monking.view.DepthPageTransformer;

import java.util.ArrayList;

/**
 * 引导界面
 * Created by MonKing on 2016/10/16.
 */

public class GuideActivity extends Activity implements ViewPager.OnPageChangeListener {
    private ViewPager mViewPager;
    private ArrayList<ImageView> mImageLists;
    private int[] ImageRes = {R.mipmap.guide_1, R.mipmap.guide_2, R.mipmap.guide_3};
    private Button mButton;
    private static final boolean NO_FIRST_NUMBER=true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Boolean x=SharedPreferencesUtils.getBoolean(this,"first_time",false);
        if (!x) {
            initView();
            initData();
            SharedPreferencesUtils.saveBoolean(this, "first_time", NO_FIRST_NUMBER);
            System.out.println(x);
        }else{
            Intent intent=new Intent(GuideActivity.this,MainActivity.class);
            startActivity(intent);
            finish();
        }

        mButton= (Button) findViewById(R.id.button);
        mViewPager.setOnPageChangeListener(this);
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(GuideActivity.this,MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

    }

    private void initData() {
        mImageLists = new ArrayList<ImageView>();
        for (int i = 0; i < ImageRes.length; i++) {
            ImageView imageView = new ImageView(this);
            imageView.setBackgroundResource(ImageRes[i]);
            mImageLists.add(imageView);
        }
        GuideAdapter adapter = new GuideAdapter(mImageLists);
        mViewPager.setPageTransformer(true, new DepthPageTransformer());
        mViewPager.setAdapter(adapter);
    }

    private void initView() {
        setContentView(R.layout.activity_guide);
        mViewPager = (ViewPager) findViewById(R.id.view_pager);

    }


    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    /**
     * 设置引导页面最后一页的按钮可见。
     *
     * @param position
     */
    @Override
    public void onPageSelected(int position) {
        if (position == mImageLists.size() - 1) {
            mButton.setVisibility(View.VISIBLE);
        }else {
            mButton.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }


}
