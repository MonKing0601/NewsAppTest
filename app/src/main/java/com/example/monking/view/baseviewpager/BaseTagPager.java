package com.example.monking.view.baseviewpager;

import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.monking.domain.NewsMenu;
import com.example.monking.view.fragment.MenuFragment;
import com.example.monking.global.GlobalConstants;
import com.example.monking.newsapptest.R;
import com.example.monking.newsapptest.act.MainActivity;
import com.example.monking.utils.CacheUtils;
import com.google.gson.Gson;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;

/**
 * 底部RadioGroup的相关页面加载的基类
 * Created by MonKing on 2016/10/19.
 */

public class BaseTagPager {
    protected MainActivity mainActivity;
    protected View root;
    protected ImageButton im_menu;
    protected TextView textView;
    protected FrameLayout frameLayout;
    protected String result;
    protected NewsMenu newsMenu;

    public BaseTagPager(MainActivity mainActivity) {
        this.mainActivity=mainActivity;
        initView();
        initData();
        initEvent();

    }



    public void initEvent() {
            im_menu.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mainActivity.getSlidingMenu().toggle();
                }
            });
    }

    public void initData() {

    }

    public View initView() {
        root = View.inflate(mainActivity, R.layout.fragment_content_base_context,null);
        im_menu = (ImageButton) root.findViewById(R.id.image_btn);
        textView = (TextView) root.findViewById(R.id.tv_base_title);
        frameLayout = (FrameLayout) root.findViewById(R.id.fl_base_content_tag);
        return  root;
    }

    /**
     * 返回界面空间
     * @return
     */
    public View getRoot(){
        return root;
    }

    /**
     * 用Xutils开源框架获取服务器json数据
     */
    protected void getDataFromServer() {
        HttpUtils utils = new HttpUtils();
        utils.send(HttpRequest.HttpMethod.GET, GlobalConstants.CATEGORY_URL, new RequestCallBack<String>() {

            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {
                result = responseInfo.result;
                processJson(result);
                CacheUtils.setCache(mainActivity, GlobalConstants.CATEGORY_URL, result);
            }

            @Override
            public void onFailure(HttpException e, String s) {
                e.getExceptionCode();

            }
        });
    }

    /**
     * 解析JSON的方法 调用的是Gson
     * @param json
     */
    public void processJson(String json) {
        Gson gson = new Gson();
        newsMenu = gson.fromJson(json, NewsMenu.class);
        System.out.println("解析完成后的" + newsMenu);
        //获取侧边栏对象
        MainActivity mainUI = mainActivity;
        MenuFragment menuFragment = mainUI.getLeftMenuFragment();
        menuFragment.setMenuData(newsMenu.data);
    }

    public NewsMenu getNewsMenu(){
        NewsMenu newsMenuData=new NewsMenu();
        newsMenuData=newsMenu;
        return newsMenuData;
    }

    public String  getResult(){
        return result;
    }

}
