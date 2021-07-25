package com.easy.passbase;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.content.ContextCompat;

import static com.easy.passbase.PasswordDBHelper.AMOUNT_OF_MAIN_ATTRIBUTES;
import static com.easy.passbase.PasswordDBHelper.INDEX_EMAIL;
import static com.easy.passbase.PasswordDBHelper.INDEX_NAME;
import static com.easy.passbase.PasswordDBHelper.INDEX_NOTES;
import static com.easy.passbase.PasswordDBHelper.INDEX_PASSWORD;
import static com.easy.passbase.PasswordDBHelper.INDEX_USERNAME;
import static com.easy.passbase.PasswordDBHelper.MAIN_ATTRIBUTES;

public class SelectionDisplay {
    private final static int AMOUNT_OF_COPY_BUTTONS = 3;
    private final static int ACTUAL_PASSWORD = 0;
    private final static int HIDDEN_PASSWORD = 1;
    private final MainActivity mainActivity;

    /**Index 0 is the actual password, index 1 is the password in 'hidden' symbols*/
    private final String[] currentPassword = new String[2];
    private final TextView[] txtAttribute = new TextView[AMOUNT_OF_MAIN_ATTRIBUTES];
    private final TextView[] txtTitle = new TextView[AMOUNT_OF_MAIN_ATTRIBUTES];
    private final ImageButton[] ibtCopy = new ImageButton[AMOUNT_OF_COPY_BUTTONS];
    private final ImageButton ibtPasswordReveal;
    private boolean isPasswordRevealed = false;

    SelectionDisplay(MainActivity parentActivity) {
        mainActivity = parentActivity;

        txtAttribute[INDEX_NAME]     = mainActivity.findViewById(R.id.txt_selectedName);
        txtAttribute[INDEX_PASSWORD] = mainActivity.findViewById(R.id.txt_selectedPassword);
        txtAttribute[INDEX_EMAIL]    = mainActivity.findViewById(R.id.txt_selectedEmail);
        txtAttribute[INDEX_USERNAME] = mainActivity.findViewById(R.id.txt_selectedUsername);
        txtAttribute[INDEX_NOTES]    = mainActivity.findViewById(R.id.txt_selectedNotes);

        txtTitle[INDEX_NAME]     = mainActivity.findViewById(R.id.txt_titleName);
        txtTitle[INDEX_PASSWORD] = mainActivity.findViewById(R.id.txt_titlePassword);
        txtTitle[INDEX_EMAIL]    = mainActivity.findViewById(R.id.txt_titleEmail);
        txtTitle[INDEX_USERNAME] = mainActivity.findViewById(R.id.txt_titleUsername);
        txtTitle[INDEX_NOTES]    = mainActivity.findViewById(R.id.txt_titleNotes);

        ibtCopy[0] = mainActivity.findViewById(R.id.ibt_copyPassword);
        ibtCopy[1] = mainActivity.findViewById(R.id.ibt_copyEmail);
        ibtCopy[2] = mainActivity.findViewById(R.id.ibt_copyUsername);

        ibtPasswordReveal = mainActivity.findViewById(R.id.ibt_selectPasswordReveal);

        attachPasswordRevealListener();
    }

    private void attachPasswordRevealListener() {
        ibtPasswordReveal.setOnClickListener(v -> {
            isPasswordRevealed = !isPasswordRevealed;
            if (isPasswordRevealed) {
                ibtPasswordReveal.setImageDrawable(ContextCompat.getDrawable(mainActivity, R.drawable.ic_round_visibility_off_24));
                txtAttribute[INDEX_PASSWORD].setText(currentPassword[ACTUAL_PASSWORD]);
            } else {
                ibtPasswordReveal.setImageDrawable(ContextCompat.getDrawable(mainActivity, R.drawable.ic_round_visibility_24));
                txtAttribute[INDEX_PASSWORD].setText(currentPassword[HIDDEN_PASSWORD]);
            }
        });
    }

    private void setCurrentPassword(String password) {
        currentPassword[ACTUAL_PASSWORD] = password;
        StringBuilder hiddenPassword = new StringBuilder();
        for (int i = 0; i < password.length(); ++i)
            hiddenPassword.append("\u2022");
        currentPassword[HIDDEN_PASSWORD] = hiddenPassword.toString();
    }

    private void hidePassword() {
        isPasswordRevealed = false;
        txtAttribute[INDEX_PASSWORD].setText(currentPassword[HIDDEN_PASSWORD]);
    }

    public void onDisplayUpdate(String[] attributes) {
        for (int i = 0; i < AMOUNT_OF_MAIN_ATTRIBUTES; ++i)
            setTextView(i, attributes[i]);

        setCurrentPassword(attributes[INDEX_PASSWORD]);
        setCopyButtonVisibility();
        setPasswordRevealVisibility();
        hidePassword();
    }

    public void copyAttributeToClipboard(View v) {
        /* IMPORTANT:
         * The tags of the three 'Copy ImageButtons' in activity_main.xml are in order:
         * 0, 1, 2  -- for -- Password, Email, Notes
         * These tags are used as an index in the following code, hence changing these
         * tags to something different will break the app.*/
        int index = Integer.parseInt((String) v.getTag()) + 1;

        String attribute;
        if (index == INDEX_PASSWORD)
            attribute = currentPassword[ACTUAL_PASSWORD];
        else
            attribute = (String) txtAttribute[index].getText();

        ClipboardManager clipboard = (ClipboardManager) mainActivity.getSystemService(Context.CLIPBOARD_SERVICE);
        ClipData clip = ClipData.newPlainText("Clipped " + MAIN_ATTRIBUTES[index], attribute);
        clipboard.setPrimaryClip(clip);

        Toast.makeText(
                mainActivity,
                String.format("Copied %s to clipboard", MAIN_ATTRIBUTES[index]),
                Toast.LENGTH_SHORT
        ).show();
    }

    private void setTextView(int txtAttributeIndex, String text) {
        TextView title = txtTitle[txtAttributeIndex];
        TextView attribute = txtAttribute[txtAttributeIndex];

        if (text == null || text.isEmpty()) {
            attribute.setText(R.string.unavailable_info);
            attribute.setVisibility(View.GONE);
            title.setVisibility(View.GONE);
        } else {
            attribute.setText(text);
            attribute.setVisibility(View.VISIBLE);
            title.setVisibility(View.VISIBLE);
        }
    }

    private void setCopyButtonVisibility() {
        //Only Password, Email and Username are being checked
        //as these are the only ones with copy buttons
        for (int i = 0; i < AMOUNT_OF_COPY_BUTTONS; ++i) {
            if (txtAttribute[i + 1].getText().equals(mainActivity.getString(R.string.unavailable_info)))
                ibtCopy[i].setVisibility(View.INVISIBLE);
            else
                ibtCopy[i].setVisibility(View.VISIBLE);
        }
    }

    private void setPasswordRevealVisibility() {
        if (txtAttribute[INDEX_PASSWORD].getVisibility() == View.VISIBLE)
            ibtPasswordReveal.setVisibility(View.VISIBLE);
        else
            ibtPasswordReveal.setVisibility(View.INVISIBLE);
    }
}