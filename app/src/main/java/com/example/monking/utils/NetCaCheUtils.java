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

    public NetCaCheUtils(String tag) {
        this.tag = tag;
    }

    public void getBitMapFromNet(ImageView mImage, String url) {
        imageView = mImage;
        new BitMapTask().execute(mImage, url);
    }

    class BitMapTask extends AsyncTask<Object, Integer, Bitmap> {
        @Override
        protected void onPostExecute(Bitmap result) {
            Log.d(TAG, "onPostExecute: url:" + url);
            Log.d(TAG, "onPostExecute: tag:" + tag);
            if (result != null) {
                imageView.setImageBitmap(result);

            }
        }

        //更新下载时时
        @Override
        protected void onProgressUpdate(Integer[] values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected Bitmap doInBackground(Object[] params) {
            imageView = (ImageView) params[0];
            url = (String) params[1];
            //下载图片
            Bitmap bitmap = null;
            try {
                bitmap = download(url);
                Log.d(TAG, "doInBackground: url:" + url);
                Log.d(TAG, "doInBackground: tag:" + tag);
                //这里拿到了正确的图片地址  并且生成了bitmap
            } catch (IOException e) {
                e.printStackTrace();
            }
            return bitmap;
        }

        @Override
        protected void onPreExecute() {
            Log.d(TAG, "onPreExecute: ");
            super.onPreExecute();
        }
    }

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
