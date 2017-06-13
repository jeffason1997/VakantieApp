package com.example.jeffrey.vakantieapp;

import android.os.AsyncTask;
import org.json.JSONArray;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.text.SimpleDateFormat;

/**
 * Created by Jeffrey on 13-6-2017.
 */

class SchoolVakantieTask extends AsyncTask<String, Void, String> {

    private SchoolVakantieTaskListener listener;

    SchoolVakantieTask(SchoolVakantieTaskListener listener) {
        this.listener = listener;
    }


    @Override
    protected String doInBackground(String... params) {
        BufferedReader reader = null;
        String response = "";

        try {
            URL url = new URL(params[0]);
            URLConnection connection = url.openConnection();

            reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            response = reader.readLine();
            String line;
            while ((line = reader.readLine()) != null) {
                response += line;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return response;
    }

    @Override
    protected void onPostExecute(String response) {
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");

            JSONObject jsonObject = new JSONObject(response);
            JSONArray vakanties = jsonObject.getJSONArray("content").getJSONObject(0).getJSONArray("vacations");

            for (int i = 0; i < vakanties.length(); i++) {
                VakantieItem vakantie = new VakantieItem();
                vakantie.name = vakanties.getJSONObject(i).getString("type");
                JSONArray regios = vakanties.getJSONObject(i).getJSONArray("regions");

                for (int ii = 0; ii < regios.length(); ii++) {
                    Tijdvlak tv = new Tijdvlak();
                    tv.region = regios.getJSONObject(ii).getString("region");
                    tv.startDate = dateFormat.parse(regios.getJSONObject(ii).getString("startdate"));
                    tv.endDate = dateFormat.parse(regios.getJSONObject(ii).getString("enddate"));
                    vakantie.tijdvlak.add(tv);
                }
                listener.onVakantieAvailable(vakantie);
            }


        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
