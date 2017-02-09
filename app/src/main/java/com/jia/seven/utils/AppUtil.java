package com.jia.seven.utils;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.view.Display;
import android.view.WindowManager;

import com.jia.seven.HSApplication;

import java.util.concurrent.TimeUnit;

import io.reactivex.Flowable;

/**
 * Created by wu on 2017/1/9.
 */
@SuppressWarnings("All")
public class AppUtil {
    /**
     * 获取程序的所有权限，用于6.0之上的动态获取
     *
     * @return 动态获取的所有权限
     */
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    public static String[] getPermissions() {
        return new String[]{
                Manifest.permission.CAMERA,
                Manifest.permission.INTERNET,
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE};

    }
    //获取应用的VersionCode
    public static int getVersionCode(Context context) {
        try {
            PackageInfo info = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
            return info.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return 1;
    }

    //获取应用的VersionName
    public static String getVersionName(Context context) {
        try {
            PackageInfo info = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
            return info.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
        return "";
    }

    //获取应用的渠道名字
    public static String getChannelName(Context context){
        String value = "HuaSheng";
        try{
            ApplicationInfo ainfo = context.getPackageManager()
                    .getApplicationInfo(context.getPackageName()
                            ,PackageManager.GET_META_DATA);
            value = ainfo.metaData.getString("UMENG_CHANNEL");
        }catch (Exception e){}
        return value;
    }

    //实现从dip到px的转换
    public static int dpToPx(Context context, int dps) {
        return Math.round(context.getResources().getDisplayMetrics().density * (float) dps);
    }

    public static int pxToDp(Context context, float px) {
        return Math.round(px / context.getResources().getDisplayMetrics().density);
    }

    public static Display screenSize() {
        WindowManager wm = (WindowManager) HSApplication.getContext().getSystemService(Context.WINDOW_SERVICE);
        return wm.getDefaultDisplay();
    }

    private static long lastClickTime;

    /**
     * 防止快速点击
     */
    public synchronized static boolean isFastClick() {
        long time = System.currentTimeMillis();
        if (time - lastClickTime < 500) {
            return true;
        }
        lastClickTime = time;
        return false;
    }

    /**
     * 获取一个全局的定时器，用于完成程序内的打点等工作
     *
     * @return 全局心跳
     */
    public static Flowable getGlobalTimer() {
        return Flowable.interval(3, TimeUnit.SECONDS).share();
    }
}
