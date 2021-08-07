package com.easy.argonify.Settings;

public class ThousandSeparator {
    private final static int SEPARATOR_DISTANCE = 3;
    private final static char SEPARATOR_CHARACTER = ' ';

    public static String thousandSeparatorFormat(String string) {
        StringBuilder builder = new StringBuilder(string);

        for (int i = 1; i <= getSeparatorAmount(string); ++i)
            builder.insert(getOffset(string, i), SEPARATOR_CHARACTER);

        return builder.toString();
    }

    private static int getLastIndex(String string) {
        return string.length() - 1;
    }

    private static int getSeparatorAmount(String string) {
        return getLastIndex(string) / SEPARATOR_DISTANCE;
    }

    private static int getOffset(String string, int iterator) {
        return string.length() - (iterator * SEPARATOR_DISTANCE);
    }
}
