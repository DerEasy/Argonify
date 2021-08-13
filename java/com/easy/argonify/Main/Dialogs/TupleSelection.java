package com.easy.argonify.Main.Dialogs;

import static com.easy.argonify.Utility.PasswordDB.getIDsAndNames;
import static com.easy.argonify.Utility.PasswordDB.getTupleByID;
import static com.easy.argonify.Utility.PasswordDBHelper.AMOUNT_OF_MAIN_ATTRIBUTES;
import static com.easy.argonify.Utility.PasswordDBHelper.MAIN_ATTRIBUTES;
import static com.easy.argonify.Utility.PasswordDBHelper._ID;

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

import com.easy.argonify.Main.PasswordAdapter;
import com.easy.argonify.R;

public class TupleSelection extends AppCompatDialogFragment {
    public static boolean isOpen = false;
    public String[] attributes = new String[AMOUNT_OF_MAIN_ATTRIBUTES];
    public Cursor tuple;

    @SuppressWarnings("ConstantConditions")
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        isOpen = true;
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View relationView = inflater.inflate(R.layout.dg_select_tuple, null);

        RecyclerView relationRecycler = relationView.findViewById(R.id.relationRecycler);
        relationRecycler.setLayoutManager(new LinearLayoutManager(getActivity()));
        relationRecycler.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL));

        PasswordAdapter passwordAdapter = new PasswordAdapter(this, getActivity(), getIDsAndNames());
        relationRecycler.setAdapter(passwordAdapter);

        builder.setView(relationView)
                .setTitle("Pick desired entry")
                .setNegativeButton("Cancel", ((dialog, which) -> {}));

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

        tuple = getTupleByID(idTuples[adapterPosition]);
        tuple.moveToFirst();

        for (int i = 0; i < AMOUNT_OF_MAIN_ATTRIBUTES; ++i)
            attributes[i] = getAttribute(tuple, MAIN_ATTRIBUTES[i]);
    }

    /**
     * This method will be invoked when the user has selected a tuple.
     * @param adapterPosition The position of the selected tuple in the PasswordAdapter
     * @param idCursor The cursor that contains the IDs of every password in the database
     */
    public void onTupleSelection(int adapterPosition, Cursor idCursor) {
        setAttributes(adapterPosition, idCursor);
    }
}
