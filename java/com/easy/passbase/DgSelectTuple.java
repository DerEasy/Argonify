package com.easy.passbase;

import android.database.Cursor;

public class DgSelectTuple extends TupleSelection {
    private final MainActivity mainActivity;

    public DgSelectTuple(MainActivity parentActivity) {
        mainActivity = parentActivity;
    }

    @Override
    public void onTupleSelection(int adapterPosition, Cursor idCursor) {
        super.onTupleSelection(adapterPosition, idCursor);
        mainActivity.updateDisplayedTuple(attributes);
        dismiss();
    }
}
