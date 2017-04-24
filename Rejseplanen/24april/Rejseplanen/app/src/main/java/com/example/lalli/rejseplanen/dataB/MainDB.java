package com.example.lalli.rejseplanen.dataB;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.CursorWrapper;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabaseLockedException;

import com.example.lalli.rejseplanen.dataB.DBschema.UserTable;


import java.util.ArrayList;
import java.util.List;

/**
 * Created by Lalli on 24-03-2017.
 */

public class MainDB {

    private static MainDB sMainDB;
    private Context mContext;
    private SQLiteDatabase mDatabase;

    public static MainDB get(Context context) {
        if (sMainDB == null) {
            sMainDB= new MainDB(context);
        }
        return sMainDB;
    }

    public MainDB(Context context) {

        mContext        = context.getApplicationContext();
        mDatabase       = new UserBaseHelper(mContext).getWritableDatabase();

    }
    private static ContentValues getContentValues(User user) {
        ContentValues values = new ContentValues();
        values.put(DBschema.UserTable.Cols.UUID, user.getId().toString());
        values.put(UserTable.Cols.FIRSTNAME, user.getMfirstname().toString());
        values.put(UserTable.Cols.LASTNAME, user.getMlastname().toString());
        values.put(DBschema.UserTable.Cols.EMAIL, user.getEmail());
        values.put(DBschema.UserTable.Cols.mobile, user.getMobile());
        values.put(DBschema.UserTable.Cols.PASSWORD, user.getPassword());
        values.put(UserTable.Cols.CreditCardNR, user.getMcreditcardnr());
        values.put(UserTable.Cols.CRC, user.getMcrc().toString());
        values.put(DBschema.UserTable.Cols.CREDIT, Double.toString(user.getCredit()));
        return values;
    }

    public boolean checkUser(User user){
        boolean exists = false;
        final User usr= user;

        UserCursorWrapper cursor = queryUsers(
                UserTable.Cols.mobile + "=?",
                new String[]{usr.getMobile()}
        );
        try{
            if(cursor.getCount()>0){
                System.out.println("exists");
                //move to first and only element
                cursor.moveToFirst();
                String pass = cursor.getString(cursor.getColumnIndex("password")) ;
                if(pass.equals(user.getPassword())){
                    exists=true;
                }
            }
        }catch (Exception e ){
            System.out.println("error checking user because of : " +e);
        }
        return exists;
    }

    public Double getBalance(User user){
        final User usr= user;

        UserCursorWrapper cursor = queryUsers(
                UserTable.Cols.mobile + "=?",
                new String[]{usr.getMobile()}
        );
        if(cursor.getCount()>0){
            System.out.println("exists");
            //move to first and only element
            cursor.moveToFirst();
            Double balance = cursor.getDouble(cursor.getColumnIndex("credit")) ;
            return balance;
        }
        return 0.0;
    }

    public User getUser(String mobile){

        final User usr = new User("","");

        UserCursorWrapper cursor = queryUsers(
                UserTable.Cols.mobile + "=?",
                new String[]{mobile}
        );
        if(cursor.getCount()>0){
            System.out.println("exists");
            //move to first and only element
            cursor.moveToFirst();
            usr.setPassword(cursor.getString(cursor.getColumnIndex("password")));
            usr.setEmail(cursor.getString(cursor.getColumnIndex("email")));
            usr.setMcreditcardnr(cursor.getString(cursor.getColumnIndex("creditcardnr")));
            usr.setMfirstname(cursor.getString(cursor.getColumnIndex("firstname")));
            usr.setMcrc(cursor.getString(cursor.getColumnIndex("crc")));
            usr.setMlastname(cursor.getString(cursor.getColumnIndex("lastname")));
            usr.setCredit(cursor.getDouble(cursor.getColumnIndex("credit")));
            usr.setMobile(cursor.getString(cursor.getColumnIndex("mobile")));
        }
        return usr;
    }

    public List<User> getUsers(){

        List<User> users = new ArrayList<>();
        UserCursorWrapper cursor = queryUsers(null, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            users.add(cursor.getUser());
            cursor.moveToNext();
        }
        cursor.close();
        return users;
    }

    public boolean addUser(User user){
        try{
            ContentValues cValues = getContentValues(user);
            mDatabase.insertOrThrow(UserTable.NAME,null,cValues);
            return true;
        }catch (Exception e){

            System.out.println("errrrrrrrrrrror : "+e);
            return false;
        }
    }

    public boolean addMoney(User user){
        UserCursorWrapper cursor = queryUsers(
                UserTable.Cols.mobile + "=?",
                new String[]{user.getMobile()}
        );
        try{
            System.out.println("found users mobile");
            //move to first and only element
            cursor.moveToFirst();
            if(cursor.getCount()>0){
                //get balance from db
                Double balance = cursor.getDouble(cursor.getColumnIndex("credit")) ;
                //check if bank balance is greater then the ammount specified in the bankaccount balance
                User u = sMainDB.getUser(user.getMobile());

                    Double finalAmmount = balance+user.getCredit();
                    ContentValues newValues = new ContentValues();
                    newValues.put("credit", finalAmmount.toString());
                    mDatabase.update(DBschema.UserTable.NAME, newValues, UserTable.Cols.mobile+ " =?", new String[]{user.getMobile()});
            }
        }catch (SQLException e){
            System.out.println("error updating database because of : "+e);
        }
        return false;
    }


    public boolean withdraw(User user, int ammount){

        // i should be using a USER object as a parameter because the bankaccount object is really confusing to use here!!!
        UserCursorWrapper cursor = queryUsers(
                UserTable.Cols.mobile + "=?",
                new String[]{user.getMobile()}
        );
        try{
            System.out.println("found users mobile");
            //move to first and only element
            cursor.moveToFirst();
            if(cursor.getCount()>0){
                //get balance from db
                Double balance = cursor.getDouble(cursor.getColumnIndex("credit")) ;
                //check if bank balance is greater then the ammount specified in the bankaccount balance
                if(balance >= ammount) {
                    Double finalAmmount = balance-ammount;
                    ContentValues newValues = new ContentValues();
                    newValues.put("credit", finalAmmount.toString());
                    mDatabase.update(UserTable.NAME, newValues, UserTable.Cols.mobile+ " =?", new String[]{user.getMobile()});

                    return true;
                }

            }

        }catch (SQLException e){
            System.out.println("error updating database because of : "+e);
        }
        return false;
    }


    private UserCursorWrapper queryUsers(String whereClause, String[] whereArgs) {
        Cursor cursor = mDatabase.query(
                DBschema.UserTable.NAME,
                null, // Columns - null selects all columns
                whereClause,
                whereArgs,
                null, // groupBy
                null, // having
                null  // orderBy
        );
        return new UserCursorWrapper(cursor);
    }





}
