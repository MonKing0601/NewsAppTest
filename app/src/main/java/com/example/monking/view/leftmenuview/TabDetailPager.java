package com.example.monking.view.leftmenuview;

import android.content.Intent;
import android.graphics.Color;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.monking.domain.NewsMenu;
import com.example.monking.domain.TabDetailBean;
import com.example.monking.global.GlobalConstants;
import com.example.monking.newsapptest.R;
import com.example.monking.newsapptest.act.MainActivity;
import com.example.monking.newsapptest.act.NewsDetailActivity;
import com.example.monking.rewrite.PullToRefreshListView;
import com.example.monking.utils.SharedPreferencesUtils;
import com.google.gson.Gson;
import com.lidroid.xutils.BitmapUtils;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;
import com.viewpagerindicator.CirclePageIndicator;

import java.util.ArrayList;

/**
 * 新闻中心页签对象
 * Created by MonKing on 2016/10/25.
 */

public class TabDetailPager extends BaseMenuDetailPager {
    private NewsMenu.Children dataMenus;
    private TextView tv;
    private ViewPager mViewpager;
    private TextView mTextview;
    private PullToRefreshListView mListView;
    private CirclePageIndicator mIndicator;
    private String result;
    private String Url;
    private TabDetailBean tdBean;
    private ArrayList<TabDetailBean.TopNewsData> topNewsDatas;
    private ArrayList<TabDetailBean.NewsData> listNewsDatas;
    private String mMoreUrl;
    private TabDetailListAdapter tabDetailListAdapter;

    public TabDetailPager(MainActivity activity, NewsMenu.Children dataMenus) {
        super(activity);
        this.dataMenus = dataMenus;

    }

