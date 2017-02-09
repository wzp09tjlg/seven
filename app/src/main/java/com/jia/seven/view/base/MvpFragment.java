package com.jia.seven.view.base;

import android.app.Activity;

@SuppressWarnings("All")
public abstract class MvpFragment<P extends BasePresenter> extends BaseFragment {
    protected P mvpPresenter;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mvpPresenter = createPresenter();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (mvpPresenter != null) {
            mvpPresenter.detachView();
        }
    }

    protected abstract P createPresenter();
}
