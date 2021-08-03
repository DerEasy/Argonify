package com.easy.passbase.Settings;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.easy.passbase.R;

public class ApplockPickerActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_applock);
    }

    public void orderlessPattern(View v) {
        Intent orderlessPatternIntent = new Intent(this, SetOLPatternActivity.class);
        startActivity(orderlessPatternIntent);
    }
}