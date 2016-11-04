package com.example.monking.newsapptest.act;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
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

public class NewsDetailActivity extends Activity implements View.OnClickListener {
    ImageButton mImagebtn, mBackBtn,mTextSizeBtn,mShareBtn;
    LinearLayout mLinearLayout;
    FrameLayout mFrameLayout;
    private String mUrl;
    private WebView mWebView;
    private WebSettings settings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.newsdetail_layout);

        mImagebtn = (ImageButton) findViewById(R.id.image_btn);
        mBackBtn = (ImageButton) findViewById(R.id.image_back);
        mLinearLayout = (LinearLayout) findViewById(R.id.right_linearlayout);
        mFrameLayout = (FrameLayout) findViewById(R.id.fl_base_content_tag);
        mTextSizeBtn= (ImageButton) findViewById(R.id.btn_tSize);
        mShareBtn= (ImageButton) findViewById(R.id.btn_share);

        mImagebtn.setVisibility(View.GONE);
        mBackBtn.setVisibility(View.VISIBLE);
        mLinearLayout.setVisibility(View.VISIBLE);

        mBackBtn.setOnClickListener(this);
        mShareBtn.setOnClickListener(this);
        mTextSizeBtn.setOnClickListener(this);

        mWebView = new WebView(this);
        Intent intent = getIntent();
        mUrl = intent.getStringExtra("url");
        mWebView.loadUrl(mUrl);
        mWebView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
            }

            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                mWebView.loadUrl(request.getUrl()+"");
                return true;
            }
        });
        settings = mWebView.getSettings();
        settings.setBuiltInZoomControls(true);//显示缩放按钮
        settings.setUseWideViewPort(true);//支持双击缩放
        settings.setJavaScriptEnabled(true);//支持网页JS功能
        mFrameLayout.addView(mWebView);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.image_back:
                finish();
                break;
            case R.id.btn_share:
                break;
            case R.id.btn_tSize:
                changedTextSize();
                break;
        }
    }

    /**
     * 改变网页字体大小。
     */
    private int mCurrentSize=2;
    private int mTempWhich;
    private void changedTextSize() {
        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        builder.setTitle("字体设置");
        String[] str=new String[]{"超大字体","大字体","正常字体","小字体","超小字体"};
        builder.setSingleChoiceItems(str, mCurrentSize, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                     mTempWhich=which;
            }
        });
        builder.setNegativeButton("取消",null);
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                    switch (mTempWhich){
                        case 0:
                            settings.setTextSize(WebSettings.TextSize.LARGEST);
                            break;
                        case 1:
                            settings.setTextSize(WebSettings.TextSize.LARGER);
                            break;
                        case 2:
                            settings.setTextSize(WebSettings.TextSize.NORMAL);
                            break;
                        case 3:
                            settings.setTextSize(WebSettings.TextSize.SMALLER);
                            break;
                        case 4:
                            settings.setTextSize(WebSettings.TextSize.SMALLEST);
                            break;
                    }
                mCurrentSize=mTempWhich;
            }
        });
        builder.show();
    }
}
