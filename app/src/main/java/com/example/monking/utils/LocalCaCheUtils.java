package com.example.monking.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

/**
 * Created by MonKing on 2016/11/13.
 */

public class LocalCaCheUtils {
    private static final String LOCAL_CACHE_PATH = Environment.getExternalStorageDirectory().getAbsolutePath() + "/zhbj_cache";

    //写本地缓存
    public void setLocalCache(String url, Bitmap bitmap) {
        File dir = new File(LOCAL_CACHE_PATH);
        if (!dir.exists() || !dir.isDirectory()) {
            dir.mkdirs();//创建文件夹
        }

        try {
            String fileName = MD5Encoder.encode(url);
            File cacheFile = new File(dir, fileName);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, new FileOutputStream(cacheFile));
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    //读本地缓存
    public Bitmap getLocaCache(String url) throws Exception {
        File cacheFile = new File(LOCAL_CACHE_PATH, MD5Encoder.encode(url));
        if (cacheFile.exists()) {
            Bitmap bitmap = BitmapFactory.decodeStream(new FileInputStream(cacheFile));
            return bitmap;
        } else {
            return null;
        }

    }
}
