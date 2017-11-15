package org.tammy.weatherproject.Models;

// This class encapsulates data for current weather conditions

public class Weather {

    private String temperature;
    private String location;

    public Weather(String temperature, String location) {
        this.temperature = temperature;
        this.location = location;
    }

    public String getTemperature() {
        return temperature;
    }

    public void setTemperature(String temperature) {
        this.temperature = temperature;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String mLocation) {
        this.location = mLocation;
    }

}
