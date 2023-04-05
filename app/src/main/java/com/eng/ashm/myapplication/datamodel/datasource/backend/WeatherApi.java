package com.eng.ashm.myapplication.datamodel.datasource.backend;

import com.eng.ashm.myapplication.datamodel.data.CityData;
import java.util.Map;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 *
 */
public interface WeatherApi {

    String API_KEY = "cbeb1666dcc96c6fe25853eaec8690e7";

    @GET("data/2.5/forecast?appid=" + API_KEY)
    Call<Map<String, Object>> getWeatherResponse(@Query("q") String city);
    @GET
    Call<CityData> getCityDate();
}
