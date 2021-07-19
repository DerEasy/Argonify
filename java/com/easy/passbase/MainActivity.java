package com.easy.passbase;

import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import static com.easy.passbase.PasswordDB.getPassword;
import static com.easy.passbase.PasswordDB.passwordDB;
import static com.easy.passbase.PasswordDBHelper.*;

public class MainActivity extends AppCompatActivity {
    private TextView txtSelectedName;
    private TextView txtSelectedPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        PasswordDBHelper dbHelper = new PasswordDBHelper(this);
        passwordDB = dbHelper.getWritableDatabase();

        txtSelectedName = findViewById(R.id.txt_selectedName);
        txtSelectedPassword = findViewById(R.id.txt_selectedPassword);
    }

    public void openSelectEntry(View v) {
        DgSelectEntry dgSelectEntry = new DgSelectEntry(this);
        dgSelectEntry.show(getSupportFragmentManager(), "Select Entry Dialog");
    }

    public void updateSelectedEntry(int id) {
        Cursor cursor = getPassword(id);
        cursor.moveToFirst();

        txtSelectedName.setText(cursor.getString(cursor.getColumnIndex(COLUMN_NAME)));
        txtSelectedPassword.setText(cursor.getString(cursor.getColumnIndex(COLUMN_PASSWORD)));
    }
}