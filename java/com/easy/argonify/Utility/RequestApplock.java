package com.easy.argonify.Utility;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import com.easy.argonify.Settings.Applock.ActionExecutor;
import com.easy.argonify.Settings.Applock.ApplockStrings;
import com.easy.argonify.Settings.Applock.Password.RequestPasswordActivity;
import com.easy.argonify.Settings.Applock.Pattern.RequestPatternActivity;

public class RequestApplock implements ApplockStrings {
    /**
     * Requests the user to enter their applock. This method automatically asks for
     * the currently saved applock key.
     * @param context The context you are calling from
     * @param GENERIC_REASON The reason you are calling
     * @param ACTION The action you want to perform on confirmation
     */
    public static void requestApplock(Context context, String[] GENERIC_REASON, String ACTION) {
        requestApplock(context, GENERIC_REASON, ACTION, null, null);
    }

    /**
     * Requests the user to enter their applock. This method should only be used if
     * you need to request an applock that is not currently saved (e.g. when changing the key).
     * @param context The context you are calling from
     * @param GENERIC_REASON The reason you are calling
     * @param ACTION The action you want to perform on confirmation
     * @param LOCK_TYPE The applock type that is requested
     * @param KEY The key that is asked for
     */
    public static void requestApplock(Context context, String[] GENERIC_REASON, String ACTION, String LOCK_TYPE, String KEY) {
        boolean lockIsNull = LOCK_TYPE == null;
        boolean keyIsNull = KEY == null;

        if (lockIsNull != keyIsNull)
            throw new IllegalArgumentException("Either LOCK_TYPE or KEY is null while the other is not.");
        if (lockIsNull & keyIsNull) {
            SharedPreferences preferences = context.getSharedPreferences(APPLOCK, Context.MODE_PRIVATE);
            LOCK_TYPE = preferences.getString(APPLOCK_TYPE, EMPTY);
            KEY = preferences.getString(APPLOCK_KEY, null);
        }
        if (LOCK_TYPE.equals(EMPTY)) {
            skipToKeylessAction(context, ACTION);
            return;
        }

        Intent applockRequest;
        String REASON = getReason(LOCK_TYPE, GENERIC_REASON);

        switch (LOCK_TYPE) {
            case PATTERN:
                applockRequest = new Intent(context, RequestPatternActivity.class);
                startRequest(context, applockRequest, REASON, ACTION, KEY);
                break;
            case PASSWORD:
                applockRequest = new Intent(context, RequestPasswordActivity.class);
                startRequest(context, applockRequest, REASON, ACTION, KEY);
                break;
        }
    }

    private static void startRequest(Context context, Intent requestIntent, String REASON, String ACTION, String KEY) {
        requestIntent.putExtra(REQUESTED_KEY, KEY);
        requestIntent.putExtra(ACTION_ON_CONFIRM, ACTION);
        requestIntent.putExtra(REQUEST_REASON, REASON);

        context.startActivity(requestIntent);
    }

    private static void skipToKeylessAction(Context context, String ACTION) {
        ActionExecutor actionExecutor = new ActionExecutor(context);
        switch (ACTION) {
            case RUN_SET_PATTERN:
                actionExecutor.runSetPattern();
                break;
            case RUN_SET_PASSWORD:
                actionExecutor.runSetPassword();
                break;
            default:
                throw new IllegalStateException(
                        String.format("Action '%s' does not exist or is illegal without a key.", ACTION)
                );
        }
    }

    private static String getReason(String APPLOCK_TYPE, String[] GENERIC_REASON) {
        return GENERIC_REASON[getApplockIndex(APPLOCK_TYPE)];
    }

    private static int getApplockIndex(String APPLOCK_TYPE) {
        switch (APPLOCK_TYPE) {
            case PATTERN:
                return 0;
            case PASSWORD:
                return 1;
            default:
                throw new IllegalArgumentException(
                        new NoSuchFieldException(
                                String.format("Applock type '%s' does not exist.", APPLOCK_TYPE)
                        )
                );
        }
    }
}
