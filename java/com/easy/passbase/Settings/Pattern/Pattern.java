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
import android.widget.TextView;

import androidx.appcompat.content.res.AppCompatResources;

import com.easy.passbase.R;
import com.easy.passbase.Settings.ApplockStrings;

import java.util.concurrent.ConcurrentLinkedDeque;

abstract class Pattern implements ApplockStrings {
    private final static int TAG_ROW_INDEX = 0;
    private final static int TAG_COLUMN_INDEX = 1;
    final static int TOP_BAR_INDEX = 0;
    final static int BOTTOM_BAR_INDEX = 1;

    final static int MIN_SELECTABLE_CELLS = 8;
    final static int CELLS_PER_SIDE = 7;
    final static int TRAFFIC_CELLS = 3;
    final static int SEEKBAR_AMOUNT = 2;

    private final ImageButton[][] cell = new ImageButton[CELLS_PER_SIDE][CELLS_PER_SIDE];
    private final ImageButton[] traffic = new ImageButton[TRAFFIC_CELLS];
    private final boolean[][] cellState = new boolean[CELLS_PER_SIDE][CELLS_PER_SIDE];
    private final ConcurrentLinkedDeque<String> patternData = new ConcurrentLinkedDeque<>();
    private final int[] barData = new int[SEEKBAR_AMOUNT];

    int selectedCells = 0;
    int trafficData = -1;
    boolean[] usedSeekBar = new boolean[SEEKBAR_AMOUNT];
    final TextView txtError;
    String favCell = EMPTY;

    Pattern(PatternInitialisation initData) {
        new GlobalLayoutListenerAdapter(initData.grid, initData.trafficRow);
        new SeekBarListenerAdapter(initData.bars);
        txtError = initData.txtError;
    }

    private class GlobalLayoutListenerAdapter {
        private ViewTreeObserver.OnGlobalLayoutListener listener;

        /**Gives the okay to start initialising the pattern and traffic grids.*/
        private GlobalLayoutListenerAdapter(TableLayout grid, TableRow trafficRow) {
            grid.getViewTreeObserver().addOnGlobalLayoutListener(listener = () -> {
                onGridInitialisation(grid, trafficRow, grid.getWidth() / CELLS_PER_SIDE);
                grid.getViewTreeObserver().removeOnGlobalLayoutListener(listener);
            });
        }
    }

