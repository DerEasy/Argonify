package com.easy.argonify.Settings;

import org.springframework.security.crypto.argon2.Argon2PasswordEncoder;

public class Argon2Utility {
    private static final Argon2PasswordEncoder passwordEncoder =
            new Argon2PasswordEncoder(
                    16,
                    48,
                    Runtime.getRuntime().availableProcessors(),
                    1 << 15,
                    3
            );

    public static String argonHash(String raw) {
        return passwordEncoder.encode(raw);
    }

    public static boolean matchesArgonHash(String raw, String hash) {
        return passwordEncoder.matches(raw, hash);
    }
}
