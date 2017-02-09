package com.jia.seven.view.main;

import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.jia.seven.R;
import com.jia.seven.databinding.ActivityMainBinding;
import com.jia.seven.utils.RxBus;
import com.jia.seven.view.base.BindingActivity;
import com.jia.seven.view.favorite.FavoriteAFragment;


public class MainActivity extends BindingActivity<ActivityMainBinding, MainPresenter> implements
        MainContract.View,
        TabStyleWrapper.Fragments,
        TabStyleWrapper.OnTabSelectListener {

    private TabStyleWrapper mTabStyleWrapper = null;

    @Override
    protected MainPresenter createPresenter() {
        return new MainPresenter(this);
    }

    @Override
    protected int createLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mTabStyleWrapper = new TabStyleWrapper(binding, MainActivity.this, getSupportFragmentManager());
        mTabStyleWrapper.setOnTabSelectListener(MainActivity.this);
        mTabStyleWrapper.setTab(0);
    }

    @Override
    public Class<? extends Fragment>[] getFragments() {
        return new Class[]{TestFragment.class, FavoriteAFragment.class,
                TestFragment.class, TestFragment.class, TestFragment.class};
    }

    @Override
    public void onTabSelect(int position) {
        RxBus.getInstance().post(new Test(position));
    }

}
