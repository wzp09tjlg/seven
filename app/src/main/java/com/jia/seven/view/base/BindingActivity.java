package com.jia.seven.view.base;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;

@SuppressWarnings("All")
public abstract class BindingActivity<B extends ViewDataBinding, P extends BasePresenter> extends BaseActivity {
    protected P mvpPresenter;
    protected B binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, createLayoutId());
        mvpPresenter = createPresenter();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mvpPresenter != null) {
            mvpPresenter.detachView();
        }
    }

    protected abstract P createPresenter();

    protected abstract int createLayoutId();
}
