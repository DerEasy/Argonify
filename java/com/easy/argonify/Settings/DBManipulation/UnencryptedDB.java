package com.easy.argonify.Settings.DBManipulation;

import static com.easy.argonify.Utility.PasswordDBHelper.AMOUNT_OF_MAIN_ATTRIBUTES;
import static com.easy.argonify.Utility.PasswordDBHelper.COLUMN_NAME;
import static com.easy.argonify.Utility.PasswordDBHelper.MAIN_ATTRIBUTES;
import static com.easy.argonify.Utility.PasswordDBHelper.TABLE_NAME;

import android.content.ContentValues;
import android.database.Cursor;

import androidx.fragment.app.FragmentActivity;

import com.easy.argonify.Utility.PasswordDB;

import net.sqlcipher.database.SQLiteDatabase;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class UnencryptedDB {
    private final SQLiteDatabase unencryptedDB;
    private Cursor cursor;

    UnencryptedDB(SQLiteDatabase db) {
        unencryptedDB = db;
    }

    void exportDB(FragmentActivity context) {
        cursor = PasswordDB.getMainAttributes();

        DgExportLoading loading = new DgExportLoading();
        loading.show(context.getSupportFragmentManager(), null);
        loading.initialise(this);
    }

    void exportDB(DgExportLoading loading) {
        Executor executor = Executors.newFixedThreadPool(2);

        executor.execute(() -> {
            loading.setMaxProgress(cursor.getCount());

            for (int i = 0; i < cursor.getCount(); ++i) {
                this.insertTuple(getTupleCV());
                executor.execute(loading::incrementProgress);
                cursor.moveToNext();
            }

            loading.dismiss();
        });
    }

    void importDB(FragmentActivity context) {
        cursor = this.getMainAttributes();

        DgImportLoading loading = new DgImportLoading();
        loading.show(context.getSupportFragmentManager(), null);
        loading.initialise(this);
    }

    /**
     * Do not manually call this method. Instead call {@link #importDB(FragmentActivity)}
     */
    void importDB(DgImportLoading loading) {
        Executor executor = Executors.newFixedThreadPool(2);

        executor.execute(() -> {
            loading.setMaxProgress(cursor.getCount());

            for (int i = 0; i < cursor.getCount(); ++i) {
                PasswordDB.insertTuple(getTupleCV());
                executor.execute(loading::incrementProgress);
                cursor.moveToNext();
            }

            loading.dismiss();
        });
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
                null
        );
        cursor.moveToFirst();
        return cursor;
    }
}
