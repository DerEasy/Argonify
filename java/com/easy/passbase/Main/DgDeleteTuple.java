package com.easy.passbase.Main;

import android.database.Cursor;

public class DgDeleteTuple extends TupleSelection {
    @Override
    public void onTupleSelection(int adapterPosition, Cursor idCursor) {
        super.onTupleSelection(adapterPosition, idCursor);
        DgDeleteTupleConfirmation confirmation = new DgDeleteTupleConfirmation(attributes, tuple);
        confirmation.show(getParentFragmentManager(), "Deletion Confirmation Dialog");
        dismiss();
    }
}
