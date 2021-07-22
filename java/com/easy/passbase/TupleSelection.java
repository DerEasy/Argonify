package com.easy.passbase;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import static com.easy.passbase.PasswordDB.getAllPasswordEntries;
import static com.easy.passbase.PasswordDB.getPassword;
import static com.easy.passbase.PasswordDBHelper.AMOUNT_OF_MAIN_ATTRIBUTES;
import static com.easy.passbase.PasswordDBHelper.MAIN_ATTRIBUTES;
import static com.easy.passbase.PasswordDBHelper._ID;

public class TupleSelection extends AppCompatDialogFragment {
    public static boolean isOpen = false;
    public String[] attributes = new String[AMOUNT_OF_MAIN_ATTRIBUTES];

    @SuppressWarnings("ConstantConditions")
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View relationView = inflater.inflate(R.layout.dg_select_tuple, null);

        RecyclerView relationRecycler = relationView.findViewById(R.id.relationRecycler);
        relationRecycler.setLayoutManager(new LinearLayoutManager(getActivity()));
        relationRecycler.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL));

        PasswordAdapter passwordAdapter = new PasswordAdapter(this, getActivity(), getAllPasswordEntries());
        relationRecycler.setAdapter(passwordAdapter);

        builder.setView(relationView)
                .setTitle("Pick desired entry")
                .setNegativeButton("Cancel", ((dialog, which) -> {})
                );

        return builder.create();
    }

    @Override
    public void onDismiss(@NonNull DialogInterface dialog) {
        super.onDismiss(dialog);
        isOpen = false;
    }

    private String getAttribute(Cursor cursor, String column) {
        return cursor.getString(cursor.getColumnIndex(column));
    }

    private void setAttributes(int adapterPosition, Cursor idCursor) {
        int[] idTuples = new int[idCursor.getCount()];
        for (int i = 0; i < idCursor.getCount(); ++i) {
            idTuples[i] = idCursor.getInt(idCursor.getColumnIndex(_ID));
            idCursor.moveToNext();
        }

        Cursor tuple = getPassword(idTuples[adapterPosition]);
        tuple.moveToFirst();

        for (int i = 0; i < AMOUNT_OF_MAIN_ATTRIBUTES; ++i)
            attributes[i] = getAttribute(tuple, MAIN_ATTRIBUTES[i]);
    }

    /**
     * Virtual method; does nothing, must be overwritten with custom behaviour.
     * @param adapterPosition The position of the selected tuple in the PasswordAdapter
     * @param idCursor The idCursor with the selected tuple
     */
    public void onTupleSelection(int adapterPosition, Cursor idCursor) {
        setAttributes(adapterPosition, idCursor);

    }
}
