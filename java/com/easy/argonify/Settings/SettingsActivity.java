package com.easy.argonify.Settings;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.easy.argonify.R;
import com.easy.argonify.Settings.Applock.ApplockPickerActivity;
import com.easy.argonify.Settings.PassGenConfig.PassGenConfigActivity;

public class SettingsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
    }

    public void applock(View v) {
        Intent applockPickerIntent = new Intent(this, ApplockPickerActivity.class);
        startActivity(applockPickerIntent);
    }

    public void passgenSettings(View v) {
        Intent passgenConfigIntent = new Intent(this, PassGenConfigActivity.class);
        startActivity(passgenConfigIntent);
    }
}