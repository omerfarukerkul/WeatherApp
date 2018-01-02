package com.example.ahmet.weatherapplication;


import android.os.Parcel;
import android.os.Parcelable;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by AHMET on 03.05.2017.
 */

public class InstantWeatherData implements Parcelable {
    private Date date;
    private double temp;
    private int humidity;
    private String weather_main;
    private String weather_description;
    private String weather_imgurl;
    private int clouds_all;
    private double wind_speed;
    private double wind_deg;

    protected InstantWeatherData(Parcel in) {
        String dateString = in.readString();
        setDate(dateString);
        temp = in.readDouble();
        humidity = in.readInt();
        weather_main = in.readString();
        weather_description = in.readString();
        weather_imgurl = in.readString();
        clouds_all = in.readInt();
        wind_speed = in.readDouble();
        wind_deg = in.readDouble();
    }

    public InstantWeatherData(){

    }

    //getters and setters
    public Date getDate(){ return this.date; }
    public void setDate(String s){
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd kk:mm:ss");
            this.date = dateFormat.parse(s);
        } catch (ParseException e ){e.printStackTrace();}
           // this.date = DateFormat.getDateInstance().parse(s);

    }
    public String getWeather_imgurl(){return this.weather_imgurl;}
    public void setWeather_imgurl(String s){
        this.weather_imgurl = "http://openweathermap.org/img/w/" + s + ".png";
    }

    public double getTemp(){return this.temp;}
    public void setTemp(double f){this.temp =f;}

    public int getHumidity(){return this.humidity;}
    public void setHumidity(int i){this.humidity = i;}

    public String getWeather_main(){ return this.weather_main; }
    public void setWeather_main(String s){ this.weather_main = s;}

    public String getWeather_description(){ return this.weather_description; }
    public void setWeather_description(String s){ this.weather_description = s;}

    public int getClouds_all(){return this.clouds_all;}
    public void setClouds_all(int i){this.clouds_all = i;}

    public double getWind_speed(){return this.wind_speed;}
    public void setWind_speed(double f){this.wind_speed =f;}

    public double getWind_deg(){return this.wind_deg;}
    public void setWind_deg(double f){this.wind_deg =f;}


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd kk:mm:ss");
        String dateString =simpleDateFormat.format(this.date);
        dest.writeString(dateString);
        dest.writeDouble(temp);
        dest.writeInt(humidity);
        dest.writeString(weather_main);
        dest.writeString(weather_description);
        dest.writeString(weather_imgurl);
        dest.writeInt(clouds_all);
        dest.writeDouble(wind_speed);
        dest.writeDouble(wind_deg);
    }
    public static final Creator<InstantWeatherData> CREATOR = new Creator<InstantWeatherData>() {
        @Override
        public InstantWeatherData createFromParcel(Parcel in) {
            return new InstantWeatherData(in);
        }

        @Override
        public InstantWeatherData[] newArray(int size) {
            return new InstantWeatherData[size];
        }
    };
}
