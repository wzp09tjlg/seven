package com.jia.seven.network;

/**
 * Created by wu on 2017/1/11.
 */
@SuppressWarnings("All")
public class ApiFinal {
    // 是否强制使用测试服务器,当这是为true是无论debug，还是release都将使用测试服务器
    public static final boolean FORCEUSE_TEST_HOST = false;
    // Host地址
    public static final String HS_HOST = "https://client-hs.vread.com/";
    public static final String HS_HOST_KEY = "client-hs.vread.com";
    // 测试服务器地址
    public static String HS_HOST_TEST = "http://221.179.193.164/huasheng/client/";
    public static String HS_HOST_KEY_TEST = "221.179.193.164";


    //运营位地址
    public static final String HS_OPERATION = "operation/show.json";
    //版本检测地址
    public static final String HS_UPDATE_CHECK = "config/check_app_update.json";
    // 获取用户收藏
    public static final String HS_USER_COLLECTIONS = "user/fav_list.json";
    // 获取用户赞列表
    public static final String HS_USER_FOLLOWS = "user/like_list.json";
    // 用户登入
    public static final String HS_USER_LOGIN = "user/login.json";
    // 搜索接口
    public static final String HS_SEARCH_ALL = "search/all.json";
    public static final String HS_SEARCH_MORE = "search/more.json";
    // 用户基本信息
    public static final String HS_PROFILE_USER_INFO = "user/info.json";
    // 个人中心数据
    public static final String HS_PERSONAL_CENTER = "user/me.json";
    // 统计-用户行为统计
    public static final String HS_USER_STATS = "stats/clickInfo.json";
    // 统计-用户设备统计
    public static final String HS_DEVICE_STATS = "stats/device_info.json";
    // 关注-我的关注
    public static final String HS_FAVORITE_MINE = "follow/lists.json";
    // 关注-推荐关注
    public static final String HS_FAVORITE_RECOMMEND = "operation/recommend_follow.json";
}
