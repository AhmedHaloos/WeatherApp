package com.eng.ashm.myapplication.datamodel.datasource.cache;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.eng.ashm.myapplication.datamodel.data.CityData;

import java.util.List;

@Dao
public interface CityDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public void addAllCity(List<CityData> cities);

    @Query("select * from CityData")
    public List<CityData> getAllCityData();
}
