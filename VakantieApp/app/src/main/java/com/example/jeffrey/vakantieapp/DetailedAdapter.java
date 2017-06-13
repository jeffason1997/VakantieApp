package com.example.jeffrey.vakantieapp;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.List;

/**
 * Created by Jeffrey on 13-6-2017.
 */

public class DetailedAdapter extends ArrayAdapter<Tijdvlak> {

    public DetailedAdapter(@NonNull Context context, @NonNull List<Tijdvlak> objects) {
        super(context, 0, objects);
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        Tijdvlak tijdvlak = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.lv_detailed_row,parent,false);
        }

        SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");

        TextView name = (TextView) convertView.findViewById(R.id.detailed_row_regio_id);
        name.setText(tijdvlak.region);

        TextView amount = (TextView) convertView.findViewById(R.id.detailed_row_start_id);
        amount.setText(format.format(tijdvlak.startDate));

        TextView regio = (TextView) convertView.findViewById(R.id.detailed_row_eind_id);
        regio.setText(format.format(tijdvlak.endDate));


        return convertView;
    }
}
