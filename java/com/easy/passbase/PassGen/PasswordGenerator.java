package com.easy.passbase.PassGen;

import java.security.SecureRandom;
import java.util.Arrays;
import java.util.LinkedList;

public class PasswordGenerator {
    private final static SecureRandom RNG = new SecureRandom();
    private final static int AMOUNT_OF_CATS = 4;

    private final LinkedList<Character> LOWERCASE = new LinkedList<>();
    private final LinkedList<Character> UPPERCASE = new LinkedList<>();
    private final LinkedList<Character> NUMBER = new LinkedList<>();
    private final LinkedList<Character> SYMBOL = new LinkedList<>();

    private final LinkedList<LinkedList<Character>> CHAR_CATS = new LinkedList<>();
    private final boolean[] SELECTED_CATS = new boolean[AMOUNT_OF_CATS];

    PasswordGenerator(LinkedList<Integer> usedCategories, char[] exclusions) {
        initialiseCharLists();
        removeExclusions(exclusions);
        initialiseSelectedCats(usedCategories);
    }

    private void initialiseCharLists() {
        char[] l = "abcdefghijklmnopqrstuvwxyz".toCharArray();
        char[] u = "ABCDEFGHIJKLMNOPQRSTUVWXYZ".toCharArray();
        char[] n = "0123456789".toCharArray();
        char[] s = "$*.[]{}()?-\"!@#%&/\\,><':;|_".toCharArray();

        for (char c : l)
            LOWERCASE.add(c);
        for (char c : u)
            UPPERCASE.add(c);
        for (char c : n)
            NUMBER.add(c);
        for (char c : s)
            SYMBOL.add(c);
    }

    private void removeExclusions(char[] exclusions) {
        for (Character c : exclusions) {
            if (Character.isLowerCase(c))
                LOWERCASE.remove(c);
            else if (Character.isUpperCase(c))
                UPPERCASE.remove(c);
            else if (Character.isDigit(c))
                NUMBER.remove(c);
            else
                SYMBOL.remove(c);
        }
    }

    private void initialiseSelectedCats(LinkedList<Integer> usedCategories) {
        for (int i = 0; i < usedCategories.size(); ++i)
            switch (usedCategories.get(i)) {
                case 0: CHAR_CATS.add(LOWERCASE); SELECTED_CATS[0] = true; break;
                case 1: CHAR_CATS.add(UPPERCASE); SELECTED_CATS[1] = true; break;
                case 2: CHAR_CATS.add(NUMBER)   ; SELECTED_CATS[2] = true; break;
                case 3: CHAR_CATS.add(SYMBOL)   ; SELECTED_CATS[3] = true; break;
            }

        for (int i = 0; i < CHAR_CATS.size(); ++i)
            if (CHAR_CATS.get(i).size() == 0) {
                CHAR_CATS.remove(i);
                SELECTED_CATS[i] = false;
            }
    }

    public void getPassword(int passwordLength) {
        System.out.println(randomConcat(passwordLength));
    }

    private String randomConcat(int passwordLength) {
        if (CHAR_CATS.size() == 0)
            return null;

        StringBuilder password = new StringBuilder();

        for (int i = 0; i < passwordLength; ++i) {
            LinkedList<Character> list = CHAR_CATS.get(RNG.nextInt(CHAR_CATS.size()));
            char nextChar = list.get(RNG.nextInt(list.size()));
            password.append(nextChar);

            if (needsRebuilding(password, passwordLength)) {
                password = new StringBuilder();
                i = 0;
            }
        }

        return password.toString();
    }

    private boolean needsRebuilding(StringBuilder password, int passwordLength) {
        return passwordLength >= CHAR_CATS.size() &&
                password.length() == passwordLength - 1 &&
                doesNotContainSelectedCats(password);
    }

    @SuppressWarnings("ConstantConditions")
    private boolean doesNotContainSelectedCats(StringBuilder password) {
        boolean[] presentCats = new boolean[AMOUNT_OF_CATS];

        for (int i = 0; i < password.length(); ++i)
            presentCats = checkPresentCats(presentCats, password.charAt(i));

        return !Arrays.equals(presentCats, SELECTED_CATS);
    }

    private boolean[] checkPresentCats(boolean[] presentCats, char c) {
        if (Character.isLowerCase(c))
            presentCats[0] = true;
        else if (Character.isUpperCase(c))
            presentCats[1] = true;
        else if (Character.isDigit(c))
            presentCats[2] = true;
        else
            presentCats[3] = true;

        return presentCats;
    }
}
