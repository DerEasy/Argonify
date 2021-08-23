package com.easy.argonify.Settings.Applock.Password;

import static com.easy.argonify.Utility.RequestApplock.requestApplock;

import android.content.Intent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.appcompat.content.res.AppCompatResources;

import com.easy.argonify.R;
import com.easy.argonify.Settings.Applock.ApplockStrings;

class SetPassword extends Password implements ApplockStrings {
    private final SetPasswordActivity setPasswordActivity;

    private final static int MIN_LENGTH = 8;
    private final static int AMOUNT_OF_REQUIREMENTS = 5;
    private final static int INDEX_REQUIREMENT_LENGTH = 0;
    private final static int INDEX_REQUIREMENT_LOWERCASE = 1;
    private final static int INDEX_REQUIREMENT_UPPERCASE = 2;
    private final static int INDEX_REQUIREMENT_NUMBER = 3;
    private final static int INDEX_REQUIREMENT_SYMBOL = 4;
    private final static ImageView[] imgReq = new ImageView[AMOUNT_OF_REQUIREMENTS];

    SetPassword(SetPasswordActivity parentActivity, EditText passwordView) {
        super(passwordView);
        setPasswordActivity = parentActivity;

        imgReq[INDEX_REQUIREMENT_LENGTH] = setPasswordActivity.findViewById(R.id.img_setPasswordRequirementLength);
        imgReq[INDEX_REQUIREMENT_LOWERCASE] = setPasswordActivity.findViewById(R.id.img_setPasswordRequirementLowercase);
        imgReq[INDEX_REQUIREMENT_UPPERCASE] = setPasswordActivity.findViewById(R.id.img_setPasswordRequirementUppercase);
        imgReq[INDEX_REQUIREMENT_NUMBER] = setPasswordActivity.findViewById(R.id.img_setPasswordRequirementNumbers);
        imgReq[INDEX_REQUIREMENT_SYMBOL] = setPasswordActivity.findViewById(R.id.img_setPasswordRequirementSymbols);
    }

    @Override
    void onTextChange() {
        setConfirmationButtonVisibility(evaluateRequirements());
    }

    @Override
    void onPasswordConfirmation() {
        requestApplock(setPasswordActivity, REASON_REENTER_CONFIRM, SAVE_KEY, PASSWORD, getHashedPassword());
        setPasswordActivity.finish();
    }

    void setConfirmationButtonVisibility(boolean status) {
        View confirmButton = setPasswordActivity.findViewById(R.id.fab_setPasswordConfirm);
        if (status)
            confirmButton.setVisibility(View.VISIBLE);
        else
            confirmButton.setVisibility(View.INVISIBLE);
    }

    boolean evaluateRequirements() {
        if (passwordData.equals("0"))
            return true;

        boolean[] requirement = new boolean[AMOUNT_OF_REQUIREMENTS];
        requirement[INDEX_REQUIREMENT_LENGTH] = evaluateLength();
        requirement[INDEX_REQUIREMENT_LOWERCASE] = evaluateLowercase();
        requirement[INDEX_REQUIREMENT_UPPERCASE] = evaluateUppercase();
        requirement[INDEX_REQUIREMENT_NUMBER] = evaluateNumber();
        requirement[INDEX_REQUIREMENT_SYMBOL] = evaluateSymbol();

        updateRequirement(requirement);
        return fulfillsRequirements(requirement);
    }

    boolean fulfillsRequirements(boolean[] requirement) {
        for (boolean b : requirement)
            if (!b)
                return false;
        return true;
    }

    void updateRequirement(boolean[] requirement) {
        for (int i = 0; i < requirement.length; ++i) {
            if (requirement[i])
                imgReq[i].setImageDrawable(AppCompatResources.getDrawable(setPasswordActivity, R.drawable.circle_status_true));
            else
                imgReq[i].setImageDrawable(AppCompatResources.getDrawable(setPasswordActivity, R.drawable.circle_status_false));
        }
    }

    boolean evaluateLength() {
        return passwordData.length() >= MIN_LENGTH;
    }

    boolean evaluateLowercase() {
        for (int i = passwordData.length() - 1; i >= 0; --i)
            if (Character.isLowerCase(passwordData.charAt(i)))
                return true;
        return false;
    }

    boolean evaluateUppercase() {
        for (int i = passwordData.length() - 1; i >= 0; --i)
            if (Character.isUpperCase(passwordData.charAt(i)))
                return true;
        return false;
    }

    boolean evaluateNumber() {
        for (int i = passwordData.length() - 1; i >= 0; --i)
            if (Character.isDigit(passwordData.charAt(i)))
                return true;
        return false;
    }

    boolean evaluateSymbol() {
        for (int i = passwordData.length() - 1; i >= 0; --i) {
            char c = passwordData.charAt(i);

            if (!Character.isDigit(c) &&
                    !Character.isLowerCase(c) &&
                    !Character.isUpperCase(c))
                return true;
        }
        return false;
    }
}
