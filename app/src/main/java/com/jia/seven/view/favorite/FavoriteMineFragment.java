package com.jia.seven.view.favorite;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.view.LayoutInflater;
import android.view.View;

import com.jia.seven.HSApplication;
import com.jia.seven.R;
import com.jia.seven.databinding.FragmentMyFavoriteBinding;
import com.jia.seven.databinding.ViewNoLoginLayoutBinding;
import com.jia.seven.network.parse.FavoriteList;
import com.jia.seven.view.base.BindingFragment;

import java.util.List;

/**
 * Created by wu on 2017/2/9.
 */

public class FavoriteMineFragment extends BindingFragment<FragmentMyFavoriteBinding,FavoriteMinePresenter> implements
        FavoriteAContract.MineFavorite,
        FavoriteAContract.OnScrollListener
{
    private FavoriteMineViewWrapper mineViewWrapper;
    // 当前页数
    private int mCurrentPage = 1;
    // 是否正在刷新
    private boolean refreshing = false;
    // 总数量
    private int mTotalCount;
    private Context mContext;

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = binding.getRoot().getContext();
        mineViewWrapper = new FavoriteMineViewWrapper(binding);
        mineViewWrapper.setOnScrollListening(this);

        if(HSApplication.getUser()!= null){
            mvpPresenter.start();
        }
    }

    @Override
    protected int createViewLayoutId() {
        return R.layout.fragment_my_favorite;
    }

    @Override
    protected FavoriteMinePresenter createPresenter() {
        return new FavoriteMinePresenter(this);
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser && isResumed()) {
            update();
        }
    }

    private void update() {
        if (HSApplication.getUser() != null) {
            mvpPresenter.load(1);
        } else {
            binding.rvMyFavoriteLayout.setVisibility(View.GONE);
            binding.flCoverLayout.setVisibility(View.VISIBLE);
            binding.flCoverLayout.removeAllViews();
            ViewNoLoginLayoutBinding noLoginLayoutBinding = DataBindingUtil.inflate(LayoutInflater.from(mContext),
                    R.layout.view_no_login_layout,
                    binding.flCoverLayout,
                    false);
            noLoginLayoutBinding.loginButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                   //模拟登录之后的操作
                    mvpPresenter.login();
                }
            });
            binding.flCoverLayout.addView(noLoginLayoutBinding.getRoot());
        }
    }

    @Override
    public void clearData() {
        mineViewWrapper.clearData();
    }

    @Override
    public void addStory(List<FavoriteList.Card> data) {
        refreshing = false;
       mineViewWrapper.addStory(data);
    }

    @Override
    public void getPage(int page) {
      mCurrentPage = page;
    }

    @Override
    public void getTotalCount(int totalCount) {
        mTotalCount = totalCount;
    }

    @Override

    public void error(int error) {

    }

    @Override
    public void showLoading() {
        showProgress("加载中...", true);
    }

    @Override
    public void hideLoading() {
        dismissProgress();
    }

    @Override
    public void loginSuccess() {
        binding.rvMyFavoriteLayout.setVisibility(View.VISIBLE);
        binding.flCoverLayout.setVisibility(View.INVISIBLE);
        binding.flCoverLayout.removeAllViews();

        mvpPresenter.load(1);
    }

    @Override
    public void loginFail() {

    }

    @Override
    public void onScroll2Bottom() {
        if (!refreshing) {
            if (mTotalCount != mineViewWrapper.getTotalCount()) {
                mvpPresenter.load(mCurrentPage + 1);
            }else{//加载完之后 就没有显示加载更多了//或者显示没有更多数据的提示
                mineViewWrapper.removeLoadMoreView();
            }
            refreshing = true;
        }
    }

    @Override
    public void onDownScroll() {

    }
}
