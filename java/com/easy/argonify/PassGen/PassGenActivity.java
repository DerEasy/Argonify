package com.easy.argonify.PassGen;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.easy.argonify.R;

public class PassGenActivity extends AppCompatActivity {
    private PasswordDisplay passwordDisplay;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_passgen);

        passwordDisplay = new PasswordDisplay(this);
    }

    public void generate(View v) {
        passwordDisplay.generate();
    }

    public void copyGeneratedPassword(View v) {
        passwordDisplay.copyGeneratedPasswordToClipboard();
    }
}
