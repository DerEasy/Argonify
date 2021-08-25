package com.easy.argonify.Utility;

import static com.easy.argonify.Utility.PasswordDBHelper.COLUMN_EMAIL;
import static com.easy.argonify.Utility.PasswordDBHelper.COLUMN_NAME;
import static com.easy.argonify.Utility.PasswordDBHelper.COLUMN_NOTES;
import static com.easy.argonify.Utility.PasswordDBHelper.COLUMN_PASSWORD;
import static com.easy.argonify.Utility.PasswordDBHelper.COLUMN_USERNAME;
import static com.easy.argonify.Utility.PasswordDBHelper.MAIN_ATTRIBUTES;
import static com.easy.argonify.Utility.PasswordDBHelper.TABLE_NAME;
import static com.easy.argonify.Utility.PasswordDBHelper._ID;
import static net.sqlcipher.DatabaseUtils.sqlEscapeString;

import android.content.ContentValues;
import android.database.Cursor;

import net.sqlcipher.database.SQLiteDatabase;

public class PasswordDB {
    public static SQLiteDatabase passwordDB;

    public static Cursor getMainAttributes() {
        Cursor cursor = passwordDB.query(
                TABLE_NAME,
                MAIN_ATTRIBUTES,
                null,
                null,
                null,
                null,
                null
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
                COLUMN_NAME + " COLLATE NOCASE ASC"
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

    private static String formatWhere(String column, String value) {
        if (value == null || value.isEmpty())
            return String.format("(%s = '' OR %s IS NULL)", column, column);
        else
            return String.format("%s = %s", column, sqlEscapeString(value));
    }

    private static boolean isDuplicate(ContentValues cv) {
        final String WHERE = String.format(
                "%s AND %s AND %s AND %s AND %s",
                formatWhere(COLUMN_NAME, cv.getAsString(COLUMN_NAME)),
                formatWhere(COLUMN_PASSWORD, cv.getAsString(COLUMN_PASSWORD)),
                formatWhere(COLUMN_EMAIL, cv.getAsString(COLUMN_EMAIL)),
                formatWhere(COLUMN_USERNAME, cv.getAsString(COLUMN_USERNAME)),
                formatWhere(COLUMN_NOTES, cv.getAsString(COLUMN_NOTES))
        );
        Cursor cursor = passwordDB.query(
                TABLE_NAME,
                MAIN_ATTRIBUTES,
                WHERE,
                null,
                null,
                null,
                null
        );

        return cursor.getCount() > 0;
    }

    public static void insertTuple(ContentValues cv) {
        if (!isDuplicate(cv))
            passwordDB.insert(
                    TABLE_NAME,
                    null,
                    cv
            );
    }

    public static void editTuple(ContentValues cv, int id) {
        passwordDB.update(
                TABLE_NAME,
                cv,
                String.format("%s = %s", _ID, id),
                null
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
