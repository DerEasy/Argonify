package com.easy.argonify.Settings.DBManipulation;

import static com.easy.argonify.Utility.PasswordDBHelper.SQL_CREATE_PASSWORD_TABLE;
import static net.sqlcipher.database.SQLiteDatabase.openOrCreateDatabase;

import android.os.Environment;
import android.view.Gravity;
import android.widget.TextView;
import android.widget.Toast;

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

            unencryptedDB = openOrCreateDatabase(
                    externalFile,
                    null,
                    null
            );
        } catch (Exception e) {
            Toast toast = Toast.makeText(context, "Task failed. Did you grant read permissions?", Toast.LENGTH_LONG);
            TextView v = toast.getView().findViewById(R.id.message);
            if (v != null)
                v.setGravity(Gravity.CENTER); toast.show();

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
            Toast toast = Toast.makeText(context, "Task failed. Did you grant read permissions?", Toast.LENGTH_LONG);
            TextView v = toast.getView().findViewById(R.id.message);
            if (v != null)
                v.setGravity(Gravity.CENTER); toast.show();

            e.printStackTrace();
            return;
        }

        new UnencryptedDB(unencryptedDB)
                .importDB(context);
    }
}
