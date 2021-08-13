package com.easy.argonify.Main;

import static com.easy.argonify.Utility.PasswordDBHelper.AMOUNT_OF_MAIN_ATTRIBUTES;
import static com.easy.argonify.Utility.PasswordDBHelper.INDEX_EMAIL;
import static com.easy.argonify.Utility.PasswordDBHelper.INDEX_NAME;
import static com.easy.argonify.Utility.PasswordDBHelper.INDEX_NOTES;
import static com.easy.argonify.Utility.PasswordDBHelper.INDEX_PASSWORD;
import static com.easy.argonify.Utility.PasswordDBHelper.INDEX_USERNAME;
import static com.easy.argonify.Utility.PasswordDBHelper.MAIN_ATTRIBUTES;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.content.ContextCompat;

import com.easy.argonify.R;

public class SelectionDisplay {
    private final MainActivity mainActivity;
    private final static int AMOUNT_OF_COPY_BUTTONS = 3;
    public final static int ACTUAL_PASSWORD = 0;
    public final static int HIDDEN_PASSWORD = 1;

    /**Index 0 is the actual password, index 1 is the password in 'hidden' symbols*/
    public final String[] currentPassword = new String[2];
    public final TextView[] txtAttribute = new TextView[AMOUNT_OF_MAIN_ATTRIBUTES];
    private final TextView[] txtTitle = new TextView[AMOUNT_OF_MAIN_ATTRIBUTES];
    private final ImageButton[] ibtCopy = new ImageButton[AMOUNT_OF_COPY_BUTTONS];
    private final ImageButton ibtPasswordReveal;
    private final ImageView appicon;
    private final TextView appname;
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

        ibtCopy[INDEX_PASSWORD - 1] = mainActivity.findViewById(R.id.ibt_copyPassword);
        ibtCopy[INDEX_EMAIL - 1] = mainActivity.findViewById(R.id.ibt_copyEmail);
        ibtCopy[INDEX_USERNAME - 1] = mainActivity.findViewById(R.id.ibt_copyUsername);

        ibtPasswordReveal = mainActivity.findViewById(R.id.ibt_selectPasswordReveal);
        appicon = mainActivity.findViewById(R.id.img_mainAppName);
        appname = mainActivity.findViewById(R.id.txt_mainAppIcon);

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
        setAppIconVisibility();
        hidePassword();
    }

    void copyAttributeToClipboard(View v) {
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
        ClipData clip = ClipData.newPlainText("Argonify " + MAIN_ATTRIBUTES[index], attribute);
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

    private void setAppIconVisibility() {
        if (displayIsEmpty())
            appicon.animate().alpha(1).setDuration(800);
        else
            appicon.animate().alpha(0).setDuration(800);
    }

    private boolean displayIsEmpty() {
        for (TextView txt : txtAttribute)
            if (txt.getVisibility() == View.VISIBLE)
                return false;
        return true;
    }
}
