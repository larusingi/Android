package com.example.lalli.rejseplanen.dataB;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.example.lalli.rejseplanen.dataB.DBschema.UserTable;

/**
 * Created by Lalli on 24-03-2017.
 */

public class UserBaseHelper extends SQLiteOpenHelper{


    private static final int VERSION = 1;
    private static final String DATABASE_NAME = "userBase.db";
    public UserBaseHelper(Context context) {
        super(context, DATABASE_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + UserTable.NAME + "(" +
               // " _id integer primary key autoincrement, " +
                UserTable.Cols.UUID + ", " +
                UserTable.Cols.FIRSTNAME +", " +
                UserTable.Cols.LASTNAME +", " +
                UserTable.Cols.mobile + " PRIMARY KEY, " +
                UserTable.Cols.EMAIL + ", " +
                UserTable.Cols.PASSWORD + ", " +
                UserTable.Cols.CreditCardNR + ", " +
                UserTable.Cols.CRC + ", " +
                UserTable.Cols.CREDIT +
                ")"
        );

        db.execSQL("create table " + DBschema.BankTable.NAME + "(" +
                // " _id integer primary key autoincrement, " +
                DBschema.BankTable.Cols.CreditCardNR+ " PRIMARY KEY, " +
                DBschema.BankTable.Cols.CRC + ", " +
                DBschema.BankTable.Cols.FIRSTNAME + ", " +
                DBschema.BankTable.Cols.LASTNAME + ", " +
                DBschema.BankTable.Cols.BALANCE +
                ")"
        );


    }



    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
