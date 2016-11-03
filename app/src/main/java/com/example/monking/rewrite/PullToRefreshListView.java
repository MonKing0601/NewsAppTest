package com.example.monking.rewrite;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.AbsListView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.monking.newsapptest.R;

import java.sql.Date;
import java.text.SimpleDateFormat;

/**
 * Created by MonKing on 2016/10/30.
 */

public class PullToRefreshListView extends ListView implements AbsListView.OnScrollListener{
    private View mHeadView;
    private int measurHeight;
    private int startY = -1;
    private TextView mTextview;
    //定义三个常量，用来标识状态，定义一个变量来标识当前头栏状态
    private static final int STATE_PULL_TO_REFREASH = 1;//下拉刷新
    private static final int STATE_RELEASE_TO_REFREASH = 2;//松开刷新
    private static final int STATE_REFREASHING = 3;//正在刷新
    private int mCurrtentState = STATE_PULL_TO_REFREASH;
    private TextView mTime;
    private ImageView mImageview;
    private RotateAnimation animUp;
    private RotateAnimation animDown;
    private ProgressBar mProgress;
    private OnRefreshListener mRefreshListener;
    private View mFooterView;
    private int mFooterViewHeight;


    public PullToRefreshListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initHeadView();
        initFooterView();
    }

    public PullToRefreshListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initHeadView();
        initFooterView();
    }

    public PullToRefreshListView(Context context) {
        super(context);
        initHeadView();
        initFooterView();
    }

    /**
     * 添加头布局,绑定了一个头布局的刷新布局layout
     * 里面实现的功能有：
     * 1.隐藏头布局
     * 2.初始化刷新动画的效果
     * 3.初始化时间
     */
    public void initHeadView() {
        mHeadView = View.inflate(getContext(), R.layout.refresh_header_layout, null);
        mTextview = (TextView) mHeadView.findViewById(R.id.refreash_textview);
        mTime = (TextView) mHeadView.findViewById(R.id.refreash_time);
        mImageview = (ImageView) mHeadView.findViewById(R.id.refresh_detail_image);
        mProgress = (ProgressBar) mHeadView.findViewById(R.id.pd_loading);
        this.addHeaderView(mHeadView);
        //隐藏头布局的代码
        mHeadView.measure(0, 0);
        measurHeight = mHeadView.getMeasuredHeight();
        mHeadView.setPadding(0, -measurHeight, 0, 0);
        //初始化动画
        initAnimation();
        //初始化时间
        updateTime();
    }

    /**
     * 触底刷新的方法。
     * 添加脚布局
     *
     */
    public void initFooterView(){
        mFooterView = View.inflate(getContext(), R.layout.refresh_footer_layout,null);
            this.addFooterView(mFooterView);
            mFooterView.measure(0,0);
        mFooterViewHeight = mFooterView.getMeasuredHeight();
            mFooterView.setPadding(0,-mFooterViewHeight,0,0);
            this.setOnScrollListener(this);//给listview设置滑动监听

    }
    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                startY = (int) ev.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                if (startY == -1) {
                    startY = (int) ev.getY();
                }
                int endY = (int) ev.getY();
                int dy = endY - startY;
                if (mCurrtentState==STATE_REFREASHING){
                    break;
                }
                int firstVisiblePostion = getFirstVisiblePosition();
                if (dy > 0 && firstVisiblePostion == 0) {
                    int padding = dy - measurHeight;//计算当前下拉的布局
                    mHeadView.setPadding(0, padding, 0, 0);
                    if (padding > 0 && mCurrtentState != STATE_RELEASE_TO_REFREASH) {
                        mCurrtentState = STATE_RELEASE_TO_REFREASH;
                        refreashState();
                    } else if (padding < 0 && mCurrtentState != STATE_PULL_TO_REFREASH) {
                        mCurrtentState = STATE_PULL_TO_REFREASH;
                        refreashState();
                    }
                    return true;
                }
                break;
            case MotionEvent.ACTION_UP:
                startY=-1;
                if (mCurrtentState==STATE_RELEASE_TO_REFREASH){
                    mCurrtentState=STATE_REFREASHING;
                    refreashState();
                    mHeadView.setPadding(0,0,0,0);
                    mRefreshListener.onRefresh();

                }else if(mCurrtentState==STATE_PULL_TO_REFREASH){
                    mHeadView.setPadding(0,-measurHeight,0,0);
                }

                break;
            default:
                break;
        }
        return super.onTouchEvent(ev);
    }

    /**
     * 初始化箭头动画效果
     */
    public void initAnimation() {

        animUp = new RotateAnimation(0, -180, Animation.RELATIVE_TO_SELF, 0.5f,
                Animation.RELATIVE_TO_SELF, 0.5f);
        animUp.setDuration(200);
        animUp.setFillAfter(true);

        animDown = new RotateAnimation(-180, 0, Animation.RELATIVE_TO_SELF,
                0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        animDown.setDuration(200);
        animDown.setFillAfter(true);

    }

    /**
     * 下拉事件判断方法。
     */
    private void refreashState() {
        switch (mCurrtentState) {
            case STATE_PULL_TO_REFREASH:
                mTextview.setText("下拉刷新");
                mImageview.setVisibility(View.VISIBLE);
                mProgress.setVisibility(View.INVISIBLE);
                mImageview.startAnimation(animUp);
                break;
            case STATE_RELEASE_TO_REFREASH:
                mTextview.setText("松开刷新");
                mImageview.setVisibility(View.VISIBLE);
                mProgress.setVisibility(View.INVISIBLE);
                mImageview.startAnimation(animDown);
                break;
            case STATE_REFREASHING:
                mTextview.setText("正在刷新");
                mImageview.setVisibility(View.INVISIBLE);
                mProgress.setVisibility(View.VISIBLE);
                mImageview.clearAnimation();
                break;
        }
    }
    /**
     * 写一个回调方法
     */
    public void setOnRefreshListener(OnRefreshListener refreshListener){
        mRefreshListener = refreshListener;
    }


    /**
     * 一个回调的接口
     */
    public interface OnRefreshListener{
        public void onRefresh();
        public void onLoadMore();
    }
    /**
     * 隐藏头部list方法   调用
     */
    public void visibleHeadView(boolean success){
        if (!isLoadMore){
            mCurrtentState=STATE_PULL_TO_REFREASH;
            mHeadView.setPadding(0,-measurHeight,0,0);
            mTextview.setText("下拉刷新");
            mImageview.setVisibility(View.VISIBLE);
            mProgress.setVisibility(View.INVISIBLE);
            if (success){//刷新成功才更新时间
                updateTime();
            }
        }else{
            mFooterView.setPadding(0,-mFooterViewHeight,0,0);
            isLoadMore=false;
        }

    }
    /**
     * 下拉刷新时间
     */
    public void updateTime(){
        SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date =new Date(System.currentTimeMillis());
        String time =format.format(date);
        mTime.setText(time);
    }

    /**
     * 滑动状态接口监听方法
     * @param view
     * @param scrollState
     */
    private boolean isLoadMore;
    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {
        if (scrollState==SCROLL_STATE_IDLE){
            int lastVisiblePosition = getLastVisiblePosition();
            if (lastVisiblePosition==getCount()-1&&!isLoadMore){
                isLoadMore=true;
                mFooterView.setPadding(0,0,0,0);
                setSelection(getCount()-1);
                //通知主页加载数据
                if(mRefreshListener!=null){
                    mRefreshListener.onLoadMore();
                }
            }
        }
    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

    }
}
