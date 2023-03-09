package com.easy.argonify.Main.Dialogs;

import android.database.Cursor;

import com.easy.argonify.Main.MainActivity;
import com.easy.argonify.Main.SelectionDisplay;

public class DgSelectTuple extends TupleSelection {
    private final SelectionDisplay display;

    public DgSelectTuple(SelectionDisplay display) {
        this.display = display;
    }

    @Override
    public void onTupleSelection(int adapterPosition, Cursor idCursor) {
        super.onTupleSelection(adapterPosition, idCursor);
        display.onDisplayUpdate(attributes, selectionID);
        dismiss();
    }
}
