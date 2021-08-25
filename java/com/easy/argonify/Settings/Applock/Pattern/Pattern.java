package com.easy.argonify.Settings.Applock.Pattern;

import android.graphics.drawable.Drawable;
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

import com.easy.argonify.R;
import com.easy.argonify.Settings.Applock.ApplockStrings;

import java.util.concurrent.ConcurrentLinkedDeque;

abstract class Pattern implements ApplockStrings {
    private final static int TAG_ROW_INDEX = 0;
    private final static int TAG_COLUMN_INDEX = 1;
    final static int TOP_BAR_INDEX = 0;
    final static int BOTTOM_BAR_INDEX = 1;

    final static int CELLS_PER_SIDE = 7;
    final static int TRAFFIC_CELLS = 3;
    final static int SEEKBAR_AMOUNT = 2;

    private final static int CELL_ON = R.drawable.pattern_rectangle_on;
    private final static int CELL_OFF = R.drawable.pattern_rectangle_off;
    private final static int CELL_FAV = R.drawable.pattern_rectangle_fav;

    private final ImageButton[][] CELL = new ImageButton[CELLS_PER_SIDE][CELLS_PER_SIDE];
    private final ImageButton[] TRAFFIC = new ImageButton[TRAFFIC_CELLS];
    private final boolean[][] CELL_STATE = new boolean[CELLS_PER_SIDE][CELLS_PER_SIDE];
    private final ConcurrentLinkedDeque<String> PATTERN_DATA = new ConcurrentLinkedDeque<>();
    private final int[] BAR_DATA = new int[SEEKBAR_AMOUNT];

    int selectedCells = 0;
    int trafficData = -1;
    boolean[] usedSeekBar = new boolean[SEEKBAR_AMOUNT];
    String favCell = EMPTY;

    Pattern(PatternInitialisation initData) {
        new GlobalLayoutListenerAdapter(initData.grid, initData.trafficRow);
        new SeekBarListenerAdapter(initData.bars);
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
                    BAR_DATA[TOP_BAR_INDEX] = progress;
                }
                public void onStartTrackingTouch(SeekBar seekBar) {
                    seekBar.getThumb().setAlpha(255);
                }
                public void onStopTrackingTouch(SeekBar seekBar) {
                    usedSeekBar[TOP_BAR_INDEX] = true;
                    onControlStateChange();
                }
            });

            bars[BOTTOM_BAR_INDEX].setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
                public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                    BAR_DATA[BOTTOM_BAR_INDEX] = progress;
                }
                public void onStartTrackingTouch(SeekBar seekBar) {
                    seekBar.getThumb().setAlpha(255);
                }
                public void onStopTrackingTouch(SeekBar seekBar) {
                    usedSeekBar[BOTTOM_BAR_INDEX] = true;
                    onControlStateChange();
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
                CELL[i][j] = (ImageButton) gridRow[i].getChildAt(j);
                CELL[i][j].setLayoutParams(new TableRow.LayoutParams(cellSize, cellSize));
            }
        }

        onTrafficInitialisation(trafficRow, cellSize);
    }

    private void onTrafficInitialisation(TableRow trafficRow, int cellSize) {
        for (int i = 0; i < TRAFFIC_CELLS; ++i) {
            TRAFFIC[i] = (ImageButton) trafficRow.getChildAt(i);
            TRAFFIC[i].setLayoutParams(new TableRow.LayoutParams(cellSize, cellSize));
        }
    }

    void onCellClick(View v) {
        String cellTag = (String) v.getTag();
        int row = parseInt(cellTag, TAG_ROW_INDEX);
        int column = parseInt(cellTag, TAG_COLUMN_INDEX);

        if (!CELL_STATE[row][column])
            turnOnCell(v, cellTag, row, column);
        else {
            removeOldFavCell(v);
            setFavCell(v, cellTag, row, column);
        }

        onControlStateChange();
    }

    private void turnOnCell(View v, String cellTag, int row, int column) {
        PATTERN_DATA.push(cellTag);
        CELL_STATE[row][column] = !CELL_STATE[row][column];
        CELL[row][column].setImageDrawable(getDrawable(v, CELL_ON));
        ++selectedCells;
    }

    private void turnOffCell(View v, int row, int column) {
        CELL_STATE[row][column] = !CELL_STATE[row][column];
        CELL[row][column].setImageDrawable(getDrawable(v, CELL_OFF));
        PATTERN_DATA.pop();
        --selectedCells;
    }

    private void setFavCell(View v, String cellTag, int row, int column) {
        favCell = cellTag;
        CELL[row][column].setImageDrawable(getDrawable(v, CELL_FAV));
    }

    private void removeOldFavCell(View v) {
        if (!favCell.equals(EMPTY)) {
            int oldFavRow = parseInt(favCell, TAG_ROW_INDEX);
            int oldFavColumn = parseInt(favCell, TAG_COLUMN_INDEX);
            CELL[oldFavRow][oldFavColumn].setImageDrawable(getDrawable(v, CELL_ON));
        }
    }

    void onTrafficClick(View v) {
        trafficData = Integer.parseInt((String) v.getTag());
        setTrafficLight(v);
        onControlStateChange();
    }

    private void setTrafficLight(View v) {
        for (int i = 0; i < TRAFFIC_CELLS; ++i)
            TRAFFIC[i].setImageDrawable(getDrawable(v, CELL_OFF));
        TRAFFIC[trafficData].setImageDrawable(getDrawable(v, CELL_ON));
    }

    @SuppressWarnings("ConstantConditions")
    void onUndo(View v) {
        if (PATTERN_DATA.isEmpty())
            return;
        if (PATTERN_DATA.peek().equals(favCell))
            favCell = EMPTY;

        int row = parseInt(PATTERN_DATA.peek(), TAG_ROW_INDEX);
        int column = parseInt(PATTERN_DATA.peek(), TAG_COLUMN_INDEX);

        turnOffCell(v, row, column);
        onControlStateChange();
    }

    void onClear(View v) {
        for (int i = 0; i < PATTERN_DATA.size(); ++i)
            new Handler(Looper.getMainLooper()).postDelayed(() ->
                    onUndo(v), i * 50L);
    }

    String getHashedPattern() {
        return new PatternEncoder(PATTERN_DATA, favCell, trafficData, BAR_DATA)
                .getHashedKey();
    }

    String getRawPattern() {
        System.out.println(new PatternEncoder(PATTERN_DATA, favCell, trafficData, BAR_DATA).getRawKey());
        return new PatternEncoder(PATTERN_DATA, favCell, trafficData, BAR_DATA)
                .getRawKey();
    }

    boolean inputPatternMatches(String requestedPattern) {
        return new PatternEncoder(PATTERN_DATA, favCell, trafficData, BAR_DATA)
                .inputKeyMatches(requestedPattern);
    }

    private int parseInt(String s, int index) {
        return Integer.parseInt(String.valueOf(s.charAt(index)));
    }

    private Drawable getDrawable(View v, int resId) {
        return AppCompatResources.getDrawable(v.getContext(), resId);
    }

    abstract void onPatternConfirmation();
    abstract void onControlStateChange();
}
