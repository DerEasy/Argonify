package com.easy.argonify.Settings.DBManipulation;

import static com.easy.argonify.Utility.PasswordDBHelper.SQL_CREATE_PASSWORD_TABLE;
import static net.sqlcipher.database.SQLiteDatabase.openOrCreateDatabase;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Environment;
import android.provider.Settings;
import android.view.Gravity;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;

import com.easy.argonify.R;

import net.sqlcipher.database.SQLiteDatabase;

import java.io.File;

public class DBTransfer {
    static void exportDatabase(FragmentActivity context) {
        File externalFile = new File(Environment.getExternalStorageDirectory() + "/Argonify/unencryptedArgonify.db");
        SQLiteDatabase unencryptedDB;

        try {
            if (externalFile.exists())
                externalFile.delete();
            else
                externalFile.getParentFile().mkdirs();

            unencryptedDB = openOrCreateDatabase(
                    externalFile,
                    null,
                    null
            );
        } catch (Exception e) {
            Toast.makeText(
                    context,
                    "Unexpected failure.",
                    Toast.LENGTH_SHORT
            ).show();
            e.printStackTrace();
            return;
        }

        unencryptedDB.execSQL(SQL_CREATE_PASSWORD_TABLE);

        new UnencryptedDB(unencryptedDB)
                .exportDB(context);
    }

    static void importDatabase(FragmentActivity context) {
        File externalFile = new File(Environment.getExternalStorageDirectory() + "/Argonify/unencryptedArgonify.db");
        SQLiteDatabase unencryptedDB;

        try {
            if (!externalFile.exists()) {
                Toast.makeText(
                        context,
                        "There is no database file to retrieve items from.",
                        Toast.LENGTH_LONG
                ).show();
                return;
            }

            unencryptedDB = openOrCreateDatabase(
                    externalFile,
                    null,
                    null
            );
        } catch (Exception e) {
            Toast.makeText(
                    context,
                    "Unexpected failure.",
                    Toast.LENGTH_SHORT
            ).show();
            e.printStackTrace();
            return;
        }

        try {
            new UnencryptedDB(unencryptedDB)
                    .importDB(context);
        } catch (net.sqlcipher.database.SQLiteException e) {
            Toast.makeText(
                    context,
                    "Database structure does not match requirements.",
                    Toast.LENGTH_SHORT).show();
        }
    }
}
