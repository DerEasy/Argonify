package com.easy.argonify.Settings;

public interface ApplockStrings {
    //General
    String APPLOCK = "APPLOCK";
    String APPLOCK_TYPE = "APPLOCK_TYPE";
    String APPLOCK_PASS = "APPLOCK_PASS";
    String EMPTY = "";

    //Applock types
    String PASSWORD = "PASSWORD";
    String PATTERN = "PATTERN";

    //Intent Extras
    String REQUESTED_PATTERN = "REQUESTED_PATTERN";
    String ACTION_ON_CONFIRM = "ACTION_ON_CONFIRM";
    String REQUEST_REASON = "REQUEST_REASON";

    //Actions on confirmation
    String RUN_SET_PATTERN = "RUN_SET_PATTERN";
    String SAVE_PATTERN = "SAVE_PATTERN";

    //Pattern request reasons
    String PATTERN_SENSITIVE_DATA = "Draw your current pattern to change this setting.";
    String PATTERN_REDRAW_CONFIRM = "Redraw the pattern to confirm the change.";

    //Pattern errors
    String PATTERN_ERROR_MISMATCH = "Pattern does not match.";
    String PATTERN_ERROR_MISSING_FAVOURITE = "You must select one favourite cell.";
    String PATTERN_ERROR_MISSING_TRAFFICLIGHT = "You must select one of the 3 cells at the bottom.";
    String PATTERN_ERROR_MISSING_SEEKBAR = "You must set both sliders to a value.";
    String PATTERN_ERROR_TRAFFICLIGHT_INDEX = "Traffic light index out of range.";
}
