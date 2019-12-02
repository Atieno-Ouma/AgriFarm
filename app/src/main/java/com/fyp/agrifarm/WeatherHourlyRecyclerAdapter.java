package com.fyp.agrifarm;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.fyp.agrifarm.beans.WeatherHourlyForecast;

import java.util.List;

public class WeatherHourlyRecyclerAdapter extends RecyclerView.Adapter {

    private Context context;
    private List<WeatherHourlyForecast> weatherList;

    public WeatherHourlyRecyclerAdapter(Context context, List<WeatherHourlyForecast> weatherList){
        this.context = context;
        this.weatherList = weatherList;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context)
                .inflate(R.layout.rv_item_hourly_forecast, viewGroup, false);

        return new WeatherListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int i) {
        WeatherHourlyForecast record = weatherList.get(i);

        WeatherListViewHolder holder = (WeatherListViewHolder) viewHolder;

        holder.tvTime.setText(record.getTime());
        holder.tvTemperature.setText(record.getTemperature() + "\u00B0");
    }

    @Override
    public int getItemCount() {
        return weatherList.size();
    }

    private class WeatherListViewHolder extends RecyclerView.ViewHolder {

        TextView tvTime, tvTemperature;

        public WeatherListViewHolder(View view) {
            super(view);
            tvTime = view.findViewById(R.id.tvWeatherHourlyTime);
            tvTemperature = view.findViewById(R.id.tvWeatherHourlyTemp);
        }
    }
}

