package com.example.monking.rewrite;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.monking.newsapptest.R;

/**
 * Created by MonKing on 2016/10/30.
 */

public class PullToRefreshListView extends ListView {
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


    public PullToRefreshListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initHeadView();
    }

    public PullToRefreshListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initHeadView();
    }

    public PullToRefreshListView(Context context) {
        super(context);
        initHeadView();
    }


    public void initHeadView() {
        mHeadView = View.inflate(getContext(), R.layout.refresh_layout, null);
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
    }
    /**
     * 隐藏头部list方法   调用
     */
    public void visibleHeadView(){
        mCurrtentState=STATE_PULL_TO_REFREASH;
        mHeadView.setPadding(0,-measurHeight,0,0);
        mTextview.setText("下拉刷新");
        mImageview.setVisibility(View.VISIBLE);
        mProgress.setVisibility(View.INVISIBLE);
    }
}
