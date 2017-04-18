package com.example.lalli.rejseplanen.dataB;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
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

        mContext    = context.getApplicationContext();
        mDatabase   = new UserBaseHelper(mContext).getWritableDatabase();
    }
    private static ContentValues getContentValues(User user) {
        ContentValues values = new ContentValues();
        values.put(DBschema.UserTable.Cols.UUID, user.getId().toString());
        values.put(DBschema.UserTable.Cols.EMAIL, user.getEmail());
        values.put(DBschema.UserTable.Cols.mobile, user.getMobile());
        values.put(DBschema.UserTable.Cols.PASSWORD, user.getPassword());
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
            else{

            }
        }
        finally {

        }

        return exists;
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
