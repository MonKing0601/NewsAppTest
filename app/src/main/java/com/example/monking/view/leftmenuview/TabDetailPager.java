package com.example.monking.view.leftmenuview;

import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.monking.domain.NewsMenu;
import com.example.monking.domain.TabDetailBean;
import com.example.monking.global.GlobalConstants;
import com.example.monking.newsapptest.R;
import com.example.monking.newsapptest.act.MainActivity;
import com.google.gson.Gson;
import com.lidroid.xutils.BitmapUtils;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;

import java.util.ArrayList;

/**
 * 新闻中心页签对象
 * Created by MonKing on 2016/10/25.
 */

public class TabDetailPager extends BaseMenuDetailPager {
    private NewsMenu.Children dataMenus;
    private TextView tv;
    private ViewPager mViewpager;
    private ListView mListView;
    private String result;
    private String Url;
    private TabDetailBean tdBean;
    private ArrayList<TabDetailBean.TopNewsData> topNewsDatas;

    public TabDetailPager(MainActivity activity, NewsMenu.Children dataMenus) {
        super(activity);
        this.dataMenus=dataMenus;

    }

    @Override
    public View initView() {
        View view=View.inflate(mainActivity, R.layout.tab_detail_content_layout,null);
        mViewpager= (ViewPager) view.findViewById(R.id.tab_detail_top_viewpager);

        return view;
    }

    @Override
    public void initData() {
        Url= GlobalConstants.SERVER_URL+dataMenus.url;
        getDataFromServer();
    }
    /**
     * 写个访问网络数据的方法。
     * 地址是本地数据+之前传过来的数据url
     */
    protected void getDataFromServer() {
        HttpUtils utils = new HttpUtils();
        utils.send(HttpRequest.HttpMethod.GET,Url, new RequestCallBack<String>() {

            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {
                result = responseInfo.result;
                processJson(result);
            }

            @Override
            public void onFailure(HttpException e, String s) {
                e.getExceptionCode();

            }
        });
    }
    /**
     * 写个解析Json的方法。
     */
    public void processJson(String json) {
        Gson gson = new Gson();
        tdBean = gson.fromJson(json, TabDetailBean.class);
        topNewsDatas=tdBean.data.topnews;
        if (topNewsDatas!=null){
            mViewpager.setAdapter(new TabDetailAdapter());
        }else{
            System.out.println("topnewsdata返回为空值");
        }
    }

    /**
     * 创建viewpager适配器
     */
    class TabDetailAdapter extends PagerAdapter{
        private  BitmapUtils mBitmapUtils;

        public TabDetailAdapter() {
             mBitmapUtils= new BitmapUtils(mainActivity);
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            ImageView imageView=new ImageView(mainActivity);
            imageView.setImageResource(R.drawable.topnews_item_default);
            String url=topNewsDatas.get(position).topimage;
            mBitmapUtils.display(imageView,url);
            container.addView(imageView);
            return imageView;
        }

        @Override
        public int getCount() {
            return topNewsDatas.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view==object;
        }
    }
}
