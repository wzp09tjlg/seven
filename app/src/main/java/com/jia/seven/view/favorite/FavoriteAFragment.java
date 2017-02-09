package com.jia.seven.view.favorite;

import com.jia.seven.R;
import com.jia.seven.databinding.FragmentFavoriteBinding;
import com.jia.seven.view.base.BasePresenter;
import com.jia.seven.view.base.BindingFragment;

/**
 * Created by wu on 2017/2/9.
 */

public class FavoriteAFragment extends BindingFragment<FragmentFavoriteBinding,BasePresenter> {
    private FavoriteAViewWrapper favoriteViewWrapper;

    @Override
    public void onCreate() {
        super.onCreate();
        favoriteViewWrapper = new FavoriteAViewWrapper(binding,getChildFragmentManager());
    }

    @Override
    protected int createViewLayoutId() {
        return R.layout.fragment_favorite;
    }

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }
}
