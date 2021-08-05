package com.easy.passbase.Settings.Pattern;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.easy.passbase.R;

public class SetPatternActivity extends AppCompatActivity {
    private SetPattern setPattern;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.set_pattern);

        setPattern = new SetPattern(this, findViewById(R.id.grid_setPattern));
    }

    public void onCellClick(View v) {
        setPattern.onCellClick(v);
    }

    public void onUndo(View v) {
        setPattern.onUndo(v);
    }

    public void onClear(View v) {
        setPattern.onClear(v);
    }

    public void onPatternConfirmation(View v) {
        setPattern.onPatternConfirmation();
    }
}