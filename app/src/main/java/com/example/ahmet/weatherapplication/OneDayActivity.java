package com.example.ahmet.weatherapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class OneDayActivity extends AppCompatActivity {
    TextView tvDateCity ;
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_one_day);
        tvDateCity = (TextView)findViewById(R.id.tv_Date_City);
        recyclerView =(RecyclerView)findViewById(R.id.rv_One_Day_View);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);

        Intent intent = getIntent();
        if(intent != null){
            try {
                List<InstantWeatherData> weatherDatas = intent.getParcelableArrayListExtra("OneDayData");
                String c = intent.getStringExtra("cityName");
                String d= intent.getStringExtra("date");
                tvDateCity.setText("[ " + c + " ]( " + d + " )");
                InstantWeatherData deneme = new InstantWeatherData();
                /*deneme.setDate("2017-05-18 20:31:00");
                deneme.setWeather_main("Bulutlu");
                deneme.setClouds_all(12);
                deneme.setWind_speed(1.1);
                deneme.setHumidity(10);
                deneme.setTemp(111.2);
                deneme.setWeather_imgurl("10d");
                deneme.setWeather_description("baya iyi bence");
                List<InstantWeatherData> weatherDatas = new ArrayList<>();
                weatherDatas.add(deneme);
                weatherDatas.add(deneme);*/
                HourlyWeatherAdapter adapter = new HourlyWeatherAdapter(weatherDatas);
                recyclerView.setAdapter(adapter);
                adapter.notifyDataSetChanged();
            }catch (Exception e){
                e.printStackTrace();
            }

        }else {
            Toast.makeText(this,"Can not get Daily Weather Data Details",Toast.LENGTH_LONG);
        }
    }
}
