package com.permutassep.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.List;

/**
 * By Jorge E. Hernandez (@lalongooo) 2015
 */
public abstract class SpinnerBaseAdapter<T> extends BaseAdapter {

    private List<? extends T> mList;

    public SpinnerBaseAdapter(List<? extends T> list) {
        this.mList = list;
    }

    public void clear() {
        this.mList.clear();
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public T getItem(int position) {
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public abstract View getView(int position, View convertView, ViewGroup parent);
}
