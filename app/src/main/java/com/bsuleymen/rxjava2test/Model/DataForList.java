package com.bsuleymen.rxjava2test.Model;

public class DataForList {
    String city;
    String description,icon_url,temperature,date, humidity, wind_speed;


    public DataForList(String city, String description, String icon_url, String temperature, String date, String humidity, String wind_speed) {
        this.city = city;
        this.description = description;
        this.icon_url = icon_url;
        this.temperature = temperature;
        this.date = date;
        this.humidity = humidity;
        this.wind_speed = wind_speed;
    }

    public DataForList(String city) {
        this.city = city;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getIcon_url() {
        return icon_url;
    }

    public void setIcon_url(String icon_url) {
        this.icon_url = icon_url;
    }

    public String getTemperature() {
        return temperature;
    }

    public void setTemperature(String temperature) {
        this.temperature = temperature;
    }

    public String getHumidity() {
        return humidity;
    }

    public void setHumidity(String humidity) {
        this.humidity = humidity;
    }

    public String getWind_speed() {
        return wind_speed;
    }

    public void setWind_speed(String wind_speed) {
        this.wind_speed = wind_speed;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof DataForList)) return false;
        DataForList data = (DataForList) obj;
        return this.city == ((DataForList) obj).city;
    }
}
