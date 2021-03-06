package com.example.readhub.list.adpter;

import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

import static com.example.readhub.Constants.TITLES;


/**
 * ViewPager适配器
 */
public class ViewPagerFragmentAdapter extends FragmentPagerAdapter {

    //Fragment列表
    private List<Fragment> mFragmentList;

    public ViewPagerFragmentAdapter(@NonNull FragmentManager fragmentManager, @NonNull List<Fragment> list) {
        super(fragmentManager);
        this.mFragmentList = list;
    }

    @Override
    public Fragment getItem(int position) {
        return mFragmentList.get(position);
    }

    @Override
    public int getCount() {
        return mFragmentList != null ? mFragmentList.size() : 0;
    }

    /**
     * 得到TabLayout的栏目标题，并把TabLayout和ViewPager适配到一起
     */
    @Override
    public CharSequence getPageTitle(int position) {
        return TITLES[position];
    }
}
