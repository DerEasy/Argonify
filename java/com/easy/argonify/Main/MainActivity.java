package com.easy.argonify.Main;

import static com.easy.argonify.Main.SelectionDisplay.NO_ID;
import static com.easy.argonify.Utility.PasswordDBHelper.DATABASE_NAME;
import static com.easy.argonify.Utility.PasswordDBHelper.createDatabase;
import static com.easy.argonify.Utility.RequestApplock.requestApplock;
import static net.sqlcipher.database.SQLiteDatabase.loadLibs;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import com.easy.argonify.Main.Dialogs.DgAddTuple;
import com.easy.argonify.Main.Dialogs.DgDeleteTupleConfirmation;
import com.easy.argonify.Main.Dialogs.DgEditTupleConfirmation;
import com.easy.argonify.Main.Dialogs.DgSelectTuple;
import com.easy.argonify.PassGen.PassGenActivity;
import com.easy.argonify.R;
import com.easy.argonify.Settings.Applock.ApplockPickerActivity;
import com.easy.argonify.Settings.Applock.ApplockStrings;
import com.easy.argonify.Settings.SettingsActivity;

import java.io.File;

public class MainActivity extends AppCompatActivity implements ApplockStrings {
    private OptionsAnimator optionsAnimator;
    public SelectionDisplay selectionDisplay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);

        if (hasApplock() && getIntent().getStringExtra(RAW_KEY) == null) {
            requestApplock(this, REASON_APP_ACCESS, ALLOW_APP_ACCESS);
            finish();
        } else if (getIntent().getStringExtra(RAW_KEY) != null) {
            initialiseDatabase();
            initialiseMainDisplay();
        }
    }

    private boolean hasApplock() {
        SharedPreferences preferences = getSharedPreferences(APPLOCK, Context.MODE_PRIVATE);
        if (preferences.getString(APPLOCK_TYPE, EMPTY).equals(EMPTY)) {
            Intent applockIntent = new Intent(this, ApplockPickerActivity.class);
            startActivity(applockIntent);
            finish();
            return false;
        }
        return true;
    }

    private void initialiseDatabase() {
        loadLibs(this);

        File databaseFile = getDatabasePath(DATABASE_NAME);

        if (createDatabase(databaseFile, getIntent().getStringExtra(RAW_KEY))) {
            Toast.makeText(
                    this,
                    "ERROR: Restart app or device.",
                    Toast.LENGTH_LONG
            ).show();
        }

        getIntent().removeExtra(RAW_KEY);
    }

    private void initialiseMainDisplay() {
        new GlobalLayoutListenerAdapter();
        selectionDisplay = new SelectionDisplay(this);
    }

    private class GlobalLayoutListenerAdapter {
        private ViewTreeObserver.OnGlobalLayoutListener listener;

        /**Attaches a GlobalLayoutListener to receive the y-position of the closed-state
         * options menu, then automatically instantiates the MainActivity OptionsAnimator
         * and detaches the listener.*/
        private GlobalLayoutListenerAdapter() {
            View optionY = findViewById(R.id.fab_optionAddTuple);

            optionY.getViewTreeObserver().addOnGlobalLayoutListener(listener = () -> {
                setOptionsAnimator(new OptionsAnimator(MainActivity.this, optionY.getY()));
                new AppIconAnimator(MainActivity.this);
                optionY.getViewTreeObserver().removeOnGlobalLayoutListener(listener);
            });
        }
    }
    private void setOptionsAnimator(OptionsAnimator anim) {
        optionsAnimator = anim;
    }

    public void selectTuple(View v) {
        if (!DgSelectTuple.isOpen) {
            DgSelectTuple dgSelectTuple = new DgSelectTuple(selectionDisplay);
            dgSelectTuple.show(getSupportFragmentManager(), null);
        }
    }

    public void copyAttributeToClipboard(View v) {
        selectionDisplay.copyAttributeToClipboard(v);
    }

    public void clearAttributeFromClipboard(View v) {
        selectionDisplay.clearAttributeFromClipboard();
    }

    public void options(View v) {
        optionsAnimator.switchState();
    }

    public void addTuple(View v) {
        if (!DgAddTuple.isOpen) {
            optionsAnimator.close();
            DgAddTuple dgAddTuple = new DgAddTuple();
            dgAddTuple.show(getSupportFragmentManager(), null);
        }
    }

    public void deleteTuple(View v) {
        if (!DgDeleteTupleConfirmation.isOpen && selectionDisplay.getSelectionID() == NO_ID) {
            Toast.makeText(this,
                    "Select an entry first.",
                    Toast.LENGTH_SHORT
            ).show();
        }

        else if (!DgDeleteTupleConfirmation.isOpen) {
            optionsAnimator.close();
            DgDeleteTupleConfirmation confirmation = new DgDeleteTupleConfirmation(selectionDisplay);
            confirmation.show(getSupportFragmentManager(), null);
        }
    }

    public void editTuple(View v) {
        if (!DgEditTupleConfirmation.isOpen && selectionDisplay.getSelectionID() == NO_ID) {
            Toast.makeText(this,
                    "Select an entry first.",
                    Toast.LENGTH_SHORT
            ).show();
        }

        else if (!DgEditTupleConfirmation.isOpen) {
            optionsAnimator.close();
            DgEditTupleConfirmation confirmation = new DgEditTupleConfirmation(selectionDisplay);
            confirmation.show(getSupportFragmentManager(), null);
        }
    }

    public void passwordGenerator(View v) {
        Intent passwordGeneratorIntent = new Intent(this, PassGenActivity.class);
        startActivity(passwordGeneratorIntent);
    }

    public void settings(View v) {
        Intent settingsIntent = new Intent(this, SettingsActivity.class);
        startActivity(settingsIntent);
    }
}