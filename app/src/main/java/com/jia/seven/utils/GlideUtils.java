package com.jia.seven.utils;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

/**
 * Created by wu on 2017/2/8.
 */
public class GlideUtils {

    public static final void setImage(Context context, ImageView imgView, String url,
                                      int placeHolder, int error, int mobile){
        imgView.setVisibility(View.VISIBLE);
        if (!CommonHelper.isLoadImageEnable(context)) {
            imgView.setImageResource(mobile);
            return;
        } else if (TextUtils.isEmpty(url) || !url.toLowerCase().startsWith("http://")) {
            imgView.setImageResource(error);
            return;
        } else {
            if(context == null) return;//存在activity被销毁之后 回调
            try{
                Glide.with(context)
                        .load(url)
                        .dontAnimate()
                        .crossFade()
                        .placeholder(placeHolder)
                        .error(error)
                        .into(imgView);
            }catch (Exception e){}
        }
    }
}
