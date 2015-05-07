package com.permutassep.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.permutassep.model.City;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Created by lalongooo on 23/02/15.
 */
public class CitySpinnerBaseAdapter extends BaseAdapter {

    private Context context;
    private List<City> cities = new ArrayList<>();

    public CitySpinnerBaseAdapter(Context context, List<City> cities) {

        Collections.sort(cities, new Comparator<City>() {
            @Override
            public int compare(City c1, City c2) {
                return c1.getNombreMunicipio().compareTo(c2.getNombreMunicipio());
            }
        });
        cities.add(0,cities.remove(cities.size()-1));
        this.context = context;
        this.cities = cities;
    }

    @Override
    public int getCount() {
        return cities.size();
    }

    @Override
    public Object getItem(int position) {
        return cities.get(position);
    }

    @Override
    public long getItemId(int position) {
        return cities.get(position).getClaveMunicipio();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        TextView textview = (TextView) inflater.inflate(android.R.layout.simple_list_item_1, null);
        textview.setText(cities.get(position).getNombreMunicipio());
        textview.setTextColor(Color.BLACK);
        return textview;
    }
}
