package com.easy.passbase.Settings;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.easy.passbase.R;
import com.easy.passbase.Settings.Pattern.RequestPatternActivity;
import com.easy.passbase.Settings.Pattern.SetPatternActivity;

public class ApplockPickerActivity extends AppCompatActivity implements ApplockStrings {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_applock);
    }

    public void enhancedPattern(View v) {
        Intent applock;
        SharedPreferences preferences = getSharedPreferences(APPLOCK, Context.MODE_PRIVATE);

        switch (preferences.getString(APPLOCK_TYPE, EMPTY)) {
            case PATTERN:
                applock = new Intent(this, RequestPatternActivity.class);
                applock.putExtra(REQUESTED_PATTERN, preferences.getString(APPLOCK_PASS, null));
                applock.putExtra(ACTION_ON_CONFIRM, RUN_SET_PATTERN);
                applock.putExtra(REQUEST_REASON, PATTERN_SENSITIVE_DATA);
                startActivity(applock);
                break;
            case EMPTY:
                applock = new Intent(this, SetPatternActivity.class);
                startActivity(applock);
                break;
        }
    }
}