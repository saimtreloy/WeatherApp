package com.moodybugs.saim.weatherapp;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.transition.Fade;
import android.transition.Slide;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Detail extends AppCompatActivity {

    // For JSON  element
    String FULL_JSON_DATA = null, ACTIVITY_DATE;
    String city_name, city_country, weather_main, weather_description, weather_icon;
    double pressure, humidity, speed, clouds, rain, temp_day, temp_min, temp_max;

    // XML elements
    TextView txtCityNameDetail, txtWeatherMain, txtWeatherDescription, txtDate, txtTempMain, txtTempMin, txtTempMax, txtPressure, txtHumidity,txtWind, txtCloud, txtRain;
    ImageView iconWeatherDetail;
    ImageButton btnNavigationHome, btnDeveloper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.AppThemeNew);
        setContentView(R.layout.activity_detail);
        SetupWindowAnimations();

        Initialization();
        ButtonEvent();
        try {
            RetriveJsonData(FULL_JSON_DATA);
            SetValueFromJSON();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void SetupWindowAnimations() {

        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Slide slide = new Slide();
            slide.setDuration(1000);


            Fade fade = new Fade();
            fade.setDuration(1000);
            getWindow().setEnterTransition(fade);
            getWindow().setExitTransition(fade);
        }
    }

    public void Initialization(){
        FULL_JSON_DATA = getIntent().getStringExtra("full_json_data");
        ACTIVITY_DATE = getIntent().getStringExtra("weekday");

        txtCityNameDetail = (TextView) findViewById(R.id.txtCityNameDetail);
        txtWeatherMain = (TextView) findViewById(R.id.txtWeatherMain);
        txtWeatherDescription = (TextView) findViewById(R.id.txtWeatherDescription);
        txtDate = (TextView) findViewById(R.id.txtDate);
        txtTempMain = (TextView) findViewById(R.id.txtTempMain);
        txtTempMin = (TextView) findViewById(R.id.txtTempMin);
        txtTempMax = (TextView) findViewById(R.id.txtTempMax);
        txtPressure = (TextView) findViewById(R.id.txtPressure);
        txtHumidity = (TextView) findViewById(R.id.txtHumidity);
        txtWind = (TextView) findViewById(R.id.txtWind);
        txtCloud = (TextView) findViewById(R.id.txtCloud);
        txtRain = (TextView) findViewById(R.id.txtRain);

        iconWeatherDetail = (ImageView) findViewById(R.id.iconWeatherDetail);

        btnNavigationHome = (ImageButton) findViewById(R.id.btnNavigationHome);
        btnDeveloper = (ImageButton) findViewById(R.id.btnDeveloper);
    }

    public void ButtonEvent (){
        btnNavigationHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        btnDeveloper.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActivityOptions options = null;
                Intent intent = new Intent(Detail.this, Developer.class);
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
                    options = ActivityOptions.makeSceneTransitionAnimation(Detail.this);
                    startActivity(intent, options.toBundle());
                }else{
                    startActivity(intent);
                }
            }
        });
    }

    public void RetriveJsonData(String full_json_data) throws JSONException {
        JSONObject jsonObject = new JSONObject(full_json_data);
        JSONObject jsonObjectCity = jsonObject.getJSONObject("city");
        city_name = jsonObjectCity.getString("name");
        city_country = jsonObjectCity.getString("country");

        JSONArray jsonArray = jsonObject.getJSONArray("list");

        JSONObject jsonObjectList = jsonArray.getJSONObject(0);
        pressure = jsonObjectList.getDouble("pressure");
        humidity = jsonObjectList.getDouble("humidity");
        speed = jsonObjectList.getDouble("speed");
        clouds = jsonObjectList.getDouble("clouds");
        try {
            rain = jsonObjectList.getDouble("rain");
        } catch (Exception e){

        }

        JSONObject jsonObjectTemp = jsonObjectList.getJSONObject("temp");
        temp_day = jsonObjectTemp.getDouble("day");
        temp_min = jsonObjectTemp.getDouble("min");
        temp_max = jsonObjectTemp.getDouble("max");
        JSONArray jsonArrayWeather = jsonObjectList.getJSONArray("weather");
        JSONObject jsonObjectWeather = jsonArrayWeather.getJSONObject(0);
        weather_icon = jsonObjectWeather.getString("icon");
        weather_description = jsonObjectWeather.getString("description");
        weather_main = jsonObjectWeather.getString("main");
    }

    public void SetValueFromJSON(){
        txtCityNameDetail.setText(city_name);
        txtWeatherMain.setText(weather_main);
        txtWeatherDescription.setText(weather_description);

        txtDate.setText(ACTIVITY_DATE);

        txtTempMain.setText(Math.round(temp_day) + " \u2103");
        txtTempMin.setText(Math.round(temp_min) + " \u2103");
        txtTempMax.setText(Math.round(temp_max) + " \u2103");

        txtPressure.setText(Math.round(pressure) + "");
        txtHumidity.setText(Math.round(humidity) + "%");
        txtWind.setText(Math.round(speed) + "m/s");
        txtCloud.setText(Math.round(clouds) + "%");
        txtRain.setText(rain + "");

        int idWeatherIcon = getResources().getIdentifier("ic_" + weather_icon, "drawable", getPackageName());
        iconWeatherDetail.setImageResource(idWeatherIcon);

    }
}
