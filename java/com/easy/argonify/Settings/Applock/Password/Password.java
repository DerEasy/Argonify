package com.easy.argonify.Settings.Applock.Password;

import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

abstract class Password {
    private final EditText etxtPassword;
    String passwordData;

    Password(EditText passwordView) {
        etxtPassword = passwordView;
        setOnTextChangedListener();
    }

    void setOnTextChangedListener() {
        etxtPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) { }

            @Override
            public void afterTextChanged(Editable s) {
                passwordData = s.toString();
                onTextChange();
            }
        });
    }

    String getRawPassword() {
        return passwordData;
    }

    String getHashedPassword() {
        return new PasswordEncoder(passwordData).getHashedKey();
    }

    boolean inputPasswordMatches(String requestedPassword) {
        return new PasswordEncoder(passwordData).inputKeyMatches(requestedPassword);
    }

    abstract void onTextChange();
    abstract void onPasswordConfirmation();
}
