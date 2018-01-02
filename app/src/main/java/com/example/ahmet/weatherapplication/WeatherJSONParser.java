package com.example.ahmet.weatherapplication;

/**
 * Created by AHMET on 04.05.2017.
 */
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class WeatherJSONParser {
    public static List<OneDayWeatherData> parseFeed(String content){
        List<OneDayWeatherData> feedResult = new ArrayList<>();
        try {
            JSONObject jsonObject = new JSONObject(content);
            int code = jsonObject.getInt("cod");
            if(code != 200){return null;}
            else{
                JSONArray list = jsonObject.getJSONArray("list");
                OneDayWeatherData daily = null;
                for (int i=0 ; i < list.length();i++){
                    if (daily == null){
                        daily = new OneDayWeatherData();
                    }
                    JSONObject item =list.getJSONObject(i);
                    InstantWeatherData d = new InstantWeatherData();

                    d.setDate(item.getString("dt_txt"));
                    JSONObject main = item.getJSONObject("main");
                    d.setTemp(main.getDouble("temp"));
                    d.setHumidity(main.getInt("humidity"));
                    JSONArray weather_arr = item.getJSONArray("weather");
                    JSONObject weather_obj = weather_arr.getJSONObject(0);
                    d.setWeather_main(weather_obj.getString("main"));
                    d.setWeather_description(weather_obj.getString("description"));
                    d.setWeather_imgurl(weather_obj.getString("icon"));
                    JSONObject clouds = item.getJSONObject("clouds");
                    d.setClouds_all(clouds.getInt("all"));
                    JSONObject wind = item.getJSONObject("wind");
                    d.setWind_deg(wind.getDouble("deg"));
                    d.setWind_speed(wind.getDouble("speed"));

                    daily.addInstantWeatherData(d);
                    if((i+1)%8 == 0) {
                        JSONObject city = jsonObject.getJSONObject("city");
                        daily.setCity(city.getString("name"));
                        feedResult.add(daily);
                        daily = null;
                    }
                }
            }
        }catch (JSONException e){
            e.printStackTrace();
            feedResult = null;
        }
        return feedResult;
    }
}
