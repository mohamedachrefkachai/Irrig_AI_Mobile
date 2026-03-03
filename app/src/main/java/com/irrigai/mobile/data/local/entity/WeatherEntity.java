package com.irrigai.mobile.data.local.entity;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "weather")
public class WeatherEntity {

    @PrimaryKey
    public int id = 1; // Single row

    public double temperature;
    public double windSpeed;
    public String condition;
    public String icon;
    public double humidity;
    public Double rainPrediction;
    public String irrigationSuggestion;
    public boolean isDay;
    // Forecast
    public double tomorrowTemp;
    public String tomorrowCondition;
    public Double tomorrowRainChance;

    public WeatherEntity() {}

    public WeatherEntity(double temperature, double windSpeed, String condition, String icon,
                         double humidity, Double rainPrediction, String irrigationSuggestion,
                         boolean isDay, double tomorrowTemp, String tomorrowCondition,
                         Double tomorrowRainChance) {
        this.temperature = temperature;
        this.windSpeed = windSpeed;
        this.condition = condition != null ? condition : "Clear";
        this.icon = icon != null ? icon : "sunny";
        this.humidity = humidity;
        this.rainPrediction = rainPrediction;
        this.irrigationSuggestion = irrigationSuggestion != null ? irrigationSuggestion : "Normal irrigation recommended";
        this.isDay = isDay;
        this.tomorrowTemp = tomorrowTemp;
        this.tomorrowCondition = tomorrowCondition != null ? tomorrowCondition : "Clear";
        this.tomorrowRainChance = tomorrowRainChance;
    }
}
