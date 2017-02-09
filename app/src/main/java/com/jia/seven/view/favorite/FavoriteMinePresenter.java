package com.jia.seven.view.favorite;

import com.apkfuns.logutils.LogUtils;
import com.jia.seven.HSApplication;
import com.jia.seven.network.ApiCallback;
import com.jia.seven.network.Error;
import com.jia.seven.network.parse.FavoriteList;
import com.jia.seven.network.parse.User;
import com.jia.seven.view.base.BasePresenter;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by wu on 2017/2/9.
 */

public class FavoriteMinePresenter extends BasePresenter<FavoriteAContract.MineFavorite> implements
    FavoriteAContract.Presenter
{
    private final int S_FRIST_PAGE = 1;
    public FavoriteMinePresenter(FavoriteAContract.MineFavorite mineFavorite){
        super(mineFavorite);
    }

    @Override
    public void start() {
        load(1);
    }

    public void load(final int page){
        if(page == S_FRIST_PAGE){
            mvpView.showLoading();
        }
        String token  = HSApplication.getUser().access_token;
        addSubscription(apiStores.getMineFavorite(token, String.valueOf(page), "10"), new ApiCallback<FavoriteList>() {
            @Override
            public void onSuccess(FavoriteList model) {
                boolean isShowStory = false;
                if (model != null) {
                    if (model.rows != null ) {
                        if (model.rows.size() != 0) {
                            isShowStory = true;
                        }
                        if(page == S_FRIST_PAGE){
                            mvpView.clearData();
                        }
                        mvpView.addStory(model.rows);
                        mvpView.getPage(page);
                    }
                }
                LogUtils.e("model:" + model.toString());

                // header的数据第一次调用时更新
                if (page == S_FRIST_PAGE) {
                    try {
                        mvpView.getTotalCount(Integer.valueOf(model.count));
                    } catch (NumberFormatException e) {
                        e.printStackTrace();
                        mvpView.getTotalCount(model.rows.size());
                    }
                }

                // 只有单页数为第一页的时候，处理没有搜索到结果的情况
                if (!isShowStory && model.rows.size() == 0 && page == S_FRIST_PAGE) {
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

    //简单的处理登录过程
    public void login(){
        Map<String,String> param = new HashMap<>();
        param.put("phone", "15210521539");
        param.put("password", "123456");
        addSubscription(apiStores.login(param), new ApiCallback<User>() {
            @Override
            public void onSuccess(User model) {
                HSApplication.setUser(model);

                mvpView.loginSuccess();
            }

            @Override
            public void onFailure(Error error) {
                mvpView.loginFail();
                mvpView.hideLoading();
                mvpView.error(error.getErrorCode());
            }
        });
    }
}
