package com.example.monking.domain;

import java.util.ArrayList;

/**
 * Created by MonKing on 2016/11/6.
 */

public class PhotoBean {
    public PhotoData data;

    public class PhotoData {
        public ArrayList<PhotoNews> news;
    }

    public class PhotoNews {
        public int id;
        public String listimage;
        public String title;
    }
}
