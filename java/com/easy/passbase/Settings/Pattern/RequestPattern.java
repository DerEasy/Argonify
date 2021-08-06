package com.easy.passbase.Settings.Pattern;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

public class RequestPattern extends Pattern {
    private final RequestPatternActivity requestPatternActivity;
    private final String requestedPattern;
    private final String actionOnConfirm;

    RequestPattern(RequestPatternActivity.RequestPatternInitialisation initData) {
        super(initData);
        requestPatternActivity = (RequestPatternActivity) initData.parentActivity;
        requestedPattern = initData.requestedPattern;
        actionOnConfirm = initData.actionOnConfirm;
    }

    @Override
    void onPatternConfirmation() {
        if (noErrorsExtended()) {
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
    }

    boolean noErrorsExtended() {
        if (!noErrors())
            return false;
        if (!inputPatternMatches(requestedPattern)) {
            txtError.setText(PATTERN_ERROR_MISMATCH);
            return false;
        }
        return true;
    }

    @Override
    void onCellToggled() {}
}
