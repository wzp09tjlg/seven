package com.jia.seven.view.favorite;

import android.content.Context;
import android.content.res.ColorStateList;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.jia.seven.R;
import com.jia.seven.databinding.FragmentFavoriteBinding;
import com.jia.seven.view.base.BaseFragment;
import com.jia.seven.view.base.BaseViewWrapper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wu on 2017/2/9.
 */

public class FavoriteAViewWrapper extends BaseViewWrapper<FragmentFavoriteBinding> {
    /** View */
    private View tabView1;
    private View tabView2;
    private TextView textTab1;
    private TextView textTab2;
    private ImageView imgHot;

    /** Data */
    private Context mContext;
    private boolean isUserFavoriteFirst = false;
    private FavoriteAPageAdapter pageAdapter;

    public FavoriteAViewWrapper(FragmentFavoriteBinding binding,FragmentManager fm){
        super(binding);
        mContext = binding.getRoot().getContext();
        initView(fm);

        onModeModifyListener();
    }

    @Override
    public void release() {

    }

    private void initView(FragmentManager fm){
        initViewPager(fm);
        initTab();
    }

    private void initViewPager(FragmentManager fm){
        List<BaseFragment> fragments = new ArrayList<>();
        FavoriteMineFragment favoriteMine = new FavoriteMineFragment();
        FavoriteRecommendFragment favoriteRecommend = new FavoriteRecommendFragment();

        fragments.add(favoriteRecommend);
        fragments.add(favoriteMine);

        pageAdapter = new FavoriteAPageAdapter(fm,fragments);

        binding.favoriteViewpager.setAdapter(pageAdapter);
    }

    private void initTab(){
        tabView1 = LayoutInflater.from(mContext).inflate(R.layout.item_favorite_title,null);
        textTab1 = $(tabView1,R.id.text_view);
        textTab1.setText(pageAdapter.getPageTitle(0));
        TabLayout.Tab tab1 = binding.favoriteTabLayout.newTab();
        tab1.setCustomView(tabView1);
        binding.favoriteTabLayout.addTab(tab1);

        tabView2 = LayoutInflater.from(mContext).inflate(R.layout.item_favorite_title,null);
        textTab2 = $(tabView2,R.id.text_view);
        textTab2.setText(pageAdapter.getPageTitle(1));
        TabLayout.Tab tab2 = binding.favoriteTabLayout.newTab();
        tab2.setCustomView(tabView2);
        binding.favoriteTabLayout.addTab(tab2);

        binding.favoriteViewpager.addOnPageChangeListener(
                new TabLayout.TabLayoutOnPageChangeListener(
                        binding.favoriteTabLayout));
        binding.favoriteTabLayout.setOnTabSelectedListener(getTabSelectedListener());

        imgHot = $(isUserFavoriteFirst ? tabView1 : tabView2,R.id.tip_view);
    }

    private <T> T $(View view,int id){
        return (T)view.findViewById(id);
    }

    private TabLayout.OnTabSelectedListener getTabSelectedListener(){
        TabLayout.OnTabSelectedListener listener = new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int position = tab.getPosition();
                binding.favoriteViewpager.setCurrentItem(position);
                if ((isUserFavoriteFirst && position == 0) || (!isUserFavoriteFirst && position == 1)) {
                    imgHot.setVisibility(View.INVISIBLE);
                    binding.translateView.setVisibility(View.INVISIBLE);
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        };
        return listener;
    }

    public void onModeModifyListener() {
        binding.favoriteLayoutStatusBar.setBackgroundColor(mContext.getResources().getColor(R.color.color_292929));
        binding.favoriteTabLayout.setBackgroundColor(mContext.getResources().getColor(R.color.color_292929));
        binding.actionSearch.setImageResource(R.drawable.icon_action_search);
        binding.favoriteTabLayout.setSelectedTabIndicatorColor(mContext.getResources().getColor(R.color.color_ffc300));
        binding.favoriteViewpager.setBackgroundColor(mContext.getResources().getColor(R.color.color_f2f2f2));
        ColorStateList csl = mContext.getResources().getColorStateList(R.color.tab_text_selector);
        textTab1.setTextColor(csl);
        textTab2.setTextColor(csl);
    }
}
