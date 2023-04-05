package com.eng.ashm.myapplication.datamodel.datasource.backend;

import android.util.Log;

import com.eng.ashm.myapplication.MainActivity;
import com.eng.ashm.myapplication.datamodel.data.WeatherData;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 *
 */
public class WeatherBackend {

    private static WeatherBackend weatherBackend = null;
    private List<WeatherData> weatherDataList = null;
    private WeatherApi api = null;

    private WeatherBackend() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https:/api.openweathermap.org/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
         api = retrofit.create(WeatherApi.class);
    }

    public static WeatherBackend getInstance() {

        if (weatherBackend == null) {
            weatherBackend = new WeatherBackend();
        }
        return weatherBackend;
    }

    /**
     * request data from weather api and use the bind method to convert the data loaded into
     * data model format usable by the app
     *
     * @return
     */
    public List<WeatherData> getWeather(String city) throws IOException {

        bindWeatherResponse(city);
        return weatherDataList;
    }

    /**
     * bind api response to the weatherData class model
     *
     * @param city String
     */
    private void bindWeatherResponse(String city) throws IOException {
        Map<String, Object> response = api.getWeatherResponse(city).execute().body();
        List weatherDays = (List) response.get("list");
        ArrayList<WeatherData> weatherDataList = new ArrayList<>();
        String weatherDesc;
        double dt;
        Double  temp, minTemp, maxTemp, humidity, windSpeed, windDeg;
        for (int i = 0; i < weatherDays.size(); i++) {
            dt = (double) ((Map) weatherDays.get(i)).get("dt");
            temp = (Double) ((Map) ((Map) weatherDays.get(i)).get("main")).get("temp");
            maxTemp = (Double) ((Map) ((Map) weatherDays.get(i)).get("main")).get("temp");
            minTemp = (Double) ((Map) ((Map) weatherDays.get(i)).get("main")).get("temp");
            humidity = (Double) ((Map) ((Map) weatherDays.get(i)).get("main")).get("humidity");

            weatherDesc = (String) ((Map) ((List) ((Map) weatherDays.get(i)).get("weather")).get(0)).get("description");
            windSpeed = (double) ((Map) ((Map) weatherDays.get(i)).get("wind")).get("speed");
            windDeg = (double) ((Map) ((Map) weatherDays.get(i)).get("wind")).get("deg");
              String cityName = (String) ((Map)((Map)response.get("city"))).get("name");
//            Log.d(MainActivity.TAG, "bindWeatherResponse: "+cityName);
            cityName = cityName.toLowerCase();
            weatherDataList.add(
                    new WeatherData(dt, temp, minTemp, maxTemp,
                            humidity, weatherDesc, windSpeed, windDeg, cityName)
            );
        }
        this.weatherDataList = weatherDataList;
    }


}
