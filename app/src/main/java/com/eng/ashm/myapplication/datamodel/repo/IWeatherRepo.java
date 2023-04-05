package com.eng.ashm.myapplication.datamodel.repo;

import com.eng.ashm.myapplication.datamodel.data.WeatherData;

import java.io.IOException;
import java.util.List;

public interface IWeatherRepo {

    List<WeatherData> getWeatherData(String city) throws IOException;


}
