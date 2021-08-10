package com.easy.argonify.Settings.Pattern;

import static com.easy.argonify.Settings.Argon2Utility.argonHash;
import static com.easy.argonify.Settings.Argon2Utility.matchesArgonHash;
import static com.easy.argonify.Settings.Pattern.ExtraCharacterMap.getExtraChar;

import com.easy.argonify.Settings.ApplockStrings;

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

    String getRawPattern() {
        ArrayList<String> basicPattern = getBasicPattern();
        StringBuilder pattern = getEncodedPattern(basicPattern);
        int favCellIndex = 1 + 3 * basicPattern.indexOf(favCell);

        return putExtraCharacter(pattern, favCellIndex, getExtraChar(barData));
    }

    String getHashedPattern() {
        return argonHash(getRawPattern());
    }
    
    boolean inputPatternMatches(String requestedPattern) {
        return matchesArgonHash(getRawPattern(), requestedPattern);
    }

    private StringBuilder getEncodedPattern(ArrayList<String> basicPattern) {
        StringBuilder encodedPattern = new StringBuilder(basicPattern.size() * 2);

        for (String tag : basicPattern)
            encodedPattern.append(getEncodedCoordinate(tag));

        return encodedPattern;
    }

    private ArrayList<String> getBasicPattern() {
        ArrayList<String> basicPattern = new ArrayList<>(patternData.size());
        basicPattern.addAll(patternData);

        return basicPattern;
    }

    private String putExtraCharacter(StringBuilder encodedPattern, int index, String extraChar) {
        encodedPattern.insert(index + trafficData, extraChar);
        return encodedPattern.toString();
    }

    private String getEncodedCoordinate(String coord) {
        if (coord.length() != 2)
            throw new IllegalArgumentException("Coordinate did not have exactly 2 numbers in it.");

        StringBuilder encodedCoord = new StringBuilder();
        int asciiCodeOfY = coord.charAt(0);
        int asciiCodeOfX = coord.charAt(1);
        encodedCoord.append(asciiCodeOfY);
        encodedCoord.append(asciiCodeOfX);

        char leftMiddleNumber = encodedCoord.toString().charAt(1);
        char rightMiddleNumber = encodedCoord.toString().charAt(2);

        encodedCoord.deleteCharAt(2);
        encodedCoord.setCharAt(1, getSingleDigitNumber(charToInt(leftMiddleNumber), charToInt(rightMiddleNumber)));

        return shuffleASCIICoordinate(encodedCoord, coord);
    }

    private String shuffleASCIICoordinate(StringBuilder encodedCoord, String coord) {
        encodedCoord.setCharAt(0, getSingleDigitNumber(encodedCoord.charAt(0), encodedCoord.charAt(1)));
        encodedCoord.setCharAt(0, getSingleDigitNumber(encodedCoord.charAt(0), encodedCoord.charAt(2)));
        encodedCoord.setCharAt(1, getSingleDigitNumber(encodedCoord.charAt(1), encodedCoord.charAt(2)));

        if (Integer.parseInt(coord) % 2 == 0) {
            char x = encodedCoord.charAt(2);
            encodedCoord.setCharAt(2, encodedCoord.charAt(1));
            encodedCoord.setCharAt(1, x);
        }

        return encodedCoord.toString();
    }

    private char getSingleDigitNumber(int operand1, int operand2) {
        if (operand1 + operand2 < 10)
            return String.valueOf(operand1 + operand2).charAt(0);
        else
            return String.valueOf(Math.abs(operand1 - operand2)).charAt(0);
    }

    private int charToInt(char character) {
        return Integer.parseInt(String.valueOf(character));
    }
}
