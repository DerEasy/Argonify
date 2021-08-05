package com.easy.passbase.Settings.Pattern;

import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TableLayout;
import android.widget.TableRow;

import androidx.appcompat.content.res.AppCompatResources;

import com.easy.passbase.R;

import java.util.concurrent.ConcurrentLinkedDeque;

public abstract class Pattern {
    private final static int TAG_ROW_INDEX = 0;
    private final static int TAG_COLUMN_INDEX = 1;
    final static int TOP_BAR_INDEX = 0;
    final static int BOTTOM_BAR_INDEX = 1;

    final static int CELLS_PER_SIDE = 7;
    final static int CELLS_IN_TOTAL = CELLS_PER_SIDE * CELLS_PER_SIDE;
    final static int TRAFFIC_CELLS = 3;
    final static int SEEKBAR_AMOUNT = 2;
    final static int MIN_SELECTABLE_CELLS = 8;

    private final ImageButton[][] cell = new ImageButton[CELLS_PER_SIDE][CELLS_PER_SIDE];
    private final ImageButton[] traffic = new ImageButton[TRAFFIC_CELLS];
    private final boolean[][] cellState = new boolean[CELLS_PER_SIDE][CELLS_PER_SIDE];
    private final ConcurrentLinkedDeque<String> patternData = new ConcurrentLinkedDeque<>();
    private final int[] barData = new int[SEEKBAR_AMOUNT];
    private int trafficData;

    int selectedCells = 0;

    Pattern(TableLayout grid, TableRow trafficRow, SeekBar[] bars) {
        new GlobalLayoutListenerAdapter(grid);
        new GlobalLayoutListenerAdapter(trafficRow);
        new SeekBarListenerAdapter(bars);
    }

    private class GlobalLayoutListenerAdapter {
        private ViewTreeObserver.OnGlobalLayoutListener listener;

        /**Gives the okay to start initialising the pattern grid.*/
        private GlobalLayoutListenerAdapter(TableLayout grid) {
            grid.getViewTreeObserver().addOnGlobalLayoutListener(listener = () -> {
                onGridInitialisation(grid,grid.getWidth() / CELLS_PER_SIDE);
                grid.getViewTreeObserver().removeOnGlobalLayoutListener(listener);
            });
        }
        /**Gives the okay to start initialising the traffic light.*/
        private GlobalLayoutListenerAdapter(TableRow trafficRow) {
            trafficRow.getViewTreeObserver().addOnGlobalLayoutListener(listener = () -> {
                onTrafficInitialisation(trafficRow,trafficRow.getWidth() / TRAFFIC_CELLS);
                trafficRow.getViewTreeObserver().removeOnGlobalLayoutListener(listener);
            });
        }
    }

    private class SeekBarListenerAdapter {
        private SeekBarListenerAdapter(SeekBar[] bars) {
            bars[TOP_BAR_INDEX].setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
                public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) { barData[TOP_BAR_INDEX] = progress; }
                public void onStartTrackingTouch(SeekBar seekBar) { seekBar.getThumb().setAlpha(255); }
                public void onStopTrackingTouch(SeekBar seekBar) {}
            });

            bars[BOTTOM_BAR_INDEX].setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
                public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) { barData[BOTTOM_BAR_INDEX] = progress; }
                public void onStartTrackingTouch(SeekBar seekBar) { seekBar.getThumb().setAlpha(255); }
                public void onStopTrackingTouch(SeekBar seekBar) {}
            });
        }
    }


    private void onGridInitialisation(TableLayout grid, int cellSize) {
        final TableRow[] gridRow = new TableRow[CELLS_PER_SIDE];

        for (int i = 0; i < CELLS_PER_SIDE; ++i)
            gridRow[i] = (TableRow) grid.getChildAt(i);

        for (int i = 0; i < CELLS_PER_SIDE; ++i) {
            for (int j = 0; j < CELLS_PER_SIDE; ++j) {
                cell[i][j] = (ImageButton) gridRow[i].getChildAt(j);
                cell[i][j].setLayoutParams(new TableRow.LayoutParams(cellSize, cellSize));
            }
        }
    }

    private void onTrafficInitialisation(TableRow trafficRow, int cellSize) {
        for (int i = 0; i < TRAFFIC_CELLS; ++i) {
            traffic[i] = (ImageButton) trafficRow.getChildAt(i);
            traffic[i].setLayoutParams(new TableRow.LayoutParams(cellSize, cellSize));
        }
    }

    void onCellClick(View v) {
        String cellTag = (String) v.getTag();
        int row = Integer.parseInt(String.valueOf(cellTag.charAt(TAG_ROW_INDEX)));
        int column = Integer.parseInt(String.valueOf(cellTag.charAt(TAG_COLUMN_INDEX)));

        if (!cellState[row][column]) {
            patternData.push(cellTag);
            cellState[row][column] = !cellState[row][column];
            cell[row][column].setImageDrawable(AppCompatResources.getDrawable(v.getContext(), R.drawable.pattern_rectangle_on));
            ++selectedCells;
            onGridUpdate();
        }
    }

    private void setTrafficLight(View v) {
        for (int i = 0; i < TRAFFIC_CELLS; ++i)
            traffic[i].setImageDrawable(AppCompatResources.getDrawable(v.getContext(), R.drawable.pattern_rectangle_off));
        traffic[trafficData].setImageDrawable(AppCompatResources.getDrawable(v.getContext(), R.drawable.pattern_rectangle_on));
    }

    void onTrafficClick(View v) {
        trafficData = Integer.parseInt((String) v.getTag());
        setTrafficLight(v);
    }

    @SuppressWarnings("ConstantConditions")
    void onUndo(View v) {
        if (patternData.isEmpty())
            return;

        int row = Integer.parseInt(String.valueOf(patternData.peek().charAt(TAG_ROW_INDEX)));
        int column = Integer.parseInt(String.valueOf(patternData.peek().charAt(TAG_COLUMN_INDEX)));

        cellState[row][column] = !cellState[row][column];
        cell[row][column].setImageDrawable(AppCompatResources.getDrawable(v.getContext(), R.drawable.pattern_rectangle_off));
        patternData.pop();
        --selectedCells;
        onGridUpdate();
    }

    void onClear(View v) {
        for (int i = 0; i < patternData.size(); ++i)
            new Handler(Looper.getMainLooper()).postDelayed(() -> onUndo(v), i * 50L);
    }

    String getPattern() {
        String[] patternData = this.patternData.toArray(new String[0]);
        StringBuilder pattern = new StringBuilder();

        for (String tag : patternData)
            pattern.append(tag);

        return pattern.toString();
    }

    abstract void onPatternConfirmation();
    abstract void onGridUpdate();
}
