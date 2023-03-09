package com.easy.argonify.Settings.Applock.Pattern;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.easy.argonify.R;
import com.easy.argonify.Settings.Applock.ApplockStrings;

public class RequestPatternActivity extends AppCompatActivity implements ApplockStrings {
    private RequestPattern requestPattern;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.req_pattern);

        setRequestReason();
        requestPattern = new RequestPattern(new RequestPatternInitialisation());
    }

    class RequestPatternInitialisation extends PatternInitialisation {
        final String requestedPattern = initialiseString(REQUESTED_KEY);
        final String actionOnConfirm = initialiseString(ACTION_ON_CONFIRM);

        RequestPatternInitialisation() {
            super(
                    RequestPatternActivity.this,
                    findViewById(R.id.grid_reqPattern),
                    findViewById(R.id.traffic_reqPattern),
                    findViewById(R.id.txt_reqPatternError),
                    getSeekBars(
                            findViewById(R.id.seekBar_reqPatternTop),
                            findViewById(R.id.seekBar_reqPatternBottom)
                    )
            );
        }

        private String initialiseString(String intentExtra) {
            String temp = getIntent().getStringExtra(intentExtra);
            return temp != null ? temp : EMPTY;
        }
    }

    private void setRequestReason() {
        TextView reasonView = findViewById(R.id.txt_reqPatternReason);
        String requestReason = getIntent().getStringExtra(REQUEST_REASON);
        reasonView.setText(requestReason != null ? requestReason : EMPTY);
    }

    public void onCellClick(View v) {
        requestPattern.onCellClick(v);
    }

    public void onTrafficClick(View v) {
        requestPattern.onTrafficClick(v);
    }

    public void onUndo(View v) {
        requestPattern.onUndo(v);
    }

    public void onClear(View v) {
        requestPattern.onClear(v);
    }

    public void onPatternConfirmation(View v) {
        showLoading();
        new Handler(Looper.getMainLooper()).post(() ->
                requestPattern.onPatternConfirmation());
    }

    public void clearAttributeFromClipboard(View v) {
        ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
        ClipData clip = ClipData.newPlainText("", "");
        clipboard.setPrimaryClip(clip);

        Toast.makeText(
                this,
                "Cleared latest clipboard entry.",
                Toast.LENGTH_SHORT
        ).show();
    }

    private void showLoading() {
        if (requestPattern.noErrors()) {
            findViewById(R.id.load_reqPatternLoading).setVisibility(View.VISIBLE);
            findViewById(R.id.txt_reqPatternLoading).setVisibility(View.VISIBLE);
        }
    }

    void hideLoading() {
        findViewById(R.id.load_reqPatternLoading).setVisibility(View.INVISIBLE);
        findViewById(R.id.txt_reqPatternLoading).setVisibility(View.INVISIBLE);
    }
}