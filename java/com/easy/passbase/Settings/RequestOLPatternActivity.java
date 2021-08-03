package com.easy.passbase.Settings;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.content.res.AppCompatResources;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.Toast;

import com.easy.passbase.R;

import java.util.Arrays;

public class RequestOLPatternActivity extends AppCompatActivity {
    private final static int CELLS_PER_SIDE = 7;
    private final static int MAXIMUM_AMOUNT_OF_CELLS = 24;

    private final TableRow[] row = new TableRow[CELLS_PER_SIDE];
    private final ImageButton[][] cell = new ImageButton[CELLS_PER_SIDE][CELLS_PER_SIDE];
    private final boolean[][] cellState = new boolean[CELLS_PER_SIDE][CELLS_PER_SIDE];
    private static boolean[][] requestedPattern = new boolean[CELLS_PER_SIDE][CELLS_PER_SIDE];

    private int cellSize;
    private int selectedCells = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.request_orderless_pattern);

        new GlobalLayoutListenerAdapter(this);
    }

    public static void setRequestedPattern(boolean[][] pattern) {
        requestedPattern = pattern;
    }

    public void onTableInitialisation(int width) {
        cellSize = width / CELLS_PER_SIDE;
        initialiseTableContent();
    }

    private void initialiseTableContent() {
        TableLayout table = findViewById(R.id.table_requestOLPattern);

        for (int i = 0; i < CELLS_PER_SIDE; ++i)
            row[i] = (TableRow) table.getChildAt(i);

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
            cell[row][column].setImageDrawable(AppCompatResources.getDrawable(this, R.drawable.pattern_rectangle_off));
            --selectedCells;
        } else {
            cell[row][column].setImageDrawable(AppCompatResources.getDrawable(this, R.drawable.pattern_rectangle_on));
            ++selectedCells;
        }

        cellState[row][column] = !cellState[row][column];
    }

    public void onPatternConfirmation(View v) {
        if (patternMatches())
            Toast.makeText(this, "Successful", Toast.LENGTH_SHORT).show();
        else
            Toast.makeText(this, "Pattern does not match", Toast.LENGTH_SHORT).show();
    }

    private boolean patternMatches() {
        for (int i = 0; i < CELLS_PER_SIDE; ++i) {
            for (int j = 0; j < CELLS_PER_SIDE; ++j) {
                if (cellState[i][j] != requestedPattern[i][j])
                    return false;
            }
        }
        return true;
    }
}