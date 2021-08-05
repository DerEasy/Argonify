package com.easy.passbase.Settings.Pattern;

import android.os.Bundle;
import android.view.View;
import android.widget.TableLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.easy.passbase.R;
import com.easy.passbase.Settings.ApplockStrings;

public class RequestPatternActivity extends AppCompatActivity implements ApplockStrings {
    private RequestPattern requestPattern;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.request_pattern);

        setRequestReason();
        requestPattern = new RequestPattern(new RequestPatternInitialisation());
    }

    class RequestPatternInitialisation {
        final RequestPatternActivity parentActivity;
        final TableLayout grid;
        final String requestedPattern;
        final String actionOnConfirm;

        RequestPatternInitialisation() {
            parentActivity = RequestPatternActivity.this;
            grid = findViewById(R.id.table_reqPattern);

            String p = getIntent().getStringExtra(REQUESTED_PATTERN);
            if (p != null) requestedPattern = p;
            else           requestedPattern = EMPTY;

            String a = getIntent().getStringExtra(ACTION_ON_CONFIRM);
            if (a != null) actionOnConfirm = a;
            else           actionOnConfirm = EMPTY;
        }
    }

    public void setRequestReason() {
        TextView reasonView = findViewById(R.id.txt_reqPatternReason);
        reasonView.setText(getIntent().getStringExtra(REQUEST_REASON));
    }

    public void onCellClick(View v) {
        requestPattern.onCellClick(v);
    }

    public void onUndo(View v) {
        requestPattern.onUndo(v);
    }

    public void onClear(View v) {
        requestPattern.onClear(v);
    }

    public void onPatternConfirmation(View v) {
        requestPattern.onPatternConfirmation();
    }
}