package com.example.monking.utils;

import android.content.Context;

/**
 * 存储数据的方法类，基于之前的封装的SharedPreferencesUtils
 * Created by MonKing on 2016/10/21.
 */

public class CacheUtils {
    public static void setCache(Context context, String url, String json){
        SharedPreferencesUtils.saveString(context,url,json);
    }

    public static String getCache(Context context, String url){
        return SharedPreferencesUtils.getString(context,url,null);

    }
}
