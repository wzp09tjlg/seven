package com.jia.seven.view.base;


import android.databinding.ViewDataBinding;

public abstract class BaseViewWrapper<B extends ViewDataBinding> {
    protected B binding;

    public BaseViewWrapper(B binding) {
        this.binding = binding;
    }

    public abstract void release();
}
