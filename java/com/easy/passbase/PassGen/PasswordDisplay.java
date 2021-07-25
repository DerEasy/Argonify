package com.easy.passbase.PassGen;

import android.widget.EditText;
import android.widget.NumberPicker;

import com.easy.passbase.R;
import com.google.android.material.switchmaterial.SwitchMaterial;

import java.util.LinkedList;

public class PasswordDisplay {
    private final static int AMOUNT_OF_CHAR_SWITCHES = 4;

    private final PassGenActivity passGenActivity;
    private final SwitchMaterial[] charSwitch = new SwitchMaterial[AMOUNT_OF_CHAR_SWITCHES];
    private final EditText etxtExclusions;
    private final NumberPicker npPasswordLength;
    private final boolean[] checkedSwitches = new boolean[AMOUNT_OF_CHAR_SWITCHES];
    private int passwordLength;

    PasswordDisplay(PassGenActivity parentActivity) {
        passGenActivity = parentActivity;

        charSwitch[0] = passGenActivity.findViewById(R.id.switch_lowercase);
        charSwitch[1] = passGenActivity.findViewById(R.id.switch_uppercase);
        charSwitch[2] = passGenActivity.findViewById(R.id.switch_numbers);
        charSwitch[3] = passGenActivity.findViewById(R.id.switch_symbols);

        etxtExclusions = passGenActivity.findViewById(R.id.etxt_excludedChars);
        npPasswordLength = passGenActivity.findViewById(R.id.np_passwordLength);

        initialiseSwitches();
        initialiseNumberPicker();
    }

    private void initialiseSwitches() {
        for (int i = 0; i < AMOUNT_OF_CHAR_SWITCHES; ++i) {
            int j = i; //Lambda needs effectively final int, so this is necessary
            charSwitch[j].setOnCheckedChangeListener((buttonView, isChecked) -> checkedSwitches[j] = isChecked);
        }
    }

    private void initialiseNumberPicker() {
        npPasswordLength.setMinValue(1);
        npPasswordLength.setMaxValue(255);
        npPasswordLength.setValue(20);
        npPasswordLength.setOnValueChangedListener((picker, oldVal, newVal) -> passwordLength = newVal);
    }

    public void generate() {
        PasswordGenerator generator = new PasswordGenerator(getCharCategories(), getExclusions());
        generator.getPassword(passwordLength);
    }

    private LinkedList<Integer> getCharCategories() {
        LinkedList<Integer> charCategories = new LinkedList<>();
        for (int i = 0; i < AMOUNT_OF_CHAR_SWITCHES; ++i)
            if (checkedSwitches[i])
                charCategories.add(i);

        return charCategories;
    }

    private char[] getExclusions() {
        return String.valueOf(etxtExclusions.getText()).toCharArray();
    }
}
