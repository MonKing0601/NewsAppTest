package com.example.monking.domain;

import java.util.ArrayList;

/**
 * Created by MonKing on 2016/10/21.
 * 用于提取JSon数据
 */

public class NewsMenu {
    public int retcode;
    public ArrayList<DataMenu> data;
    public ArrayList<Integer> extend;

    public class DataMenu{
        public int id;
        public String title;
        public int type;
        public ArrayList<Children> children;
    }

    public class Children{
        public int id;
        public String url;
        public int type;
        public String title;
    }

}
