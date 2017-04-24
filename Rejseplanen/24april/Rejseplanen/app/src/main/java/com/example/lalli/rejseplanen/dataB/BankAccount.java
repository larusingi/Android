package com.example.lalli.rejseplanen.dataB;

/**
 * Created by Lalli on 18-04-2017.
 */

public class BankAccount {

    private String mcreditcardnr;
    private String mcrc;
    private String mfirstname;
    private String mlastname;
    private double mbalance;

    public String getMcreditcardnr() {
        return mcreditcardnr;
    }

    public void setMcreditcardnr(String mcreditcardnr) {
        this.mcreditcardnr = mcreditcardnr;
    }

    public String getMcrc() {
        return mcrc;
    }

    public void setMcrc(String mcrc) {
        this.mcrc = mcrc;
    }

    public String getMfirstname() {
        return mfirstname;
    }

    public void setMfirstname(String mfirstname) {
        this.mfirstname = mfirstname;
    }

    public String getMlastname() {
        return mlastname;
    }

    public void setMlastname(String mlastname) {
        this.mlastname = mlastname;
    }

    public double getMbalance() {
        return mbalance;
    }

    public void setMbalance(double mbalance) {
        this.mbalance = mbalance;
    }
}
