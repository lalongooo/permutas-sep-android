package com.permutassep.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.permutassep.model.Town;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lalongooo on 23/02/15.
 */
public class TownSpinnerBaseAdapter extends BaseAdapter {

    private Context context;
    private List<Town> towns = new ArrayList<>();

    public TownSpinnerBaseAdapter(Context context, List<Town> towns) {
        this.context = context;
        this.towns = towns;
    }

    @Override
    public int getCount() {
        return towns.size();
    }

    @Override
    public Object getItem(int position) {
        return towns.get(position);
    }

    @Override
    public long getItemId(int position) {
        return towns.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        TextView textview = (TextView) inflater.inflate(android.R.layout.simple_list_item_1, null);
        textview.setText(towns.get(position).getNombre());
        textview.setTextColor(Color.BLACK);
        return textview;
    }
}
