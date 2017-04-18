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

    public User getUser(){
        String uuidString   = getString(getColumnIndex(UserTable.Cols.UUID));
        String mobile       = getString(getColumnIndex(UserTable.Cols.mobile));
        String email        = getString(getColumnIndex(UserTable.Cols.EMAIL));
        String password     = getString(getColumnIndex(UserTable.Cols.PASSWORD));
        int credit          = getInt(getColumnIndex(UserTable.Cols.CREDIT));


       User user = new User(UUID.fromString(uuidString));
        user.setMobile(mobile);
        user.setEmail(email);
        user.setPassword(password);
        user.setCredit(credit);
        return user;
    }

}
