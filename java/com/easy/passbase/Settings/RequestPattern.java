package com.easy.passbase.Settings;

import android.widget.TableLayout;
import android.widget.Toast;

public class RequestPattern extends Pattern {
    private final RequestPatternActivity requestPatternActivity;
    private boolean[][] requestedPattern;

    RequestPattern(RequestPatternActivity parentActivity, TableLayout grid) {
        super(grid);
        requestPatternActivity = parentActivity;
    }

    @Override
    void onPatternConfirmation() {
        if (patternMatches())
            Toast.makeText(requestPatternActivity, "Successful", Toast.LENGTH_SHORT).show();
        else
            Toast.makeText(requestPatternActivity, "Pattern does not match", Toast.LENGTH_SHORT).show();
    }

    @Override
    void onSuccessfulClick() {}

    private boolean patternMatches() {
        for (int i = 0; i < CELLS_PER_SIDE; ++i) {
            for (int j = 0; j < CELLS_PER_SIDE; ++j) {
                if (cellState[i][j] != requestedPattern[i][j])
                    return false;
            }
        }
        return true;
    }

    void setRequestedPattern(boolean[][] pattern) {
        requestedPattern = pattern;
    }
}
