package com.easy.passbase;

import android.database.Cursor;

public class DgDeleteTuple extends TupleSelection {
    @Override
    public void onTupleSelection(int adapterPosition, Cursor idCursor) {
        DgDeleteTupleConfirmation confirmation = new DgDeleteTupleConfirmation();

    }
}
