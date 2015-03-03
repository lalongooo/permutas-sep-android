package com.permutassep.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.permutassep.model.State;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lalongooo on 23/02/15.
 */
public class StateSpinnerBaseAdapter extends BaseAdapter {

    private Context context;
    private List<State> states = new ArrayList<>();

    public StateSpinnerBaseAdapter(Context context, List<State> states) {
        this.context = context;
        this.states = states;
    }

    @Override
    public int getCount() {
        return states.size();
    }

    @Override
    public Object getItem(int position) {
        return states.get(position);
    }

    @Override
    public long getItemId(int position) {
        return states.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        TextView textview = (TextView) inflater.inflate(android.R.layout.simple_list_item_1, null);
        textview.setText(states.get(position).getStateName());
        textview.setTextColor(Color.BLACK);
        return textview;
    }
}
