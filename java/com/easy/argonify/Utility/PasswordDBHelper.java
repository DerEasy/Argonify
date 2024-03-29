package com.easy.argonify.Utility;

import static com.easy.argonify.Utility.PasswordDB.passwordDB;
import static net.sqlcipher.database.SQLiteDatabase.openOrCreateDatabase;

import android.widget.Toast;

import java.io.File;

public class PasswordDBHelper {
    public static final int AMOUNT_OF_MAIN_ATTRIBUTES = 5;
    public static final String DATABASE_NAME    = "argonify.db";

    public static final String TABLE_NAME       = "ArgonMain";
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

    public static final String SQL_CREATE_PASSWORD_TABLE =
                                      " CREATE TABLE IF NOT EXISTS " +
                    TABLE_NAME      + " ( " +
                    _ID             + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COLUMN_NAME     + " TEXT, " +
                    COLUMN_PASSWORD + " TEXT, " +
                    COLUMN_EMAIL    + " TEXT, " +
                    COLUMN_USERNAME + " TEXT, " +
                    COLUMN_NOTES    + " TEXT  " +
                                      " );" ;

    static {
        //Puts the most used columns in an array for making iterations and loops easier
        //The order of the columns must ALWAYS be Name, Password, Email, Username, Notes
        MAIN_ATTRIBUTES[INDEX_NAME]     = COLUMN_NAME;
        MAIN_ATTRIBUTES[INDEX_PASSWORD] = COLUMN_PASSWORD;
        MAIN_ATTRIBUTES[INDEX_EMAIL]    = COLUMN_EMAIL;
        MAIN_ATTRIBUTES[INDEX_USERNAME] = COLUMN_USERNAME;
        MAIN_ATTRIBUTES[INDEX_NOTES]    = COLUMN_NOTES;
    }

    /**
     * Tries to create or open the password database with the given key
     * @param databaseFile The abstract database path
     * @param key The correct key that this database needs to be decrypted
     * @return True if a fatal error has occurred and database couldn't be accessed, false on success
     */
    public static boolean createDatabase(File databaseFile, String key) {
        try {
            // This should work when opening the database normally
            passwordDB = openOrCreateDatabase(databaseFile, key, null);
        } catch (RuntimeException firstException) {
            firstException.printStackTrace();

            try {
                //If the user changes the applock key though, the database needs to be recreated first
                passwordDB.changePassword(key);
                passwordDB = openOrCreateDatabase(databaseFile, key, null);
            } catch (RuntimeException secondException) {
                // ~~And if that doesn't work, only database recreation will...~~
                // Feature removed for being hazardous, displays Toast instead.
                // dbErasure = databaseFile.delete();
                // passwordDB = openOrCreateDatabase(databaseFile, key, null);
                secondException.printStackTrace();
                return true;
            }
        }


        passwordDB.execSQL(SQL_CREATE_PASSWORD_TABLE);
        return false;
    }
}
