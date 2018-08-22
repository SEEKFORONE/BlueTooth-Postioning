package com.example.hesiliang.myapplication;

public class Bluetoothitem {
    private String name;
    private double rssi;

    public Bluetoothitem(String name,double rssi){
        this.name=name;
        this.rssi=rssi;
    }

    public String getName() {
        return name;
    }

    public double getRssi() {
        return rssi;
    }
}
