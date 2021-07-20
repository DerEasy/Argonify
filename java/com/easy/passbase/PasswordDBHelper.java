package com.easy.passbase;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class PasswordDBHelper extends SQLiteOpenHelper {
    public static final int DATABASE_VERSION = 1;
    public static final int AMOUNT_OF_MAIN_COLUMNS = 5;
    public static final String DATABASE_NAME = "pw.db";

    public static final String TABLE_NAME       = "PASSBASE";
    public static final String _ID              = "id";
    public static final String COLUMN_NAME      = "Name";
    public static final String COLUMN_PASSWORD  = "Password";
    public static final String COLUMN_EMAIL     = "Email";
    public static final String COLUMN_USERNAME  = "Username";
    public static final String COLUMN_NOTES     = "Notes";

    public static final String[] mainColumns = new String[AMOUNT_OF_MAIN_COLUMNS];

    public PasswordDBHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);

        //Puts the most used columns in an array for making iterations and loops easier
        //The order of the columns must ALWAYS be Name, Password, Email, Username, Notes
        mainColumns[0] = COLUMN_NAME;
        mainColumns[1] = COLUMN_PASSWORD;
        mainColumns[2] = COLUMN_EMAIL;
        mainColumns[3] = COLUMN_USERNAME;
        mainColumns[4] = COLUMN_NOTES;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        final String SQL_CREATE_PASSWORD_TABLE = "CREATE TABLE " +
                TABLE_NAME +        " (" +
                _ID +               " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_NAME +       " TEXT, " +
                COLUMN_PASSWORD +   " TEXT, " +
                COLUMN_EMAIL +      " TEXT, " +
                COLUMN_USERNAME +   " TEXT, " +
                COLUMN_NOTES +      " TEXT  " +
                                    ");";
        db.execSQL(SQL_CREATE_PASSWORD_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {}
}
