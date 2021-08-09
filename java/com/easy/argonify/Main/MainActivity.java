package com.easy.argonify.Main;

import static com.easy.argonify.Main.PasswordDBHelper.DATABASE_NAME;
import static com.easy.argonify.Main.PasswordDBHelper.createDatabase;
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

import com.easy.argonify.PassGen.PassGenActivity;
import com.easy.argonify.R;
import com.easy.argonify.Settings.ApplockPickerActivity;
import com.easy.argonify.Settings.ApplockStrings;
import com.easy.argonify.Settings.Pattern.RequestPatternActivity;
import com.easy.argonify.Settings.SettingsActivity;

import java.io.File;

public class MainActivity extends AppCompatActivity implements ApplockStrings {
    private OptionsAnimator optionsAnimator;
    SelectionDisplay selectionDisplay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);

        if (hasApplock() && getIntent().getStringExtra(RAW_KEY) == null) {
            showApplock();
            finish();
        } else {
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
        if (createDatabase(databaseFile, getIntent().getStringExtra(RAW_KEY)))
            Toast.makeText(this, "An error has occurred. Database has been reset.", Toast.LENGTH_LONG).show();

        getIntent().removeExtra(RAW_KEY);
    }

    private void initialiseMainDisplay() {
        new GlobalLayoutListenerAdapter();
        selectionDisplay = new SelectionDisplay(this);
    }

    private void showApplock() {
        SharedPreferences preferences = getSharedPreferences(APPLOCK, Context.MODE_PRIVATE);
        Intent applockRequest = new Intent(this, RequestPatternActivity.class);

        applockRequest.putExtra(REQUESTED_PATTERN, preferences.getString(APPLOCK_KEY, null));
        applockRequest.putExtra(ACTION_ON_CONFIRM, ALLOW_APP_ACCESS);
        applockRequest.putExtra(REQUEST_REASON, PATTERN_REASON_APP_ACCESS);
        startActivity(applockRequest);
        finish();
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
    private void setOptionsAnimator(OptionsAnimator anim) { optionsAnimator = anim; }



    public void onDisplayUpdate(String[] attributes) {
        selectionDisplay.onDisplayUpdate(attributes);
    }

    public void selectTuple(View v) {
        if (!DgSelectTuple.isOpen) {
            DgSelectTuple dgSelectTuple = new DgSelectTuple(this);
            dgSelectTuple.show(getSupportFragmentManager(), "Select Tuple Dialog");
        }
    }

    public void copyAttributeToClipboard(View v) {
        selectionDisplay.copyAttributeToClipboard(v);
    }

    public void options(View v) {
        optionsAnimator.switchState();
    }

    public void addTuple(View v) {
        if (!DgAddTuple.isOpen) {
            optionsAnimator.close();
            DgAddTuple dgAddTuple = new DgAddTuple();
            dgAddTuple.show(getSupportFragmentManager(), "Add Tuple Dialog");
        }
    }

    public void deleteTuple(View v) {
        if (!DgDeleteTuple.isOpen) {
            optionsAnimator.close();
            DgDeleteTuple dgDeleteTuple = new DgDeleteTuple(selectionDisplay);
            dgDeleteTuple.show(getSupportFragmentManager(), "Delete Tuple Dialog");
        }
    }

    public void editTuple(View v) {
        if (!DgEditTuple.isOpen) {
            optionsAnimator.close();
            DgEditTuple dgEditTuple = new DgEditTuple(this);
            dgEditTuple.show(getSupportFragmentManager(), "Edit Tuple Dialog");
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