package com.jia.seven.network.parse;

import java.io.Serializable;

/**
 * Created by Andy on 2015/11/23.
 */
public class PersonalCenter implements Serializable {

    public String id;
    public String nickname;
    public String avatar;
    public String status;
    public String story_count;
    public String follow_count;
    public String fav_count;//收藏数
    public String like_count;//赞数
    public String weibo_verified_type;//V
    public String description;//个人简介
    public boolean notify_center_update;//通知中心是否打点

    public String cover;//1.6.0 个人封面
    public String gender;//1.6.0 性别


    @Override
    public String toString() {
        return "PersonalCenter{" +
                "id='" + id + '\'' +
                ", nickname='" + nickname + '\'' +
                ", avatar='" + avatar + '\'' +
                ", status='" + status + '\'' +
                ", story_count='" + story_count + '\'' +
                ", follow_count='" + follow_count + '\'' +
                ", fav_count='" + fav_count + '\'' +
                ", like_count='" + like_count + '\'' +
                ", weibo_verified_type='" + weibo_verified_type + '\'' +
                ", description='" + description + '\'' +
                ", notify_center_update=" + notify_center_update +
                ", cover='" + cover + '\'' +
                ", gender='" + gender + '\'' +
                '}';
    }
}
