package com.eng.ashm.myapplication.datamodel.datasource.cache;


import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.eng.ashm.myapplication.datamodel.data.WeatherData;

import java.util.List;

@Dao
public interface WeatherDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    List<Long> addAllWeatherData(List<WeatherData> weatherList);

    @Query("select * from weatherdata where cityName like :city")
     List<WeatherData> getAllWeatherList(String city);

    @Query("delete from weatherdata")
    void  deleteAllWeather();
}
