package com.eng.ashm.myapplication.datamodel.repo;

import static com.eng.ashm.myapplication.MainActivity.TAG;

import android.content.Context;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import com.eng.ashm.myapplication.MainActivity;
import com.eng.ashm.myapplication.datamodel.WResult;
import com.eng.ashm.myapplication.datamodel.data.WeatherData;
import com.eng.ashm.myapplication.datamodel.datasource.backend.WeatherBackend;
import com.eng.ashm.myapplication.datamodel.datasource.cache.WeatherCache;

import java.io.IOException;
import java.util.List;

/**
 * this class is responsible for providing weather data to the presenter either from the api
 * or from the cache
 */
public class WeatherRepo implements IWeatherRepo {

    public static final String CACHED = "cached";
    public static final String NO_CACHE = "no_cached";
    private WeatherBackend weatherBackend;
    private WeatherCache weatherCache;
    private static WeatherRepo instance;
    private AppCompatActivity mActivity;

    private WResult<String> result = null;

    private WeatherRepo(AppCompatActivity mActivity, WResult<String> result) {
        this.mActivity = mActivity;
        this.result = result;
        if (this.mActivity == null) throw new NullPointerException("activity can not be null");
        weatherBackend = WeatherBackend.getInstance();
        weatherCache = Room.databaseBuilder(this.mActivity, WeatherCache.class, "weather_cache").build();
    }

    public static final WeatherRepo getInstance(AppCompatActivity mActivity, WResult<String> result) {

        if (instance == null) instance = new WeatherRepo(mActivity, result);
        return instance;
    }


    /**
     * this method return the weather data either from the cache or from the api
     * if the api provide the data, then the data is returned, else the data is retrieved from
     * the cache, if the cache data is not available then it returns null
     *
     * @param city
     * @return the data from the api or the cache or null
     * @throws IOException
     */
    @Override
    public List<WeatherData> getWeatherData(String city) {
        city = city.toLowerCase().trim();
        List<WeatherData> apiDataList = null;
        List<WeatherData> cacheDataList = null;
        try {
            cacheDataList = loadDataFromCache(city);
            Log.d(TAG, "getWeatherData: data from cache : " + cacheDataList.size());
            apiDataList = loadDataFromApi(city);
        } catch (IOException ex) {
            Log.d(TAG, "getWeatherData: from cache IOException");
            handleResultCache(cacheDataList);
            return cacheDataList;
        }

        if (apiDataList.size() != 0) {
            Log.d(TAG, "getWeatherData: from api");

            addWeatherDataList(apiDataList);
            return apiDataList;
        } else {
            Log.d(TAG, "getWeatherData: from cache");
            handleResultCache(cacheDataList);
            return cacheDataList;
        }
    }

    /**
     *
     */
    private void handleResultCache(List<WeatherData> cacheDataList) {

        mActivity.runOnUiThread(() -> {
            if (result != null) {
                if (cacheDataList.size() > 0)
                    result.onResult(CACHED, 25);
                else
                    result.onResult(NO_CACHE, 25);
            }
        });
    }

    private List<WeatherData> loadDataFromApi(String city) throws IOException {
        return weatherBackend.getWeather(city);
    }

    private List<WeatherData> loadDataFromCache(String city) {
        List<WeatherData> res = weatherCache.getWeatherDao().getAllWeatherList(city);
        Log.d(TAG, "loadDataFromCache: res : " + res.size());
        return res;
    }

    private void addWeatherDataList(List<WeatherData> weatherDataList) {

        weatherCache.getWeatherDao().deleteAllWeather();
        List<Long> result = weatherCache.getWeatherDao().addAllWeatherData(weatherDataList);
        for (int i = 0; i < result.size(); i++) {

            Log.d(TAG, "addWeatherDataList: result : " + result.get(i));
        }

    }
}
