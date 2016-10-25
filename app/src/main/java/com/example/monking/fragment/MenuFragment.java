package com.example.monking.fragment;

import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import com.example.monking.domain.NewsMenu;
import com.example.monking.newsapptest.R;
import com.example.monking.viewpager.OneBaseTagPager;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

import java.util.ArrayList;

/**
 * Created by MonKing on 2016/10/17.
 * 侧滑菜单的fragment
 */

public class MenuFragment extends BaseFragment {

    @ViewInject(R.id.menu_list_item)
    private ListView list;

    private ArrayList<NewsMenu.DataMenu> mnewsMenusData=new ArrayList<NewsMenu.DataMenu>();

    private int mCurrentPos = 0;   //被选中的list

    @Override
    public View initView() {
        View view = View.inflate(mainActivity, R.layout.activity_menu, null);
        //list = (ListView) view.findViewById(R.id.list_item);
        //用Xutil绑定控件
        ViewUtils.inject(this, view);
        return view;
    }


    public void setMenuData(ArrayList<NewsMenu.DataMenu> data) {
        mnewsMenusData = data;
        final LeftMenuAdapter adapter = new LeftMenuAdapter();
        list.setAdapter(adapter);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mCurrentPos = position;
                adapter.notifyDataSetChanged();//刷新listview
                //收起侧边栏
                mainActivity.getSlidingMenu().toggle();
                //从侧边菜单栏设置布局的内容
                setMenuPager(position);
            }


        });
    }
    /**
     * 传位置postition的数据到方法中
     * 获取右侧内容栏的对象，从主活动对象中获取
     * 然后再获取右侧内容对象中的 第一页面对象（里面有更改布局的方法setNewMenuPager）
     * 如果锁定的界面不是第一页，则切换界面到第一页
     * 然后用方法切换页面
     */
    private void setMenuPager(int position) {
        MainContentFragment mcFragment = mainActivity.getContentMenuFragment();
        OneBaseTagPager btPager = (OneBaseTagPager) mcFragment.getOneTagPager();
        if (mcFragment.getPagerPositionNumber()!=0){
            mcFragment.changedView(0);
        btPager.setNewMenuPager(position);}
    }

    class LeftMenuAdapter extends BaseAdapter {
        @Override
        public int getCount() {
            return mnewsMenusData.size();
        }

        @Override
        public Object getItem(int position) {
            return mnewsMenusData.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view = View.inflate(mainActivity, R.layout.left_item_menu, null);
            TextView tv = (TextView) view.findViewById(R.id.tv_left_menu);
            NewsMenu.DataMenu dataMenu = (NewsMenu.DataMenu) getItem(position);
            tv.setText(dataMenu.title);
            if (position == mCurrentPos) {
                tv.setEnabled(true);
            } else {
                tv.setEnabled(false);
            }
            return view;
        }
    }
    public ArrayList<NewsMenu.DataMenu> getmnewsMenusData(){
        return mnewsMenusData;
    }
}
