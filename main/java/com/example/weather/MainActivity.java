package com.example.weather;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.location.LocationListenerCompat;

import android.Manifest;
import android.app.DownloadManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;
import android.util.Log;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.text.DecimalFormat;

import cz.msebera.android.httpclient.Header;

public class MainActivity extends AppCompatActivity {

    final String APP_ID = "5d32bb3f6dce89f78616f1ae45fccd7a";
    final String WEATHER_URL = "https://api.openweathermap.org/data/2.5/weather";

    final long MIN_TIME = 5000;
    final float MIN_DISTANCE = 1000;
    final int REQUEST_CODE = 101;

    String location_provider = LocationManager.GPS_PROVIDER;

    TextView name_of_location, updated_at,temperature,status,min_temp,max_temp,sunset,sunrise,wind,humidity,m_city_finder;
    ImageView weather_icon, search_icon;
    LocationManager mlocation_manager;
    LocationListener mlocation_listener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        temperature = findViewById(R.id.temp);
        name_of_location = findViewById(R.id.address);
        updated_at = findViewById(R.id.updated_at);
        status = findViewById(R.id.status);
        min_temp = findViewById(R.id.temp_min);
        max_temp = findViewById(R.id.temp_max);
        sunset = findViewById(R.id.sunset);
        sunrise = findViewById(R.id.sunrise);
        wind = findViewById(R.id.wind);
        humidity = findViewById(R.id.humidity);
        m_city_finder = findViewById(R.id.search);
        search_icon = findViewById(R.id.search_icon);
        weather_icon = findViewById(R.id.weather_icon);


        m_city_finder.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, city_search.class);
            startActivity(intent);
        });

        search_icon.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, city_search.class);
            startActivity(intent);
        });

    }



    @Override
    protected void onResume() {
        super.onResume();
        getweatherforcurrentlocation();
    }


//    @Override
//    protected void onResume() {
//        super.onResume();
//        Intent mIntent=getIntent();
//        String city=mIntent.getStringExtra("City");
//
//        if (city!=null){
//            getweatherforNewcity(city);
//        }
//        else {
//            getweatherforcurrentlocation();
//        }
//
//
//
//    }
//
//    private void getweatherforNewcity(String city){
//        RequestParams params=new RequestParams();
//        params.put("q=",city);
//        params.put("&appid=",APP_ID);
//        lets_doSomeNetworking(params);
//
//    }







    private void getweatherforcurrentlocation() {

        String tempUrl = "";
        String city = search_city.getText().toString().trim();
        String country = etCountry.getText().toString().trim();
        if(city.equals("")){
            tvResult.setText("City field can not be empty!");
        }else{
            if(!country.equals("")){
                tempUrl = url + "?q=" + city + "," + country + "&appid=" + appid;
            }else{
                tempUrl = url + "?q=" + city + "&appid=" + appid;
            }
            StringRequest stringRequest = new StringRequest(Request.Method.POST, tempUrl, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    String output = "";
                    try {
                        JSONObject jsonResponse = new JSONObject(response);
                        JSONArray jsonArray = jsonResponse.getJSONArray("weather");
                        JSONObject jsonObjectWeather = jsonArray.getJSONObject(0);
                        String description = jsonObjectWeather.getString("description");
                        JSONObject jsonObjectMain = jsonResponse.getJSONObject("main");
                        double temp = jsonObjectMain.getDouble("temp") - 273.15;
                        double feelsLike = jsonObjectMain.getDouble("feels_like") - 273.15;
                        float pressure = jsonObjectMain.getInt("pressure");
                        int humidity = jsonObjectMain.getInt("humidity");
                        JSONObject jsonObjectWind = jsonResponse.getJSONObject("wind");
                        String wind = jsonObjectWind.getString("speed");
                        JSONObject jsonObjectClouds = jsonResponse.getJSONObject("clouds");
                        String clouds = jsonObjectClouds.getString("all");
                        JSONObject jsonObjectSys = jsonResponse.getJSONObject("sys");
                        String countryName = jsonObjectSys.getString("country");
                        String cityName = jsonResponse.getString("name");
                        tvResult.setTextColor(Color.rgb(68,134,199));
                        output += "Current weather of " + cityName + " (" + countryName + ")"
                                + "\n Temp: " + df.format(temp) + " °C"
                                + "\n Feels Like: " + df.format(feelsLike) + " °C"
                                + "\n Humidity: " + humidity + "%"
                                + "\n Description: " + description
                                + "\n Wind Speed: " + wind + "m/s (meters per second)"
                                + "\n Cloudiness: " + clouds + "%"
                                + "\n Pressure: " + pressure + " hPa";
                        tvResult.setText(output);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }, new Response.ErrorListener(){

                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(getApplicationContext(), error.toString().trim(), Toast.LENGTH_SHORT).show();
                }
            });
            RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
            requestQueue.add(stringRequest);
        }
    }
}




            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {


            }

            @Override
            public void onProviderEnabled(String provider) {


            }

            @Override
            public void onProviderDisabled(String provider) {
                    Toast.makeText(MainActivity.this,"Error",Toast.LENGTH_SHORT).show();

            }


        };

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.ACCESS_FINE_LOCATION},REQUEST_CODE);
            return;
        }
        mlocation_manager.requestLocationUpdates(location_provider, MIN_TIME, MIN_DISTANCE, mlocation_listener);



    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode==REQUEST_CODE){
            if (grantResults.length>0&& grantResults[0]==PackageManager.PERMISSION_GRANTED){
                getweatherforcurrentlocation();
                Toast.makeText(MainActivity.this,"Location Granted",Toast.LENGTH_SHORT).show();
            }
            else {
                Toast.makeText(MainActivity.this,"Please Grant location access!",Toast.LENGTH_SHORT).show();
            }
        }

    }






    private void updateUI(weather_data weather){
        temperature.setText(weather.getMtemperature());
        name_of_location.setText(weather.getMlocation());
        status.setText(weather.getMstatus());
        int resourceID=getResources().getIdentifier(weather.getMicon(),"drawable",getPackageName());
        weather_icon.setImageResource(resourceID);
    }

    @Override
    protected void onPause() {
        super.onPause();
        if(mlocation_manager!=null){

            mlocation_manager.removeUpdates(mlocation_listener);
        }
    }
}
















