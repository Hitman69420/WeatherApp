package com.example.weather;

import org.json.JSONException;
import org.json.JSONObject;

public class weather_data {
    private String mlocation, micon, mtemperature,mstatus;
    private int mcondition;

    public static weather_data fromJson(JSONObject jsonObject){

        try
        {
            weather_data weatherData=new weather_data();
            weatherData.mlocation=jsonObject.getString("name");
            weatherData.mcondition=jsonObject.getJSONArray("weather").getJSONObject(0).getInt("id");
            weatherData.mstatus=jsonObject.getJSONArray("weather").getJSONObject(0).getString("main");
            weatherData.micon=updateWeatherIcon(weatherData.mcondition);
            double tempResult=jsonObject.getJSONObject("main").getDouble("temp")-273.15;
            double minTemp=jsonObject.getJSONObject("main").getDouble("temp_min")-273.15;
            int roundedValue=(int)Math.rint(tempResult);
            weatherData.mtemperature=Integer.toString(roundedValue);
            return weatherData;
        }

        catch (JSONException e)
        {
            e.printStackTrace();
            return null;
        }


    }

    private static String updateWeatherIcon(int condition){
        if(condition>=0 && condition<=300){
            return "stormy";

        }
        else if(condition>=300 && condition<=500){
            return  "lightrain";

        }
        else if(condition>=500 && condition<=600){
            return  "shower";

        }
        else if(condition>=600 && condition<=700){
            return  "snowy";

        }
        else if(condition>=700 && condition<=771){
            return  "fog";

        }
        else if(condition>=772 && condition<=800){
            return  "partly_cloudy";

        }
        else if(condition==800){
            return  "sunny";

        }
        else if(condition>=801 && condition<=804){
            return  "partly_cloudy";

        }
        else if(condition>=900 && condition<=902){
            return  "stormy";

        }
        else if(condition==903){
            return  "snow";

        }
        else if(condition==904){
            return  "sunny";

        }
        else if(condition>=905 && condition<=1000){
            return  "stormy";

        }
        return "hello";
    }

    public String getMlocation() {
        return mlocation;
    }

    public String getMicon() {
        return micon;
    }

    public String getMtemperature() {
        return mtemperature;
    }

    public String getMstatus() {
        return mstatus;
    }

    public int getMcondition() {
        return mcondition;
    }
}
