package com.jia.seven.view.favorite;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;

import com.apkfuns.logutils.LogUtils;
import com.jia.seven.R;
import com.jia.seven.databinding.FragmentRecommendBinding;
import com.jia.seven.network.parse.BannerInfo;
import com.jia.seven.network.parse.FavoriteList;
import com.jia.seven.utils.GlideUtils;
import com.jia.seven.view.base.BaseViewWrapper;
import com.jia.seven.view.common.HeaderAndFooterWrapper;
import com.jia.seven.view.favorite.blockView.BlockListAdapter;
import com.jia.seven.view.favorite.blockView.BlockListView;
import com.jia.seven.view.widget.LoadFooterView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.jia.seven.view.widget.LoadFooterView.STATE_NORMAL;

/**
 * Created by wu on 2017/2/9.
 */
public class FavoriteRecommendViewWrapper extends BaseViewWrapper<FragmentRecommendBinding> {
    private final int BANNER_COLUMN = 2;

    private List<FavoriteList.Card> mData = new ArrayList<>();
    private HeaderAndFooterWrapper<FavoriteList.Card> adapterWrapper;
    private LinearLayoutManager mLinearLayoutManager;
    private BlockListView mHeaderView;
    private BlockListAdapter mBannerAdapter;
    private LoadFooterView mFooterView;
    private Context mContext;
    FavoriteAContract.OnScrollListener mOnScrollListener = null;

    public FavoriteRecommendViewWrapper(FragmentRecommendBinding binding){
       super(binding);
        mContext = binding.getRoot().getContext();
        initView();
    }

    @Override
    public void release() {

    }

    private void initView(){
        adapterWrapper = new HeaderAndFooterWrapper<>(new FavoriteCommonAdapter(mContext,mData,false));
        // 设置footer
        mFooterView = new LoadFooterView(mContext);
        mFooterView.setState(STATE_NORMAL);
        mFooterView.gone();
        adapterWrapper.addFootView(mFooterView);

        int padding = mContext.getResources().getDimensionPixelSize(R.dimen.favorite_listview_padding);
        binding.rvRecommendLayout.setPadding(padding,0,padding,0);
        binding.rvRecommendLayout.setAdapter(adapterWrapper);
        binding.rvRecommendLayout.setLayoutManager(mLinearLayoutManager = new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false));

        // 下拉刷新
        binding.rvRecommendLayout.setOnScrollListener(new RecyclerView.OnScrollListener() {
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

    public void addHeader(List<BannerInfo> list){
        if (mHeaderView == null && list!= null && list.size() > 0) {
            mHeaderView = new BlockListView(mContext);
            mHeaderView.setAdapter(createBannerAdapter());
            mHeaderView.setOnItemClickListener(createBannerListener());
            mBannerAdapter.setColumnNum(BANNER_COLUMN);
            mBannerAdapter.registerView(mHeaderView);
            mBannerAdapter.displayBlocks(list);
            adapterWrapper.addHeaderView(mHeaderView);
        }
    }

    private BlockListAdapter<?> createBannerAdapter() {

        mBannerAdapter = new BlockListAdapter<BannerInfo>() {
            public View getView(LayoutInflater layoutInflater, int position) {
                ImageView iv = (ImageView) layoutInflater.inflate(R.layout.item_favorite_banner, null);
                BannerInfo info = (BannerInfo) mBannerAdapter.getItem(position);

                GlideUtils.setImage(mContext, iv, info.image,
                        R.drawable.icon_loading_rectangle,
                        R.drawable.icon_loading_rectangle_failed,
                        R.drawable.icon_loading_rectangle_no_wifi);

                return iv;
            }
        };
        return mBannerAdapter;
    }

    private BlockListView.OnItemClickListener createBannerListener() {
        return new BlockListView.OnItemClickListener() {
            @Override
            public void onItemClick(View v, int position) {
                BannerInfo info = (BannerInfo) mBannerAdapter.getItem(position);
                LogUtils.e("banner Item onClick bannerInfo:" + info.toString());
                Map params = new HashMap();
                params.put("link", info.link);
            }
        };
    }

    public void addStory(List<FavoriteList.Card> data){
       mData.addAll(data);

        if(adapterWrapper != null){
            adapterWrapper.notifyDataSetChanged();
        }
    }

    public int getTotalCount(){
        if(mData != null)
            return mData.size();
        else
            return 0;
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
