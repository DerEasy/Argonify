package com.easy.passbase.Main;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewTreeObserver;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import com.easy.passbase.PassGen.PassGenActivity;
import com.easy.passbase.R;
import com.easy.passbase.Settings.SettingsActivity;

import static com.easy.passbase.Main.PasswordDB.passwordDB;
import static com.easy.passbase.Settings.Argon2Utility.argonHash;

public class MainActivity extends AppCompatActivity {
    private OptionsAnimator optionsAnimator;
    SelectionDisplay selectionDisplay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);

        PasswordDBHelper dbHelper = new PasswordDBHelper(this);
        passwordDB = dbHelper.getWritableDatabase();

        new GlobalLayoutListenerAdapter();
        selectionDisplay = new SelectionDisplay(this);
    }

    private void setOptionsAnimator(OptionsAnimator anim) { optionsAnimator = anim; }

    private class GlobalLayoutListenerAdapter {
        private ViewTreeObserver.OnGlobalLayoutListener listener;

        /**Attaches a GlobalLayoutListener to receive the y-position of the closed-state
         * options menu, then automatically instantiates the MainActivity OptionsAnimator
         * and detaches the listener.*/
        private GlobalLayoutListenerAdapter() {
            View setupY = findViewById(R.id.fab_optionAddTuple);

            setupY.getViewTreeObserver().addOnGlobalLayoutListener(listener = () -> {
                setOptionsAnimator(new OptionsAnimator(MainActivity.this, setupY.getY()));
                setupY.getViewTreeObserver().removeOnGlobalLayoutListener(listener);
            });
        }
    }



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