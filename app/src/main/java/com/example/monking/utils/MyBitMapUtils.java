package com.example.monking.utils;

import android.util.Log;
import android.widget.ImageView;

import static android.content.ContentValues.TAG;

/**
 * 自定义三级缓存图片加载
 * Created by MonKing on 2016/11/12.
 */

public class MyBitMapUtils {

    private NetCaCheUtils mNetCaCheUtils;

    public MyBitMapUtils(String tag) {
        mNetCaCheUtils = new NetCaCheUtils(tag);
    }



    public void display(ImageView mImage, String url, Integer position) {
        Log.d(TAG, "display: url:"+url);
        //网络加载图片
        mNetCaCheUtils.getBitMapFromNet(mImage,url);
    }
}
