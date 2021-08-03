package com.easy.passbase.Settings;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.easy.passbase.R;

public class RequestPatternActivity extends AppCompatActivity {
    private RequestPattern requestPattern;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.request_pattern);

        requestPattern = new RequestPattern(this, findViewById(R.id.table_reqPattern));
        requestPattern.setRequestedPattern((boolean[][])
                getIntent()
                        .getBundleExtra("REQUESTED_PATTERN")
                        .getSerializable("REQUESTED_PATTERN")
        );
    }

    public void onCellClick(View v) {
        requestPattern.onCellClick(v);
    }

    public void onPatternConfirmation(View v) {
        requestPattern.onPatternConfirmation();
    }
}