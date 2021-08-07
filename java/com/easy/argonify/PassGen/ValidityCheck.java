package com.easy.argonify.PassGen;

import android.widget.Toast;

import java.util.Arrays;

import static com.easy.argonify.PassGen.PasswordGenerator.AMOUNT_OF_CATS;

public class ValidityCheck {
    private final PassGenActivity passGenActivity;
    private final PasswordGenerator generator;

    ValidityCheck(PassGenActivity parentActivity, PasswordGenerator generator) {
        this.generator = generator;
        this.passGenActivity = parentActivity;
    }

    boolean hasErrors() {
        return errorNoCats();
    }

    private boolean errorNoCats() {
        if(generator.CHAR_CATS.size() == 0) {
            Toast.makeText(passGenActivity, "No categories selected or all characters have been excluded.", Toast.LENGTH_SHORT).show();
            return true;
        }
        return false;
    }

    boolean needsRebuilding(StringBuilder password, int passwordLength) {
        return password.length() == passwordLength &&
                passwordLength >= generator.CHAR_CATS.size() &&
                doesNotContainSelectedCats(password);
    }

    @SuppressWarnings("ConstantConditions")
    private boolean doesNotContainSelectedCats(StringBuilder password) {
        boolean[] presentCats = new boolean[AMOUNT_OF_CATS];

        for (int i = 0; i < password.length(); ++i)
            presentCats = checkPresentCats(presentCats, password.charAt(i));

        return !Arrays.equals(presentCats, generator.SELECTED_CATS);
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
