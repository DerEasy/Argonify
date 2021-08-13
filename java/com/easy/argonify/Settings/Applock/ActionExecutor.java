package com.easy.argonify.Settings.Applock;

import static android.content.Context.MODE_PRIVATE;

import android.content.Intent;
import android.content.SharedPreferences;

import androidx.appcompat.app.AppCompatActivity;

import com.easy.argonify.Main.MainActivity;
import com.easy.argonify.Settings.Applock.Password.SetPasswordActivity;
import com.easy.argonify.Settings.Applock.Pattern.SetPatternActivity;

public class ActionExecutor implements ApplockStrings {
    private final AppCompatActivity activity;

    public ActionExecutor(AppCompatActivity activity) {
        this.activity = activity;
    }

    public void runSetPassword() {
        Intent passwordIntent = new Intent(activity, SetPasswordActivity.class);
        activity.startActivity(passwordIntent);
    }

    public void runSetPattern() {
        Intent patternIntent = new Intent(activity, SetPatternActivity.class);
        activity.startActivity(patternIntent);
    }

    public void saveKey(String applockType, String hashedKey, String rawKey) {
        SharedPreferences preferences = activity.getSharedPreferences(APPLOCK, MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();

        editor.putString(APPLOCK_TYPE, applockType);
        editor.putString(APPLOCK_KEY, hashedKey);
        editor.apply();

        Intent applockIntent = new Intent(activity, ApplockPickerActivity.class);
        applockIntent.putExtra(APPLOCK_IS_SET, true);
        applockIntent.putExtra(RAW_KEY, rawKey);
        activity.startActivity(applockIntent);
    }

    public void allowAppAccess(String rawKey) {
        Intent mainActivityIntent = new Intent(activity, MainActivity.class);
        mainActivityIntent.putExtra(RAW_KEY, rawKey);
        activity.startActivity(mainActivityIntent);
    }
}
