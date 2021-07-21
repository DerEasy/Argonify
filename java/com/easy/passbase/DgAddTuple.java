package com.easy.passbase;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.ContentValues;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;

import static com.easy.passbase.PasswordDB.passwordDB;
import static com.easy.passbase.PasswordDBHelper.AMOUNT_OF_MAIN_ATTRIBUTES;
import static com.easy.passbase.PasswordDBHelper.COLUMN_NAME;
import static com.easy.passbase.PasswordDBHelper.COLUMN_PASSWORD;
import static com.easy.passbase.PasswordDBHelper.MAIN_ATTRIBUTES;
import static com.easy.passbase.PasswordDBHelper.TABLE_NAME;

public class DgAddTuple extends AppCompatDialogFragment {
    private final EditText[] attributes = new EditText[AMOUNT_OF_MAIN_ATTRIBUTES];

    @SuppressWarnings("ConstantConditions")
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View addTupleView = inflater.inflate(R.layout.dg_add_tuple, null);

        attributes[0] = addTupleView.findViewById(R.id.etxt_addName);
        attributes[1] = addTupleView.findViewById(R.id.etxt_addPassword);
        attributes[2] = addTupleView.findViewById(R.id.etxt_addEmail);
        attributes[3] = addTupleView.findViewById(R.id.etxt_addUsername);
        attributes[4] = addTupleView.findViewById(R.id.etxt_addNotes);

        builder.setView(addTupleView)
                .setTitle("Add a new entry")
                .setNegativeButton("Cancel", (dialog, which) -> {})
                .setPositiveButton("Add", (dialog, which) -> {
                    if (!add(getCV(getInput())))
                        Toast.makeText(getContext(), "Entry must have at least a name and a password", Toast.LENGTH_SHORT).show();
                });

        return builder.create();
    }

    private String[] getInput() {
        String[] input = new String[AMOUNT_OF_MAIN_ATTRIBUTES];
        for (int i = 0; i < AMOUNT_OF_MAIN_ATTRIBUTES; ++i)
            input[i] = String.valueOf(attributes[i].getText());

        return input;
    }

    private ContentValues getCV(String[] input) {
        ContentValues cv = new ContentValues();
        for (int i = 0; i < AMOUNT_OF_MAIN_ATTRIBUTES; ++i)
            cv.put(MAIN_ATTRIBUTES[i], input[i]);

        return cv;
    }

    private boolean isInputValid(ContentValues cv) {
        return cv.get(COLUMN_NAME) != null &&
               cv.get(COLUMN_PASSWORD) != null &&
               !String.valueOf(cv.get(COLUMN_NAME)).isEmpty() &&
               !String.valueOf(cv.get(COLUMN_PASSWORD)).isEmpty();
    }

    private boolean add(ContentValues cv) {
        boolean successful = isInputValid(cv);
        if (!successful)
            return false;

        passwordDB.insert(
                TABLE_NAME,
                null,
                cv
        );
        return true;
    }
}
