package com.easy.passbase;

import android.database.Cursor;

import static com.easy.passbase.PasswordDBHelper._ID;

public class DgSelectTuple extends TupleSelection {
    private final MainActivity mainActivity;

    public DgSelectTuple(MainActivity parentActivity) {
        mainActivity = parentActivity;
    }

    @Override
    public void onTupleSelection(int adapterPosition, Cursor idCursor) {
        idCursor.moveToFirst();

        int[] idTuples = new int[idCursor.getCount()];
        for (int i = 0; i < idCursor.getCount(); ++i) {
            idTuples[i] = idCursor.getInt(idCursor.getColumnIndex(_ID));
            idCursor.moveToNext();
        }

        mainActivity.updateDisplayedTuple(idTuples[adapterPosition]);
        dismiss();
    }
}
