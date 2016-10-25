package com.example.monking.newsapptest.act;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.example.monking.fragment.MainContentFragment;
import com.example.monking.fragment.MenuFragment;
import com.example.monking.newsapptest.R;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.jeremyfeinstein.slidingmenu.lib.app.SlidingFragmentActivity;

public class MainActivity extends SlidingFragmentActivity {
    private static final String LEFT_MENU_TAG = "LEFT_MENU_TAG";
    private static final String RIGHT_MAIN_TAG = "RIGHT_MAIN_TAG";


    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_right_main);
        //初始化侧滑界面的方法
        createSlidingMenu();
        //初始化数据
        initData();


    }

    /**
     * 初始化数据
     */
    private void initData() {
        FragmentManager fragmentmanager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentmanager.beginTransaction();
        //完成界面的替换
        transaction.replace(R.id.layout_left_menu, new MenuFragment(), LEFT_MENU_TAG);
        transaction.replace(R.id.layout_right_main, new MainContentFragment(), RIGHT_MAIN_TAG);
        transaction.commit();
    }

    /**
     * 创建侧滑菜单的方法
     */
    private void createSlidingMenu() {
        setBehindContentView(R.layout.layout_left_menu);
        SlidingMenu menu = getSlidingMenu();
        menu.setMode(SlidingMenu.LEFT);
        menu.setBehindOffsetRes(R.dimen.sliding_menu_offset);
        menu.setShadowDrawable(R.drawable.shadow);
        menu.setTouchModeAbove(SlidingMenu.TOUCHMODE_MARGIN);
    }

    public MenuFragment getLeftMenuFragment() {
        FragmentManager fm = getSupportFragmentManager();
        MenuFragment Fragment = (MenuFragment) fm.findFragmentByTag(LEFT_MENU_TAG);
        return Fragment;

    }

    public MainContentFragment getContentMenuFragment(){
        FragmentManager fm=getSupportFragmentManager();
        MainContentFragment mainContentFragment= (MainContentFragment) fm.findFragmentByTag(RIGHT_MAIN_TAG);
        return mainContentFragment;
    }

}
