package com.easy.passbase;

import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import static com.easy.passbase.PasswordDB.getPassword;
import static com.easy.passbase.PasswordDB.passwordDB;
import static com.easy.passbase.PasswordDBHelper.AMOUNT_OF_SELECTABLE_COLUMNS;
import static com.easy.passbase.PasswordDBHelper.COLUMN_EMAIL;
import static com.easy.passbase.PasswordDBHelper.COLUMN_NAME;
import static com.easy.passbase.PasswordDBHelper.COLUMN_NOTES;
import static com.easy.passbase.PasswordDBHelper.COLUMN_PASSWORD;
import static com.easy.passbase.PasswordDBHelper.COLUMN_USERNAME;
import static com.easy.passbase.PasswordDBHelper.selectableColumns;

public class MainActivity extends AppCompatActivity {
    private Cursor currentCursor;
    private TextView txtSelectedName;
    private TextView txtSelectedPassword;
    private TextView txtSelectedEmail;
    private TextView txtSelectedUsername;
    private TextView txtSelectedNotes;
    private ImageButton ibtCopyPassword;
    private ImageButton ibtCopyEmail;
    private ImageButton ibtCopyUsername;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        PasswordDBHelper dbHelper = new PasswordDBHelper(this);
        passwordDB = dbHelper.getWritableDatabase();

        txtSelectedName = findViewById(R.id.txt_selectedName);
        txtSelectedPassword = findViewById(R.id.txt_selectedPassword);
        txtSelectedEmail = findViewById(R.id.txt_selectedEmail);
        txtSelectedUsername = findViewById(R.id.txt_selectedUsername);
        txtSelectedNotes = findViewById(R.id.txt_selectedNotes);

        ibtCopyPassword = findViewById(R.id.ibt_copyPassword);
        ibtCopyEmail = findViewById(R.id.ibt_copyEmail);
        ibtCopyUsername = findViewById(R.id.ibt_copyUsername);

        checkVisibility();
    }

    public void openSelectEntry(View v) {
        DgSelectEntry dgSelectEntry = new DgSelectEntry(this);
        dgSelectEntry.show(getSupportFragmentManager(), "Select Entry Dialog");
    }

    public void updateSelectedEntry(int id) {
        Cursor cursor = getPassword(id);
        cursor.moveToFirst();
        currentCursor = cursor;

        String[] entry = new String[AMOUNT_OF_SELECTABLE_COLUMNS];
        for (int i = 0; i < AMOUNT_OF_SELECTABLE_COLUMNS; ++i) {
            entry[i] = getEntry(cursor, selectableColumns[i]);
            setTextViewCheckNull(selectableColumns[i], entry[i]);
        }
        checkVisibility();
    }

    private String getEntry(Cursor cursor, String column) {
        return cursor.getString(cursor.getColumnIndex(column));
    }

    private TextView getTextViewByColumn(String column) {
        switch (column) {
            case COLUMN_NAME:
                return txtSelectedName;
            case COLUMN_PASSWORD:
                return txtSelectedPassword;
            case COLUMN_EMAIL:
                return txtSelectedEmail;
            case COLUMN_USERNAME:
                return txtSelectedUsername;
            case COLUMN_NOTES:
                return txtSelectedNotes;
            default:
                return null;
        }
    }

    private ImageButton getImageButtonByColumn(String column) {
        switch (column) {
            case COLUMN_PASSWORD:
                return ibtCopyPassword;
            case COLUMN_EMAIL:
                return ibtCopyEmail;
            case COLUMN_USERNAME:
                return ibtCopyUsername;
            default:
                return null;
        }
    }

    @SuppressWarnings("ConstantConditions")
    private void checkVisibility() {
        //Index 1 to 3 meaning only Password, Email and Username are being checked
        //as these are the only ones with copy buttons
        for (int i = 1; i < AMOUNT_OF_SELECTABLE_COLUMNS - 1; ++i) {
            if (getTextViewByColumn(selectableColumns[i]).getText().equals(getString(R.string.unavailable_info)))
                getImageButtonByColumn(selectableColumns[i]).setVisibility(View.INVISIBLE);
            else
                getImageButtonByColumn(selectableColumns[i]).setVisibility(View.VISIBLE);
        }
    }

    private void setTextViewCheckNull(String column, String text) {
        TextView textView = getTextViewByColumn(column);
        if (textView == null)
            return;

        if (text == null || text.isEmpty())
            textView.setText(R.string.unavailable_info);
        else
            textView.setText(text);
    }
}