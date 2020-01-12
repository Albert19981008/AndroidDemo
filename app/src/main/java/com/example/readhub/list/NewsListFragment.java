package com.example.readhub.list;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.readhub.R;
import com.example.readhub.data.entity.News;
import com.example.readhub.list.adpter.NewsRecyclerViewAdapter;

import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 * 每种新闻的列表Fragment，也是MVP模式中新闻页的View
 */
public class NewsListFragment extends Fragment implements NewsListContract.View {

    private Context mContext;

    private View mView;

    // RecyclerView
    private RecyclerView mRecyclerView;

    //recyclerView 的适配器
    private NewsRecyclerViewAdapter mRecyclerViewAdapter;

    //绑定的的Presenter
    private NewsListContract.Presenter mPresenter;


    public NewsListFragment() {
    }

    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        //判断是否是第一次初始化该Fragment
        if (mView == null) {
            mView = inflater.inflate(R.layout.fragment_news, null);
            initRecyclerView();
        }

        mPresenter.start();
        return mView;
    }

    @Override
    public void notifyItemChanged(int begin, int end) {
        mRecyclerViewAdapter.notifyItemChanged(begin, end);
    }


    /**
     * 初始化RecyclerView，并设置监听器
     */
    private void initRecyclerView() {

        //找到这个ListView
        mRecyclerView = mView.findViewById(R.id.news_recycler);

        //设置线性管理器
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));

        //设置滚动监听器 滚动到底部自动加载更多
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                //触底时候去 加载更多新闻
                if (!mPresenter.getLoadingStatus() && !recyclerView.canScrollVertically(1)) {
                    mPresenter.loadMore();
                }
            }
        });

        //设置分割线
        mRecyclerView.addItemDecoration(
                new DividerItemDecoration(mContext, DividerItemDecoration.VERTICAL));
    }


    /**
     * 初始化数据并关联RecyclerView
     */
    @Override
    public void setPresenter(NewsListContract.Presenter presenter) {
        mPresenter = presenter;
    }


    /**
     * 初始化RecyclerView的适配器
     *
     * @param context  ListActivity的context
     * @param newsList 新闻数据列表
     */
    @Override
    public void initRecyclerViewAdapter(Context context, List<News> newsList) {

        mRecyclerViewAdapter = new NewsRecyclerViewAdapter(context, newsList);
        mRecyclerView.setAdapter(mRecyclerViewAdapter);
    }

    //设置Context
    void setContext(Context context) {
        mContext = context;
    }
}

