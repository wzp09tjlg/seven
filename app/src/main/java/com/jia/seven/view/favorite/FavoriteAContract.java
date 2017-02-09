package com.jia.seven.view.favorite;

import com.jia.seven.network.parse.BannerInfo;
import com.jia.seven.network.parse.FavoriteList;
import com.jia.seven.view.base.BaseView;

import java.util.List;

/**
 * Created by wu on 2017/2/9.
 */

public class FavoriteAContract {

    public interface Presenter{
        void start();
    }

    //我的关注
    public interface MineFavorite extends BaseView{
        void clearData();

        void addStory(List<FavoriteList.Card> data);

        void getPage(int page);

        void getTotalCount(int totalCount);

        void showLoading();

        void hideLoading();

        //简化接口
        void loginSuccess();

        void loginFail();
    }

    //推荐关注
    public interface RecommendFavorite extends BaseView{
        void addStory(List<FavoriteList.Card> data);

        void getPage(int page);

        void getTotalCount(int totalCount);

        void addHeader(List<BannerInfo> data);

        void showLoading();

        void hideLoading();
    }

    public interface OnScrollListener {
        /**
         * 上拉刷新
         */
        void onScroll2Bottom();

        /**
         * 向下滑动一段距离后
         */
        void onDownScroll();
    }
}
