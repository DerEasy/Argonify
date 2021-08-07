package com.easy.argonify.PassGen;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.Toast;

import com.easy.argonify.Main.TextDecryptor;
import com.easy.argonify.Main.TupleManipulation;
import com.easy.argonify.R;
import com.google.android.material.switchmaterial.SwitchMaterial;

import java.util.LinkedList;

public class PasswordDisplay {
    private final static int AMOUNT_OF_CHAR_SWITCHES = 4;

    private final PassGenActivity passGenActivity;
    private final SwitchMaterial[] charSwitch = new SwitchMaterial[AMOUNT_OF_CHAR_SWITCHES];
    private final EditText etxtExclusions;
    private final NumberPicker npPasswordLength;
    private final TextView txtGeneratedPassword;
    private final ImageButton ibtCopyGeneratedPW;
    private final boolean[] checkedSwitches = new boolean[AMOUNT_OF_CHAR_SWITCHES];

    PasswordDisplay(PassGenActivity parentActivity) {
        passGenActivity = parentActivity;

        charSwitch[0] = passGenActivity.findViewById(R.id.switch_genLowercase);
        charSwitch[1] = passGenActivity.findViewById(R.id.switch_genUppercase);
        charSwitch[2] = passGenActivity.findViewById(R.id.switch_genNumbers);
        charSwitch[3] = passGenActivity.findViewById(R.id.switch_genSymbols);

        etxtExclusions = passGenActivity.findViewById(R.id.etxt_genExcludedChars);
        npPasswordLength = passGenActivity.findViewById(R.id.np_genPasswordLength);
        txtGeneratedPassword = passGenActivity.findViewById(R.id.txt_genPassword);
        ibtCopyGeneratedPW = passGenActivity.findViewById(R.id.ibt_copyGeneratedPW);

        if (TupleManipulation.isOpen) {
            ibtCopyGeneratedPW.setVisibility(View.GONE);
            passGenActivity.findViewById(R.id.txt_genFromManipulation).setVisibility(View.VISIBLE);
        }

        initialiseSwitches();
        initialiseNumberPicker();
    }

    private void initialiseSwitches() {
        for (int i = 0; i < AMOUNT_OF_CHAR_SWITCHES; ++i) {
            int j = i; //Lambda needs effectively final int, so this is necessary
            charSwitch[j].setOnCheckedChangeListener((buttonView, isChecked) -> checkedSwitches[j] = isChecked);
            charSwitch[j].setChecked(true);
        }
    }

    private void initialiseNumberPicker() {
        npPasswordLength.setMinValue(1);
        npPasswordLength.setMaxValue(64);
        npPasswordLength.setValue(20);
    }

    void copyGeneratedPasswordToClipboard() {
        ClipboardManager clipboard = (ClipboardManager) passGenActivity.getSystemService(Context.CLIPBOARD_SERVICE);
        ClipData clip = ClipData.newPlainText("Clipped generated password", txtGeneratedPassword.getText());
        clipboard.setPrimaryClip(clip);

        Toast.makeText(passGenActivity, "Copied generated password to clipboard", Toast.LENGTH_SHORT).show();
    }

    void onDisplayUpdate(String password, boolean showCopyButton) {
        txtGeneratedPassword.setText(password);
        if (showCopyButton)
            ibtCopyGeneratedPW.setVisibility(View.VISIBLE);
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

    public void generate() {
        new PasswordGenerator(passGenActivity, this, getCharCategories(), getExclusions())
                .getPassword(npPasswordLength.getValue());
    }
}
