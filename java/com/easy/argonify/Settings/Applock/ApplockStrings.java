package com.easy.argonify.Settings.Applock;

public interface ApplockStrings {
    //General
    String APPLOCK = "APPLOCK";
    String APPLOCK_TYPE = "APPLOCK_TYPE";
    String APPLOCK_KEY = "APPLOCK_KEY";
    String EMPTY = "";

    //Applock types
    String PASSWORD = "PASSWORD";
    String PATTERN = "PATTERN";

    //Intent Extras
    String REQUESTED_KEY = "REQUESTED_KEY";
    String ACTION_ON_CONFIRM = "ACTION_ON_CONFIRM";
    String REQUEST_REASON = "REQUEST_REASON";
    //Missing applock
    String APPLOCK_IS_SET = "APPLOCK_IS_SET";
    String RAW_KEY = "RAW_KEY";

    //Actions on confirmation
    String RUN_SET_PATTERN = "RUN_SET_PATTERN";
    String RUN_SET_PASSWORD = "RUN_SET_PASSWORD";
    String SAVE_KEY = "SAVE_KEY";
    String ALLOW_APP_ACCESS = "ALLOW_APP_ACCESS";
    String CLEAR_DATA = "CLEAR_DATA";

    //Pattern request reasons
    String PATTERN_REASON_SENSITIVE_DATA = "Draw your pattern to change this setting.";
    String PATTERN_REASON_REDRAW_CONFIRM = "Redraw the pattern to confirm the change.";
    String PATTERN_REASON_APP_ACCESS = "Draw your pattern to access the app.";
    String PATTERN_REASON_CLEAR_DATA = "Draw your pattern to erase all data.";

    //Password request reasons
    String PASSWORD_REASON_SENSITIVE_DATA = "Enter your password to change this setting.";
    String PASSWORD_REASON_REENTER_CONFIRM = "Reenter the password to confirm the change.";
    String PASSWORD_REASON_APP_ACCESS = "Enter your password to access the app.";
    String PASSWORD_REASON_CLEAR_DATA = "Enter your password to erase all data.";

    //Generic request reasons
    String[] REASON_SENSITIVE_DATA = {
            PATTERN_REASON_SENSITIVE_DATA,
            PASSWORD_REASON_SENSITIVE_DATA
    };
    String[] REASON_REENTER_CONFIRM = {
            PATTERN_REASON_REDRAW_CONFIRM,
            PASSWORD_REASON_REENTER_CONFIRM
    };
    String[] REASON_APP_ACCESS = {
            PATTERN_REASON_APP_ACCESS,
            PASSWORD_REASON_APP_ACCESS
    };
    String[] REASON_CLEAR_DATA = {
            PATTERN_REASON_CLEAR_DATA,
            PASSWORD_REASON_CLEAR_DATA
    };

    //Pattern errors
    String PATTERN_ERROR_MISMATCH = "Pattern does not match.";
    String PATTERN_ERROR_MISSING_FAVOURITE = "You must select one favourite cell.";
    String PATTERN_ERROR_MISSING_TRAFFICLIGHT = "You must select one of the 3 cells at the bottom.";
    String PATTERN_ERROR_MISSING_SEEKBAR = "You must set both sliders to a value.";

    //Password errors
    String PASSWORD_ERROR_MISMATCH = "Password does not match.";
}
