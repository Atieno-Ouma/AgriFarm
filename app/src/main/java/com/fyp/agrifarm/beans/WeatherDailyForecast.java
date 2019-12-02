package com.fyp.agrifarm.beans;

public class WeatherDailyForecast {

    String day;
    String temperature;
    String description;

    public WeatherDailyForecast(String day, String temperature, String description) {
        this.day = day;
        this.temperature = temperature;
        this.description = description;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getTemperature() {
        return temperature;
    }

    public void setTemperature(String temperature) {
        this.temperature = temperature;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
