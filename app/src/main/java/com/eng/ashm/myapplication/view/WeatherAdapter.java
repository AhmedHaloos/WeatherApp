package com.eng.ashm.myapplication.view;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.eng.ashm.myapplication.databinding.WeatherItemBinding;
import com.eng.ashm.myapplication.datamodel.data.WeatherData;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class WeatherAdapter extends RecyclerView.Adapter<WeatherVH> {

    List<WeatherData> weatherList;

    public WeatherAdapter(){
        weatherList = new ArrayList<>();
    }
    @NonNull
    @Override
    public WeatherVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = WeatherItemBinding.inflate(inflater, parent, false).getRoot();
        return new WeatherVH(view);
    }

    @Override
    public void onBindViewHolder(@NonNull WeatherVH holder, int position) {
        long dt = (long) weatherList.get(position).getDate();
        String temp = String.format("%.0f", weatherList.get(position).getTemp() - 272.15);
        String minTemp = String.format("%.2f", weatherList.get(position).getMinTemp() - 273);
        String maxTemp = String.format("%.2f",weatherList.get(position).getMaxTemp() - 273);

        holder.weatherItemBinding.date.setText(getDateFormat(dt));
        holder.weatherItemBinding.temp.setText((temp+" C"));
        holder.weatherItemBinding.minTempVal.setText(minTemp + " C");
        holder.weatherItemBinding.maxTempVal.setText(maxTemp + " C");
        holder.weatherItemBinding.humidityVal.setText(String.valueOf(weatherList.get(position).getHumidity()) + " %");
        holder.weatherItemBinding.wSpeedVal.setText(String.valueOf(weatherList.get(position).getWindSpeed()) + " m/sec");
        holder.weatherItemBinding.wDegVal.setText(String.valueOf(weatherList.get(position).getWindDeg()) + " deg");
        holder.weatherItemBinding.weatherDescVal.setText(String.valueOf(weatherList.get(position).getWeatherDesc()));
    }
    private String getDateFormat(long dt){

        Date date = new Date(dt);
        return date.toLocaleString();

    }

    @Override
    public int getItemCount() {
        return weatherList.size();
    }

    public void updateWeatherList(List<WeatherData> weatherList){
        this.weatherList = weatherList;
        notifyDataSetChanged();
        notifyItemRangeRemoved(0, this.weatherList.size());
        notifyItemRangeInserted(0, weatherList.size());
    }

    public void clearWeatherList(){

        weatherList.clear();
        notifyItemRangeRemoved(0, weatherList.size());
    }
}
class WeatherVH extends RecyclerView.ViewHolder{

    WeatherItemBinding weatherItemBinding;

    public WeatherVH(@NonNull View itemView) {
        super(itemView);
        weatherItemBinding = WeatherItemBinding.bind(itemView);
    }
}
