package com.easy.passbase.Settings;

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

    public void onPatternConfirmation(View v) {
        setPattern.onPatternConfirmation();
    }
}