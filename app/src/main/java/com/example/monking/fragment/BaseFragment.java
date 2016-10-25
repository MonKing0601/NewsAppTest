package com.example.monking.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.monking.newsapptest.act.MainActivity;

/**
 * Created by MonKing on 2016/10/18.
 * 主侧菜单的基类
 */

public abstract class BaseFragment extends Fragment {
    protected MainActivity mainActivity;//获取上下文

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mainActivity = (MainActivity) getActivity();

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = initView();
        return root;

    }

    /**
     * 覆盖此方法完成界面初始化
     *
     * @return
     */
    public abstract View initView();

    /**
     * 子类覆盖此方法完成数据的初始化
     */
    public void initData() {

    }

    /**
     * 覆盖此方法完成事件初始化
     */
    public void initEvent() {

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        //初始化事件和数据
        super.onActivityCreated(savedInstanceState);
        initData();//初始化数据
        initEvent();//初始化事件
    }
}
