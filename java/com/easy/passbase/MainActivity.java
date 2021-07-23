package com.easy.passbase;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import static com.easy.passbase.PasswordDB.passwordDB;

public class MainActivity extends AppCompatActivity {
    private OptionsAnimator optionsAnimator;
    private SelectionDisplay selectionDisplay;

    public void setOptionsAnimator(OptionsAnimator anim) { optionsAnimator = anim; }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        PasswordDBHelper dbHelper = new PasswordDBHelper(this);
        passwordDB = dbHelper.getWritableDatabase();

        new GlobalLayoutListenerAdapter(this);
        selectionDisplay = new SelectionDisplay(this);
    }

    public void selectTuple(View v) {
        if (!DgSelectTuple.isOpen) {
            DgSelectTuple dgSelectTuple = new DgSelectTuple(this);
            dgSelectTuple.show(getSupportFragmentManager(), "Select Tuple Dialog");
        }
    }

    public void updateDisplayedTuple(String[] attributes) {
        selectionDisplay.updateDisplayedTuple(attributes);
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
            DgDeleteTuple dgDeleteTuple = new DgDeleteTuple();
            dgDeleteTuple.show(getSupportFragmentManager(), "Delete Tuple Dialog");
        }
    }
}