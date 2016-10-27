package com.example.monking.domain;

import java.util.ArrayList;

/**
 * Created by MonKing on 2016/10/27.
 */

public class TabDetailBean {
    public TabDetailData data;
    public class TabDetailData {
        public String more;
        public ArrayList<NewsData> news;
        public ArrayList<TopNewsData> topnews;
    }

    public class NewsData {
        public int id;
        public String listimage;
        public String pubdate;
        public String title;
        public String type;
        public String url;



    }

    public class TopNewsData {
        public int id;
        public String pubdate;
        public String title;
        public String topimage;
        public String type;
        public String url;

    }
}
