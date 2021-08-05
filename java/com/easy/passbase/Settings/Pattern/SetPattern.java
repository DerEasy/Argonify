package com.easy.passbase.Settings.Pattern;

import static com.easy.passbase.Settings.Argon2Utility.argonHash;
import static com.easy.passbase.Settings.ThousandSeparator.thousandSeparatorFormat;

import android.content.Intent;
import android.view.View;
import android.widget.TableLayout;
import android.widget.TextView;

import com.easy.passbase.R;
import com.easy.passbase.Settings.ApplockStrings;

import java.math.BigDecimal;

public class SetPattern extends Pattern implements ApplockStrings {
    private final SetPatternActivity setPatternActivity;

    SetPattern(SetPatternActivity parentActivity, TableLayout grid) {
        super(grid);
        setPatternActivity = parentActivity;
    }

    @Override
    void onPatternConfirmation() {
        Intent requestPatternIntent = new Intent(setPatternActivity, RequestPatternActivity.class);
        requestPatternIntent.putExtra(REQUESTED_PATTERN, argonHash(getPattern()));
        requestPatternIntent.putExtra(ACTION_ON_CONFIRM, SAVE_PATTERN);
        requestPatternIntent.putExtra(REQUEST_REASON, PATTERN_REDRAW_CONFIRM);

        setPatternActivity.startActivity(requestPatternIntent);
        setPatternActivity.finish();
    }

    @Override
    void onGridUpdate() {
        TextView comboView = setPatternActivity.findViewById(R.id.txt_setPatternComboAmount);
        BigDecimal intAmount = getRawComboBigDecimal();
        String formattedAmount = thousandSeparatorFormat(intAmount.stripTrailingZeros().toPlainString());

        comboView.setText(formattedAmount);
        adjustComboViewSize(comboView);
        setConfirmationButtonVisibility();
    }

    private BigDecimal fact(int number) {
        BigDecimal f = new BigDecimal("1");
        int j = 1;
        while(j <= number) {
            f = f.multiply(BigDecimal.valueOf(j));
            j++;
        }
        return f;
    }

    private BigDecimal getRawComboBigDecimal() {
        return fact(CELLS_IN_TOTAL).divide(
                        fact(CELLS_IN_TOTAL - selectedCells),
                        BigDecimal.ROUND_HALF_UP
                );
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

    private void setConfirmationButtonVisibility() {
        View confirmButton = setPatternActivity.findViewById(R.id.fab_setPatternConfirm);
        if (selectedCells >= MIN_SELECTABLE_CELLS) {
            confirmButton.setVisibility(View.VISIBLE);
        } else {
            confirmButton.setVisibility(View.INVISIBLE);
        }
    }
}
