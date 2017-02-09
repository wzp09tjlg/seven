package com.jia.seven.view.favorite;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.jia.seven.view.base.BaseFragment;

import java.util.List;

/**
 * Created by wu on 2017/2/9.
 */

public class FavoriteAPageAdapter extends FragmentPagerAdapter {
    public static final String[] TITLES = {"推荐关注", "我的关注"};
    private List<BaseFragment> fragments;

    public FavoriteAPageAdapter(FragmentManager fm, List<BaseFragment> fragments){
        super(fm);
        this.fragments = fragments;
    }

    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return TITLES[position];
    }
}
