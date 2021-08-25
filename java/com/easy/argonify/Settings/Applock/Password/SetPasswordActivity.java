package com.easy.argonify.Settings.Applock.Password;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.easy.argonify.R;

public class SetPasswordActivity extends AppCompatActivity {
    private SetPassword setPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.set_password);

        setPassword = new SetPassword(this, findViewById(R.id.etxt_setPassword));
    }

    public void onPasswordConfirmation(View v) {
        showLoading();
        new Handler(Looper.getMainLooper()).post(() ->
                setPassword.onPasswordConfirmation());
    }

    private void showLoading() {
        findViewById(R.id.load_setPasswordLoading).setVisibility(View.VISIBLE);
        findViewById(R.id.txt_setPasswordLoading).setVisibility(View.VISIBLE);
    }
}