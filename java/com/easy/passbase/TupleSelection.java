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

public class TupleSelection extends AppCompatDialogFragment {
    public static boolean isOpen = false;

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

    public void setSelectedTuple(int adapterPosition, Cursor cursor) {}
}
