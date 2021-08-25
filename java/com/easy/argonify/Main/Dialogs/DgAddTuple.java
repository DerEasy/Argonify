package com.easy.argonify.Main.Dialogs;

import static com.easy.argonify.Utility.ArgonifyDialog.argonifyDialog;
import static com.easy.argonify.Utility.PasswordDB.insertTuple;

import android.app.Dialog;
import android.content.ContentValues;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class DgAddTuple extends TupleManipulation {
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        prepareDialog();

        builder.setView(tupleView)
                .setTitle("Add a new entry")
                .setNegativeButton("Cancel", (dialog, which) -> {})
                .setPositiveButton("Add", (dialog, which) -> {
                    if (add(getCV(getInput())))
                        Toast.makeText(getContext(), "Entry added", Toast.LENGTH_SHORT).show();
                    else
                        Toast.makeText(getContext(), "Entry must have at least a name and a password", Toast.LENGTH_SHORT).show();
                });

        return argonifyDialog(builder, getContext());
    }

    private boolean add(ContentValues cv) {
        boolean successful = isInputValid(cv);
        if (!successful)
            return false;

        insertTuple(cv);
        return true;
    }
}
