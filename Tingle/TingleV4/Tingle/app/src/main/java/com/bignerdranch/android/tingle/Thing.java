package com.bignerdranch.android.tingle;

/**
 * Created by Lalli on 22-02-2017.
 */

public class Thing {

    private String mWhat= null;
    private String mWhere= null;
    public Thing(String what, String where) {
        mWhat= what;
        mWhere= where;
    }
    @Override
    public String toString() { return oneLine("is here: "); }
    public String getWhat() { return mWhat; }
    public void setWhat(String what) { mWhat= what; }
    public String getWhere() { return mWhere; }
    public void setWhere(String where) { mWhere= where; }
    public String oneLine(String post) {
        return mWhat + " "+post + mWhere;
    }




}
