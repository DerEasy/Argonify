package com.easy.passbase;

import android.database.Cursor;

import static com.easy.passbase.PasswordDBHelper._ID;

public class DgSelectTuple extends TupleSelection {
    private final MainActivity mainActivity;

    public DgSelectTuple(MainActivity parentActivity) {
        mainActivity = parentActivity;
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
