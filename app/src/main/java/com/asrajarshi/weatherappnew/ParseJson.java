package com.asrajarshi.weatherappnew;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by asrajarshi on 10/21/2015.
 */

public class ParseJson {
    private static final String TAG = "ParseJson";
    String data;
    JSONObject jObj;
    ArrayList weatherData = new ArrayList();

    public ParseJson(String data) {
        this.data=data;
    }

    public ArrayList parseIt(){
        try {
            if (data == null) return null;
            jObj = new JSONObject(data);
            JSONObject sysObj = getObject("sys", jObj);
            String country = getString("country", sysObj);
            String city = getString("name", jObj);

            JSONArray jArr = jObj.getJSONArray("weather");
            JSONObject JSONWeather = jArr.getJSONObject(0);
            String description = getString("description", JSONWeather);

            JSONObject main = getObject("main", jObj);
            int humidity = getInt("humidity", main);
            int pressure = getInt("pressure", main);
            int temp_max = getInt("temp_max", main);
            int temp_min= getInt("temp_min", main);
            int temp = getInt("temp", main);

            JSONObject wind = getObject("wind",jObj);
            int speed = getInt("speed",wind);

            weatherData.add(city);
            weatherData.add(country);
            weatherData.add(description);
            weatherData.add(humidity);
            weatherData.add(pressure);
            weatherData.add(temp-273);
            weatherData.add(temp_max-273);
            weatherData.add(temp_min-273);
            weatherData.add(speed);}

        catch (JSONException e) {
            e.printStackTrace();
        }

        return weatherData;
    }



    private int getInt(String inte, JSONObject sysObj) throws JSONException {
        return sysObj.getInt(inte);
    }

    private JSONObject getObject(String sys, JSONObject jObj) throws JSONException {
        return jObj.getJSONObject(sys);
    }

    private String getString(String sys, JSONObject jObj) throws JSONException {
        return jObj.getString(sys);
    }
}

