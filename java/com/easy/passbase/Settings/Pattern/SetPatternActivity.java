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

        setPattern = new SetPattern(new SetPatternInitialisation());
    }

    class SetPatternInitialisation extends PatternInitialisation {
        SetPatternInitialisation() {
            super(
                    SetPatternActivity.this,
                    findViewById(R.id.grid_setPattern),
                    findViewById(R.id.traffic_setPattern),
                    findViewById(R.id.txt_setPatternError),
                    getSeekBars(
                            findViewById(R.id.seekBar_setPatternTop),
                            findViewById(R.id.seekBar_setPatternBottom)
                    )
            );
        }
    }

    public void onCellClick(View v) {
        setPattern.onCellClick(v);
    }

    public void onTrafficClick(View v) {
        setPattern.onTrafficClick(v);
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