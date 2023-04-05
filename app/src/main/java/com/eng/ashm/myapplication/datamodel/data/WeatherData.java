package com.eng.ashm.myapplication.datamodel.data;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Date;

/**
 * DataModel for weather response object
 */
@Entity
public class WeatherData {
    @PrimaryKey
    @NonNull
    private String id = String.valueOf(Math.ceil(Math.random() * 1000000000));
    private double date;
    private double temp;
    private double minTemp;
    private double maxTemp;
    private double humidity;
    private String weatherDesc;
    private double windSpeed;
    private double windDeg;

    private String cityName = "Cairo";

    public WeatherData(double date, double temp, double minTemp, double maxTemp,
                       double humidity, String weatherDesc, double windSpeed, double windDeg, String cityName) {
        this.date = date;
        this.temp = temp;
        this.minTemp = minTemp;
        this.maxTemp = maxTemp;
        this.humidity = humidity;
        this.weatherDesc = weatherDesc;
        this.windSpeed = windSpeed;
        this.windDeg = windDeg;
        this.cityName = cityName;
    }

    public String getCityName() {
        return cityName;
    }

    public void setId(String id){
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public double getDate() {
        return date;
    }

    public double getTemp() {
        return temp;
    }

    public double getMinTemp() {
        return minTemp;
    }

    public double getMaxTemp() {
        return maxTemp;
    }

    public double getHumidity() {
        return humidity;
    }

    public String getWeatherDesc() {
        return weatherDesc;
    }

    public double getWindSpeed() {
        return windSpeed;
    }

    public double getWindDeg() {
        return windDeg;
    }
}
