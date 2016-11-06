package com.example.monking.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.monking.domain.PhotoBean;
import com.example.monking.newsapptest.R;
import com.lidroid.xutils.BitmapUtils;

import java.util.ArrayList;

/**
 * Created by MonKing on 2016/11/6.
 */

public class PhotoAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context mContext;
    private ArrayList<PhotoBean.PhotoNews> mData;
    private LayoutInflater mInflater;
    private MyViewHolder mHolder;
    private BitmapUtils utils;

    public PhotoAdapter(Context context, ArrayList<PhotoBean.PhotoNews> data) {
        mContext = context;
        mData = data;
        mInflater = LayoutInflater.from(context);
        utils = new BitmapUtils(mContext);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.photo_item_layout, parent, false);
        mHolder = new MyViewHolder(view);
        return mHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        utils.display(mHolder.mImage,mData.get(position).listimage);
        mHolder.mText.setText(mData.get(position).title);
    }



    @Override
    public int getItemCount() {
        return mData.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        private ImageView mImage;
        private TextView mText;

        public MyViewHolder(View itemView) {
            super(itemView);
            mImage = (ImageView) itemView.findViewById(R.id.photo_item_image);
            mText = (TextView) itemView.findViewById(R.id.photo_item_text);
        }
    }
}