    /**
     * 绑定布局
     *
     * @return
     */
    @Override
    public View initView() {
        View view = View.inflate(mainActivity, R.layout.tab_detail_content_layout, null);
        mListView = (PullToRefreshListView) view.findViewById(R.id.tab_detail_listview);
        View heardview = View.inflate(mainActivity, R.layout.detail_list_headview, null);
        mViewpager = (ViewPager) heardview.findViewById(R.id.tab_detail_top_viewpager);
        mTextview = (TextView) heardview.findViewById(R.id.detail_content_title);
        mIndicator = (CirclePageIndicator) heardview.findViewById(R.id.circle_indicator);
        mListView.addHeaderView(heardview);
        mListView.setOnRefreshListener(new PullToRefreshListView.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getDataFromServer();
            }

            @Override
            public void onLoadMore() {
                if (mMoreUrl != null) {
                    getMoreDataFromSever();
                } else {
                    mListView.visibleHeadView(false);
                    Toast.makeText(mainActivity, "没有数据了", Toast.LENGTH_SHORT).show();
                }
            }
        });
        //设置小标题新闻的的点击事件
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                position = position - mListView.getHeaderViewsCount();//减去两个头布局的数量，使获取的position是所需的数据
                TabDetailBean.NewsData data= tdBean.data.news.get(position);
                String readId = SharedPreferencesUtils.getString(mainActivity,"readId","");//获取保存在本机的数据，用于记录是否被点击了
                if (!readId.contains(data.id+"")){//判断字符串是否有这个ID
                    readId = readId + data.id + ","; //再加上新的点击数据到字符串里
                    SharedPreferencesUtils.saveString(mainActivity, "readId", readId);//然后保存到数据里面
                    TextView tvTitle= (TextView) view.findViewById(R.id.list_content_textview);
                    tvTitle.setTextColor(Color.GRAY);
                }
                Intent intent=new Intent(mainActivity, NewsDetailActivity.class);
                intent.putExtra("url",data.url);
                mainActivity.startActivity(intent);

            }
        });
        return view;
    }

    /**
     * 链接服务器获取数据
     */
    @Override
    public void initData() {
        Url = GlobalConstants.SERVER_URL + dataMenus.url;
        getDataFromServer();
    }

    /**
     * 写个访问网络数据的方法。
     * 地址是本地数据+之前传过来的数据url
     */
    protected void getDataFromServer() {
        HttpUtils utils = new HttpUtils();
        utils.send(HttpRequest.HttpMethod.GET, Url, new RequestCallBack<String>() {
            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {
                result = responseInfo.result;
                processJson(result, false);
                mListView.visibleHeadView(true);
            }

            @Override
            public void onFailure(HttpException e, String s) {
                e.getExceptionCode();
                mListView.visibleHeadView(false);
            }
        });
    }

    /**
     * 写个解析Json的方法。
     */
    public void processJson(String json, boolean isMore) {
        Gson gson = new Gson();
        tdBean = gson.fromJson(json, TabDetailBean.class);
        String moreUrl = tdBean.data.more;
        if (!TextUtils.isEmpty(moreUrl)) {
            mMoreUrl = GlobalConstants.SERVER_URL + moreUrl;
        } else {
            mMoreUrl = null;
        }

        //判断是否正在加载更多数据
        if (!isMore) {
            //取到头条viewpager的信息，并设置相关的属性
            topNewsDatas = tdBean.data.topnews;
            if (topNewsDatas != null) {
                mViewpager.setAdapter(new TabDetailAdapter());
                mIndicator.setViewPager(mViewpager);
                mIndicator.setSnap(true);
                mIndicator.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                    @Override
                    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                        //设置标题
                        mTextview.setText(topNewsDatas.get(position).title);
                    }

                    @Override
                    public void onPageSelected(int position) {

                    }

                    @Override
                    public void onPageScrollStateChanged(int state) {

                    }


                });
                mIndicator.onPageSelected(0);


            } else {
                System.out.println("topnewsdata返回为空值");
            }
            //listview的新闻信息，并设置
            listNewsDatas = tdBean.data.news;
            if (listNewsDatas != null) {
                tabDetailListAdapter = new TabDetailListAdapter();
                mListView.setAdapter(tabDetailListAdapter);
            } else {
                System.out.println("listnewsdatas 为空值");
            }
        } else {
            ArrayList<TabDetailBean.NewsData> moreNewsData = tdBean.data.news;
            listNewsDatas.addAll(moreNewsData);
            tabDetailListAdapter.notifyDataSetChanged();
        }

    }

    public void getMoreDataFromSever() {
        HttpUtils utils = new HttpUtils();
        utils.send(HttpRequest.HttpMethod.GET, mMoreUrl, new RequestCallBack<String>() {
            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {
                String result = responseInfo.result;
                processJson(result, true);
                mListView.visibleHeadView(true);
            }

            @Override
            public void onFailure(HttpException e, String s) {
                e.getExceptionCode();
                mListView.visibleHeadView(false);
            }
        });
    }


    /**
     * 创建viewpager适配器
     */
    class TabDetailAdapter extends PagerAdapter {
        private BitmapUtils mBitmapUtils;

        public TabDetailAdapter() {
            mBitmapUtils = new BitmapUtils(mainActivity);
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            ImageView imageView = new ImageView(mainActivity);
            mBitmapUtils.configDefaultLoadingImage(R.drawable.topnews_item_default);
            String url = topNewsDatas.get(position).topimage;
            mBitmapUtils.display(imageView, url);
            container.addView(imageView);
            return imageView;
        }

        @Override
        public int getCount() {
            return topNewsDatas.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }
    }

    /**
     * 创建listview适配器
     */
    class TabDetailListAdapter extends BaseAdapter {
        private BitmapUtils bitmapUtils;

        public TabDetailListAdapter() {
            bitmapUtils = new BitmapUtils(mainActivity);
        }

        @Override
        public int getCount() {
            return listNewsDatas.size();
        }

        @Override
        public TabDetailBean.NewsData getItem(int position) {
            return listNewsDatas.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder viewHolder;
            if (convertView == null) {
                convertView = View.inflate(mainActivity, R.layout.tab_detail_content_listview, null);
                viewHolder = new ViewHolder();
                viewHolder.mIcon = (ImageView) convertView.findViewById(R.id.detail_list_image);
                viewHolder.mContentText = (TextView) convertView.findViewById(R.id.list_content_textview);
                viewHolder.mDateText = (TextView) convertView.findViewById(R.id.list_time_textview);
                convertView.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) convertView.getTag();
            }
            String str = listNewsDatas.get(position).listimage;
            bitmapUtils.configDefaultLoadingImage(R.drawable.news_pic_default);
            bitmapUtils.display(viewHolder.mIcon, str);
            viewHolder.mContentText.setText(listNewsDatas.get(position).title);
            viewHolder.mDateText.setText(listNewsDatas.get(position).pubdate);
            String readId = SharedPreferencesUtils.getString(mainActivity,"readId","");
            if (readId.contains(listNewsDatas.get(position).id+"")){
                viewHolder.mContentText.setTextColor(Color.GRAY);
            }else{
                viewHolder.mContentText.setTextColor(Color.BLACK);
            }
            return convertView;
        }
    }

    static class ViewHolder {
        public ImageView mIcon;
        public TextView mContentText;
        public TextView mDateText;
    }


}

