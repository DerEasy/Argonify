package com.easy.argonify.PassGen;

import static com.easy.argonify.Settings.Applock.ApplockStrings.EMPTY;

import com.easy.argonify.Main.Dialogs.TupleManipulation;

import java.security.SecureRandom;
import java.util.LinkedList;

public class PasswordGenerator {
    private final PasswordDisplay passwordDisplay;
    private final PassGenActivity passGenActivity;
    private final SecureRandom rng = new SecureRandom();

    private final LinkedList<Character> LOWERCASE = new LinkedList<>();
    private final LinkedList<Character> UPPERCASE = new LinkedList<>();
    private final LinkedList<Character> NUMBER = new LinkedList<>();
    private final LinkedList<Character> SYMBOL = new LinkedList<>();

    public final static int AMOUNT_OF_CATS = 4;
    final LinkedList<LinkedList<Character>> CHAR_CATS = new LinkedList<>();
    final boolean[] SELECTED_CATS = new boolean[AMOUNT_OF_CATS];

    public PasswordGenerator(PassGenActivity parentActivity, PasswordDisplay display, LinkedList<Integer> usedCategories, char[] exclusions) {
        passGenActivity = parentActivity;
        passwordDisplay = display;
        initialiseCharLists();
        removeExclusions(exclusions);
        initialiseSelectedCats(usedCategories);
    }

    private void initialiseCharLists() {
        char[] l = "abcdefghijklmnopqrstuvwxyz".toCharArray();
        char[] u = "ABCDEFGHIJKLMNOPQRSTUVWXYZ".toCharArray();
        char[] n = "0123456789".toCharArray();
        char[] s = "$*.[]{}()?~-\"!@#%&/\\,><':;|_".toCharArray();

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

    public String getPassword(int passwordLength) {
        ValidityCheck validityCheck = new ValidityCheck(passGenActivity,this);
        if (validityCheck.hasErrors())
            return EMPTY;

        StringBuilder password = new StringBuilder();

        for (int i = 0; i < passwordLength; ++i) {
            LinkedList<Character> list = CHAR_CATS.get(rng.nextInt(CHAR_CATS.size()));
            char nextChar = list.get(rng.nextInt(list.size()));
            password.append(nextChar);

            if (validityCheck.needsRebuilding(password, passwordLength)) {
                password = new StringBuilder();
                i = -1; //I absolutely cannot fathom or grasp in any way whatsoever why this needs to be -1 instead of 0
            }
        }

        if (passwordDisplay != null && !TupleManipulation.isOpen)
            passwordDisplay.onDisplayUpdate(password.toString(), true);
        else if (passwordDisplay != null){
            passwordDisplay.onDisplayUpdate(password.toString(), false);
            TupleManipulation.generatedPassword = password.toString();
        }

        return password.toString();
    }
}
