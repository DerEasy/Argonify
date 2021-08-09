package com.easy.argonify.Main;

import static com.easy.argonify.Main.PasswordDB.passwordDB;
import static com.easy.argonify.Settings.ApplockStrings.APPLOCK;

import android.content.Context;
import android.content.SharedPreferences;
import android.widget.Toast;

import net.sqlcipher.database.SQLiteDatabase;

import java.io.File;

public class PasswordDBHelper {
    public static final int AMOUNT_OF_MAIN_ATTRIBUTES = 5;
    public static final String DATABASE_NAME = "pw.db";

    public static final String TABLE_NAME       = "PASSBASE";
    public static final String _ID              = "id";
    public static final String COLUMN_NAME      = "Name";
    public static final String COLUMN_PASSWORD  = "Password";
    public static final String COLUMN_EMAIL     = "Email";
    public static final String COLUMN_USERNAME  = "Username";
    public static final String COLUMN_NOTES     = "Notes";

    public static final int INDEX_NAME      = 0;
    public static final int INDEX_PASSWORD  = 1;
    public static final int INDEX_EMAIL     = 2;
    public static final int INDEX_USERNAME  = 3;
    public static final int INDEX_NOTES     = 4;

    public static final String[] MAIN_ATTRIBUTES = new String[AMOUNT_OF_MAIN_ATTRIBUTES];

    static {
        //Puts the most used columns in an array for making iterations and loops easier
        //The order of the columns must ALWAYS be Name, Password, Email, Username, Notes
        MAIN_ATTRIBUTES[INDEX_NAME]     = COLUMN_NAME;
        MAIN_ATTRIBUTES[INDEX_PASSWORD] = COLUMN_PASSWORD;
        MAIN_ATTRIBUTES[INDEX_EMAIL]    = COLUMN_EMAIL;
        MAIN_ATTRIBUTES[INDEX_USERNAME] = COLUMN_USERNAME;
        MAIN_ATTRIBUTES[INDEX_NOTES]    = COLUMN_NOTES;
    }

    public static boolean createDatabase(File databaseFile, String password) {
        boolean dbErasure = false;
        try {
            passwordDB = SQLiteDatabase.openOrCreateDatabase(databaseFile, password, null);
        } catch (RuntimeException exception) {
            dbErasure = databaseFile.delete();
            passwordDB = SQLiteDatabase.openOrCreateDatabase(databaseFile, password, null);
        }

        final String SQL_CREATE_PASSWORD_TABLE =
                                  " CREATE TABLE IF NOT EXISTS " +
                TABLE_NAME      + " ( " +
                _ID             + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_NAME     + " TEXT, " +
                COLUMN_PASSWORD + " TEXT, " +
                COLUMN_EMAIL    + " TEXT, " +
                COLUMN_USERNAME + " TEXT, " +
                COLUMN_NOTES    + " TEXT  " +
                                  " );";

        passwordDB.execSQL(SQL_CREATE_PASSWORD_TABLE);
        return dbErasure;
    }
}
