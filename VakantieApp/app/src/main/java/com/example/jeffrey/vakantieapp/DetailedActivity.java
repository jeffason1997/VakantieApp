package com.example.jeffrey.vakantieapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class DetailedActivity extends AppCompatActivity {
    ArrayAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailed);


        ListView lv = (ListView) findViewById(R.id.detailed_time);
        VakantieItem vakantie = (VakantieItem) getIntent().getSerializableExtra("VAKANTIE");

        TextView tv = (TextView) findViewById(R.id.detailed_vakantie_id);
        tv.setText(vakantie.name);

        mAdapter = new DetailedAdapter(this,vakantie.tijdvlak);
        lv.setAdapter(mAdapter);
    }
}
