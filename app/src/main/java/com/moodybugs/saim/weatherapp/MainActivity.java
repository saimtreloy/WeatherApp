package com.moodybugs.saim.weatherapp;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.transition.Fade;
import android.transition.Slide;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DateFormatSymbols;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    // For JSON  element
    String JSON_URL = null, FULL_JSON_DATA = null;
    int COUNT = 0;
    String city_name, city_country;
    String weather_description, weather_main, weather_main_day1, weather_main_day2, weather_main_day3, weather_icon, weather_icon_day1, weather_icon_day2, weather_icon_day3;
    String date_today;
    double temp_day, temp_day1, temp_day2, temp_day3, temp_min, temp_min1, temp_min2, temp_min3;
    String weekday, weekday1, weekday2, weekday3;

    // XML elements
    TextView txtCityName, txtTempMain, txtTempLowest, txtWeatherDescription, txtDate, txtDay1, txtDay2, txtDay3, txtTemp1, txtTemp2, txtTemp3;
    ImageButton btnNavigationMenu, btnSearchCity, btnSearch;
    ImageView iconWeather, iconWeather1, iconWeather2, iconWeather3;
    EditText inputCityName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.AppThemeNew);
        setContentView(R.layout.activity_main);
        SetupWindowAnimations();
        Initialization();
        try {
            FULL_JSON_DATA = new BackgroundTaskNew().execute().get();
            //Toast.makeText(getApplicationContext(), FULL_JSON_DATA, Toast.LENGTH_SHORT).show();
            RetriveJsonData(FULL_JSON_DATA);
            SetValueFromJSON();
            ButtonEvent();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void SetupWindowAnimations() {

        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Fade fade = new Fade();
            fade.setDuration(1000);
            getWindow().setEnterTransition(fade);

            Slide slide = new Slide();
            slide.setDuration(1000);
            getWindow().setReturnTransition(fade);
        }
    }


    public void Initialization(){
        txtCityName = (TextView) findViewById(R.id.txtCityName);
        txtTempMain = (TextView) findViewById(R.id.txtTempMain);
        txtTempLowest = (TextView) findViewById(R.id.txtTempLowest);
        txtWeatherDescription = (TextView) findViewById(R.id.txtWeatherDescription);
        txtDate = (TextView) findViewById(R.id.txtDate);
        txtDay1 = (TextView) findViewById(R.id.txtDay1);
        txtDay2 = (TextView) findViewById(R.id.txtDay2);
        txtDay3 = (TextView) findViewById(R.id.txtDay3);
        txtTemp1 = (TextView) findViewById(R.id.txtTemp1);
        txtTemp2 = (TextView) findViewById(R.id.txtTemp2);
        txtTemp3 = (TextView) findViewById(R.id.txtTemp3);
        txtCityName = (TextView) findViewById(R.id.txtCityName);

        btnNavigationMenu = (ImageButton) findViewById(R.id.btnNavigationMenu);
        btnSearchCity = (ImageButton) findViewById(R.id.btnSearchCity);
        btnSearch =(ImageButton) findViewById(R.id.btnSearch);

        iconWeather = (ImageView) findViewById(R.id.iconWeather);
        iconWeather1 = (ImageView) findViewById(R.id.iconWeather1);
        iconWeather2 = (ImageView) findViewById(R.id.iconWeather2);
        iconWeather3 = (ImageView) findViewById(R.id.iconWeather3);

        inputCityName = (EditText) findViewById(R.id.inputCityName);

    }


    // Extra Class for getting JSON value
    public class BackgroundTaskNew extends AsyncTask<Void, Void, String> {

        String JSON_STRING, json_url2;

        @Override
        protected void onPreExecute() {
            if (COUNT == 0){
                json_url2 = "http://api.openweathermap.org/data/2.5/forecast/daily?q=London&mode=json&units=metric&cnt=4&appid=7110f50c4fc5038d638ed407ef0074ae";
            }else if(COUNT == 1){
                json_url2 = JSON_URL;
            }

        }

        @Override
        protected String doInBackground(Void... params) {
            try {
                URL url = new URL(json_url2);
                HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();
                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                StringBuilder stringBuilder = new StringBuilder();
                while ((JSON_STRING = bufferedReader.readLine()) != null){
                    stringBuilder.append(JSON_STRING + "\n");
                }
                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();
                return stringBuilder.toString().trim();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
        }
    }


    //Retrive JSON Data
    public void RetriveJsonData(String full_json_data) throws JSONException {

        JSONObject jsonObject = new JSONObject(full_json_data);
        JSONObject jsonObjectCity = jsonObject.getJSONObject("city");
        city_name = jsonObjectCity.getString("name");
        city_country = jsonObjectCity.getString("country");

        JSONArray jsonArray = jsonObject.getJSONArray("list");

        JSONObject jsonObjectList = jsonArray.getJSONObject(0);
        JSONObject jsonObjectTemp = jsonObjectList.getJSONObject("temp");
        temp_day = jsonObjectTemp.getDouble("day");
        temp_min = jsonObjectTemp.getDouble("min");
        JSONArray jsonArrayWeather = jsonObjectList.getJSONArray("weather");
        JSONObject jsonObjectWeather = jsonArrayWeather.getJSONObject(0);
        weather_icon = jsonObjectWeather.getString("icon");
        weather_description = jsonObjectWeather.getString("description");


        JSONObject jsonObjectList1 = jsonArray.getJSONObject(1);
        JSONObject jsonObjectTemp1 = jsonObjectList1.getJSONObject("temp");
        temp_day1 = jsonObjectTemp1.getDouble("day");
        temp_min1 = jsonObjectTemp1.getDouble("min");
        JSONArray jsonArrayWeather1 = jsonObjectList1.getJSONArray("weather");
        JSONObject jsonObjectWeather1 = jsonArrayWeather1.getJSONObject(0);
        weather_icon_day1 = jsonObjectWeather1.getString("icon");

        JSONObject jsonObjectList2 = jsonArray.getJSONObject(2);
        JSONObject jsonObjectTemp2 = jsonObjectList2.getJSONObject("temp");
        temp_day2 = jsonObjectTemp2.getDouble("day");
        temp_min2 = jsonObjectTemp2.getDouble("min");
        JSONArray jsonArrayWeather2 = jsonObjectList2.getJSONArray("weather");
        JSONObject jsonObjectWeather2 = jsonArrayWeather2.getJSONObject(0);
        weather_icon_day2 = jsonObjectWeather2.getString("icon");

        JSONObject jsonObjectList3 = jsonArray.getJSONObject(3);
        JSONObject jsonObjectTemp3 = jsonObjectList3.getJSONObject("temp");
        temp_day3 = jsonObjectTemp3.getDouble("day");
        temp_min3 = jsonObjectTemp3.getDouble("min");
        JSONArray jsonArrayWeather3 = jsonObjectList3.getJSONArray("weather");
        JSONObject jsonObjectWeather3 = jsonArrayWeather3.getJSONObject(0);
        weather_icon_day3 = jsonObjectWeather3.getString("icon");
    }

    //Setting Value from JSON
    public void SetValueFromJSON(){
        txtCityName.setText(city_name);
        txtTempMain.setText(Math.round(temp_day) + " \u2103");
        txtTempLowest.setText(Math.round(temp_min) + " \u2103");
        txtWeatherDescription.setText(weather_description);

        int idWeatherIcon = getResources().getIdentifier("ic_" + weather_icon, "drawable", getPackageName());
        int idWeatherIcon1 = getResources().getIdentifier("ic_" + weather_icon_day1, "drawable", getPackageName());
        int idWeatherIcon2 = getResources().getIdentifier("ic_" + weather_icon_day2, "drawable", getPackageName());
        int idWeatherIcon3 = getResources().getIdentifier("ic_" + weather_icon_day3, "drawable", getPackageName());
        iconWeather.setImageResource(idWeatherIcon);
        iconWeather1.setImageResource(idWeatherIcon1);
        iconWeather2.setImageResource(idWeatherIcon2);
        iconWeather3.setImageResource(idWeatherIcon3);

        txtTemp1.setText(Math.round(temp_day1) + " \u2103");
        txtTemp2.setText(Math.round(temp_day2) + " \u2103");
        txtTemp3.setText(Math.round(temp_day3) + " \u2103");


        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());

        DateFormatSymbols dfs = new DateFormatSymbols();
        String[] months = dfs.getMonths();
        String month_current = months[calendar.get(Calendar.MONTH)];
        int day_current = calendar.get(Calendar.DAY_OF_MONTH);

        SimpleDateFormat dayFormat = new SimpleDateFormat("EEEE", Locale.US);
        weekday = dayFormat.format(calendar.getTime());

        if (weekday.toLowerCase().equals("friday")){
            weekday1 = "saturday";
            weekday2 = "sunday";
            weekday3 = "monday";
        } else if (weekday.toLowerCase().equals("saturday")){
            weekday1 = "sunday";
            weekday2 = "monday";
            weekday3 = "tuesday";
        } else if (weekday.toLowerCase().equals("sunday")){
            weekday1 = "monday";
            weekday2 = "tuesday";
            weekday3 = "wednesday";
        } else if (weekday.toLowerCase().equals("monday")){
            weekday1 = "tuesday";
            weekday2 = "wednesday";
            weekday3 = "thursday";
        } else if (weekday.toLowerCase().equals("tuesday")){
            weekday1 = "wednesday";
            weekday2 = "thursday";
            weekday3 = "friday";
        } else if (weekday.toLowerCase().equals("wednesday")){
            weekday1 = "thursday";
            weekday2 = "friday";
            weekday3 = "saturday";
        } else if (weekday.toLowerCase().equals("thursday")){
            weekday1 = "friday";
            weekday2 = "saturday";
            weekday3 = "sunday";
        }

        txtDate.setText(weekday + ", " + month_current + " " + day_current);
        txtDay1.setText(weekday1.substring(0,3));
        txtDay2.setText(weekday2.substring(0,3));
        txtDay3.setText(weekday3.substring(0,3));

    }


    public void ButtonEvent (){
        btnSearchCity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                txtCityName.setVisibility(View.GONE);
                btnSearchCity.setVisibility(View.GONE);
                inputCityName.setVisibility(View.VISIBLE);
                btnSearch.setVisibility(View.VISIBLE);
            }
        });


        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                COUNT = 1;
                String url_2nd;
                if (inputCityName.getText().toString().isEmpty()) {
                    inputCityName.setVisibility(View.GONE);
                    btnSearch.setVisibility(View.GONE);
                    txtCityName.setVisibility(View.VISIBLE);
                    btnSearchCity.setVisibility(View.VISIBLE);
                    Toast.makeText(MainActivity.this, "City could not be empty!", Toast.LENGTH_SHORT).show();
                } else {
                    inputCityName.setVisibility(View.GONE);
                    btnSearch.setVisibility(View.GONE);
                    txtCityName.setVisibility(View.VISIBLE);
                    btnSearchCity.setVisibility(View.VISIBLE);
                    url_2nd = inputCityName.getText().toString();
                    JSON_URL = "http://api.openweathermap.org/data/2.5/forecast/daily?q=" + url_2nd + "&mode=json&units=metric&cnt=4&appid=7110f50c4fc5038d638ed407ef0074ae";
                    try {
                        FULL_JSON_DATA = new BackgroundTaskNew().execute().get();
                        RetriveJsonData(FULL_JSON_DATA);
                        SetValueFromJSON();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }
            }
        });

        btnNavigationMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActivityOptions options = null;
                Intent intent = new Intent(MainActivity.this, Detail.class);
                intent.putExtra("full_json_data", FULL_JSON_DATA);
                intent.putExtra("weekday", txtDate.getText().toString());

                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
                    View sharedView = iconWeather;
                    options = ActivityOptions.makeSceneTransitionAnimation(MainActivity.this, sharedView, getString(R.string.icon_transition));
                    startActivity(intent, options.toBundle());
                }else{
                    startActivity(intent);
                }
            }
        });
    }

}
