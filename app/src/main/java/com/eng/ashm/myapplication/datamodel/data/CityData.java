package com.eng.ashm.myapplication.datamodel.data;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Date;

@Entity
public class CityData {

    @PrimaryKey
    @NonNull
    private String id = new Date().toString();
    private String cityName;
    private String lat;
    private String lon;
    private String county;

    public CityData(String cityName, String lat, String lon, String county) {
        this.cityName = cityName;
        this.lat = lat;
        this.lon = lon;
        this.county = county;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getLon() {
        return lon;
    }

    public void setLon(String lon) {
        this.lon = lon;
    }

    public String getCounty() {
        return county;
    }

    public void setCounty(String county) {
        this.county = county;
    }
}
