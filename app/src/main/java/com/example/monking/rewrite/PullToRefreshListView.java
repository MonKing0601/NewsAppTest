package com.example.monking.rewrite;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ListView;

import com.example.monking.newsapptest.R;

/**
 * Created by MonKing on 2016/10/30.
 */

public class PullToRefreshListView extends ListView {
    private View mHeadView;
    private int measurHeight;

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


    public void initHeadView(){
        mHeadView= View.inflate(getContext(), R.layout.refresh_layout,null);
        this.addHeaderView(mHeadView);
        //隐藏头布局的代码
        mHeadView.measure(0,0);
        measurHeight = mHeadView.getMeasuredHeight();
        mHeadView.setPadding(0,-measurHeight,0,0);

        
    }
}
