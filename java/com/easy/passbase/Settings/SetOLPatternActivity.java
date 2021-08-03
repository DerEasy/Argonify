package com.easy.passbase.Settings;

import static com.easy.passbase.Settings.ThousandSeparator.thousandSeparatorFormat;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.content.res.AppCompatResources;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.easy.passbase.R;

import java.math.BigDecimal;

public class SetOLPatternActivity extends AppCompatActivity {
    private final static int CELLS_PER_SIDE = 7;
    private final static int CELLS_IN_TOTAL = CELLS_PER_SIDE * CELLS_PER_SIDE;
    private final static int MAXIMUM_AMOUNT_OF_CELLS = 24;

    private final TableRow[] row = new TableRow[CELLS_PER_SIDE];
    private final ImageButton[][] cell = new ImageButton[CELLS_PER_SIDE][CELLS_PER_SIDE];
    private final boolean[][] cellState = new boolean[CELLS_PER_SIDE][CELLS_PER_SIDE];

    private int cellSize;
    private int selectedCells = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.set_orderless_pattern);

        new GlobalLayoutListenerAdapter(this);
    }

    public void onTableInitialisation(int width) {
        cellSize = width / CELLS_PER_SIDE;
        initialiseTableContent();
    }

    private void initialiseTableContent() {
        TableLayout table = findViewById(R.id.table_setOLPattern);

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
        } else if (selectedCells < MAXIMUM_AMOUNT_OF_CELLS){
            cell[row][column].setImageDrawable(AppCompatResources.getDrawable(this, R.drawable.pattern_rectangle_on));
            ++selectedCells;
        } else {
            Toast.makeText(this, "Maximum number of cells is 24", Toast.LENGTH_SHORT).show();
            return;
        }

        cellState[row][column] = !cellState[row][column];
        onCombinationsUpdate();
    }

    private void onCombinationsUpdate() {
        TextView comboView = findViewById(R.id.txt_OLPatternComboAmount);
        BigDecimal intAmount = new BigDecimal(getRawComboString());
        String formattedAmount = thousandSeparatorFormat(intAmount.stripTrailingZeros().toPlainString());

        comboView.setText(formattedAmount);
        adjustComboViewSize(comboView);
    }

    private String getRawComboString() {
        return String.valueOf(Math.pow(CELLS_IN_TOTAL, selectedCells));
    }

    private void adjustComboViewSize(TextView comboView) {
        if (comboView.getText().length() < 14)
            comboView.setTextSize(24);
        else if (comboView.getText().length() < 20)
            comboView.setTextSize(20);
        else if (comboView.getText().length() < 26)
            comboView.setTextSize(16);
        else
            comboView.setTextSize(12);
    }
}