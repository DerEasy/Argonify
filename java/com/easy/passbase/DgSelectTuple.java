package com.easy.passbase;

import android.app.AlertDialog;
import android.app.Dialog;
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
import static com.easy.passbase.PasswordDBHelper._ID;

public class DgSelectTuple extends AppCompatDialogFragment {
    private final MainActivity mainActivity;

    public DgSelectTuple(MainActivity parentActivity) {
        mainActivity = parentActivity;
    }

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

    public void setSelectedTuple(int adapterPosition, Cursor cursor) {
        cursor.moveToFirst();

        int[] idTuples = new int[cursor.getCount()];
        for (int i = 0; i < cursor.getCount(); ++i) {
            idTuples[i] = cursor.getInt(cursor.getColumnIndex(_ID));
            cursor.moveToNext();
        }

        mainActivity.updateDisplayedTuple(idTuples[adapterPosition]);
        dismiss();
    }
}
