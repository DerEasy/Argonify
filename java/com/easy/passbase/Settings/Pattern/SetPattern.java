package com.easy.passbase.Settings.Pattern;

import android.content.Intent;
import android.view.View;

import com.easy.passbase.R;

public class SetPattern extends Pattern {
    private final SetPatternActivity setPatternActivity;

    SetPattern(SetPatternActivity.SetPatternInitialisation initData) {
        super(initData);
        setPatternActivity = (SetPatternActivity) initData.parentActivity;
    }

    @Override
    void onPatternConfirmation() {
        if (noErrors()) {
            Intent requestPatternIntent = new Intent(setPatternActivity, RequestPatternActivity.class);
            requestPatternIntent.putExtra(REQUESTED_PATTERN, getHashedPattern());
            requestPatternIntent.putExtra(ACTION_ON_CONFIRM, SAVE_PATTERN);
            requestPatternIntent.putExtra(REQUEST_REASON, PATTERN_REDRAW_CONFIRM);

            setPatternActivity.startActivity(requestPatternIntent);
            setPatternActivity.finish();
        }
    }

    @Override
    void onCellToggled() { setConfirmationButtonVisibility(); }

    private void setConfirmationButtonVisibility() {
        View confirmButton = setPatternActivity.findViewById(R.id.fab_setPatternConfirm);
        if (selectedCells >= MIN_SELECTABLE_CELLS)
            confirmButton.setVisibility(View.VISIBLE);
        else
            confirmButton.setVisibility(View.INVISIBLE);
    }
}
