package com.jia.seven.network.parse;

import java.io.Serializable;

/**
 * Created by wu on 2017/2/7.
 */
public class BannerInfo implements Serializable {
    public String link;
    public String image;

    @Override
    public String toString() {
        return "{link:" + link + ";image:" + image + "}";
    }
}
