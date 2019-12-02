package com.fyp.agrifarm.beans;

public class WeatherHourlyForecast {

    String time;
    String temperature;

    public WeatherHourlyForecast(String time, String temperature) {
        this.time = time;
        this.temperature = temperature;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getTemperature() {
        return temperature;
    }

    public void setTemperature(String temperature) {
        this.temperature = temperature;
    }
}
