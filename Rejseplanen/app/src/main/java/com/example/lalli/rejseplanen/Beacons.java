package com.example.lalli.rejseplanen;

/**
 * Created by Lalli on 22-04-2017.
 */

public class Beacons {

    String UUId;
    int minor;
    int major;
    int counter;
    int beacPower;

    public int getCounter() {
        return counter;
    }

    public void setCounter(int counter) {
        this.counter = counter;
    }

    @Override
    public String toString() {
        // TODO Auto-generated method stub
        String minorString = Integer.toString(minor);
        String room = minorString.substring(1, 3);

        return "power : " + beacPower + " room: " + room + " floor: " + major;
    }

    public String getUUID() {
        return UUId;
    }

    public void setUUID(String UUId) {
        this.UUId = UUId;
    }

    public int getMinor() {
        return minor;
    }

    public void setMinor(int minor) {
        this.minor = minor;
    }

    public int getMajor() {
        return major;
    }

    public void setMajor(int major) {
        this.major = major;
    }

    public int getBeacPower() {
        return beacPower;
    }

    public void setBeacPower(int beacPower) {
        this.beacPower = beacPower;
    }
}
