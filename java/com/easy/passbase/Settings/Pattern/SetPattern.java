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
        Intent requestPatternIntent = new Intent(setPatternActivity, RequestPatternActivity.class);
        requestPatternIntent.putExtra(REQUESTED_PATTERN, getHashedPattern());
        requestPatternIntent.putExtra(ACTION_ON_CONFIRM, SAVE_PATTERN);
        requestPatternIntent.putExtra(REQUEST_REASON, PATTERN_REDRAW_CONFIRM);

        setPatternActivity.startActivity(requestPatternIntent);
        setPatternActivity.finish();
    }

    @Override
    void onControlStateChange() { setConfirmationButtonVisibility(); }

    private void setConfirmationButtonVisibility() {
        View confirmButton = setPatternActivity.findViewById(R.id.fab_setPatternConfirm);
        if (fulfillsRequirements())
            confirmButton.setVisibility(View.VISIBLE);
        else
            confirmButton.setVisibility(View.INVISIBLE);
    }

    private boolean fulfillsRequirements() {
        return selectedCells >= MIN_SELECTABLE_CELLS &&
                !favCell.equals(EMPTY) &&
                trafficData >= 0 &&
                usedSeekBar[TOP_BAR_INDEX] &&
                usedSeekBar[BOTTOM_BAR_INDEX];
    }
}
