package com.example.monking.view;

import android.support.v4.view.ViewPager;
import android.view.View;

/**
 * 开始引导页面的切换代码
 * Created by MonKing on 2016/10/16.
 */

public class DepthPageTransformer implements ViewPager.PageTransformer {
    private static float MIN_SCALE = 0.75f;

    @Override
    public void transformPage(View page, float position) {
        int pageWidth = page.getWidth();
        System.out.println("pageWidth = " + pageWidth);

        if (position < -1) {
            page.setAlpha(0);
        } else if (position <= 0) {
            page.setAlpha(1);
            page.setTranslationX(1);
            page.setScaleX(1);
            page.setScaleY(1);
        } else if (position <= 1) {
            page.setAlpha(1 - position);
            page.setTranslationX(pageWidth * (-position));
            float scaleFactor = MIN_SCALE + (1 - MIN_SCALE) * (1 - Math.abs(position));
            page.setScaleY(scaleFactor);
            page.setScaleX(scaleFactor);
        } else {
            page.setAlpha(0);
        }
    }
}
