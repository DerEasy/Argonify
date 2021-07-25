package com.easy.passbase.Main;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.easy.passbase.PassGen.PassGenActivity;
import com.easy.passbase.R;

import static com.easy.passbase.Main.PasswordDB.passwordDB;

public class MainActivity extends AppCompatActivity {
    private SelectionDisplay selectionDisplay;
    private OptionsAnimator optionsAnimator;

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
            DgDeleteTuple dgDeleteTuple = new DgDeleteTuple();
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
}