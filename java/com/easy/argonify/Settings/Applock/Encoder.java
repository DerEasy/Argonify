package com.easy.argonify.Settings.Applock;

import static com.easy.argonify.Utility.Argon2Utility.argonHash;
import static com.easy.argonify.Utility.Argon2Utility.matchesArgonHash;

public abstract class Encoder {
    protected abstract String getRawKey();

    protected String getHashedKey() {
        return argonHash(getRawKey());
    }

    protected boolean inputKeyMatches(String requestedKey) {
        return matchesArgonHash(getRawKey(), requestedKey);
    }
}
