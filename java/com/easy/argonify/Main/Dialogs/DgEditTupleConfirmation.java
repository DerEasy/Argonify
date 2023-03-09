package com.easy.argonify.Main.Dialogs;

import static com.easy.argonify.Utility.ArgonifyDialog.argonifyDialog;
import static com.easy.argonify.Utility.PasswordDB.editTuple;
import static com.easy.argonify.Utility.PasswordDB.getTupleByID;
import static com.easy.argonify.Utility.PasswordDBHelper.AMOUNT_OF_MAIN_ATTRIBUTES;
import static com.easy.argonify.Utility.PasswordDBHelper.MAIN_ATTRIBUTES;

import android.app.Dialog;
import android.content.ContentValues;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.easy.argonify.Main.SelectionDisplay;

public class DgEditTupleConfirmation extends TupleManipulation {
    private final SelectionDisplay display;
    private final String[] oldAttributes = new String[AMOUNT_OF_MAIN_ATTRIBUTES];
    private final int selectionID;

    public DgEditTupleConfirmation(SelectionDisplay display) {
        this.display = display;
        selectionID = display.getSelectionID();
        Cursor tuple = getTupleByID(selectionID);

        for (int i = 0; i < AMOUNT_OF_MAIN_ATTRIBUTES; ++i) {
            oldAttributes[i] = getAttribute(tuple, MAIN_ATTRIBUTES[i]);
        }
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        prepareDialog();

        for (int i = 0; i < AMOUNT_OF_MAIN_ATTRIBUTES; ++i) {
            etxtAttribute[i].setText(oldAttributes[i]);
        }

        builder.setView(tupleView)
                .setTitle("Edit an existing entry")
                .setNegativeButton("Cancel", (dialog, which) -> {})
                .setPositiveButton("Apply", (dialog, which) -> {
                    if (edit(getCV(getInput()))) {
                        Toast.makeText(getContext(), "Entry updated", Toast.LENGTH_SHORT).show();
                        display.onDisplayUpdate(getInput(), selectionID);
                    } else {
                        Toast.makeText(getContext(), "Entry must have a name and a password", Toast.LENGTH_SHORT).show();
                    }
                });

        return argonifyDialog(builder, getContext());
    }

    private boolean edit(ContentValues cv) {
        boolean successful = isInputValid(cv);
        if (!successful)
            return false;

        editTuple(cv, selectionID);
        return true;
    }

    private String getAttribute(Cursor cursor, String column) {
        return cursor.getString(cursor.getColumnIndex(column));
    }
}
