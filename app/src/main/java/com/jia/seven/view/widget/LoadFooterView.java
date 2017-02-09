package com.jia.seven.view.widget;

import android.content.Context;
import android.support.annotation.ColorInt;
import android.support.annotation.DrawableRes;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.jia.seven.R;
import com.jia.seven.utils.AppUtil;


public class LoadFooterView extends LinearLayout {
    public final static int STATE_NORMAL = 0;
    public final static int STATE_LOADING = 1;

    private Context mContext;

    private View mContentView;
    private ProgressBar mProgressBar;
    private TextView mTextView;

    public LoadFooterView(Context context) {
        super(context);
        initView(context);
    }

    public LoadFooterView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    public void setState(int state) {
        if (state == STATE_LOADING) {
            mProgressBar.setVisibility(View.VISIBLE);
            mTextView.setText(R.string.loadinfo_loadmoreing);
        } else {
            mProgressBar.setVisibility(View.GONE);
            mTextView.setText(R.string.loadinfo_loadmore);
        }
    }

    public void setBottomMargin(int height) {
        if (height < 0)
            return;
        LayoutParams lp = (LayoutParams) mContentView.getLayoutParams();
        lp.bottomMargin = height;
        mContentView.setLayoutParams(lp);
    }

    public int getBottomMargin() {
        LayoutParams lp = (LayoutParams) mContentView.getLayoutParams();
        return lp.bottomMargin;
    }

    public void setModeColor(int color) {
        mContentView.setBackgroundColor(color);
    }

    public void setBgResource(@DrawableRes int colorSelector) {
        mContentView.setBackgroundResource(colorSelector);
    }

    /**
     * normal status
     */
    public void normal() {
        mProgressBar.setVisibility(View.GONE);
    }

    /**
     * loading status
     */
    public void loading() {
        mProgressBar.setVisibility(View.VISIBLE);
    }

    /**
     * hide footer when disable pull load more
     */
    public void hide() {
        LayoutParams lp = (LayoutParams) mContentView.getLayoutParams();
        lp.height = 0;
        mContentView.setLayoutParams(lp);
    }

    /**
     * show footer
     */
    public void show() {
        LayoutParams lp = (LayoutParams) mContentView.getLayoutParams();
        lp.height = LayoutParams.WRAP_CONTENT;
        mContentView.setLayoutParams(lp);
    }

    public void gone() {
        mContentView.setVisibility(GONE);
    }

    public void visible() {
        mContentView.setVisibility(VISIBLE);
    }

    public void setBackground(@ColorInt int color) {
        mContentView.setBackgroundColor(color);
    }

    public boolean isShown() {
        LayoutParams lp = (LayoutParams) mContentView.getLayoutParams();
        return lp.height > 0;
    }

    private void initView(Context context) {
        mContext = context;
        mContentView = LayoutInflater.from(mContext).inflate(R.layout.listview_footer, null);
        mContentView.setLayoutParams(new LayoutParams(AppUtil.screenSize().getWidth(), LayoutParams.MATCH_PARENT));
        addView(mContentView);

        mProgressBar = (ProgressBar) mContentView.findViewById(R.id.footer_progress);
        mTextView = (TextView) mContentView.findViewById(R.id.footer_textview);
    }
}
