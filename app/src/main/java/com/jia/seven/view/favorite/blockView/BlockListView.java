package com.jia.seven.view.favorite.blockView;

import android.content.Context;
import android.support.v7.widget.LinearLayoutCompat;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jia.seven.R;


/**
 * Created by Andy on 2015/11/25.
 * 实现类似GridView的效果
 */
public class BlockListView extends LinearLayoutCompat {

    private boolean mHorizontalDiver = true;
    private int mHorDiver = R.drawable.shape_diver;
    private boolean mVerticalDiver = true;
    private int mVerDiver = R.drawable.shape_diver;

    private Context mContext;

    private boolean dowImgWifiOnly = false;

    public interface OnItemClickListener {
        void onItemClick(View v, int position);
    }

    private static final int INDEX_TAG = 0x04 << 24;

    private BlockListAdapter<?> mBlockListAdapter;

    private LayoutInflater mLayoutInflater;

    private OnItemClickListener mOnItemClickListener;

    public BlockListView(Context context) {
        this(context, null);
    }

    public BlockListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        mLayoutInflater = LayoutInflater.from(context);
        setOrientation(VERTICAL);
        setShowDividers(LinearLayoutCompat.SHOW_DIVIDER_MIDDLE
                | LinearLayoutCompat.SHOW_DIVIDER_BEGINNING);
    }

    public void setHorizontalDiver(boolean horizontalDiver, int drawble) {
        if (mHorizontalDiver != horizontalDiver) {
            mHorizontalDiver = horizontalDiver;
        }
        mHorDiver = drawble;
    }

    public void setVerticalDiver(boolean verticalDiver, int drawble) {
        if (mVerticalDiver != verticalDiver) {
            mVerticalDiver = verticalDiver;
            if (verticalDiver) {
                setShowDividers(LinearLayoutCompat.SHOW_DIVIDER_MIDDLE
                        | LinearLayoutCompat.SHOW_DIVIDER_BEGINNING | LinearLayoutCompat.SHOW_DIVIDER_END);
                setDividerDrawable(getResources().getDrawable(R.drawable.shape_diver));
            } else {
                setShowDividers(LinearLayoutCompat.SHOW_DIVIDER_NONE);
            }
        }
        mVerDiver = drawble;
    }

    public void setAdapter(BlockListAdapter<?> adapter) {
        if (adapter == null) {
            throw new IllegalArgumentException("adapter should not be null");
        }
        mBlockListAdapter = adapter;
        adapter.registerView(this);
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        if (null != mBlockListAdapter) {
            mBlockListAdapter.registerView(null);
        }
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        if (null != mBlockListAdapter) {
            mBlockListAdapter.registerView(this);
        }
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        mOnItemClickListener = listener;
    }

    OnClickListener mOnClickListener = new OnClickListener() {

        @Override
        public void onClick(View v) {
            int index = (Integer) v.getTag(INDEX_TAG);
            if (null != mOnItemClickListener) {
                mOnItemClickListener.onItemClick(v, index);
            }
        }
    };

    public void onDataListChange() {

        dowImgWifiOnly = false;

        removeAllViews();

        int len = mBlockListAdapter.getCount();

        if (len > 0) {

            int columnNum = mBlockListAdapter.getCloumnNum();

            boolean blockDescendant = getDescendantFocusability() == ViewGroup.FOCUS_BLOCK_DESCENDANTS;

            LinearLayoutCompat.LayoutParams lyp = new LinearLayoutCompat.LayoutParams(LinearLayoutCompat.LayoutParams.MATCH_PARENT, LinearLayoutCompat.LayoutParams.WRAP_CONTENT);
            lyp.weight = 1.0f;

            int rowNum = len / columnNum;
            if (len % columnNum > 0) {
                rowNum++;
            }

            for (int i = 0; i < rowNum; i++) {
                if(i != rowNum - 1|| i == 0)
                  setDividerDrawable(getResources().getDrawable(mVerDiver));//R.drawable.shape_diver
                LinearLayoutCompat rowGroup = new LinearLayoutCompat(mContext);
                rowGroup.setOrientation(LinearLayoutCompat.HORIZONTAL);
                if(mHorizontalDiver) {
                    rowGroup.setShowDividers(LinearLayoutCompat.SHOW_DIVIDER_MIDDLE);
                    rowGroup.setDividerDrawable(getResources().getDrawable(mHorDiver)); //R.drawable.shape_diver
                }

                for (int j = 0; j < columnNum; j++) {
                    if (i * columnNum + j < len) {
                        View view = mBlockListAdapter.getView(mLayoutInflater, i * columnNum + j);
                        if (!blockDescendant) {
                            view.setOnClickListener(mOnClickListener);
                        }
                        view.setTag(INDEX_TAG, i * columnNum + j);
                        rowGroup.addView(view, lyp);
                    } else {
                        rowGroup.addView(new View(mContext), lyp);
                    }
                }

                addView(rowGroup);
            }

        }
        requestLayout();
    }

    public boolean isDowImgWifiOnly() {
        return dowImgWifiOnly;
    }
}
