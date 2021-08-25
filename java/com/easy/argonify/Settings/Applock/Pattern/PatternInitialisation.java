package com.easy.argonify.Settings.Applock.Pattern;

import static com.easy.argonify.Settings.Applock.Pattern.Pattern.BOTTOM_BAR_INDEX;
import static com.easy.argonify.Settings.Applock.Pattern.Pattern.SEEKBAR_AMOUNT;
import static com.easy.argonify.Settings.Applock.Pattern.Pattern.TOP_BAR_INDEX;

import android.widget.SeekBar;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

abstract class PatternInitialisation {
    final AppCompatActivity parentActivity;
    final TableLayout grid;
    final TableRow trafficRow;
    final SeekBar[] bars;
    final TextView txtError;

    PatternInitialisation(
            AppCompatActivity parentActivity,
            TableLayout grid,
            TableRow trafficRow,
            TextView txtError,
            SeekBar[] bars
    ) {
        this.parentActivity = parentActivity;
        this.grid = grid;
        this.trafficRow = trafficRow;
        this.txtError = txtError;
        this.bars = bars;
    }

    static SeekBar[] getSeekBars(SeekBar topBar, SeekBar bottomBar) {
        SeekBar[] bars = new SeekBar[SEEKBAR_AMOUNT];
        bars[TOP_BAR_INDEX] = topBar;
        bars[BOTTOM_BAR_INDEX] = bottomBar;
        bars[TOP_BAR_INDEX].getThumb().setAlpha(0);
        bars[BOTTOM_BAR_INDEX].getThumb().setAlpha(0);

        return bars;
    }
}
