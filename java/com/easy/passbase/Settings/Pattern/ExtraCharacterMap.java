package com.easy.passbase.Settings.Pattern;

import static com.easy.passbase.Settings.Pattern.Pattern.BOTTOM_BAR_INDEX;
import static com.easy.passbase.Settings.Pattern.Pattern.TOP_BAR_INDEX;

@SuppressWarnings("SpellCheckingInspection")
public class ExtraCharacterMap {
    private static final String allChars =
            "abcdefghijklmnopqrstuvwxyz" +
            "ABCDEFGHIJKLMNOPQRSTUVWXYZ" +
            "789" +
            "$*.[]{}()?~-\"!@#%&/\\,><':;|_" +
            "ÄÅÆËÊÏÎÐÑÔÖØÚÜßäå";

    private static final char[] extraCharacter = allChars.toCharArray();

    public static String getExtraChar(int[] barData) {
        String index = String.valueOf(barData[TOP_BAR_INDEX]) + barData[BOTTOM_BAR_INDEX];
        return String.valueOf(extraCharacter[Integer.parseInt(index)]);
    }
}
