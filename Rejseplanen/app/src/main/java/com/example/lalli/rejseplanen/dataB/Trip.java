package com.example.lalli.rejseplanen.dataB;

import java.util.UUID;

/**
 * Created by Lalli on 25-04-2017.
 */

public class Trip {

    private String mId;
    private String startST;
    private String endST;
    private String startTime;
    private String EndTime;
    private double tripprice;

    public String getmId() {
        return mId;
    }

    public void setmId(String mId) {
        this.mId = mId;
    }

    public String toString() {
        // TODO Auto-generated method stub


        return "Checked in at zone : "+ startST +" at : "+startTime+" and  Checked out at zone : "+endST +" at : "+EndTime +" price : "+tripprice+" kr";
    }

    public String getStartST() {
        return startST;
    }

    public void setStartST(String startST) {
        this.startST = startST;
    }

    public String getEndST() {
        return endST;
    }

    public void setEndST(String endST) {
        this.endST = endST;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return EndTime;
    }

    public void setEndTime(String endTime) {
        EndTime = endTime;
    }

    public double getTripprice() {
        return tripprice;
    }

    public void setTripprice(double tripprice) {
        this.tripprice = tripprice;
    }
}
