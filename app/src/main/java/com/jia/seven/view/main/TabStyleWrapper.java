package com.jia.seven.view.main;

import android.content.Context;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.SparseArray;
import android.view.View;

import com.jia.seven.R;
import com.jia.seven.databinding.ActivityMainBinding;

/**
 * 首页为tab标签栏切换fragment的布局的封装
 * 1、mainactivity添加fragment
 * 2、黑色模式切换
 * <p>
 * wrapper中存在两个监听
 * OnTabSelectListener 用户点击tab标签之后就会回调，手动调用setTab方法不会回调
 * 当fragment可以成功添加到actcivity中时
 */
@SuppressWarnings("All")
public class TabStyleWrapper implements View.OnClickListener {
    // 当前fragment索引
    private int mCurrentFragmentPostion = -1;
    // 存放mainactivity持有的fragment
    private SparseArray<Fragment> mFragmentMap = new SparseArray<>();
    // 创建fragment的接口，activity实现
    private Fragments mTabFragmentInterface;
    // fragment manager
    private FragmentManager mFragmentManager;

    private ActivityMainBinding mBinding;

    private OnTabSelectListener mOnTabSelectListener = null;

    public void setOnTabSelectListener(OnTabSelectListener tabSelectListener) {
        this.mOnTabSelectListener = tabSelectListener;
    }


    public interface OnTabSelectListener {
        void onTabSelect(int position);
    }

    public interface Fragments {
        Class<? extends Fragment>[] getFragments();
    }


    public TabStyleWrapper(ActivityMainBinding binding, Fragments tabStyle, FragmentManager fragmentManager) {
        mTabFragmentInterface = tabStyle;
        mFragmentManager = fragmentManager;
        mBinding = binding;

        initView();
        onModeModifyListener();
        initFragment();
    }

    /**
     * todo 预加载fragment
     */
    private void initFragment() {

    }

    private void initView() {
        mBinding.tabLayout.tabLayout1.setOnClickListener(this);
        mBinding.tabLayout.tabLayout2.setOnClickListener(this);
        mBinding.tabLayout.tabLayout3.setOnClickListener(this);
        mBinding.tabLayout.tabLayout4.setOnClickListener(this);
        mBinding.tabLayout.tabLayout5.setOnClickListener(this);
    }

    public void setTab(int position, Bundle msg) {
        // 重复点击同一个按钮时，不对fragment进行切换操作
        if (mCurrentFragmentPostion == position)
            return;
        try {
            Fragment fragment = mFragmentMap.get(position);
            FragmentTransaction transaction = mFragmentManager.beginTransaction();
            // hide the old Fragment
            if (mCurrentFragmentPostion != -1) {
                transaction.hide(mFragmentMap.get(mCurrentFragmentPostion));
            }

            if (mTabFragmentInterface.getFragments() != null && mTabFragmentInterface.getFragments().length > 0) {
                if (fragment == null) {
                    fragment = mTabFragmentInterface.getFragments()[position].newInstance();
                    Bundle bundle = msg;
                    fragment.setArguments(bundle);
                    mFragmentMap.put(position, fragment);
                    transaction.add(mBinding.fragmentLayout.getId(), fragment, fragment.getClass().getSimpleName());
                }

                transaction.show(fragment);
                mCurrentFragmentPostion = position;
            }

            transaction.commitAllowingStateLoss();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setTab(int position) {
        setTab(position, null);
    }


    @Override
    public void onClick(View v) {
        int postion = -1;
        switch (v.getId()) {
            case R.id.tab_layout_1:
                postion = 0;
                break;
            case R.id.tab_layout_2:
                postion = 1;
                break;
            case R.id.tab_layout_3:
                postion = 2;
                break;
            case R.id.tab_layout_4:
                postion = 3;
                break;
            case R.id.tab_layout_5:
                postion = 4;
                break;
        }
        if (mOnTabSelectListener != null && postion != -1) {
            // fragment 切换
            setTab(postion);
            // 点击事件
            mOnTabSelectListener.onTabSelect(postion);
        }
    }

    public Fragment getCurrentFragment() {
        return mFragmentMap.get(mCurrentFragmentPostion);
    }

    /**
     * 释放资源，退出后view逻辑
     */
    public void release() {
        // 清除存储fragment的map
        mFragmentMap.clear();
        mFragmentMap = null;
    }

    public void onModeModifyListener() {
        Context context = mBinding.getRoot().getContext();

        mBinding.tabLayout.tabLayout.setBackgroundColor(context.getResources().getColor(R.color.color_292929));

        ColorStateList csl = context.getResources().getColorStateList(R.color.tab_text_selector);
        mBinding.tabLayout.tabText1.setTextColor(csl);
        mBinding.tabLayout.tabText2.setTextColor(csl);
        mBinding.tabLayout.tabText3.setTextColor(csl);
        mBinding.tabLayout.tabText4.setTextColor(csl);
        mBinding.tabLayout.tabText5.setTextColor(csl);

        mBinding.tabLayout.tabImageview1.setImageResource(R.drawable.tab_choice_selector);
        mBinding.tabLayout.tabImageview2.setImageResource(R.drawable.tab_favorite_selector);
        mBinding.tabLayout.tabImageview3.setImageResource(R.drawable.tab_huasheng_selector);
        mBinding.tabLayout.tabImageview4.setImageResource(R.drawable.tab_find_selector);
        mBinding.tabLayout.tabImageview5.setImageResource(R.drawable.tab_user_selector);
    }
}
