package com.easy.argonify.Main.Dialogs;

import static com.easy.argonify.Utility.PasswordDB.insertTuple;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import com.easy.argonify.R;

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

        AlertDialog dialog = builder.create();
        dialog.show();

        dialog.getButton(DialogInterface.BUTTON_POSITIVE).setTextColor(ContextCompat.getColor(getContext(), R.color.green));
        dialog.getButton(DialogInterface.BUTTON_NEGATIVE).setTextColor(ContextCompat.getColor(getContext(), R.color.green));
        dialog.getWindow().setBackgroundDrawableResource(R.color.darkGrey);

        return dialog;
    }

    private boolean add(ContentValues cv) {
        boolean successful = isInputValid(cv);
        if (!successful)
            return false;

        insertTuple(cv);
        return true;
    }
}
