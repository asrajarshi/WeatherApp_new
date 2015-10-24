package com.asrajarshi.weatherappnew;


import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by asrajarshi on 10/21/2015.
 */

public class FragmentCity extends Fragment {
    private TextView cityText;
    private TextView temp;
    private TextView Max_temp;
    private TextView speed;
    private TextView desciption;
    ArrayList weather;
    String city = MainActivity.city;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.new_york_fragment_layout, container, false);
        cityText = (TextView) view.findViewById(R.id.cityText);
        temp = (TextView) view.findViewById(R.id.temp);
        Max_temp = (TextView) view.findViewById(R.id.range);
        speed = (TextView) view.findViewById(R.id.speed);
        desciption = (TextView) view.findViewById(R.id.desciption);
        return view;
    }
    @Override
    public void onResume(){
        super.onResume();
        LoadCurrentWeatherAsyn loadCurrentWeatherAsyn = new LoadCurrentWeatherAsyn();
        loadCurrentWeatherAsyn.execute(city);
    }

    public class LoadCurrentWeatherAsyn extends AsyncTask<String, Void, String> {
        String data = null;
        private static final String TAG = "LoadCurrentWeatherAsync";
        private static final String CURRENT_WEATHER_URL = "http://api.openweathermap.org/data/2.5/weather?q=%s&mode=json";

        private BufferedReader reader;
        private static final String my_key = "f824472f83b6ec73135092a41a49eb71";

        @Override
        protected String doInBackground(String... params) {
            String city = params[0];
            return loadCurrentWeather(city);  // get the weather data for a perticular city
        }

        public String loadCurrentWeather(String city) {
            try {
                URL web_url = new URL(String.format(CURRENT_WEATHER_URL, city));
                HttpURLConnection conn = (HttpURLConnection) web_url.openConnection();
                conn.addRequestProperty("x-api-key", my_key);
                reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                String inputLine;
                StringBuffer json = new StringBuffer();
                while ((inputLine = reader.readLine()) != null)
                    json.append(inputLine).append("\n");
                reader.close();
                data = json.toString();
                Log.d("jsonString", data);
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return data;
        }

        protected void onPostExecute(String data) {
            ParseJson parseJson = new ParseJson(data);
            weather = parseJson.parseIt();
            if(weather==null) Toast.makeText(getActivity(), "Please turn on the Internet", Toast.LENGTH_LONG).show();// if city name is entered wrong
            else{
                cityText.setText("Location: " + weather.get(0) + ", " + weather.get(1));
                temp.setText("Current Temp: " + weather.get(5) + " C");
                Max_temp.setText("Max Temp: " + weather.get(6) + " C,  Min Temp: " + weather.get(7) + " C");
                desciption.setText("Description: " + weather.get(2));
                speed.setText("Wind Speed: " + weather.get(8)+" m/s");
            }}
    }
}
