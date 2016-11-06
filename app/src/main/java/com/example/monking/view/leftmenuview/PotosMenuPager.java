package com.example.monking.view.leftmenuview;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

import com.example.monking.adapter.PhotoAdapter;
import com.example.monking.domain.PhotoBean;
import com.example.monking.global.GlobalConstants;
import com.example.monking.newsapptest.R;
import com.example.monking.newsapptest.act.MainActivity;
import com.example.monking.utils.CacheUtils;
import com.example.monking.utils.DividerItemDecoration;
import com.google.gson.Gson;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;

import java.util.ArrayList;

/**
 * Created by MonKing on 2016/10/22.
 */

public class PotosMenuPager extends BaseMenuDetailPager {
    private RecyclerView mRecycleView;
    private ArrayList<PhotoBean.PhotoNews> mNewsList;
    private View view;
    private static final String TAG = "PotosMenuPager";

    public PotosMenuPager(MainActivity activity) {
        super(activity);

    }

    @Override
    public View initView() {
        initData();
        view = View.inflate(mainActivity, R.layout.photo_menu_layout, null);
        mRecycleView = (RecyclerView) view.findViewById(R.id.recycle_view);
        mRecycleView.setLayoutManager(new LinearLayoutManager(mainActivity));
        mRecycleView.addItemDecoration(new DividerItemDecoration(mainActivity,DividerItemDecoration.VERTICAL_LIST));
        //数据已经传过去了。
        PhotoAdapter adapter=new PhotoAdapter(mainActivity,mNewsList);
        mRecycleView.setAdapter(adapter);
        return view;
    }

    @Override
    public void initData() {
        String cache = CacheUtils.getCache(mainActivity, GlobalConstants.PHOTO_URL);
        if (!TextUtils.isEmpty(cache)) {
            processData(cache);
        } else {
            //这边要读取服务器文件
            getDataFromSever();
        }

    }

    public void getDataFromSever() {
        HttpUtils httpUtils = new HttpUtils();
        httpUtils.send(HttpRequest.HttpMethod.GET, GlobalConstants.PHOTO_URL, new RequestCallBack<String>() {
            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {
                String result = responseInfo.result;
                processData(result);
                CacheUtils.setCache(mainActivity, GlobalConstants.PHOTO_URL, result);
            }

            @Override
            public void onFailure(HttpException e, String s) {
                e.getExceptionCode();
                Toast.makeText(mainActivity, s, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void processData(String result) {
        Gson gson = new Gson();
        PhotoBean photoBean = gson.fromJson(result, PhotoBean.class);
        mNewsList = photoBean.data.news;
    }

}
