package com.example.readhub.list;

import android.content.Context;

import com.example.readhub.BasePresenter;
import com.example.readhub.BaseView;

import java.util.List;

import com.example.readhub.data.News;


/**
 * 接口类 标识MVP架构中Presenter和View间的协议
 */
public interface NewsListContract {

    /**
     * View 接口
     */
    interface View extends BaseView<Presenter> {

        //初始化RecyclerView适配器
        void initRecyclerViewAdapter(Context context, List<News> newsList);

        //调整recyclerView的元素个数
        void notifyItemChanged(int begin, int end);
    }


    /**
     * Presenter 接口
     */
    interface Presenter extends BasePresenter {

        //设置相对应的View
        void setFragment(NewsListFragment mFragment);

        //下拉加载更多
        void loadMore();

        //得到是否正在加载的状态
        boolean getLoadingStatus();

        //设置是否正在加载的状态
        void setLoadingStatus(boolean loadingStatus);
    }
}
