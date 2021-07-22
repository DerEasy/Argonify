package com.easy.passbase;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import static com.easy.passbase.PasswordDBHelper.COLUMN_NAME;
import static com.easy.passbase.PasswordDBHelper.TABLE_NAME;
import static com.easy.passbase.PasswordDBHelper._ID;

public class PasswordDB {
    public static SQLiteDatabase passwordDB;

    public static Cursor getEntireRelation() {
        Cursor cursor = passwordDB.query(
                TABLE_NAME,
                null,
                null,
                null,
                null,
                null,
                PasswordDBHelper.COLUMN_NAME + " ASC"
        );
        cursor.moveToFirst();
        return cursor;
    }

    public static Cursor getIDsAndNames() {
        Cursor cursor = passwordDB.query(
                TABLE_NAME,
                new String[]{_ID, COLUMN_NAME},
                null,
                null,
                null,
                null,
                null
        );
        cursor.moveToFirst();
        return cursor;
    }

    public static Cursor getTupleByID(int id) {
        Cursor cursor = passwordDB.query(
                TABLE_NAME,
                null,
                String.format("%s = %s", _ID, id),
                null,
                null,
                null,
                null
        );
        cursor.moveToFirst();
        return cursor;
    }

    public static void insertTuple(ContentValues cv) {
        passwordDB.insert(
                TABLE_NAME,
                null,
                cv
        );
    }

    public static void deleteTuple(int id) {
        passwordDB.delete(
                TABLE_NAME,
                String.format("%s = %s", _ID, id),
                null
       );
    }
}
