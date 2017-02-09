package com.jia.seven.view.base;

import android.app.Activity;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public abstract class BindingFragment<B extends ViewDataBinding, P extends BasePresenter> extends BaseFragment {
    protected B binding;
    protected P mvpPresenter;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mvpPresenter = createPresenter();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, createViewLayoutId(), container, false);
        onCreate();
        return binding.getRoot();
    }

    public void onCreate(){

    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (mvpPresenter != null) {
            mvpPresenter.detachView();
        }
    }

    protected abstract int createViewLayoutId();

    protected abstract P createPresenter();
}
