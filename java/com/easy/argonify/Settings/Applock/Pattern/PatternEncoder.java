package com.easy.argonify.Settings.Applock.Pattern;

import static com.easy.argonify.Settings.Applock.Pattern.ExtraCharacterMap.getExtraChar;

import com.easy.argonify.Settings.Applock.ApplockStrings;
import com.easy.argonify.Settings.Applock.Encoder;

import java.util.ArrayList;
import java.util.concurrent.ConcurrentLinkedDeque;

class PatternEncoder extends Encoder implements ApplockStrings {
    private final ConcurrentLinkedDeque<String> PATTERN_DATA;
    private final String FAV_CELL;
    private final int TRAFFIC_DATA;
    private final int[] BAR_DATA;

    PatternEncoder(
            ConcurrentLinkedDeque<String> PATTERN_DATA,
            String FAV_CELL,
            int TRAFFIC_DATA,
            int[] BAR_DATA
    ) {
        this.PATTERN_DATA = PATTERN_DATA;
        this.FAV_CELL = FAV_CELL;
        this.TRAFFIC_DATA = TRAFFIC_DATA;
        this.BAR_DATA = BAR_DATA;
    }

    protected String getRawKey() {
        ArrayList<String> basicPattern = getBasicPattern();
        StringBuilder pattern = getEncodedPattern(basicPattern);
        int favCellIndex = 1 + 3 * basicPattern.indexOf(FAV_CELL);

        return putExtraCharacter(pattern, favCellIndex, getExtraChar(BAR_DATA));
    }

    protected String getHashedKey() {
        return super.getHashedKey();
    }
    
    protected boolean inputKeyMatches(String requestedKey) {
        return super.inputKeyMatches(requestedKey);
    }

    private StringBuilder getEncodedPattern(ArrayList<String> basicPattern) {
        StringBuilder encodedPattern = new StringBuilder(basicPattern.size() * 2);

        for (String tag : basicPattern)
            encodedPattern.append(getEncodedCoordinate(tag));

        return encodedPattern;
    }

    private ArrayList<String> getBasicPattern() {
        ArrayList<String> basicPattern = new ArrayList<>(PATTERN_DATA.size());
        basicPattern.addAll(PATTERN_DATA);

        return basicPattern;
    }

    private String putExtraCharacter(StringBuilder encodedPattern, int index, String extraChar) {
        encodedPattern.insert(index + TRAFFIC_DATA, extraChar);
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
