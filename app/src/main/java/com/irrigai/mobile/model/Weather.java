package com.irrigai.mobile.model;

public class Weather {
    private double temperature;
    private double windSpeed;
    private String condition;
    private String icon;
    private double humidity;
    private Double rainPrediction;
    private WeatherForecast tomorrow;
    private String irrigationSuggestion;
    private boolean isDay;
    
    public Weather() {}
    
    // Getters and Setters
    public double getTemperature() { return temperature; }
    public void setTemperature(double temperature) { this.temperature = temperature; }
    
    public double getWindSpeed() { return windSpeed; }
    public void setWindSpeed(double windSpeed) { this.windSpeed = windSpeed; }
    
    public String getCondition() { return condition; }
    public void setCondition(String condition) { this.condition = condition; }
    
    public String getIcon() { return icon; }
    public void setIcon(String icon) { this.icon = icon; }
    
    public double getHumidity() { return humidity; }
    public void setHumidity(double humidity) { this.humidity = humidity; }
    
    public Double getRainPrediction() { return rainPrediction; }
    public void setRainPrediction(Double rainPrediction) { this.rainPrediction = rainPrediction; }
    
    public WeatherForecast getTomorrow() { return tomorrow; }
    public void setTomorrow(WeatherForecast tomorrow) { this.tomorrow = tomorrow; }
    
    public String getIrrigationSuggestion() { return irrigationSuggestion; }
    public void setIrrigationSuggestion(String irrigationSuggestion) { this.irrigationSuggestion = irrigationSuggestion; }
    
    public boolean isDay() { return isDay; }
    public void setDay(boolean day) { isDay = day; }
    
    public static class WeatherForecast {
        private double temperature;
        private String condition;
        private String icon;
        private Double rainChance;
        
        public double getTemperature() { return temperature; }
        public void setTemperature(double temperature) { this.temperature = temperature; }
        
        public String getCondition() { return condition; }
        public void setCondition(String condition) { this.condition = condition; }
        
        public String getIcon() { return icon; }
        public void setIcon(String icon) { this.icon = icon; }
        
        public Double getRainChance() { return rainChance; }
        public void setRainChance(Double rainChance) { this.rainChance = rainChance; }
    }
}

