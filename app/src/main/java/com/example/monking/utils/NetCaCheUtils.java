package com.example.monking.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ImageView;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import static android.content.ContentValues.TAG;

/**
 * Created by MonKing on 2016/11/12.
 */

public class NetCaCheUtils {

    private ImageView imageView;
    private String url;
    private String tag;
    private LocalCaCheUtils mLocalCaCheUtils;
    /**
     * 传递settag的标识
     *
     * @param tag
     * @param mLocalCacheUtils
     */
    public NetCaCheUtils(String tag, LocalCaCheUtils mLocalCacheUtils) {
        mLocalCaCheUtils=new LocalCaCheUtils();
        this.tag = tag;
    }

    /**
     * 提供设置图片的方法
     * 传递两个参数
     *
     * @param mImage recycleview的view
     * @param url    下载地址
     */
    public void getBitMapFromNet(ImageView mImage, String url) {
        imageView = mImage;
        new BitMapTask().execute(mImage, url);
    }

    /**
     * 异步加载的方法
     */
    class BitMapTask extends AsyncTask<Object, Integer, Bitmap> {

        /**
         * 调用前使用的方法
         * 主线程运行
         */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        /**
         * 后台运行，在子线程运行
         *
         * @param params
         * @return
         */
        @Override
        protected Bitmap doInBackground(Object[] params) {
            imageView = (ImageView) params[0];
            url = (String) params[1];
            //下载图片
            Bitmap bitmap = null;
            try {
                bitmap = download(url);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return bitmap;
        }

        /**
         * 主线程运行，用于更新主线程UI
         *
         * @param result
         */
        @Override
        protected void onPostExecute(Bitmap result) {
            if (result != null) {
                imageView.setImageBitmap(result);
                try {
                    mLocalCaCheUtils.setLocalCache(url,result);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                Log.d(TAG, "onPostExecute: 网络加载缓存了");
            }
        }

        //更新下载进度
        @Override
        protected void onProgressUpdate(Integer[] values) {
            super.onProgressUpdate(values);
        }

    }

    /**
     * 从网络下载图片的方法
     * @param url
     * @return
     * @throws IOException
     */
    public Bitmap download(String url) throws IOException {
        HttpURLConnection conn = (HttpURLConnection) new URL(url).openConnection();
        conn.setRequestMethod("GET");
        conn.setReadTimeout(5000);
        conn.setConnectTimeout(5000);
        int responseCode = conn.getResponseCode();
        if (responseCode == 200) {
            InputStream inputStream = conn.getInputStream();

            //根据输入流生成一个bitmap
            Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
            return bitmap;
        }
        if (conn != null) {
            conn.disconnect();
        }
        return null;
    }
}
