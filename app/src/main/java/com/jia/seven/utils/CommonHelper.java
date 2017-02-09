package com.jia.seven.utils;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.text.Html;
import android.text.TextUtils;
import android.view.View;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;

import com.jia.seven.R;

import static com.jia.seven.utils.AppUtil.pxToDp;

/**
 * Created by wu on 2017/1/19.
 */
public class CommonHelper {
    public static final String WEIBO_V_TYPE_BLUE = "blue";
    public static final String WEIBO_V_TYPE_YELLOW = "yellow";
    
    //在WebView 的http请求头user-agent上添加应用的信息
    public static void appendUA(Context context, WebView webView) {
        String user_agent = webView.getSettings().getUserAgentString();
        webView.getSettings().setUserAgentString(user_agent + " huasheng/" + AppUtil.getVersionName(context));
    }

    /**
     * 检测是否有emoji表情
     *
     * @param source
     * @return
     */
    public static boolean containsEmoji(String source) {
        int len = source.length();
        for (int i = 0; i < len; i++) {
            char codePoint = source.charAt(i);
            if (!isEmojiCharacter(codePoint)) { //如果不能匹配,则该字符是Emoji表情
                return true;
            }
        }
        return false;
    }

    /**
     * 判断是否是Emoji
     *
     * @param codePoint 比较的单个字符
     * @return
     */
    private static boolean isEmojiCharacter(char codePoint) {
        return (codePoint == 0x0) || (codePoint == 0x9) || (codePoint == 0xA) ||
                (codePoint == 0xD) || ((codePoint >= 0x20) && (codePoint <= 0xD7FF)) ||
                ((codePoint >= 0xE000) && (codePoint <= 0xFFFD)) || ((codePoint >= 0x10000)
                && (codePoint <= 0x10FFFF));
    }

    public static String getChanel(Context context) {
        String chanel = "";
        if (context != null) {
            try {

                ApplicationInfo appInfo = context.getPackageManager().getApplicationInfo(context.getPackageName(), PackageManager.GET_META_DATA);
                chanel = String.valueOf(appInfo.metaData.get("UMENG_CHANNEL"));

            } catch (PackageManager.NameNotFoundException e) {
                e.printStackTrace();
            }
        }
        return chanel;
    }

    public static void changeWeiboVStatus(String type, ImageView weiboVType) {
        int vId = -1;
        if (type == null || TextUtils.isEmpty(type)) {
            weiboVType.setVisibility(View.GONE);
            return;
        }
        if (!TextUtils.isEmpty(type)) {
            if (WEIBO_V_TYPE_BLUE.equals(type)) {
                vId = R.drawable.weibo_v_blue_new2;
            } else if (WEIBO_V_TYPE_YELLOW.equals(type)) {
                vId = R.drawable.weibo_v_yellow_new2;
            }
        }
        if (vId != -1) {
            weiboVType.setImageResource(vId);
            weiboVType.setVisibility(View.VISIBLE);
        } else {
            weiboVType.setVisibility(View.GONE);

        }
    }

    //这里有一个坑，就是如果图片放置的位置是drawable 文件中,计算 drawable.getIntrinsicWidth() 会是长度的密度倍.所以放置图片时应该放置在几倍的图片文件夹下
    public static void updateStoryType(final Context ctx, String title, String type, boolean show_icon, View titleView) {
        final TextView tv = (TextView) titleView;
        if (show_icon && type.equals("2")) {//接龙2
            String html = "<img src='" + R.drawable.story_item_type_jielong + "'/> " + title;
            Html.ImageGetter imgGetter = new Html.ImageGetter() {

                @Override
                public Drawable getDrawable(String source) {
                    int id = Integer.parseInt(source);
                    Drawable d = ctx.getResources().getDrawable(id);
                    float rate = pxToDp(ctx, tv.getTextSize()) > 15 ? 1.2f : 1f;
                    d.setBounds(0, 0, (int) (d.getIntrinsicWidth() * rate), (int) (d.getIntrinsicHeight() * rate));
                    return d;
                }
            };
            tv.setText(Html.fromHtml(html, imgGetter, null));
        } else {
            tv.setText(title);
        }
    }

    /**
     * 检查wifi下是否加载图片
     * */
    public static boolean isLoadImageEnable(Context context) {
        return true;
    }

    /**
     * 检查是否在wifi网络
     */
    public static boolean isNetWorkWifi(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = cm.getActiveNetworkInfo();
        return null != networkInfo && networkInfo.getType() == ConnectivityManager.TYPE_WIFI;
    }
}
