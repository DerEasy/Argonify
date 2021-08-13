package com.easy.argonify.Main.Dialogs;

import android.database.Cursor;

import com.easy.argonify.Main.MainActivity;

public class DgEditTuple extends TupleSelection {
    private final MainActivity mainActivity;

    public DgEditTuple(MainActivity parentActivity) {
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
