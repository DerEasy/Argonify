package com.easy.passbase.Settings;

import static com.easy.passbase.Settings.ThousandSeparator.thousandSeparatorFormat;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TableLayout;
import android.widget.TextView;

import com.easy.passbase.R;

import java.math.BigDecimal;

public class SetPattern extends Pattern {
    private final SetPatternActivity setPatternActivity;

    SetPattern(SetPatternActivity parentActivity, TableLayout grid) {
        super(grid);
        setPatternActivity = parentActivity;
    }

    @Override
    void onPatternConfirmation() {
        Intent requestPatternIntent = new Intent(setPatternActivity, RequestPatternActivity.class);
        Bundle requestedPattern = new Bundle();

        requestedPattern.putSerializable("REQUESTED_PATTERN", cellState);
        requestPatternIntent.putExtra("REQUESTED_PATTERN", requestedPattern);

        setPatternActivity.startActivity(requestPatternIntent);
        setPatternActivity.finish();
    }

    @Override
    void onSuccessfulClick() {
        TextView comboView = setPatternActivity.findViewById(R.id.txt_setPatternComboAmount);
        BigDecimal intAmount = new BigDecimal(getRawComboString());
        String formattedAmount = thousandSeparatorFormat(intAmount.stripTrailingZeros().toPlainString());

        comboView.setText(formattedAmount);
        adjustComboViewSize(comboView);
        setConfirmationButtonVisibility();
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

    private void setConfirmationButtonVisibility() {
        View confirmButton = setPatternActivity.findViewById(R.id.fab_setPatternConfirm);
        if (selectedCells >= MIN_SELECTABLE_CELLS) {
            confirmButton.setVisibility(View.VISIBLE);
        } else {
            confirmButton.setVisibility(View.INVISIBLE);
        }
    }
}
