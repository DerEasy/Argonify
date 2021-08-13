package com.easy.argonify.Settings.Applock;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.easy.argonify.Main.MainActivity;
import com.easy.argonify.R;
import com.easy.argonify.Settings.Applock.Password.RequestPasswordActivity;
import com.easy.argonify.Settings.Applock.Password.SetPasswordActivity;
import com.easy.argonify.Settings.Applock.Pattern.RequestPatternActivity;
import com.easy.argonify.Settings.Applock.Pattern.SetPatternActivity;

public class ApplockPickerActivity extends AppCompatActivity implements ApplockStrings {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_applock);

        if (getIntent().getBooleanExtra(APPLOCK_IS_SET, false))
            onApplockSet();
    }

    public void enhancedPattern(View v) {
        Intent applock;
        SharedPreferences preferences = getSharedPreferences(APPLOCK, Context.MODE_PRIVATE);

        switch (preferences.getString(APPLOCK_TYPE, EMPTY)) {
            case PATTERN:
                applock = new Intent(this, RequestPatternActivity.class);
                applock.putExtra(REQUESTED_KEY, preferences.getString(APPLOCK_KEY, null));
                applock.putExtra(ACTION_ON_CONFIRM, RUN_SET_PATTERN);
                applock.putExtra(REQUEST_REASON, PATTERN_REASON_SENSITIVE_DATA);
                startActivity(applock);
                break;
            case PASSWORD:
                applock = new Intent(this, RequestPasswordActivity.class);
                applock.putExtra(REQUESTED_KEY, preferences.getString(APPLOCK_KEY, null));
                applock.putExtra(ACTION_ON_CONFIRM, RUN_SET_PATTERN);
                applock.putExtra(REQUEST_REASON, PASSWORD_REASON_SENSITIVE_DATA);
                startActivity(applock);
                break;
            case EMPTY:
                applock = new Intent(this, SetPatternActivity.class);
                startActivity(applock);
                finish();
                break;
        }
    }

    public void password(View v) {
        Intent applock;
        SharedPreferences preferences = getSharedPreferences(APPLOCK, Context.MODE_PRIVATE);

        switch (preferences.getString(APPLOCK_TYPE, EMPTY)) {
            case PATTERN:
                applock = new Intent(this, RequestPatternActivity.class);
                applock.putExtra(REQUESTED_KEY, preferences.getString(APPLOCK_KEY, null));
                applock.putExtra(ACTION_ON_CONFIRM, RUN_SET_PASSWORD);
                applock.putExtra(REQUEST_REASON, PATTERN_REASON_SENSITIVE_DATA);
                startActivity(applock);
                break;
            case PASSWORD:
                applock = new Intent(this, RequestPasswordActivity.class);
                applock.putExtra(REQUESTED_KEY, preferences.getString(APPLOCK_KEY, null));
                applock.putExtra(ACTION_ON_CONFIRM, RUN_SET_PASSWORD);
                applock.putExtra(REQUEST_REASON, PASSWORD_REASON_SENSITIVE_DATA);
                startActivity(applock);
                break;
            case EMPTY:
                applock = new Intent(this, SetPasswordActivity.class);
                startActivity(applock);
                finish();
                break;
        }
    }

    private void onApplockSet() {
        Intent mainActivityIntent = new Intent(this, MainActivity.class);
        mainActivityIntent.putExtra(RAW_KEY, getIntent().getStringExtra(RAW_KEY));
        startActivity(mainActivityIntent);
        finish();
    }
}