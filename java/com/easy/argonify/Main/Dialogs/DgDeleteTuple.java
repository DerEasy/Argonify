package com.easy.argonify.Main.Dialogs;

import android.database.Cursor;

import com.easy.argonify.Main.SelectionDisplay;

public class DgDeleteTuple extends TupleSelection {
    private final SelectionDisplay selectionDisplay;

    public DgDeleteTuple(SelectionDisplay display) {
        selectionDisplay = display;
    }

    @Override
    public void onTupleSelection(int adapterPosition, Cursor idCursor) {
        super.onTupleSelection(adapterPosition, idCursor);
        DgDeleteTupleConfirmation confirmation = new DgDeleteTupleConfirmation(selectionDisplay, attributes, tuple);
        confirmation.show(getParentFragmentManager(), "Deletion Confirmation Dialog");
        dismiss();
    }
}
