package com.easy.passbase.Main;

import android.database.Cursor;

public class DgEditTuple extends TupleSelection {
    private final MainActivity mainActivity;

    DgEditTuple(MainActivity parentActivity) {
        mainActivity = parentActivity;
    }

    @Override
    public void onTupleSelection(int adapterPosition, Cursor idCursor) {
        super.onTupleSelection(adapterPosition, idCursor);
        DgEditTupleConfirmation confirmation = new DgEditTupleConfirmation(mainActivity, tuple);
        confirmation.show(getParentFragmentManager(), "Edit Tuple Dialog");
        dismiss();
    }
}
