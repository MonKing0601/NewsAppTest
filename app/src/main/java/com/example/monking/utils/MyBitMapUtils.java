package com.example.monking.utils;

import android.graphics.Bitmap;
import android.widget.ImageView;

/**
 * 自定义二级缓存图片加载
 * Created by MonKing on 2016/11/12.
 */

public class MyBitMapUtils {

    private NetCaCheUtils mNetCaCheUtils;
    private LocalCaCheUtils mLocalCacheUtils;

    public MyBitMapUtils(String tag) {
        mLocalCacheUtils = new LocalCaCheUtils();
        mNetCaCheUtils = new NetCaCheUtils(tag, mLocalCacheUtils);

    }


    public void display(ImageView mImage, String url) {
        //本地加载图片
        try {
            Bitmap bitmap = mLocalCacheUtils.getLocaCache(url);
            if (bitmap != null) {
                mImage.setImageBitmap(bitmap);
                return;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        //网络加载图片
        mNetCaCheUtils.getBitMapFromNet(mImage, url);
    }
}
