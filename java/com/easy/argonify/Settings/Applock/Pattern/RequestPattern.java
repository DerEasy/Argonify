package com.easy.argonify.Settings.Applock.Pattern;

import android.widget.TextView;

import com.easy.argonify.Settings.Applock.ActionExecutor;
import com.easy.argonify.Settings.Applock.ApplockStrings;

public class RequestPattern extends Pattern implements ApplockStrings {
    private final RequestPatternActivity requestPatternActivity;
    private final TextView txtError;
    private final String requestedPattern;
    private final String actionOnConfirm;

    RequestPattern(RequestPatternActivity.RequestPatternInitialisation initData) {
        super(initData);
        requestPatternActivity = (RequestPatternActivity) initData.parentActivity;
        txtError = initData.txtError;
        requestedPattern = initData.requestedPattern;
        actionOnConfirm = initData.actionOnConfirm;
    }

    @Override
    void onPatternConfirmation() {
        if (noErrorsExtended()) {
            ActionExecutor actionExecutor = new ActionExecutor(requestPatternActivity);
            switch (actionOnConfirm) {
                case RUN_SET_PATTERN:
                    actionExecutor.runSetPattern();
                    break;
                case RUN_SET_PASSWORD:
                    actionExecutor.runSetPassword();
                    break;
                case SAVE_KEY:
                    actionExecutor.saveKey(PATTERN, requestedPattern, getRawPattern());
                    break;
                case ALLOW_APP_ACCESS:
                    actionExecutor.allowAppAccess(getRawPattern());
                    break;
            }
            requestPatternActivity.finish();
        }
    }

    boolean noErrors() {
        if (favCell.equals(EMPTY)) {
            txtError.setText(PATTERN_ERROR_MISSING_FAVOURITE);
            return false;
        }
        if (trafficData == -1) {
            txtError.setText(PATTERN_ERROR_MISSING_TRAFFICLIGHT);
            return false;
        }
        if (!usedSeekBar[TOP_BAR_INDEX] || !usedSeekBar[BOTTOM_BAR_INDEX]) {
            txtError.setText(PATTERN_ERROR_MISSING_SEEKBAR);
            return false;
        }
        return true;
    }

    boolean noErrorsExtended() {
        if (!noErrors()) {
            requestPatternActivity.hideLoading();
            return false;
        }
        if (!inputPatternMatches(requestedPattern)) {
            requestPatternActivity.hideLoading();
            txtError.setText(PATTERN_ERROR_MISMATCH);
            return false;
        }
        return true;
    }

    @Override
    void onControlStateChange() {}
}
