package com.easy.passbase;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import static com.easy.passbase.PasswordDB.getPassword;
import static com.easy.passbase.PasswordDB.passwordDB;
import static com.easy.passbase.PasswordDBHelper.AMOUNT_OF_MAIN_ATTRIBUTES;
import static com.easy.passbase.PasswordDBHelper.COLUMN_EMAIL;
import static com.easy.passbase.PasswordDBHelper.COLUMN_NAME;
import static com.easy.passbase.PasswordDBHelper.COLUMN_NOTES;
import static com.easy.passbase.PasswordDBHelper.COLUMN_PASSWORD;
import static com.easy.passbase.PasswordDBHelper.COLUMN_USERNAME;
import static com.easy.passbase.PasswordDBHelper.MAIN_ATTRIBUTES;

public class MainActivity extends AppCompatActivity {
    private TextView txtSelectedName;
    private TextView txtSelectedPassword;
    private TextView txtSelectedEmail;
    private TextView txtSelectedUsername;
    private TextView txtSelectedNotes;
    private ImageButton ibtCopyPassword;
    private ImageButton ibtCopyEmail;
    private ImageButton ibtCopyUsername;

    private OptionsAnimator animOptions;

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

        View animSetup = findViewById(R.id.fab_optionAddTuple);
        animSetup.getViewTreeObserver().addOnGlobalLayoutListener(() ->
                animOptions = new OptionsAnimator(this, animSetup.getY()));
    }

    public void options(View v) {
        animOptions.switchState();
    }

    public void addTuple(View v) {
        animOptions.close();
        DgAddTuple dgAddTuple = new DgAddTuple();
        dgAddTuple.show(getSupportFragmentManager(), "Add Tuple Dialog");
    }

    public void selectTuple(View v) {
        animOptions.close();
        DgSelectTuple dgSelectTuple = new DgSelectTuple(this);
        dgSelectTuple.show(getSupportFragmentManager(), "Select Tuple Dialog");
    }

    public void updateDisplayedTuple(int id) {
        Cursor cursor = getPassword(id);
        cursor.moveToFirst();

        String[] tuple = new String[AMOUNT_OF_MAIN_ATTRIBUTES];
        for (int i = 0; i < AMOUNT_OF_MAIN_ATTRIBUTES; ++i) {
            tuple[i] = getAttribute(cursor, MAIN_ATTRIBUTES[i]);
            setTextViewCheckNull(MAIN_ATTRIBUTES[i], tuple[i]);
        }
        setCopyButtonVisibility();
    }

    public void copyAttribute(View v) {
        //If this fails, check if the strings of the tags match with the table attributes in the db
        String attribute = (String) getTextViewByColumn((String) v.getTag()).getText();

        ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
        ClipData clip = ClipData.newPlainText("Clipped " + (String) v.getTag(), attribute);
        clipboard.setPrimaryClip(clip);

        Toast.makeText(
                this,
                String.format("Copied %s to clipboard", (String) v.getTag()),
                Toast.LENGTH_SHORT
        ).show();
    }

    private String getAttribute(Cursor cursor, String column) {
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

    private void setTextViewCheckNull(String column, String text) {
        TextView textView = getTextViewByColumn(column);
        if (textView == null)
            return;

        if (text == null || text.isEmpty())
            textView.setText(R.string.unavailable_info);
        else
            textView.setText(text);
    }

    @SuppressWarnings("ConstantConditions")
    private void setCopyButtonVisibility() {
        //Index 1 to 3 meaning only Password, Email and Username are being checked
        //as these are the only ones with copy buttons
        for (int i = 1; i < AMOUNT_OF_MAIN_ATTRIBUTES - 1; ++i) {
            if (getTextViewByColumn(MAIN_ATTRIBUTES[i]).getText().equals(getString(R.string.unavailable_info)))
                getImageButtonByColumn(MAIN_ATTRIBUTES[i]).setVisibility(View.INVISIBLE);
            else
                getImageButtonByColumn(MAIN_ATTRIBUTES[i]).setVisibility(View.VISIBLE);
        }
    }
}