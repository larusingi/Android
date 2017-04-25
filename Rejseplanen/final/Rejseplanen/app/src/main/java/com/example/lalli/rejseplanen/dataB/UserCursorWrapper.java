package com.example.lalli.rejseplanen.dataB;

import android.database.Cursor;
import android.database.CursorWrapper;

import com.example.lalli.rejseplanen.dataB.DBschema.UserTable;

import java.util.UUID;

/**
 * Created by Lalli on 24-03-2017.
 */

public class UserCursorWrapper extends CursorWrapper {
    /**
     * Creates a cursor wrapper.
     *
     * @param cursor The underlying cursor to wrap.
     */
    public UserCursorWrapper(Cursor cursor) {
        super(cursor);
    }

    public User getUser() {
        String uuidString = getString(getColumnIndex(UserTable.Cols.UUID));

        String firstname = getString(getColumnIndex(DBschema.BankTable.Cols.FIRSTNAME));
        String lastname = getString(getColumnIndex(DBschema.BankTable.Cols.LASTNAME));
        String mobile = getString(getColumnIndex(UserTable.Cols.mobile));
        String email = getString(getColumnIndex(UserTable.Cols.EMAIL));
        String password = getString(getColumnIndex(UserTable.Cols.PASSWORD));
        String creditcardnr = getString(getColumnIndex(DBschema.BankTable.Cols.CreditCardNR));
        String crc = getString(getColumnIndex(DBschema.BankTable.Cols.CRC));
        int credit = getInt(getColumnIndex(UserTable.Cols.CREDIT));


        User user = new User(null,null);
        user.setmId(UUID.randomUUID());
        user.setMfirstname(firstname);
        user.setMlastname(lastname);
        user.setMobile(mobile);
        user.setEmail(email);
        user.setPassword(password);
        user.setMcreditcardnr(creditcardnr);
        user.setMcrc(crc);
        user.setCredit(credit);
        return user;
    }

    public  BankAccount getBankAccount(){

        String creditcardnr = getString(getColumnIndex(DBschema.BankTable.Cols.CreditCardNR));
        String crc = getString(getColumnIndex(DBschema.BankTable.Cols.CRC));
        String firstname = getString(getColumnIndex(DBschema.BankTable.Cols.FIRSTNAME));
        String lastname = getString(getColumnIndex(DBschema.BankTable.Cols.LASTNAME));
        double balance = getDouble(getColumnIndex(DBschema.BankTable.Cols.BALANCE));

        BankAccount bankAccount = new BankAccount();
        bankAccount.setMcreditcardnr(creditcardnr);
        bankAccount.setMcrc(crc);
        bankAccount.setMfirstname(firstname);
        bankAccount.setMlastname(lastname);
        bankAccount.setMbalance(balance);
        return bankAccount;

    }

    public Trip getTrip() {
        String uidString = getString(getColumnIndex(DBschema.TripTable.Cols.UID));

        String startST= getString(getColumnIndex(DBschema.TripTable.Cols.STARTST));
        String stopST = getString(getColumnIndex(DBschema.TripTable.Cols.ENDST));
        String startTime = getString(getColumnIndex(DBschema.TripTable.Cols.DEPARTTIME));
        String stopTime = getString(getColumnIndex(DBschema.TripTable.Cols.ARRIVALTIME));
        Double price = getDouble(getColumnIndex(DBschema.TripTable.Cols.TRAVELPRICE));


        Trip t = new Trip();
        t.setmId(uidString);
        t.setStartST(startST);
        t.setEndST(stopST);
        t.setStartTime(startTime);
        t.setEndTime(stopTime);
        t.setTripprice(price);
        return t;
    }




}
