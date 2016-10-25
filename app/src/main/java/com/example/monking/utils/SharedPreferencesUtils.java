package com.example.monking.utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by MonKing on 2016/10/15.
 * 工具类专门用来保存以及获取本地的SharedPreferences数据。
 */

public class SharedPreferencesUtils {
    private  static final String SP_NAME="config";
    private static SharedPreferences sp;

    public static void saveString(Context context,String key, String value){
        if (sp==null){
            sp=context.getSharedPreferences(SP_NAME,0);
        }
        sp.edit().putString(key,value).commit();
    }

    public static  void saveBoolean(Context context,String key,Boolean value){
        if (sp==null){
            sp=context.getSharedPreferences(SP_NAME,0);

        }
        sp.edit().putBoolean(key,value).commit();
    }

    public static  String getString(Context context,String key,String defValue){
        if (sp==null){
            sp=context.getSharedPreferences(SP_NAME,0);

        }
        return  sp.getString(key,defValue);
    }


    public static  Boolean getBoolean(Context context,String key,boolean defValue){
        if (sp==null){
            sp=context.getSharedPreferences(SP_NAME,0);

        }
        return  sp.getBoolean(key,defValue);
    }
}
