package com.easy.argonify.Settings.Applock.Password;

import com.easy.argonify.Settings.Applock.Encoder;

class PasswordEncoder extends Encoder {
    private final String passwordData;

    PasswordEncoder(String passwordData) {
        this.passwordData = passwordData;
    }

    @Override
    protected String getRawKey() {
        return passwordData;
    }

    @Override
    protected String getHashedKey() {
        return super.getHashedKey();
    }

    @Override
    protected boolean inputKeyMatches(String requestedKey) {
        return super.inputKeyMatches(requestedKey);
    }
}
