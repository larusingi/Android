package com.example.lalli.rejseplanen.dataB;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;
import com.example.lalli.rejseplanen.dataB.DBschema.BankTable;
/**
 * Created by Lalli on 20-04-2017.
 */

public class BankDB {

    private static BankDB sBankDB;
    private Context mContext;
    private SQLiteDatabase mDatabase;

    public BankDB(Context mContext) {
        this.mContext   = mContext;
        mDatabase       = new UserBaseHelper(mContext).getWritableDatabase();
    }

    public static BankDB get(Context context) {
        if (sBankDB == null) {
            sBankDB= new BankDB(context);
        }
        return sBankDB;
    }

    public List<BankAccount> getUsersAccounts(){

        List<BankAccount> accounts = new ArrayList<>();
        UserCursorWrapper cursor = queryBankAccounts(null,null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            accounts.add(cursor.getBankAccount());
            cursor.moveToNext();
        }
        cursor.close();
        return accounts;
    }

    public  boolean addBank(BankAccount bankAccount){
        try {
            ContentValues cVal = getBankContentValues(bankAccount);
            mDatabase.insertOrThrow(DBschema.BankTable.NAME,null,cVal);
            return true;
        }catch (Exception e){
            System.out.println("something went wrong because of : "+ e);
            return  false;
        }
    }


    public boolean withdraw(BankAccount bankAccount){

        // i should be using a USER object as a parameter because the bankaccount object is really confusing to use here!!!
        UserCursorWrapper cursor = queryBankAccounts(
                DBschema.BankTable.Cols.CreditCardNR + "=?",
                new String[]{bankAccount.getMcreditcardnr()}
        );
        try{
                System.out.println("found card");
                //move to first and only element
                cursor.moveToFirst();
            if(cursor.getCount()>0){
                //get balance from db
                Double balance = cursor.getDouble(cursor.getColumnIndex("balance")) ;
                //check if bank balance is greater then the ammount specified in the bankaccount balance
                if(balance >= bankAccount.getMbalance()) {
                    Double finalAmmount = balance-bankAccount.getMbalance();
                    ContentValues newValues = new ContentValues();
                    newValues.put("balance", finalAmmount.toString());
                    mDatabase.update(BankTable.NAME, newValues, BankTable.Cols.CreditCardNR+ " =?", new String[]{bankAccount.getMcreditcardnr()});

                return true;
                }

            }

        }catch (SQLException e){
            System.out.println("error updating database because of : "+e);
        }
    return false;
    }

    public Double getBalanceOfCCard(BankAccount b){

        UserCursorWrapper cursor = queryBankAccounts(
                DBschema.BankTable.Cols.CreditCardNR + "=?",
                new String[]{b.getMcreditcardnr()}
        );
        try{
            System.out.println("found card");
            //move to first and only element
            cursor.moveToFirst();
            if(cursor.getCount()>0){
                //get balance from db
                Double balance = cursor.getDouble(cursor.getColumnIndex("balance")) ;
                return balance;
            }

        }catch (SQLException e){
            System.out.println("error updating database because of : "+e);
        }
        return 0.0;
    }


    private UserCursorWrapper queryBankAccounts(String whereClause, String[] whereArgs){
        Cursor c = mDatabase.query(
                DBschema.BankTable.NAME,
                null,
                whereClause,
                whereArgs,
                null,
                null,
                null
        );
        return  new UserCursorWrapper(c);
    }

    private static ContentValues getBankContentValues(BankAccount bankAccount) {
        ContentValues values = new ContentValues();
        values.put(DBschema.BankTable.Cols.CreditCardNR, bankAccount.getMcreditcardnr().toString());
        values.put(DBschema.BankTable.Cols.CRC, bankAccount.getMcrc().toString());
        values.put(DBschema.BankTable.Cols.FIRSTNAME, bankAccount.getMfirstname().toString());
        values.put(DBschema.BankTable.Cols.LASTNAME, bankAccount.getMlastname().toString());
        values.put(DBschema.BankTable.Cols.BALANCE, Double.toString(bankAccount.getMbalance()));
        return values;
    }



}
