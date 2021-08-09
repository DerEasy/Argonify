package com.easy.argonify.Settings;

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
    String REQUESTED_PATTERN = "REQUESTED_PATTERN";
    String ACTION_ON_CONFIRM = "ACTION_ON_CONFIRM";
    String REQUEST_REASON = "REQUEST_REASON";
    String APP_ACCESS_GRANTED = "APP_ACCESS_GRANTED";
    //Missing applock
    String APPLOCK_IS_SET = "APPLOCK_IS_SET";
    String RAW_KEY = "RAW_KEY";

    //Actions on confirmation
    String RUN_SET_PATTERN = "RUN_SET_PATTERN";
    String SAVE_PATTERN = "SAVE_PATTERN";
    String ALLOW_APP_ACCESS = "ALLOW_APP_ACCESS";

    //Pattern request reasons
    String PATTERN_REASON_SENSITIVE_DATA = "Draw your pattern to change this setting.";
    String PATTERN_REASON_REDRAW_CONFIRM = "Redraw the pattern to confirm the change.";
    String PATTERN_REASON_APP_ACCESS = "Draw your pattern to access the app.";

    //Pattern errors
    String PATTERN_ERROR_MISMATCH = "Pattern does not match.";
    String PATTERN_ERROR_MISSING_FAVOURITE = "You must select one favourite cell.";
    String PATTERN_ERROR_MISSING_TRAFFICLIGHT = "You must select one of the 3 cells at the bottom.";
    String PATTERN_ERROR_MISSING_SEEKBAR = "You must set both sliders to a value.";
    String PATTERN_ERROR_TRAFFICLIGHT_INDEX = "Traffic light index out of range.";
}
