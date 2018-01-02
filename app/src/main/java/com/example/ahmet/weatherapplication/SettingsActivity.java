package com.example.ahmet.weatherapplication;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

public class SettingsActivity extends AppCompatActivity {

    EditText etFavCity;
    RadioGroup radioGroup;
    RadioButton rbDegree,rbKelvin;
    Button saveButton;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings_layout);

        sharedPreferences = getSharedPreferences(MainActivity.prefName, Context.MODE_PRIVATE);


        etFavCity= (EditText) findViewById(R.id.etFavCity);
        etFavCity.setText(sharedPreferences.getString("city",MainActivity.defaultCityName));

        radioGroup = (RadioGroup) findViewById(R.id.rGroup);
        rbDegree = (RadioButton) findViewById(R.id.rbDegree);
        rbKelvin = (RadioButton) findViewById(R.id.rbKelvin);

        saveButton = (Button) findViewById(R.id.btnSettingsSave);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editor = sharedPreferences.edit();
                editor.putString("city", etFavCity.getText().toString());
                if (radioGroup.getCheckedRadioButtonId() == R.id.rbDegree){
                    editor.putString("units","Metric");
                }else{
                    editor.putString("units","Default");
                }
                editor.commit();
                Toast.makeText(getBaseContext(),"Settings saved.. Affected on Next Start",Toast.LENGTH_LONG).show();

            }
        });

    }
}
