package com.jia.seven.network.parse;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * Created by wu on 2017/2/7.
 */
public class FavoriteRecommend implements Serializable {
    public String name;
    public RecommendEntity items;

    public static class RecommendEntity implements Serializable {
        @SerializedName("imgs")
        public BannerImgEntity imgs;         // 推荐位
        public FavoriteList recommend_items; // 列表信息

        public static class BannerImgEntity implements Serializable {
            @SerializedName("data")
            public List<BannerInfo> data;
        }
    }
}
