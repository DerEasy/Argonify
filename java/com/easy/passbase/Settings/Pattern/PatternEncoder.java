package com.easy.passbase.Settings.Pattern;

import static com.easy.passbase.Settings.Argon2Utility.argonHash;
import static com.easy.passbase.Settings.Argon2Utility.matchesArgonHash;
import static com.easy.passbase.Settings.Pattern.ExtraCharacterMap.getExtraChar;

import com.easy.passbase.Settings.ApplockStrings;

import java.util.ArrayList;
import java.util.concurrent.ConcurrentLinkedDeque;

class PatternEncoder implements ApplockStrings {
    private final ConcurrentLinkedDeque<String> patternData;
    private final String favCell;
    private final int trafficData;
    private final int[] barData;

    PatternEncoder(
            ConcurrentLinkedDeque<String> patternData,
            String favCell,
            int trafficData,
            int[] barData
    ) {
        this.patternData = patternData;
        this.favCell = favCell;
        this.trafficData = trafficData;
        this.barData = barData;
    }

    @SuppressWarnings("ConstantConditions")
    String getRawPattern() {
        ArrayList<String> rawPattern = getBasicPattern();
        rawPattern = putExtraCharacter(rawPattern, rawPattern.indexOf(favCell), getExtraChar(barData));

        StringBuilder pattern = new StringBuilder();
        for (String tag : rawPattern)
            pattern.append(tag);

        return pattern.toString();
    }

    String getHashedPattern() {
        return argonHash(getRawPattern());
    }
    
    boolean inputPatternMatches(String requestedPattern) {
        return matchesArgonHash(getRawPattern(), requestedPattern);
    }

    private ArrayList<String> getBasicPattern() {
        ArrayList<String> flexibleRawPattern = new ArrayList<>(patternData.size() + 1);
        flexibleRawPattern.addAll(patternData);

        return flexibleRawPattern;
    }

    private ArrayList<String> putExtraCharacter(ArrayList<String> rawPattern, int index, String extraChar) {
        switch (trafficData) {
            case 0: //LEFT
                rawPattern.add(index, extraChar);
                break;
            case 1: //MIDDLE
                final int MIDDLE_INDEX = 1;
                StringBuilder tag = new StringBuilder(rawPattern.get(index));
                tag.insert(MIDDLE_INDEX, extraChar);
                rawPattern.set(index, tag.toString());
                break;
            case 2: //RIGHT
                rawPattern.add(index + 1, extraChar);
                break;
            default:
                throw new IndexOutOfBoundsException(PATTERN_ERROR_TRAFFICLIGHT_INDEX);
        }
        return rawPattern;
    }
}
