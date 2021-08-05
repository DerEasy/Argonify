package com.easy.passbase.Settings.Pattern;

import android.os.Handler;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.ImageButton;
import android.widget.TableLayout;
import android.widget.TableRow;

import androidx.appcompat.content.res.AppCompatResources;

import com.easy.passbase.R;

import java.util.concurrent.ConcurrentLinkedDeque;

public abstract class Pattern {
    private final static int TAG_ROW_INDEX = 0;
    private final static int TAG_COLUMN_INDEX = 1;

    final static int CELLS_PER_SIDE = 7;
    final static int CELLS_IN_TOTAL = CELLS_PER_SIDE * CELLS_PER_SIDE;
    final static int MIN_SELECTABLE_CELLS = 4;

    private final ImageButton[][] cell = new ImageButton[CELLS_PER_SIDE][CELLS_PER_SIDE];
    private final ConcurrentLinkedDeque<String> patternData = new ConcurrentLinkedDeque<>();
    private final boolean[][] cellState = new boolean[CELLS_PER_SIDE][CELLS_PER_SIDE];

    int selectedCells = 0;

    Pattern(TableLayout grid) {
        new GlobalLayoutListenerAdapter(grid);
    }

    private class GlobalLayoutListenerAdapter {
        private ViewTreeObserver.OnGlobalLayoutListener listener;

        /**Gives the okay to start initialising the pattern grid.*/
        private GlobalLayoutListenerAdapter(TableLayout grid) {
            grid.getViewTreeObserver().addOnGlobalLayoutListener(listener = () -> {
                onGridInitialisation(grid, grid.getWidth() / CELLS_PER_SIDE);
                grid.getViewTreeObserver().removeOnGlobalLayoutListener(listener);
            });
        }
    }


    private void onGridInitialisation(TableLayout grid, int cellSize) {
        final TableRow[] row = new TableRow[CELLS_PER_SIDE];

        for (int i = 0; i < CELLS_PER_SIDE; ++i)
            row[i] = (TableRow) grid.getChildAt(i);

        for (int i = 0; i < CELLS_PER_SIDE; ++i) {
            for (int j = 0; j < CELLS_PER_SIDE; ++j) {
                cell[i][j] = (ImageButton) row[i].getChildAt(j);
                cell[i][j].setLayoutParams(new TableRow.LayoutParams(cellSize, cellSize));
            }
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
            new Handler().postDelayed(() -> onUndo(v), i * 50L);
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
