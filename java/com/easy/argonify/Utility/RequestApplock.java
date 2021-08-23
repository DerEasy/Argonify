package com.easy.argonify.Utility;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import com.easy.argonify.Settings.Applock.ApplockStrings;
import com.easy.argonify.Settings.Applock.Password.RequestPasswordActivity;
import com.easy.argonify.Settings.Applock.Pattern.RequestPatternActivity;

public class RequestApplock implements ApplockStrings {
    public static void requestApplock(Context context, String[] GENERIC_REASON, String ACTION) {
        SharedPreferences preferences = context.getSharedPreferences(APPLOCK, Context.MODE_PRIVATE);
        String REASON = getReason(preferences.getString(APPLOCK_TYPE, EMPTY), GENERIC_REASON);
        Intent applockRequest;

        switch (preferences.getString(APPLOCK_TYPE, EMPTY)) {
            case PATTERN:
                applockRequest = new Intent(context, RequestPatternActivity.class);
                startRequest(context, applockRequest, REASON, ACTION);
                break;
            case PASSWORD:
                applockRequest = new Intent(context, RequestPasswordActivity.class);
                startRequest(context, applockRequest, REASON, ACTION);
                break;
        }
    }

    private static void startRequest(Context context, Intent requestIntent, String REASON, String ACTION) {
        SharedPreferences preferences = context.getSharedPreferences(APPLOCK, Context.MODE_PRIVATE);

        requestIntent.putExtra(REQUESTED_KEY, preferences.getString(APPLOCK_KEY, null));
        requestIntent.putExtra(ACTION_ON_CONFIRM, ACTION);
        requestIntent.putExtra(REQUEST_REASON, REASON);

        context.startActivity(requestIntent);
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
