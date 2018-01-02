package com.example.ahmet.weatherapplication;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.text.DateFormat;
import java.text.DateFormatSymbols;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * Created by AHMET on 09.05.2017.
 */

public class HourlyWeatherAdapter extends RecyclerView.Adapter<HourlyWeatherAdapter.MyViewHolder> {

    private List<InstantWeatherData> weatherDataList;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView tvDate, weather_main, weatherDescription, temperature, windSpeed, humidity,city;
        public ImageView imageView;


        public MyViewHolder(View view) {
            super(view);
            city =(TextView) view.findViewById(R.id.tvCity);
            tvDate = (TextView) view.findViewById(R.id.tvDate);
            weather_main = (TextView) view.findViewById(R.id.weatherMain);
            weatherDescription = (TextView) view.findViewById(R.id.weather_description);
            temperature =(TextView) view.findViewById(R.id.temperature);
            windSpeed =(TextView) view.findViewById(R.id.wind_speed);
            humidity =(TextView) view.findViewById(R.id.humidity);
            imageView = (ImageView) view.findViewById(R.id.imageView);

        }
    }
    public HourlyWeatherAdapter(List<InstantWeatherData> weatherDataList) {

        this.weatherDataList = weatherDataList;
    }

    @Override
    public HourlyWeatherAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.one_day_weather_layout, parent, false);

        return new HourlyWeatherAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(HourlyWeatherAdapter.MyViewHolder holder, int position) {
        InstantWeatherData weatherData = weatherDataList.get(position);
        holder.city.setText("");
        Locale locale = new Locale("en", "UK");
        DateFormatSymbols dateFormatSymbols = new DateFormatSymbols(locale);
        DateFormat df = new SimpleDateFormat("hh:mm aa", locale);

        Date date = (weatherData.getDate());
        String reportDate = df.format(date);

        holder.tvDate.setText(reportDate);

        Glide.with(holder.imageView.getContext()).load(weatherData.getWeather_imgurl())
                .into(holder.imageView);
        holder.weather_main.setText(weatherData.getWeather_main());
        holder.weatherDescription.setText(weatherData.getWeather_description());
        holder.temperature.setText("" + weatherData.getTemp() + " °C");
        holder.windSpeed.setText("" + weatherData.getWind_speed() + " m/s");
        holder.humidity.setText("" + weatherData.getHumidity() + " %");



    }
    @Override
    public int getItemCount() {
        return weatherDataList.size();
    }

}
