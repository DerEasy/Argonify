package com.easy.argonify.Settings.Pattern;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.View;

import com.easy.argonify.Main.MainActivity;
import com.easy.argonify.R;
import com.easy.argonify.Settings.ApplockPickerActivity;

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
                    editor.putString(APPLOCK_KEY, requestedPattern);
                    editor.apply();

                    Intent applockIntent = new Intent(requestPatternActivity, ApplockPickerActivity.class);
                    applockIntent.putExtra(APPLOCK_IS_SET, true);
                    applockIntent.putExtra(RAW_KEY, getRawPattern());
                    requestPatternActivity.startActivity(applockIntent);
                    break;
                case ALLOW_APP_ACCESS:
                    Intent mainActivityIntent = new Intent(requestPatternActivity, MainActivity.class);
                    mainActivityIntent.putExtra(RAW_KEY, getRawPattern());
                    requestPatternActivity.startActivity(mainActivityIntent);
                    break;
            }
            requestPatternActivity.finish();
        }
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
