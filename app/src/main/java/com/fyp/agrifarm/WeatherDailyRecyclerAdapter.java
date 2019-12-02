package com.fyp.agrifarm;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.fyp.agrifarm.beans.WeatherDailyForecast;

import java.util.List;

public class WeatherDailyRecyclerAdapter extends RecyclerView.Adapter {

    private Context context;
    private List<WeatherDailyForecast> weatherList;

    public WeatherDailyRecyclerAdapter(Context context, List<WeatherDailyForecast> weatherList){
        this.context = context;
        this.weatherList = weatherList;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context)
                .inflate(R.layout.rv_item_daily_forecast, viewGroup, false);

        return new WeatherListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int i) {
        WeatherDailyForecast record = weatherList.get(i);

        WeatherListViewHolder holder = (WeatherListViewHolder) viewHolder;

        holder.tvDay.setText(record.getDay());
        holder.tvDescription.setText(record.getDescription());
        holder.tvTemperature.setText(record.getTemperature() + "\u00B0");
    }

    @Override
    public int getItemCount() {
        return weatherList.size();
    }

    private class WeatherListViewHolder extends RecyclerView.ViewHolder {

        TextView tvDay, tvDescription, tvTemperature;

        public WeatherListViewHolder(View view) {
            super(view);
            tvDay = view.findViewById(R.id.tvWeatherDailyDay);
            tvDescription = view.findViewById(R.id.tvWeatherDailyDesc);
            tvTemperature = view.findViewById(R.id.tvWeatherDailyTemp);
        }
    }
}

