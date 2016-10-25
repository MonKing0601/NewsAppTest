package com.example.monking.domain;

import java.util.ArrayList;

/**
 * Created by MonKing on 2016/10/21.
 * 用于提取JSon数据
 */

public class NewsMenu {
    public ArrayList<Integer> extend;
    public ArrayList<DataMenu> data;
    public int retcode;

    public class DataMenu{
        public int id;
        public String title;
        public int type;
        public ArrayList<Children> children;

        @Override
        public String toString() {
            return "DataMenu{" +
                    "title='" + title + '\'' +","+"children"+children+'\''+
                    '}';
        }
    }

    public class Children{
        public int id;
        public String url;
        public int type;
        public String title;

        @Override
        public String toString() {
            return "Children{" +
                    "title='" + title + '\'' +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "NewsMenu{" +
                "data=" + data +
                '}';
    }
}
