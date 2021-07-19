package com.easy.passbase;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import static com.easy.passbase.PasswordDBHelper.TABLE_NAME;
import static com.easy.passbase.PasswordDBHelper._ID;

public class PasswordDB {
    public static SQLiteDatabase passwordDB;

    public static Cursor getAllPasswordEntries() {
        return passwordDB.query(
                TABLE_NAME,
                null,
                null,
                null,
                null,
                null,
                PasswordDBHelper.COLUMN_NAME + " ASC"
        );
    }

    public static Cursor getAllPasswordID() {
        return passwordDB.query(
                TABLE_NAME,
                new String[]{_ID},
                null,
                null,
                null,
                null,
                null
        );
    }

    public static Cursor getPassword(int id) {
        return passwordDB.query(
                TABLE_NAME,
                null,
                String.format("%s = %s", _ID, id),
                null,
                null,
                null,
                null
        );
    }
}
