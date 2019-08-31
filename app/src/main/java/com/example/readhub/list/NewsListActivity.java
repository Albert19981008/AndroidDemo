package com.example.readhub.list;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.readhub.list.adpter.ViewPagerFragmentAdapter;
import com.example.readhub.R;

import java.util.ArrayList;
import java.util.List;


/**
 * 新闻列表页的Activity
 */
public class NewsListActivity extends AppCompatActivity {

    //不同新闻页的List
    private List<Fragment> mFragmentList = new ArrayList<>();

    //页面上的ViewPager
    private ViewPager mViewPager;

    //ViewPager的适配器
    private ViewPagerFragmentAdapter mViewPagerFragmentAdapter;

    //FragmentManager
    private FragmentManager mFragmentManager = getSupportFragmentManager();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_list);
        initFragmentList();
        initViewPager();
        initTab();
    }


    /**
     * 初始化ViewPager并添加适配器
     */
    private void initViewPager() {
        mViewPager = findViewById(R.id.view_pager);
        mViewPager.setAdapter(mViewPagerFragmentAdapter);
        mViewPager.setCurrentItem(0);
    }


    /**
     * 初始化Fragment（页）的列表，初始化并适配每页的Presenter,View 和 ViewPagerFragmentAdapter
     */
    private void initFragmentList() {

        //初始化科技新闻页
        NewsListFragment f1 = new NewsListFragment();
        f1.setContext(NewsListActivity.this);
        NewsListPresenter p1 = new NewsListPresenter(NewsListActivity.this, "news");
        p1.setFragment(f1);
        f1.setPresenter(p1);

        //初始化开发者资讯页
        NewsListFragment f2 = new NewsListFragment();
        f2.setContext(NewsListActivity.this);
        NewsListPresenter p2 = new NewsListPresenter(NewsListActivity.this, "technews");
        p2.setFragment(f2);
        f2.setPresenter(p2);

        //初始化区块链快讯页
        NewsListFragment f3 = new NewsListFragment();
        f3.setContext(NewsListActivity.this);
        NewsListPresenter p3 = new NewsListPresenter(NewsListActivity.this, "blockchain");
        p3.setFragment(f3);
        f3.setPresenter(p3);

        //将三个页面加入List
        mFragmentList.add(f1);
        mFragmentList.add(f2);
        mFragmentList.add(f3);

        //设置适配器
        mViewPagerFragmentAdapter = new ViewPagerFragmentAdapter(mFragmentManager, mFragmentList);
    }


    /**
     * 初始化导航栏
     */
    private void initTab() {
        //导航栏
        TabLayout mTabLayout = findViewById(R.id.tabs);
        mTabLayout.setupWithViewPager(mViewPager);
    }

}
