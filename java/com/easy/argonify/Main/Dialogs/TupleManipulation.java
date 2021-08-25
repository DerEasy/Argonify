package com.easy.argonify.Main.Dialogs;

import static com.easy.argonify.PassGen.PasswordGenerator.AMOUNT_OF_CATS;
import static com.easy.argonify.Settings.Applock.ApplockStrings.EMPTY;
import static com.easy.argonify.Utility.PasswordDBHelper.AMOUNT_OF_MAIN_ATTRIBUTES;
import static com.easy.argonify.Utility.PasswordDBHelper.COLUMN_NAME;
import static com.easy.argonify.Utility.PasswordDBHelper.COLUMN_PASSWORD;
import static com.easy.argonify.Utility.PasswordDBHelper.INDEX_EMAIL;
import static com.easy.argonify.Utility.PasswordDBHelper.INDEX_NAME;
import static com.easy.argonify.Utility.PasswordDBHelper.INDEX_NOTES;
import static com.easy.argonify.Utility.PasswordDBHelper.INDEX_PASSWORD;
import static com.easy.argonify.Utility.PasswordDBHelper.INDEX_USERNAME;
import static com.easy.argonify.Utility.PasswordDBHelper.MAIN_ATTRIBUTES;

import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.text.method.PasswordTransformationMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatDialogFragment;
import androidx.core.content.ContextCompat;

import com.easy.argonify.PassGen.PassGenActivity;
import com.easy.argonify.PassGen.PasswordGenerator;
import com.easy.argonify.R;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.LinkedList;

public abstract class TupleManipulation extends AppCompatDialogFragment {
    public final EditText[] etxtAttribute = new EditText[AMOUNT_OF_MAIN_ATTRIBUTES];
    public boolean isPasswordRevealed = false;
    public static boolean isOpen = false;
    public static String generatedPassword = EMPTY;
    public View tupleView;
    public AlertDialog.Builder builder;

    public void prepareDialog() {
        isOpen = true;

        builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        tupleView = inflater.inflate(R.layout.dg_manipulate_tuple, null);

        etxtAttribute[INDEX_NAME]     = tupleView.findViewById(R.id.etxt_manipulateName);
        etxtAttribute[INDEX_PASSWORD] = tupleView.findViewById(R.id.etxt_manipulatePassword);
        etxtAttribute[INDEX_EMAIL]    = tupleView.findViewById(R.id.etxt_manipulateEmail);
        etxtAttribute[INDEX_USERNAME] = tupleView.findViewById(R.id.etxt_manipulateUsername);
        etxtAttribute[INDEX_NOTES]    = tupleView.findViewById(R.id.etxt_manipulateNotes);

        ExtendedFloatingActionButton efabInstantGen = tupleView.findViewById(R.id.efab_addInstantGen);
        FloatingActionButton fabGenerator = tupleView.findViewById(R.id.efab_addOpenGenerator);

        attachPasswordRevealListener(tupleView.findViewById(R.id.ibt_manipulatePasswordReveal));
        attachPasswordGeneratorListener(efabInstantGen, fabGenerator);
    }

    @Override
    public void onDismiss(@NonNull DialogInterface dialog) {
        super.onDismiss(dialog);
        isOpen = false;
        generatedPassword = EMPTY;
    }

    @Override
    public void onResume() {
        super.onResume();
        if (!generatedPassword.isEmpty())
            etxtAttribute[INDEX_PASSWORD].setText(generatedPassword);
    }

    @SuppressWarnings("ConstantConditions")
    private void attachPasswordRevealListener(ImageButton passwordReveal) {
        passwordReveal.setOnClickListener(v -> {
            isPasswordRevealed = !isPasswordRevealed;
            if (isPasswordRevealed) {
                passwordReveal.setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable.ic_round_visibility_off_24));
                etxtAttribute[INDEX_PASSWORD].setTransformationMethod(null);
            } else {
                passwordReveal.setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable.ic_round_visibility_24));
                etxtAttribute[INDEX_PASSWORD].setTransformationMethod(new PasswordTransformationMethod());
            }
        });
    }

    public void attachPasswordGeneratorListener(ExtendedFloatingActionButton efabInstantGen, FloatingActionButton fabGenerator) {
        final int DEFAULT_PASSWORD_LENGTH = 20;
        final LinkedList<Integer> charCategories = new LinkedList<>();
        final char[] exclusions = {};

        for (int i = 0; i < AMOUNT_OF_CATS; ++i)
            charCategories.add(i);

        efabInstantGen.setOnClickListener(v -> {
            String password = new PasswordGenerator(
                    null, null, charCategories, exclusions)
                    .getPassword(DEFAULT_PASSWORD_LENGTH);

            etxtAttribute[INDEX_PASSWORD].setText(password);
        });
        fabGenerator.setOnClickListener(v -> {
            Intent passwordGeneratorIntent = new Intent(getActivity(), PassGenActivity.class);
            startActivity(passwordGeneratorIntent);
        });
    }

    public String[] getInput() {
        String[] input = new String[AMOUNT_OF_MAIN_ATTRIBUTES];
        for (int i = 0; i < AMOUNT_OF_MAIN_ATTRIBUTES; ++i)
            input[i] = String.valueOf(etxtAttribute[i].getText());

        return input;
    }

    public ContentValues getCV(String[] input) {
        ContentValues cv = new ContentValues();
        for (int i = 0; i < AMOUNT_OF_MAIN_ATTRIBUTES; ++i)
            cv.put(MAIN_ATTRIBUTES[i], input[i]);

        return cv;
    }

    public boolean isInputValid(ContentValues cv) {
        return cv.get(COLUMN_NAME) != null &&
                cv.get(COLUMN_PASSWORD) != null &&
                !String.valueOf(cv.get(COLUMN_NAME)).isEmpty() &&
                !String.valueOf(cv.get(COLUMN_PASSWORD)).isEmpty();
    }
}