    private class SeekBarListenerAdapter {
        private SeekBarListenerAdapter(SeekBar[] bars) {
            bars[TOP_BAR_INDEX].setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
                public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                    barData[TOP_BAR_INDEX] = progress;
                }
                public void onStartTrackingTouch(SeekBar seekBar) {
                    seekBar.getThumb().setAlpha(255);
                }
                public void onStopTrackingTouch(SeekBar seekBar) {
                    usedSeekBar[TOP_BAR_INDEX] = true;
                }
            });

            bars[BOTTOM_BAR_INDEX].setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
                public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                    barData[BOTTOM_BAR_INDEX] = progress;
                }
                public void onStartTrackingTouch(SeekBar seekBar) {
                    seekBar.getThumb().setAlpha(255);
                }
                public void onStopTrackingTouch(SeekBar seekBar) {
                    usedSeekBar[BOTTOM_BAR_INDEX] = true;
                }
            });
        }
    }


    private void onGridInitialisation(TableLayout grid, TableRow trafficRow, int cellSize) {
        final TableRow[] gridRow = new TableRow[CELLS_PER_SIDE];

        for (int i = 0; i < CELLS_PER_SIDE; ++i)
            gridRow[i] = (TableRow) grid.getChildAt(i);

        for (int i = 0; i < CELLS_PER_SIDE; ++i) {
            for (int j = 0; j < CELLS_PER_SIDE; ++j) {
                cell[i][j] = (ImageButton) gridRow[i].getChildAt(j);
                cell[i][j].setLayoutParams(new TableRow.LayoutParams(cellSize, cellSize));
            }
        }

        onTrafficInitialisation(trafficRow, cellSize);
    }

    private void onTrafficInitialisation(TableRow trafficRow, int cellSize) {
        for (int i = 0; i < TRAFFIC_CELLS; ++i) {
            traffic[i] = (ImageButton) trafficRow.getChildAt(i);
            traffic[i].setLayoutParams(new TableRow.LayoutParams(cellSize, cellSize));
        }
    }

    boolean noErrors() {
        if (favCell.equals(EMPTY)) {
            txtError.setText(PATTERN_ERROR_MISSING_FAVOURITE);
            return false;
        }
        if (trafficData == -1) {
            txtError.setText(PATTERN_ERROR_MISSING_TRAFFICLIGHT);
            return false;
        }
        if (!usedSeekBar[TOP_BAR_INDEX] || !usedSeekBar[BOTTOM_BAR_INDEX]) {
            txtError.setText(PATTERN_ERROR_MISSING_SEEKBAR);
            return false;
        }

        return true;
    }

    void onCellClick(View v) {
        String cellTag = (String) v.getTag();
        int row = Integer.parseInt(String.valueOf(cellTag.charAt(TAG_ROW_INDEX)));
        int column = Integer.parseInt(String.valueOf(cellTag.charAt(TAG_COLUMN_INDEX)));

        if (!cellState[row][column]) {
            turnOnCell(v, cellTag, row, column);
        } else {
            removeOldFavCell(v);
            setFavCell(v, cellTag, row, column);
        }
    }

    private void turnOnCell(View v, String cellTag, int row, int column) {
        patternData.push(cellTag);
        cellState[row][column] = !cellState[row][column];
        cell[row][column].setImageDrawable(AppCompatResources.getDrawable(v.getContext(), R.drawable.pattern_rectangle_on));
        ++selectedCells;
        onCellToggled();
    }

    private void turnOffCell(View v, int row, int column) {
        cellState[row][column] = !cellState[row][column];
        cell[row][column].setImageDrawable(AppCompatResources.getDrawable(v.getContext(), R.drawable.pattern_rectangle_off));
        patternData.pop();
        --selectedCells;
        onCellToggled();
    }

    private void setFavCell(View v, String cellTag, int row, int column) {
        favCell = cellTag;
        cell[row][column].setImageDrawable(AppCompatResources.getDrawable(v.getContext(), R.drawable.pattern_rectangle_fav));
    }

    private void removeOldFavCell(View v) {
        if (!favCell.equals(EMPTY)) {
            int oldFavRow = Integer.parseInt(String.valueOf(favCell.charAt(TAG_ROW_INDEX)));
            int oldFavColumn = Integer.parseInt(String.valueOf(favCell.charAt(TAG_COLUMN_INDEX)));
            cell[oldFavRow][oldFavColumn].setImageDrawable(AppCompatResources.getDrawable(v.getContext(), R.drawable.pattern_rectangle_on));
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
        if (patternData.peek().equals(favCell))
            favCell = EMPTY;

        int row = Integer.parseInt(String.valueOf(patternData.peek().charAt(TAG_ROW_INDEX)));
        int column = Integer.parseInt(String.valueOf(patternData.peek().charAt(TAG_COLUMN_INDEX)));

        turnOffCell(v, row, column);
    }

    void onClear(View v) {
        for (int i = 0; i < patternData.size(); ++i)
            new Handler(Looper.getMainLooper()).postDelayed(() -> onUndo(v), i * 50L);
    }

    String getHashedPattern() {
        PatternEncoder encoder = new PatternEncoder(patternData, favCell, trafficData, barData);
        return encoder.getHashedPattern();
    }

    boolean inputPatternMatches(String requestedPattern) {
        PatternEncoder encoder = new PatternEncoder(patternData, favCell, trafficData, barData);
        return encoder.inputPatternMatches(requestedPattern);
    }

    abstract void onPatternConfirmation();
    abstract void onCellToggled();
}
