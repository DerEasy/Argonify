package com.easy.passbase.Settings.Pattern;

import static com.easy.passbase.Settings.Argon2Utility.matchesArgonHash;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.widget.Toast;

import com.easy.passbase.Settings.ApplockStrings;

public class RequestPattern extends Pattern implements ApplockStrings {
    private final RequestPatternActivity requestPatternActivity;
    private final String requestedPattern;
    private final String actionOnConfirm;

    RequestPattern(RequestPatternActivity.RequestPatternInitialisation initData) {
        super(initData.grid, initData.trafficRow, initData.bars);
        requestPatternActivity = initData.parentActivity;
        requestedPattern = initData.requestedPattern;
        actionOnConfirm = initData.actionOnConfirm;
    }

    @Override
    void onPatternConfirmation() {
        if (matchesArgonHash(getPattern(), requestedPattern)) {
            switch (actionOnConfirm) {
                case RUN_SET_PATTERN:
                    Intent patternIntent = new Intent(requestPatternActivity, SetPatternActivity.class);
                    requestPatternActivity.startActivity(patternIntent);
                    break;
                case SAVE_PATTERN:
                    SharedPreferences preferences = requestPatternActivity.getSharedPreferences(APPLOCK, Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = preferences.edit();

                    editor.putString(APPLOCK_TYPE, PATTERN);
                    editor.putString(APPLOCK_PASS, requestedPattern);
                    editor.apply();
                default:
            }
            requestPatternActivity.finish();
        }
        else
            Toast.makeText(requestPatternActivity, PATTERN_ERROR_MISMATCH, Toast.LENGTH_SHORT).show();
    }

    @Override
    void onGridUpdate() {}
}
