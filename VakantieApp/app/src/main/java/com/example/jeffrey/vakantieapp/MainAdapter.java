package com.example.jeffrey.vakantieapp;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Jeffrey on 13-6-2017.
 */

public class MainAdapter extends ArrayAdapter<VakantieItem> {

    public MainAdapter(@NonNull Context context, ArrayList<VakantieItem> vakanties) {
        super(context, 0, vakanties);
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        VakantieItem vakantie = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.lv_main_row,parent,false);
        }

        TextView name = (TextView) convertView.findViewById(R.id.row_vakantie_id);
        name.setText(vakantie.name);

        TextView amount = (TextView) convertView.findViewById(R.id.nr_regio_id);
        String number = ""+vakantie.tijdvlak.size();
        amount.setText(number);

        String regio="";
        if(vakantie.tijdvlak.size()>1){
            regio = "regio's";
        } else if (vakantie.tijdvlak.size()==1){
            regio = "regio";
        } else {
            System.out.println("this should not happen");
        }

        TextView regi = (TextView) convertView.findViewById(R.id.regio_id);
        regi.setText(regio);


        return convertView;
    }
}
