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
import static com.easy.passbase.PasswordDB.getAllPasswordID;
import static com.easy.passbase.PasswordDBHelper._ID;

public class DgSelectEntry extends AppCompatDialogFragment {
    private final MainActivity mainActivity;

    public DgSelectEntry(MainActivity parentActivity) {
        mainActivity = parentActivity;
    }

    @SuppressWarnings("ConstantConditions")
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View entryView = inflater.inflate(R.layout.dg_select_entry, null);

        RecyclerView entryList = entryView.findViewById(R.id.entryRecycler);
        entryList.setLayoutManager(new LinearLayoutManager(getActivity()));
        entryList.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL));

        PasswordAdapter passwordAdapter = new PasswordAdapter(this, getActivity(), getAllPasswordEntries());
        entryList.setAdapter(passwordAdapter);

        builder.setView(entryView)
                .setTitle("Pick desired entry")
                .setNegativeButton("Cancel", ((dialog, which) -> {})
        );

        return builder.create();
    }

    public void setSelectedEntry(int adapterPosition, Cursor cursor) {
        cursor.moveToFirst();

        int[] idOfEntries = new int[cursor.getCount()];
        for (int i = 0; i < cursor.getCount(); i++) {
            idOfEntries[i] = cursor.getInt(cursor.getColumnIndex(_ID));
            cursor.moveToNext();
        }

        mainActivity.updateSelectedEntry(idOfEntries[adapterPosition]);
        dismiss();
    }
}
