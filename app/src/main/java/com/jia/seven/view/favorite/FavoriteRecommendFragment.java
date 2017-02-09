package com.jia.seven.view.favorite;

import com.jia.seven.R;
import com.jia.seven.databinding.FragmentRecommendBinding;
import com.jia.seven.network.parse.BannerInfo;
import com.jia.seven.network.parse.FavoriteList;
import com.jia.seven.view.base.BindingFragment;

import java.util.List;

/**
 * Created by wu on 2017/2/9.
 */
public class FavoriteRecommendFragment extends BindingFragment<FragmentRecommendBinding,FavoriteRecommendPresenter> implements
        FavoriteAContract.RecommendFavorite,
        FavoriteAContract.OnScrollListener
{
    private FavoriteRecommendViewWrapper recommendViewWrapper;
    // 当前页数
    private int mCurrentPage = 1;
    // 是否正在刷新
    private boolean refreshing = false;
    // 总数量
    private int mTotalCount;
    @Override
    public void onCreate() {
        super.onCreate();
        recommendViewWrapper = new FavoriteRecommendViewWrapper(binding);
        recommendViewWrapper.setOnScrollListening(this);

        mvpPresenter.start();
    }

    @Override
    protected int createViewLayoutId() {
        return R.layout.fragment_recommend;
    }

    @Override
    protected FavoriteRecommendPresenter createPresenter() {
        return new FavoriteRecommendPresenter(this);
    }

    @Override
    public void addStory(List<FavoriteList.Card> data) {
        refreshing = false;
       recommendViewWrapper.addStory(data);
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
    public void getPage(int page) {
        mCurrentPage = page;
    }

    @Override
    public void getTotalCount(int totalCount) {
        this.mTotalCount = totalCount;
    }

    @Override
    public void addHeader(List<BannerInfo> data) {
        recommendViewWrapper.addHeader(data);
    }

    @Override
    public void error(int error) {

    }

    @Override
    public void onScroll2Bottom() {
        if (!refreshing) {
            if (mTotalCount != recommendViewWrapper.getTotalCount()) {
                mvpPresenter.load(mCurrentPage + 1);
            }else{//加载完之后 就没有显示加载更多了//或者显示没有更多数据的提示
                recommendViewWrapper.removeLoadMoreView();
            }
            refreshing = true;
        }
    }

    @Override
    public void onDownScroll() {

    }
}
