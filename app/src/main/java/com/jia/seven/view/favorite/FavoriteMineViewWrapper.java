package com.jia.seven.view.favorite;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.jia.seven.R;
import com.jia.seven.databinding.FragmentMyFavoriteBinding;
import com.jia.seven.network.parse.FavoriteList;
import com.jia.seven.view.base.BaseViewWrapper;
import com.jia.seven.view.common.HeaderAndFooterWrapper;
import com.jia.seven.view.widget.LoadFooterView;

import java.util.ArrayList;
import java.util.List;

import static com.jia.seven.view.widget.LoadFooterView.STATE_NORMAL;

/**
 * Created by wu on 2017/2/9.
 */

public class FavoriteMineViewWrapper extends BaseViewWrapper<FragmentMyFavoriteBinding> {
    private HeaderAndFooterWrapper<FavoriteList.Card> adapterWrapper;
    private FavoriteAContract.OnScrollListener mOnScrollListener;
    private LinearLayoutManager mLinearLayoutManager;
    private List<FavoriteList.Card> mData = new ArrayList<>();
    private LoadFooterView mFooterView;
    private Context mContext;

    public FavoriteMineViewWrapper(FragmentMyFavoriteBinding binding){
        super(binding);
        mContext = binding.getRoot().getContext();
        initView();
    }

    @Override
    public void release() {

    }

    private void initView(){
        adapterWrapper = new HeaderAndFooterWrapper<>(new FavoriteCommonAdapter(mContext,mData,true));
        // 设置footer
        mFooterView = new LoadFooterView(mContext);
        mFooterView.setState(STATE_NORMAL);
        mFooterView.gone();
        adapterWrapper.addFootView(mFooterView);

        int padding = mContext.getResources().getDimensionPixelSize(R.dimen.favorite_listview_padding);
        binding.rvMyFavoriteLayout.setPadding(padding,0,padding,0);
        binding.rvMyFavoriteLayout.setAdapter(adapterWrapper);
        binding.rvMyFavoriteLayout.setLayoutManager(mLinearLayoutManager = new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false));

        // 下拉刷新
        binding.rvMyFavoriteLayout.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                int lastVisibleItem = mLinearLayoutManager.findLastVisibleItemPosition();
                int totalItemCount = adapterWrapper.getItemCount();

                if (dy > 0) {
                    if (mOnScrollListener != null) {
                        mOnScrollListener.onDownScroll();
                    }
                }
                // dy>0 表示向下滑动
                if (lastVisibleItem >= totalItemCount - 2 && dy > 0) {
                    if (mOnScrollListener != null) {
                        mOnScrollListener.onScroll2Bottom();
                    }
                }
            }

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }
        });
    }

    public void clearData(){
        if(mData != null){
            mData.clear();
        }
    }

    public void addStory(List<FavoriteList.Card> data){
        mData.addAll(data);

        if(adapterWrapper != null){
            adapterWrapper.notifyDataSetChanged();
        }
    }

    public int getTotalCount(){
        return mData.size();
    }

    /**
     * 移除下拉刷新
     */
    public void removeLoadMoreView() {
        if (mFooterView != null) {
            mFooterView.gone();
        }
    }

    /**
     * 显示下拉刷新
     */
    public void addLoadMoreView() {
        if (mFooterView != null) {
            mFooterView.setState(LoadFooterView.STATE_LOADING);
            mFooterView.visible();
        }
    }

    public void setOnScrollListening(FavoriteAContract.OnScrollListener l) {
        mOnScrollListener = l;
    }
}
