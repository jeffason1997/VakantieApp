package com.example.jeffrey.vakantieapp;

import android.content.Intent;
import android.nfc.Tag;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements SchoolVakantieTaskListener{

    ArrayList<VakantieItem> vakanties = new ArrayList<>();
    ArrayAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        makeDummies();

        ListView lv = (ListView) findViewById(R.id.lv_vakanties);
        mAdapter = new MainAdapter(this,vakanties);
        lv.setAdapter(mAdapter);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent i = new Intent(getApplicationContext(),DetailedActivity.class);
                i.putExtra("VAKANTIE",vakanties.get(position));
                startActivity(i);
            }
        });
        fetchVakantieItems();
    }

    public void fetchVakantieItems(){
        SchoolVakantieTask task = new SchoolVakantieTask(this);
        String[] urls = new String[]{
                "https://opendata.rijksoverheid.nl/v1/sources/rijksoverheid/infotypes/schoolholidays/schoolyear/2016-2017?output=json"
        };
        task.execute(urls);
    }

    public void makeDummies(){
        // tijdvakken
        Tijdvlak tv1 = new Tijdvlak();
        Tijdvlak tv2 = new Tijdvlak();
        Tijdvlak tv3 = new Tijdvlak();
        Tijdvlak tv4 = new Tijdvlak();
        Tijdvlak tv5 = new Tijdvlak();

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");

        try{
            tv1.startDate = dateFormat.parse("2015-10-16T22:00:00.000Z");
            tv2.startDate = dateFormat.parse("2015-11-16T22:00:00.000Z");
            tv3.startDate = dateFormat.parse("2015-12-16T22:00:00.000Z");
            tv4.startDate = dateFormat.parse("2015-09-16T22:00:00.000Z");
            tv5.startDate = dateFormat.parse("2015-08-16T22:00:00.000Z");

            tv1.endDate = dateFormat.parse("2015-10-16T22:00:00.000Z");
            tv2.endDate = dateFormat.parse("2015-11-16T22:00:00.000Z");
            tv3.endDate = dateFormat.parse("2015-12-16T22:00:00.000Z");
            tv4.endDate = dateFormat.parse("2015-09-16T22:00:00.000Z");
            tv5.endDate = dateFormat.parse("2015-08-16T22:00:00.000Z");

            tv1.region = "noord";
            tv2.region = "zuid";
            tv3.region = "noord";
            tv4.region = "midden";
            tv5.region = "zuid";
        } catch (ParseException e){
            Log.e("message",e.getLocalizedMessage());
        }

        VakantieItem vak1 = new VakantieItem("zomerVakantie");
        vak1.tijdvlak.add(tv1);
        vak1.tijdvlak.add(tv2);

        VakantieItem vak2 = new VakantieItem("herfstVakantie");
        vak2.tijdvlak.add(tv3);
        vak2.tijdvlak.add(tv4);
        vak2.tijdvlak.add(tv5);

        vakanties.add(vak1);
        vakanties.add(vak2);
    }

    @Override
    public void onVakantieAvailable(VakantieItem vakantieItem) {
        vakanties.add(vakantieItem);
        mAdapter.notifyDataSetChanged();
    }
}
