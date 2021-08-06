package com.easy.passbase.Settings;

import org.springframework.security.crypto.argon2.Argon2PasswordEncoder;

public class Argon2Utility {
    private static final Argon2PasswordEncoder passwordEncoder =
            new Argon2PasswordEncoder(20, 64, 1, 1 << 14, 3);

    public static String argonHash(String raw) {
        return passwordEncoder.encode(raw);
    }

    public static boolean matchesArgonHash(String raw, String hash) {
        return passwordEncoder.matches(raw, hash);
    }
}
