package com.example.myapplication.model;

public class LocationModel {
    private String username;
    private double uLong;
    private double uLat;

    public LocationModel() {
    }

    public LocationModel(String username, double uLong, double uLat) {
        this.username = username;
        this.uLong = uLong;
        this.uLat = uLat;
    }


    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public double getuLong() {
        return uLong;
    }

    public void setuLong(double uLong) {
        this.uLong = uLong;
    }

    public double getuLat() {
        return uLat;
    }

    public void setuLat(double uLat) {
        this.uLat = uLat;
    }
}
