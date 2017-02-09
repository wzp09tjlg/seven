package com.jia.seven.network.parse;

import java.io.Serializable;
import java.util.List;

/**
 * Created by wu on 2017/2/7.
 */
public class FavoriteList implements Serializable {

    public String count;
    public List<Card> rows;

    public static class Card implements Serializable {

        public String type;
        public List<Story> story_list;
        public boolean have_update;
        public User user;
        public Tag tag;

        public boolean animation = false;

    }

    public static class User implements Serializable {
        public String id;
        public String nickname;
        public String avatar;
        public String weibo_verified_type;
        public String description;
        public boolean followed;
    }

    public static class Tag implements Serializable {
        public String id;
        public String name;
        public String image;
        public String story_count;
        public boolean followed;
    }

    public static class Story implements Serializable {

        public String id;
        public String story_id;
        public String title;
        public String image; // 长图
        public String cover; // 方图
        public boolean show_icon;
        public String type;
    }

}