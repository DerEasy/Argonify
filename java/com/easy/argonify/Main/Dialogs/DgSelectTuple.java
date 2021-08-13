package com.easy.argonify.Main.Dialogs;

import android.database.Cursor;

import com.easy.argonify.Main.MainActivity;

public class DgSelectTuple extends TupleSelection {
    private final MainActivity mainActivity;

    public DgSelectTuple(MainActivity parentActivity) {
        mainActivity = parentActivity;
    }

    @Override
    public void onTupleSelection(int adapterPosition, Cursor idCursor) {
        super.onTupleSelection(adapterPosition, idCursor);
        mainActivity.onDisplayUpdate(attributes);
        dismiss();
    }
}
