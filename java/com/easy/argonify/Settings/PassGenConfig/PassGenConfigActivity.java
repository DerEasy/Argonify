package com.easy.argonify.Settings.PassGenConfig;

import static com.easy.argonify.Settings.Applock.ApplockStrings.EMPTY;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.NumberPicker;

import androidx.appcompat.app.AppCompatActivity;

import com.easy.argonify.R;
import com.google.android.material.switchmaterial.SwitchMaterial;

public class PassGenConfigActivity extends AppCompatActivity implements PassGenConfigData {
    private SharedPreferences prefs;
    private SharedPreferences.Editor editor;

    private SwitchMaterial[] charSwitch;
    private NumberPicker npLength;
    private EditText etxtExclusions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.passgen_config);

        prefs = getSharedPreferences(GEN_CONFIG, MODE_PRIVATE);
        editor = prefs.edit();

        npLength = findViewById(R.id.np_settingsGenLength);
        etxtExclusions = findViewById(R.id.etxt_settingsGenExcludedChars);
        charSwitch = new SwitchMaterial[] {
                findViewById(R.id.switch_settingsGenLowercase),
                findViewById(R.id.switch_settingsGenUppercase),
                findViewById(R.id.switch_settingsGenNumbers),
                findViewById(R.id.switch_settingsGenSymbols),
        };

        initialiseAllViews();
        attachAllListeners();
    }

    private void initialiseAllViews() {
        initialiseSwitches();
        initialiseNumberPicker();
        initialiseExclusion();
    }

    private void initialiseSwitches() {
        for (int i = 0; i < AMOUNT_OF_CHAR_SWITCHES; ++i)
            charSwitch[i].setChecked(prefs.getBoolean(CONFIG_CHAR_CATS[i], true));
    }

    private void initialiseNumberPicker() {
        npLength.setMinValue(1);
        npLength.setMaxValue(64);
        npLength.setValue(prefs.getInt(CONFIG_LENGTH, 20));
    }

    private void initialiseExclusion() {
        etxtExclusions.setText(prefs.getString(CONFIG_EXCLUSIONS, EMPTY));
    }

    private void attachAllListeners() {
        attachLengthListener();
        attachLowercaseListener();
        attachUppercaseListener();
        attachNumbersListener();
        attachSymbolsListener();
        attachExclusionsListener();
    }

    private void attachLengthListener() {
        npLength.setOnValueChangedListener((picker, oldVal, newVal) -> {
            editor.putInt(CONFIG_LENGTH, newVal);
            editor.apply();
        });
    }

    private void attachLowercaseListener() {
        charSwitch[INDEX_LOWERCASE].setOnCheckedChangeListener((buttonView, isChecked) -> {
            editor.putBoolean(CONFIG_LOWERCASE, isChecked);
            editor.apply();
        });
    }

    private void attachUppercaseListener() {
        charSwitch[INDEX_UPPERCASE].setOnCheckedChangeListener((buttonView, isChecked) -> {
            editor.putBoolean(CONFIG_UPPERCASE, isChecked);
            editor.apply();
        });
    }

    private void attachNumbersListener() {
        charSwitch[INDEX_NUMBERS].setOnCheckedChangeListener((buttonView, isChecked) -> {
            editor.putBoolean(CONFIG_NUMBERS, isChecked);
            editor.apply();
        });
    }

    private void attachSymbolsListener() {
        charSwitch[INDEX_SYMBOLS].setOnCheckedChangeListener((buttonView, isChecked) -> {
            editor.putBoolean(CONFIG_SYMBOLS, isChecked);
            editor.apply();
        });
    }

    private void attachExclusionsListener() {
        etxtExclusions.addTextChangedListener(new TextWatcher() {
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            public void onTextChanged(CharSequence s, int start, int before, int count) {}

            @Override
            public void afterTextChanged(Editable s) {
                editor.putString(CONFIG_EXCLUSIONS, s.toString());
                editor.apply();
            }
        });
    }
}