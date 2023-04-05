package com.eng.ashm.myapplication.datamodel.datasource.cache;


import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.eng.ashm.myapplication.datamodel.data.CityData;
import com.eng.ashm.myapplication.datamodel.data.WeatherData;

@Database(entities = {WeatherData.class, CityData.class}, version = 1)
public abstract class WeatherCache extends RoomDatabase {

    public abstract WeatherDao getWeatherDao();
    public abstract CityDao getCityDao();

}
