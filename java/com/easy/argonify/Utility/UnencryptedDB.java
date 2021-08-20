package com.easy.argonify.Utility;

import static com.easy.argonify.Utility.PasswordDBHelper.AMOUNT_OF_MAIN_ATTRIBUTES;
import static com.easy.argonify.Utility.PasswordDBHelper.MAIN_ATTRIBUTES;
import static com.easy.argonify.Utility.PasswordDBHelper.TABLE_NAME;

import android.content.ContentValues;
import android.database.Cursor;

import net.sqlcipher.database.SQLiteDatabase;

public class UnencryptedDB {
    public static final String MODE_IMPORT = "IMPORT";
    public static final String MODE_EXPORT = "EXPORT";

    private final SQLiteDatabase unencryptedDB;
    private final Cursor cursor;

    public UnencryptedDB(SQLiteDatabase db, String MODE) {
        unencryptedDB = db;
        switch (MODE) {
            case MODE_IMPORT:
                cursor = this.getMainAttributes(); break;
            case MODE_EXPORT:
                cursor = PasswordDB.getMainAttributes(); break;
            default:
                throw new IllegalArgumentException("Mode error.");
        }
    }

    public void copyEncryptedDatabase() {
        for (int i = 0; i < cursor.getCount(); ++i) {
            insertTuple(getTupleCV());
            cursor.moveToNext();
        }
    }

    public void importEntries() {
        for (int i = 0; i < cursor.getCount(); ++i) {
            PasswordDB.insertTuple(getTupleCV());
            cursor.moveToNext();
        }
    }

    private void insertTuple(ContentValues cv) {
        unencryptedDB.insert(
                TABLE_NAME,
                null,
                cv
        );
    }

    private ContentValues getTupleCV() {
        ContentValues cv = new ContentValues();
        for (int i = 0; i < AMOUNT_OF_MAIN_ATTRIBUTES; ++i)
            cv.put(MAIN_ATTRIBUTES[i], cursor.getString(cursor.getColumnIndex(MAIN_ATTRIBUTES[i])));

        return cv;
    }

    private Cursor getMainAttributes() {
        Cursor cursor = unencryptedDB.query(
                TABLE_NAME,
                MAIN_ATTRIBUTES,
                null,
                null,
                null,
                null,
                PasswordDBHelper.COLUMN_NAME + " ASC"
        );
        cursor.moveToFirst();
        return cursor;
    }
}
