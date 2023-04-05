package com.eng.ashm.myapplication;

import static com.eng.ashm.myapplication.datamodel.repo.WeatherRepo.CACHED;
import static com.eng.ashm.myapplication.datamodel.repo.WeatherRepo.NO_CACHE;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;

import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;


import com.eng.ashm.myapplication.databinding.ActivityMainBinding;
import com.eng.ashm.myapplication.datamodel.WResult;
import com.eng.ashm.myapplication.datamodel.data.WeatherData;
import com.eng.ashm.myapplication.datamodel.repo.WeatherRepo;
import com.eng.ashm.myapplication.view.WeatherAdapter;

import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observable;

import io.reactivex.rxjava3.core.ObservableOnSubscribe;
import io.reactivex.rxjava3.core.Observer;

import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;



public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    public static final String TAG = "weatherAppDebug";

    private ActivityMainBinding activityMainBinding;

    private WeatherAdapter weatherAdapter;
    private WeatherRepo weatherRepo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        activityMainBinding = ActivityMainBinding.inflate(getLayoutInflater());

        setContentView(activityMainBinding.getRoot());
        //recyclerview
        weatherAdapter = new WeatherAdapter();
        activityMainBinding.weatherRv.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        activityMainBinding.weatherRv.setAdapter(weatherAdapter);
        //init repo
        weatherRepo = WeatherRepo.getInstance(this, result);
        //retry
        activityMainBinding.retry.setOnClickListener(this);

    }


    /**
     * result object for handling the cache result form WeatherRepo repository
     */
    WResult<String> result = new WResult<String>() {
        @Override
        public void onResult(String res, int code) {
            if (code == 25){
                if (res.equals(CACHED)){
                    showError(false);
                    activityMainBinding.offlineTxt.setVisibility(View.VISIBLE);

                }
                else if (res.equals(NO_CACHE)){
                    showError(true);
                }
            }
        }
    };


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu, menu);
        MenuItem menuItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) menuItem.getActionView();
        searchView.setQueryHint("search city");
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String city) {
                Log.d(TAG, "onQueryTextSubmit: clicked");

                getWeather(city);
                return true;
            }
            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
        return true;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){

            case R.id.retry:
                weatherAdapter.clearWeatherList();
                showError(false);
                activityMainBinding.offlineTxt.setVisibility(View.GONE);
                break;

        }
    }


    /**
     * this method use the rxjava observable for loading data from repository
     * @param city city to lookup the weather on it
     */
    private void getWeather(String city) {

        //observable
        Observable<List<WeatherData>> observable = Observable.create((ObservableOnSubscribe<List<WeatherData>>)
                        emitter -> {

                           List<WeatherData> weatherDataList =  weatherRepo.getWeatherData(city);
                            Log.d(TAG, "getWeather: data from repo : "+weatherDataList.size());
                            emitter.onNext(weatherDataList);
                        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());

        //observer
        Observer<List<WeatherData>> weatherObserver = new Observer<List<WeatherData>>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {
            }

            @Override
            public void onNext(@NonNull List<WeatherData> weatherData) {

                if (weatherData.size() > 0)
                {
                weatherAdapter.updateWeatherList(weatherData);
                }
                else {
                  showError(true);
                }
            }

            @Override
            public void onError(@NonNull Throwable e) {
                Log.d(TAG, "onError: " + e.getMessage());

            }

            @Override
            public void onComplete() {
                Log.d(TAG, "onComplete: Data is available");
            }
        };

        observable.subscribe(weatherObserver);
    }

    private void showError(boolean isError){
        if (isError){
            activityMainBinding.weatherRv.setVisibility(View.GONE);
            activityMainBinding.errorLayout.setVisibility(View.VISIBLE);
        }
        else {

            activityMainBinding.errorLayout.setVisibility(View.GONE);
            activityMainBinding.weatherRv.setVisibility(View.VISIBLE);
        }

    }

    private void showProgress(boolean showProgress){

        if(showProgress){
            activityMainBinding.weatherRv.setVisibility(View.GONE);
            activityMainBinding.progressBar.setVisibility(View.VISIBLE);
        }
        else {
            activityMainBinding.progressBar.setVisibility(View.GONE);
            activityMainBinding.weatherRv.setVisibility(View.VISIBLE);
        }

    }
}
