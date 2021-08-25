package com.easy.argonify.Settings.Applock.Pattern;

import static com.easy.argonify.Settings.Applock.Pattern.Pattern.BOTTOM_BAR_INDEX;
import static com.easy.argonify.Settings.Applock.Pattern.Pattern.TOP_BAR_INDEX;

public class ExtraCharacterMap {
    private static final String allChars =
            "abcdefghijklmnopqrstuvwxyz" +
            "ABCDEFGHIJKLMNOPQRSTUVWXYZ" +
            "$*.[]{}()?~-\"!@#%&/\\,><':;|_" +
            "ÄÅÆËÊÏÎÐÑÔÖØÚÜßäåýþÿ";

    private static final char[] extraCharacter = allChars.toCharArray();

    public static String getExtraChar(int[] barData) {
        String index = String.valueOf(barData[TOP_BAR_INDEX]) + barData[BOTTOM_BAR_INDEX];
        return String.valueOf(extraCharacter[Integer.parseInt(index)]);
    }
}
