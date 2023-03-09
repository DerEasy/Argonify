package com.easy.argonify.Settings.Applock.Password;

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

public class RequestPasswordActivity extends AppCompatActivity implements ApplockStrings {
    private RequestPassword requestPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.req_password);

        requestPassword = new RequestPassword(
                this,
                findViewById(R.id.txt_reqPasswordError),
                findViewById(R.id.etxt_reqPassword)
        );

        setRequestReason();
    }

    private void setRequestReason() {
        TextView reasonView = findViewById(R.id.txt_reqPasswordReason);
        String requestReason = getIntent().getStringExtra(REQUEST_REASON);
        reasonView.setText(requestReason != null ? requestReason : EMPTY);
    }

    public void onPasswordConfirmation(View v) {
        showLoading();
        new Handler(Looper.getMainLooper()).post(() ->
                requestPassword.onPasswordConfirmation());
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
        findViewById(R.id.load_reqPasswordLoading).setVisibility(View.VISIBLE);
        findViewById(R.id.txt_reqPasswordLoading).setVisibility(View.VISIBLE);
    }

    void hideLoading() {
        findViewById(R.id.load_reqPasswordLoading).setVisibility(View.INVISIBLE);
        findViewById(R.id.txt_reqPasswordLoading).setVisibility(View.INVISIBLE);
    }
}