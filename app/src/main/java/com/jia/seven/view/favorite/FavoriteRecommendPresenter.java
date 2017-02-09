package com.jia.seven.view.favorite;

import com.apkfuns.logutils.LogUtils;
import com.jia.seven.network.ApiCallback;
import com.jia.seven.network.Error;
import com.jia.seven.network.parse.FavoriteRecommend;
import com.jia.seven.view.base.BasePresenter;

/**
 * Created by wu on 2017/2/9.
 */
public class FavoriteRecommendPresenter extends BasePresenter<FavoriteAContract.RecommendFavorite> implements
        FavoriteAContract.Presenter
{
    private final int S_FRIST_PAGE = 1;
    public FavoriteRecommendPresenter(FavoriteAContract.RecommendFavorite recommendFavorite){
        super(recommendFavorite);
    }

    @Override
    public void start() {
        load(1);
    }

    public void load(final int page) {
        if (page == S_FRIST_PAGE)
            mvpView.showLoading();
        addSubscription(apiStores.getRecommendFavorite(String.valueOf(page), "10"), new ApiCallback<FavoriteRecommend>() {
            @Override
            public void onSuccess(FavoriteRecommend model) {
                boolean isShowStory = false;
                if (model.items != null) {
                    if (model.items.recommend_items != null) {
                        if (model.items.recommend_items.rows.size() != 0) {
                            isShowStory = true;
                        }
                        mvpView.addStory(model.items.recommend_items.rows);
                        mvpView.getPage(page);
                    }
                }
                LogUtils.e("model:" + model.toString());

                // header的数据第一次调用时更新
                if (page == S_FRIST_PAGE) {
                    try {
                        mvpView.getTotalCount(Integer.valueOf(model.items.recommend_items.count));
                    } catch (NumberFormatException e) {
                        e.printStackTrace();
                        mvpView.getTotalCount(model.items.recommend_items.rows.size());
                    }
                    mvpView.addHeader(model.items.imgs.data);
                }

                // 只有单页数为第一页的时候，处理没有搜索到结果的情况
                if (!isShowStory && model.items.imgs.data.size() == 0 && model.items.recommend_items.rows.size() == 0 && page == S_FRIST_PAGE) {
                    mvpView.error(Error.S_NULL_DATA);
                }

                mvpView.hideLoading();
            }

            @Override
            public void onFailure(Error error) {
                LogUtils.e("error:" + error);
                mvpView.error(Error.S_NETWORK_UNCONNECT);
                mvpView.hideLoading();
            }
        });
    }
}
