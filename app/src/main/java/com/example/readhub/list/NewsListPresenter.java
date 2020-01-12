package com.example.readhub.list;


import android.content.Context;
import android.support.annotation.NonNull;

import com.example.readhub.Injection;
import com.example.readhub.data.DatabaseHelper;
import com.example.readhub.data.NewsCallBackApi;
import com.example.readhub.data.entity.News;

import java.util.ArrayList;
import java.util.List;


/**
 * MVP模式中Presenter的实现
 */
public class NewsListPresenter implements NewsListContract.Presenter {

    private final DatabaseHelper mDatabaseHelper = Injection.provideDatabaseHelper();

    private Context mContext;

    //需要注入的HTTP请求标识
    private String mHttpRequestCategory;

    //存储RecyclerView中的新闻列表
    private List<News> mNewsList;

    //与之对应的View
    private NewsListFragment mFragment;

    //开关变量，判断是否正在加载，防止重复触发监听器
    private boolean mLoading = false;

    //一次加载二十条新闻
    private static final int PAGE_SIZE = 20;

    private int mListSize = 0;


    /**
     * @param httpRequestCategory HTTP请求需要注入的新闻类型
     */
    NewsListPresenter(@NonNull Context context, @NonNull String httpRequestCategory) {

        mContext = context;

        mHttpRequestCategory = httpRequestCategory;
    }


    /**
     * View 每次初始化调用该方法 加载第一页数据
     */
    @Override
    public void start() {
        initData();
    }


    /**
     * 下拉加载更多
     */
    @Override
    public void loadMore() {

        mLoading = true;

        if (mNewsList != null && mNewsList.size() > 0) {

            long endTime = mNewsList.get(mNewsList.size() - 1).getTimeStamp();

            mDatabaseHelper.getLatestNews(PAGE_SIZE, mHttpRequestCategory, endTime, new NewsCallBackApi() {

                @Override
                public void onNewsLoaded(List<News> newsList) {
                    mNewsList.addAll(newsList);
                    notifyItemChanged();
                    setLoadingStatus(false);
                }

                @Override
                public void onDataNotAvailable() {
                    setLoadingStatus(false); //重置开关变量
                }
            });

        }
    }


    /**
     * 得到是否正在loadMore
     *
     * @return 是否在加载的布尔变量
     */
    @Override
    public boolean getLoadingStatus() {
        return mLoading;
    }

    /**
     * 重置开关变量
     *
     * @param loadingStatus 设置为该值
     */
    @Override
    public void setLoadingStatus(boolean loadingStatus) {
        mLoading = loadingStatus;
    }

    /**
     * Presenter关联View
     *
     * @param mFragment 要关联的Fragment(View)
     */
    @Override
    public void setFragment(NewsListFragment mFragment) {
        this.mFragment = mFragment;
    }

    /**
     * 通知View新闻数量已经改变
     */
    private void notifyItemChanged() {
        mFragment.notifyItemChanged(mListSize, mNewsList.size());
        mListSize = mNewsList.size();  //刷新当前新闻List的大小
    }

    /**
     * 初始化新闻列表
     */
    private void initData() {

        if (mNewsList == null) {
            mNewsList = new ArrayList<>();
            mFragment.initRecyclerViewAdapter(mContext, mNewsList);

            long endTime = System.currentTimeMillis();
            mDatabaseHelper.getLatestNews(PAGE_SIZE, mHttpRequestCategory, endTime,
                    new NewsCallBackApi() {

                        //新闻加载成功
                        @Override
                        public void onNewsLoaded(List<News> newsList) {
                            mNewsList.addAll(newsList);
                            notifyItemChanged();
                            setLoadingStatus(false);
                        }

                        //新闻加载失败
                        @Override
                        public void onDataNotAvailable() {
                            setLoadingStatus(false);
                        }
                    });
        }

    }

}
