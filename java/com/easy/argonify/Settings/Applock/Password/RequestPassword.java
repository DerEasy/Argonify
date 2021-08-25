package com.easy.argonify.Settings.Applock.Password;

import android.widget.EditText;
import android.widget.TextView;

import com.easy.argonify.Settings.Applock.ActionExecutor;
import com.easy.argonify.Settings.Applock.ApplockStrings;

class RequestPassword extends Password implements ApplockStrings {
    private final RequestPasswordActivity requestPasswordActivity;
    private final TextView txtError;
    private final String requestedPassword;
    private final String actionOnConfirm;

    RequestPassword(RequestPasswordActivity parentActivity, TextView errorView, EditText passwordView) {
        super(passwordView);
        requestPasswordActivity = parentActivity;
        txtError = errorView;
        requestedPassword = requestPasswordActivity.getIntent().getStringExtra(REQUESTED_KEY);
        actionOnConfirm = requestPasswordActivity.getIntent().getStringExtra(ACTION_ON_CONFIRM);
    }

    @Override
    void onPasswordConfirmation() {
        if (noErrors()) {
            ActionExecutor actionExecutor = new ActionExecutor(requestPasswordActivity);
            switch (actionOnConfirm) {
                case RUN_SET_PASSWORD:
                    actionExecutor.runSetPassword();
                    break;
                case RUN_SET_PATTERN:
                    actionExecutor.runSetPattern();
                    break;
                case SAVE_KEY:
                    actionExecutor.saveKey(PASSWORD, requestedPassword, getRawPassword());
                    break;
                case ALLOW_APP_ACCESS:
                    actionExecutor.allowAppAccess(getRawPassword());
                    break;
                case CLEAR_DATA:
                    actionExecutor.clearData();
                    break;
            }
            requestPasswordActivity.finish();
        }
    }

    boolean noErrors() {
        if (inputPasswordMatches(requestedPassword))
            return true;
        else {
            requestPasswordActivity.hideLoading();
            txtError.setText(PASSWORD_ERROR_MISMATCH);
            return false;
        }
    }

    @Override
    void onTextChange() {}
}
