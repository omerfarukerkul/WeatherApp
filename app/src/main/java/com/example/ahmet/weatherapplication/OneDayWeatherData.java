package com.example.ahmet.weatherapplication;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by AHMET on 03.05.2017.
 */

public class OneDayWeatherData {
    private List<InstantWeatherData> dailyWeatherData;
    private String city;

    public OneDayWeatherData(){
        dailyWeatherData = new ArrayList<>();
    }
    public void addInstantWeatherData(InstantWeatherData iwd){
        dailyWeatherData.add(iwd);
    }
    public List<InstantWeatherData> getOneDayWeatherData(){
        return dailyWeatherData;
    }
    public String getCity(){return this.city;}
    public void setCity(String s){this.city = s;}
}
