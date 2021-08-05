package com.easy.passbase.Settings;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.easy.passbase.R;

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
}