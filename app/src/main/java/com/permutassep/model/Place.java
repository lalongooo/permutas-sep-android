package com.permutassep.model;

/**
 * Created by jorge.hernandez on 4/7/2015.
 */
public class Place {
    private short state;
    private short city;
    private short town;
    private double lat;
    private double lon;

    public Place(short state, short city, short town, double lat, double lon) {
        this.state = state;
        this.city = city;
        this.town = town;
        this.lat = lat;
        this.lon = lon;
    }

    public short getState() {
        return state;
    }

    public void setState(short state) {
        this.state = state;
    }

    public short getCity() {
        return city;
    }

    public void setCity(short city) {
        this.city = city;
    }

    public short getTown() {
        return town;
    }

    public void setTown(short town) {
        this.town = town;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLon() {
        return lon;
    }

    public void setLon(double lon) {
        this.lon = lon;
    }

    @Override
    public String toString() {
        return "Place{" +
                "state=" + state +
                ", city=" + city +
                ", town=" + town +
                ", lat=" + lat +
                ", lon=" + lon +
                '}';
    }
}
