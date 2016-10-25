package com.example.monking.newsapptest.act;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.example.monking.utils.SharedPreferencesUtils;
import com.example.monking.newsapptest.R;


/**
 * 欢迎页面
 * Created by MonKing on 2016/10/15.
 */

public class SplashActivity extends Activity {
    public Handler mhandler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initview();
    }

    private void initview() {
        setContentView(R.layout.activity_splash);
        mhandler=new Handler();
        mhandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                boolean result= SharedPreferencesUtils.getBoolean(SplashActivity.this,"is_first_open",false);
            if (result){
                Intent intent=new Intent(SplashActivity.this,MainActivity.class);
                startActivity(intent);
                finish();
            }else{
                Intent intent=new Intent(SplashActivity.this,GuideActivity.class);
                startActivity(intent);
                SharedPreferencesUtils.saveBoolean(SplashActivity.this,"is_first_open",true);
                finish();
            }

            }

        },0);
    }
}
