package com.example.monking.newsapptest.act;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.view.View;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import com.example.monking.newsapptest.R;

/**
 * Created by MonKing on 2016/11/3.
 */

public class NewsDetailActivity extends Activity {
    ImageButton mImagebtn, mBackBtn;
    LinearLayout mLinearLayout;
    FrameLayout mFrameLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.newsdetail_layout);

        mImagebtn = (ImageButton) findViewById(R.id.image_btn);
        mBackBtn = (ImageButton) findViewById(R.id.image_back);
        mLinearLayout = (LinearLayout) findViewById(R.id.right_linearlayout);
        mFrameLayout = (FrameLayout) findViewById(R.id.fl_base_content_tag);

        mImagebtn.setVisibility(View.GONE);
        mBackBtn.setVisibility(View.VISIBLE);
        mLinearLayout.setVisibility(View.VISIBLE);

        final WebView mWebView = new WebView(this);
        Intent intent = new Intent();
        String url = intent.getStringExtra("url");

        mWebView.loadUrl("http://www.baidu.com");
        mWebView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                System.out.println("开始加载网页");
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                System.out.println("网页加载结束了");
            }

            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                mWebView.loadUrl(request.getUrl()+"");
                return true;
            }
        });
        WebSettings settings = mWebView.getSettings();
        settings.setBuiltInZoomControls(true);//显示缩放按钮
        settings.setUseWideViewPort(true);//支持双击缩放
        settings.setJavaScriptEnabled(true);//支持网页JS功能
        mFrameLayout.addView(mWebView);

    }
}
