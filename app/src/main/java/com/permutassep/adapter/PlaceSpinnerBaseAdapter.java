package com.permutassep.adapter;

/**
 * By Jorge E. Hernandez (@lalongooo) 2015
 */

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.permutassep.model.Place;

import java.util.List;

public class PlaceSpinnerBaseAdapter extends SpinnerBaseAdapter<Place> {

    private Context context;
    private List<Place> places;

    public PlaceSpinnerBaseAdapter(Context context, List<? extends Place> list) {
        super(list);
        this.context = context;
        places = (List<Place>) list;
    }


    @Override
    public long getItemId(int position) {
        return places.get(position).getPlaceId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        TextView textview = (TextView) inflater.inflate(android.R.layout.simple_list_item_1, null);
        textview.setText(places.get(position).getPlaceDescription());
        textview.setTextColor(Color.BLACK);

        return textview;
    }
}