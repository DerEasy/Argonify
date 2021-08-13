package com.easy.argonify.Main.Dialogs;

import static com.easy.argonify.Utility.PasswordDB.editTuple;
import static com.easy.argonify.Utility.PasswordDBHelper.AMOUNT_OF_MAIN_ATTRIBUTES;
import static com.easy.argonify.Utility.PasswordDBHelper.MAIN_ATTRIBUTES;
import static com.easy.argonify.Utility.PasswordDBHelper._ID;

import android.app.Dialog;
import android.content.ContentValues;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.easy.argonify.Main.MainActivity;

class DgEditTupleConfirmation extends TupleManipulation {
    private final MainActivity mainActivity;
    private final String[] oldAttributes = new String[AMOUNT_OF_MAIN_ATTRIBUTES];
    private final int id;

    DgEditTupleConfirmation(MainActivity parentActivity, Cursor tuple) {
        mainActivity = parentActivity;

        id = tuple.getInt(tuple.getColumnIndex(_ID));
        for (int i = 0; i < AMOUNT_OF_MAIN_ATTRIBUTES; ++i)
            oldAttributes[i] = tuple.getString(tuple.getColumnIndex(MAIN_ATTRIBUTES[i]));
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        prepareDialog();

        for (int i = 0; i < AMOUNT_OF_MAIN_ATTRIBUTES; ++i)
            etxtAttribute[i].setText(oldAttributes[i]);

        builder.setView(tupleView)
                .setTitle("Edit an existing entry")
                .setNegativeButton("Cancel", (dialog, which) -> {})
                .setPositiveButton("Apply", (dialog, which) -> {
                    if (edit(getCV(getInput()))) {
                        Toast.makeText(getContext(), "Entry updated", Toast.LENGTH_SHORT).show();
                        mainActivity.onDisplayUpdate(getInput());
                    } else
                        Toast.makeText(getContext(), "Entry must have at least a name and a password", Toast.LENGTH_SHORT).show();
                });

        return builder.create();
    }

    private boolean edit(ContentValues cv) {
        boolean successful = isInputValid(cv);
        if (!successful)
            return false;

        editTuple(cv, id);
        return true;
    }
}
