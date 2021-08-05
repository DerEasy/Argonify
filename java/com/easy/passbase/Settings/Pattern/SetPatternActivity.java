package com.easy.passbase.Settings.Pattern;

import static com.easy.passbase.Settings.Pattern.Pattern.TOP_BAR_INDEX;
import static com.easy.passbase.Settings.Pattern.Pattern.BOTTOM_BAR_INDEX;

import android.os.Bundle;
import android.view.View;
import android.widget.SeekBar;
import android.widget.TableLayout;
import android.widget.TableRow;

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

    class SetPatternInitialisation {
        final SetPatternActivity parentActivity;
        final TableLayout grid;
        final TableRow trafficRow;
        final SeekBar[] bars;

        SetPatternInitialisation() {
            parentActivity = SetPatternActivity.this;
            grid = findViewById(R.id.grid_setPattern);
            trafficRow = findViewById(R.id.traffic_setPattern);
            bars = getSeekBars();
        }

        private SeekBar[] getSeekBars() {
            SeekBar[] bars = new SeekBar[2];
            bars[TOP_BAR_INDEX] = findViewById(R.id.seekBar_setPatternTop);
            bars[BOTTOM_BAR_INDEX] = findViewById(R.id.seekBar_setPatternBottom);
            bars[TOP_BAR_INDEX].getThumb().setAlpha(0);
            bars[BOTTOM_BAR_INDEX].getThumb().setAlpha(0);

            return bars;
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