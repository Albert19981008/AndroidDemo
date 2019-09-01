package com.example.readhub.list.adpter;


import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.readhub.R;
import com.example.readhub.content.ContentActivity;
import com.example.readhub.data.NewsEntity;

import java.util.List;


/**
 * RecyclerView适配器
 */
public class NewsRecyclerViewAdapter extends RecyclerView.Adapter<NewsRecyclerViewAdapter.ViewHolder> {

    //数据列表
    private List<NewsEntity> mDataList;

    private Context mContext;

    public NewsRecyclerViewAdapter(Context context, List<NewsEntity> list) {
        mContext = context;
        mDataList = list;
    }

    /**
     * 返回数据List大小
     */
    @Override
    public int getItemCount() {
        return mDataList == null ? 0 : (mDataList.size());
    }

    /**
     * 关联ViewHolder
     */
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        holder.mTvTitle.setText(mDataList.get(position).getHeadline());
        holder.mTvSiteSource.setText(mDataList.get(position).getSiteSource());
        // 监听器
        holder.mRoot.setOnClickListener(v -> {
            startContentActivity(position);  //打开内容页
        });
    }


    /**
     * 初始化ViewHolder
     */
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.news_item, parent, false));
    }


    /**
     * ViewHolder
     */
    class ViewHolder extends RecyclerView.ViewHolder {

        private TextView mTvTitle;
        private TextView mTvSiteSource;
        private View mRoot;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            mRoot = itemView.findViewById(R.id.root);
            mTvTitle = itemView.findViewById(R.id.item_headline);
            mTvSiteSource = itemView.findViewById(R.id.item_siteSource);
        }
    }


    /**
     * 打开内容页
     *
     * @param position RecyclerView中被点击的位置
     */
    private void startContentActivity(int position) {
        Intent intent = new Intent(mContext, ContentActivity.class);

        intent.putExtra("siteSource", mDataList.get(position).getSiteSource());
        intent.putExtra("headline", mDataList.get(position).getHeadline());
        intent.putExtra("url", mDataList.get(position).getUrl());

        mContext.startActivity(intent); //打开内容页
    }

}
