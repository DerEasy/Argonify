package com.easy.passbase.Main;

import android.app.Dialog;
import android.content.ContentValues;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.easy.passbase.PassGen.PassGenActivity;

import static com.easy.passbase.Main.PasswordDB.insertTuple;

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

        return builder.create();
    }

    private boolean add(ContentValues cv) {
        boolean successful = isInputValid(cv);
        if (!successful)
            return false;

        insertTuple(cv);
        return true;
    }
}
