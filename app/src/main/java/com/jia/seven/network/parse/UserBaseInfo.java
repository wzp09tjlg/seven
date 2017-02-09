package com.jia.seven.network.parse;

import java.io.Serializable;
import java.util.List;

/**
 * Created by wu on 2016/11/17.
 */
public class UserBaseInfo implements Serializable {
    public String nickname;
    public String avatar;
    public String cover;
    public String phone;
    public String description;
    public String gender;//接口定义的时候 1是男 2是女
    public List<AuthPartner> open_auth;

    public class AuthPartner implements Serializable {
        public String partner;
        public String open_id;
        public String nickname;
        public String avatar;

        @Override
        public String toString() {
            return "{partner:" + partner + ";open_id:" + open_id
                    + ";nickname:" + nickname + ";avatar:" + avatar + "}";
        }
    }

    @Override
    public String toString() {
        return "{nickname:" + nickname + ";avatar:" + avatar + ";cover:" + cover
                + ";phone:" + phone + ";description:" + description
                + ";gender:" + gender + ";open_auth:" + open_auth + "}";
    }
}
