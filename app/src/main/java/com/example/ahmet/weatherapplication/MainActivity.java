package com.example.ahmet.weatherapplication;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;


public class MainActivity extends AppCompatActivity {
    public static final String prefName = "settings";
    public static final String defaultCityName ="Eskişehir";
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    List<OneDayWeatherData> weatherDatas;
    String city , country;

    ProgressBar pBar ;
    EditText etCity;
    RecyclerView recyclerView;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        pBar = (ProgressBar) findViewById(R.id.progressBar);
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        etCity = (EditText) findViewById(R.id.etCity);

        sharedPreferences = getSharedPreferences(prefName, Context.MODE_PRIVATE);
        city = sharedPreferences.getString("city", defaultCityName);
        editor = sharedPreferences.edit();
        editor.putString("city",city);
        editor.putString("units","metric");
        editor.commit();


        if(isOnline()) {
            country ="TR";
            requestData();
        } else {
            Toast.makeText(this,"Network is not available", Toast.LENGTH_LONG).show();
        }

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);

        recyclerView.addOnItemTouchListener(
                new RecyclerItemClickListener(this,recyclerView,new RecyclerItemClickListener.OnItemClickListener(){
                    @Override
                    public void onItemClick(View v,int index){
                        Intent i =new Intent(getApplicationContext(),OneDayActivity.class);
                        i.putExtra("cityName",city);
                        DateFormat df = new SimpleDateFormat("dd/MM/yyyy EEE",Locale.US);

                        Date date = (weatherDatas.get(0).getOneDayWeatherData().get(0).getDate());
                        String reportDate = df.format(date);
                        i.putExtra("date",reportDate);
                        i.putParcelableArrayListExtra("OneDayData", (ArrayList<? extends Parcelable>) weatherDatas.get(index).getOneDayWeatherData());
                        startActivity(i);
                    }

                    @Override
                    public void onLongItemClick(View view, int position) {
                        Intent i =new Intent(getApplicationContext(),OneDayActivity.class);
                        i.putParcelableArrayListExtra("OneDayData", (ArrayList<? extends Parcelable>) weatherDatas.get(0).getOneDayWeatherData());
                        startActivity(i);
                    }
                })
        );




        //SharedPreferences.Editor editor = sharedPreferences.edit();
        //editor.putBoolean("isLogged", true);

        setSupportActionBar(toolbar);


    }
    public void btnSendOnClick(View view){
        if (!etCity.getText().toString().equals("")) {
              editor.putString("city",etCity.getText().toString());
                      }
        if(isOnline()) {
            requestData();
        } else {
            Toast.makeText(this,"Network is not available", Toast.LENGTH_LONG).show();
        }
    }
    private void requestData() {
        city=sharedPreferences.getString("city",defaultCityName);
        String units = sharedPreferences.getString("units","metric");
        String url = "http://api.openweathermap.org/data/2.5/forecast?q="+city+"&units="+units+"&appid=82f1efe16c537f07d5b5852d2cba3ab6";
        MyTask task= new MyTask();
        task.execute(url);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Intent settingsIntent = new Intent(getApplicationContext(),SettingsActivity.class);
            startActivity(settingsIntent);
            //Toast.makeText(this,"this is a setting action",Toast.LENGTH_LONG).show();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    protected boolean isOnline(){
        ConnectivityManager cm=(ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo=cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }
    protected  void updateDisplayAfterGet(String res){
        weatherDatas = WeatherJSONParser.parseFeed(res);

        if(weatherDatas == null){
            Toast.makeText(this,"Can not find city with that name",Toast.LENGTH_LONG);
        }else {
            OneDayWeatherAdapter adapter = new OneDayWeatherAdapter(weatherDatas);
            recyclerView.setAdapter(adapter);
            adapter.notifyDataSetChanged();

            Toast.makeText(this,"City: "+ sharedPreferences.getString("city","Nothing Found"), Toast.LENGTH_LONG).show();

        }

    }
    private class MyTask extends AsyncTask<String,String,String> {

        @Override
        protected void onPreExecute() {

            pBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected String doInBackground(String... params) {
            return HTTPManager.getData(params[0]);

        }

        @Override
        protected void onPostExecute(String s) {
            //updateDisplay(s);
            updateDisplayAfterGet(s);
            pBar.setVisibility(View.GONE);

        }


    }
}
