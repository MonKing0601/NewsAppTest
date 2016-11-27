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
import com.example.monking.utils.MyBitMapUtils;

import java.util.ArrayList;

/**
 * Created by MonKing on 2016/11/6.
 */

public class PhotoAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context mContext;
    private ArrayList<PhotoBean.PhotoNews> mData;
    private LayoutInflater mInflater;
    private MyViewHolder mHolder;
    private MyBitMapUtils utils;

    public PhotoAdapter(Context context, ArrayList<PhotoBean.PhotoNews> data) {
        mContext = context;
        mData = data;
        mInflater = LayoutInflater.from(context);

    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.photo_item_layout, parent, false);
        mHolder = new MyViewHolder(view);
        mHolder.mImage = (ImageView) mHolder.itemView.findViewById(R.id.photo_item_image);
        mHolder.mImage.setImageResource(R.drawable.pic_item_list_default);
        mHolder.mText = (TextView) mHolder.itemView.findViewById(R.id.photo_item_text);
        return mHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        mHolder.mImage.setTag(mData.get(position).listimage);
        utils=new MyBitMapUtils(mData.get(position).listimage);
        utils.display(mHolder.mImage, mData.get(position).listimage);
        mHolder.mText.setText(mData.get(position).title);
    }

    //不重写这个方法，获取的数据会混乱不堪
    @Override
    public int getItemViewType(int position) {
        return position;
    }


    @Override
    public int getItemCount() {
        return mData.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        private ImageView mImage;
        private TextView mText;

        public MyViewHolder(View itemView) {
            super(itemView);

        }
    }
}
