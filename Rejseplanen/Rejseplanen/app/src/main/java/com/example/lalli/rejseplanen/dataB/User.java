package com.example.lalli.rejseplanen.dataB;

import java.util.UUID;

/**
 * Created by Lalli on 24-03-2017.
 */

public class User {

    private UUID mId;
    private String mmobile;
    private String memail;
    private String mpassword;
    private double mcredit;

    public User(UUID uuid){
        mId = UUID.randomUUID();
    }

    public User(String mobile, String pass){

        this.mmobile = mobile;
        this.mpassword = pass;
        mId = UUID.randomUUID();

    }

    public UUID getId() {
        return mId;
    }

    public String getMobile() {
        return mmobile;
    }

    public void setMobile(String mmobile) {
        this.mmobile = mmobile;
    }

    public String getEmail() {
        return memail;
    }

    public void setEmail(String memail) {
        this.memail = memail;
    }

    public String getPassword() {
        return mpassword;
    }

    public void setPassword(String mpassword) {
        this.mpassword = mpassword;
    }

    public double getCredit() {
        return mcredit;
    }

    public void setCredit(int mcredit) {
        this.mcredit = mcredit;
    }


}
