package com.easy.passbase.Settings;

import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.ImageButton;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.Toast;

import androidx.appcompat.content.res.AppCompatResources;

import com.easy.passbase.R;

public abstract class Pattern {
    final static int CELLS_PER_SIDE = 7;
    final static int CELLS_IN_TOTAL = CELLS_PER_SIDE * CELLS_PER_SIDE;

    final static int MIN_SELECTABLE_CELLS = 4;
    private final static int MAX_SELECTABLE_CELLS = 24;

    private final ImageButton[][] cell = new ImageButton[CELLS_PER_SIDE][CELLS_PER_SIDE];
    final boolean[][] cellState = new boolean[CELLS_PER_SIDE][CELLS_PER_SIDE];

    private int cellSize;
    int selectedCells = 0;

    Pattern(TableLayout grid) {
        new GlobalLayoutListenerAdapter(grid);
    }

    private class GlobalLayoutListenerAdapter {
        private ViewTreeObserver.OnGlobalLayoutListener listener;

        /**Gives the okay to start initialising the pattern grid.*/
        private GlobalLayoutListenerAdapter(TableLayout grid) {
            grid.getViewTreeObserver().addOnGlobalLayoutListener(listener = () -> {
                onGridInitialisation(grid);
                grid.getViewTreeObserver().removeOnGlobalLayoutListener(listener);
            });
        }
    }



    public void onGridInitialisation(TableLayout grid) {
        cellSize = grid.getWidth() / CELLS_PER_SIDE;
        initialiseCells(grid);
    }

    private void initialiseCells(TableLayout grid) {
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

    public void onCellClick(View v) {
        String cellTag = (String) v.getTag();
        int row = Integer.parseInt(String.valueOf(cellTag.charAt(0)));
        int column = Integer.parseInt(String.valueOf(cellTag.charAt(1)));

        if (cellState[row][column]) {
            cell[row][column].setImageDrawable(AppCompatResources.getDrawable(v.getContext(), R.drawable.pattern_rectangle_off));
            --selectedCells;
        }
        else if (selectedCells < MAX_SELECTABLE_CELLS){
            cell[row][column].setImageDrawable(AppCompatResources.getDrawable(v.getContext(), R.drawable.pattern_rectangle_on));
            ++selectedCells;
        }
        else {
            Toast.makeText(v.getContext(), "Maximum number of cells is 24", Toast.LENGTH_SHORT).show();
            return;
        }

        cellState[row][column] = !cellState[row][column];
        onSuccessfulClick();
    }

    abstract void onPatternConfirmation();
    abstract void onSuccessfulClick();
}
