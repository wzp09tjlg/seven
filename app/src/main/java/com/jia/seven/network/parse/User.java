package com.jia.seven.network.parse;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Andy on 2015/11/20.
 */
public class User implements Serializable {

    public String access_token = "";
    public String uid = "";
    public String nickname = "";
    public String avatar = "";
    public String load_url = "";
    public String cover = "";//1.6.0个人封面
    public String gender = "";//1.6.0性别
    public ArrayList<ThirdPartner> bind_partners;

    public static class ThirdPartner implements Serializable {
        public String nickname;
        public String open_id;
        public String partner;

        @Override
        public String toString() {
            return "{nickname:" + nickname + ";open_id:" + open_id
                    + ";partner:" + partner + "}";
        }
    }

    @Override
    public String toString() {
        return "{access_token:" + access_token + ";uid:" + uid
                + ";nickname:" + nickname + ";avatar:" + avatar
                + ";load_url:" + load_url + ";cover:" + cover
                + ";gender:" + gender + "; bind_partners:" + bind_partners + "}";
    }
}
