package com.jia.seven.view.favorite.blockView;

import android.view.LayoutInflater;
import android.view.View;

import java.util.List;

/**
 * Created by Andy on 2015/11/25.
 */

public abstract class BlockListAdapter<T> {

    private List<T> mItemList;
    private BlockListView mView;

    private int mColumnNum = 0;

    public BlockListAdapter() {
    }

    public T getItem(int position) {
        if (mItemList != null && mItemList.size() > position && position >= 0)
            return mItemList.get(position);
        else
            return null;
    }

    public void registerView(BlockListView observer) {
        mView = observer;
    }

    public void displayBlocks(List<T> itemList) {
        mItemList = itemList;

        if (null == mView) {
            throw new IllegalArgumentException("Adapter has not been attached to any BlockListView");
        }
        mView.onDataListChange();
    }

    public abstract View getView(LayoutInflater layoutInflater, int position);

    public int getCount() {
        return mItemList == null ? 0 : (mItemList.size() > 4 ? 4 : mItemList.size());
    }

    public void setColumnNum(int num) {
        mColumnNum = num;
    }

    public int getCloumnNum() {
        return mColumnNum;
    }
}
