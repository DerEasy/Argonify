package com.easy.argonify.Settings.Applock;

import static com.easy.argonify.Utility.RequestApplock.requestApplock;

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
        requestApplock(this, REASON_SENSITIVE_DATA, RUN_SET_PATTERN);
    }

    public void password(View v) {
        requestApplock(this, REASON_SENSITIVE_DATA, RUN_SET_PASSWORD);
    }

    private void onApplockSet() {
        Intent mainActivityIntent = new Intent(this, MainActivity.class);
        mainActivityIntent.putExtra(RAW_KEY, getIntent().getStringExtra(RAW_KEY));
        startActivity(mainActivityIntent);
        finish();
    }
}