package com.example.jeffrey.vakantieapp;

import android.os.AsyncTask;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Jeffrey on 13-6-2017.
 */

public class SchoolVakantieTask extends AsyncTask<String,Void,String> {

    SchoolVakantieTaskListener listener;

    public SchoolVakantieTask(SchoolVakantieTaskListener listener){this.listener=listener;}


    @Override
    protected String doInBackground(String... params) {
        InputStream inputStream = null;
        BufferedReader reader = null;
        String urlString = "";
        String response = "";

        try{
            URL url = new URL(params[0]);
            URLConnection connection = url.openConnection();

            reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            response = reader.readLine().toString();
            String line;
            while ((line = reader.readLine()) != null){
                response+=line;
            }
        } catch(Exception e){
            e.printStackTrace();
        } finally {
            if(reader!=null){
                try {
                    reader.close();
                } catch (IOException e){
                    e.printStackTrace();
                }
            }
        }

        return response;
    }

    @Override
    protected void onPostExecute(String response) {
        try{
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");

            JSONObject jsonObject = new JSONObject(response);
            JSONArray vakanties = jsonObject.getJSONArray("content").getJSONObject(0).getJSONArray("vacations");

            for(int i = 0; i<vakanties.length();i++){
                String name = vakanties.getJSONObject(i).getString("type");
                VakantieItem vakantie = new VakantieItem(name);
                JSONArray regios  = vakanties.getJSONObject(i).getJSONArray("regions");

                for(int ii = 0; ii<regios.length();ii++){
                    Tijdvlak tv = new Tijdvlak();
                    String region = regios.getJSONObject(ii).getString("region");
//                    if(region.equals("heel Nederland")){
//                        tv.region = "noord";
//                        tv.startDate = dateFormat.parse(regios.getJSONObject(ii).getString("startdate"));
//                        tv.endDate = dateFormat.parse(regios.getJSONObject(ii).getString("enddate"));
//                        vakantie.tijdvlak.add(tv);
//
//                        Tijdvlak tv1 = new Tijdvlak();
//                        tv1.region = "midden";
//                        tv1.startDate = dateFormat.parse(regios.getJSONObject(ii).getString("startdate"));
//                        tv1.endDate = dateFormat.parse(regios.getJSONObject(ii).getString("enddate"));
//                        vakantie.tijdvlak.add(tv1);
//
//                        Tijdvlak tv2 = new Tijdvlak();
//                        tv2.region = "zuid";
//                        tv2.startDate = dateFormat.parse(regios.getJSONObject(ii).getString("startdate"));
//                        tv2.endDate = dateFormat.parse(regios.getJSONObject(ii).getString("enddate"));
//                        vakantie.tijdvlak.add(tv2);
//
//                    } else {
                        tv.region = region;
                        tv.startDate = dateFormat.parse(regios.getJSONObject(ii).getString("startdate"));
                        tv.endDate = dateFormat.parse(regios.getJSONObject(ii).getString("enddate"));
                        vakantie.tijdvlak.add(tv);
//                    }
                }
                listener.onVakantieAvailable(vakantie);
            }


        } catch (Exception e){
            e.printStackTrace();
        }
    }
}
