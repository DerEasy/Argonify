package com.easy.passbase.Settings.Pattern;

import static com.easy.passbase.Settings.Pattern.Pattern.BOTTOM_BAR_INDEX;
import static com.easy.passbase.Settings.Pattern.Pattern.TOP_BAR_INDEX;

import android.os.Bundle;
import android.view.View;
import android.widget.SeekBar;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.easy.passbase.R;
import com.easy.passbase.Settings.ApplockStrings;

public class RequestPatternActivity extends AppCompatActivity implements ApplockStrings {
    private RequestPattern requestPattern;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.request_pattern);

        setRequestReason();
        requestPattern = new RequestPattern(new RequestPatternInitialisation());
    }

    class RequestPatternInitialisation {
        final RequestPatternActivity parentActivity;
        final TableLayout grid;
        final TableRow trafficRow;
        final SeekBar[] bars;
        final String requestedPattern;
        final String actionOnConfirm;

        RequestPatternInitialisation() {
            parentActivity = RequestPatternActivity.this;
            grid = findViewById(R.id.grid_reqPattern);
            trafficRow = findViewById(R.id.traffic_reqPattern);
            bars = getSeekBars();

            String p = getIntent().getStringExtra(REQUESTED_PATTERN);
            if (p != null) requestedPattern = p;
            else           requestedPattern = EMPTY;

            String a = getIntent().getStringExtra(ACTION_ON_CONFIRM);
            if (a != null) actionOnConfirm = a;
            else           actionOnConfirm = EMPTY;
        }

        private SeekBar[] getSeekBars() {
            SeekBar[] bars = new SeekBar[2];
            bars[TOP_BAR_INDEX] = findViewById(R.id.seekBar_reqPatternTop);
            bars[BOTTOM_BAR_INDEX] = findViewById(R.id.seekBar_reqPatternBottom);
            bars[TOP_BAR_INDEX].getThumb().setAlpha(0);
            bars[BOTTOM_BAR_INDEX].getThumb().setAlpha(0);

            return bars;
        }
    }

    private void setRequestReason() {
        TextView reasonView = findViewById(R.id.txt_reqPatternReason);
        reasonView.setText(getIntent().getStringExtra(REQUEST_REASON));
    }

    public void onCellClick(View v) {
        requestPattern.onCellClick(v);
    }

    public void onTrafficClick(View v) {
        requestPattern.onTrafficClick(v);
    }

    public void onUndo(View v) {
        requestPattern.onUndo(v);
    }

    public void onClear(View v) {
        requestPattern.onClear(v);
    }

    public void onPatternConfirmation(View v) {
        requestPattern.onPatternConfirmation();
    }
}