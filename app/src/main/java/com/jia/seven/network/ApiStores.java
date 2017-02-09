package com.jia.seven.network;


import com.jia.seven.network.parse.FavoriteList;
import com.jia.seven.network.parse.FavoriteRecommend;
import com.jia.seven.network.parse.HttpBase;
import com.jia.seven.network.parse.User;

import java.util.Map;

import io.reactivex.Flowable;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

@SuppressWarnings("All")
public interface ApiStores {

    /**
     * 登入接口
     *
     * @param params
     */
    @FormUrlEncoded
    @POST(ApiFinal.HS_USER_LOGIN)
    Flowable<HttpBase<User>> login(@FieldMap Map<String, String> params);

    /**
     * 关注
     *
     * @param token    token
     * @param page     当前请求的页数
     * @param pageSize 每页请求的大小
     * @return
     */
    @GET(ApiFinal.HS_FAVORITE_MINE)
    Flowable<HttpBase<FavoriteList>> getMineFavorite(@Query("access_token") String token,
                                                     @Query("page") String page,
                                                     @Query("page_size") String pageSize);

    @GET(ApiFinal.HS_FAVORITE_RECOMMEND)
    Flowable<HttpBase<FavoriteRecommend>> getRecommendFavorite(@Query("page") String page,
                                                               @Query("page_size") String pageSize);
}
