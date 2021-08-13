package com.easy.argonify.Main.Dialogs;

import static com.easy.argonify.Utility.ApplockStrings.EMPTY;
import static com.easy.argonify.Utility.PasswordDB.deleteTuple;
import static com.easy.argonify.Utility.PasswordDBHelper.AMOUNT_OF_MAIN_ATTRIBUTES;
import static com.easy.argonify.Utility.PasswordDBHelper.INDEX_EMAIL;
import static com.easy.argonify.Utility.PasswordDBHelper.INDEX_NAME;
import static com.easy.argonify.Utility.PasswordDBHelper.INDEX_NOTES;
import static com.easy.argonify.Utility.PasswordDBHelper.INDEX_PASSWORD;
import static com.easy.argonify.Utility.PasswordDBHelper.INDEX_USERNAME;
import static com.easy.argonify.Utility.PasswordDBHelper.MAIN_ATTRIBUTES;
import static com.easy.argonify.Utility.PasswordDBHelper._ID;
import static com.easy.argonify.Main.SelectionDisplay.ACTUAL_PASSWORD;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;

import com.easy.argonify.Main.SelectionDisplay;
import com.easy.argonify.R;

class DgDeleteTupleConfirmation extends AppCompatDialogFragment {
    private final TextView[] txtAttribute = new TextView[AMOUNT_OF_MAIN_ATTRIBUTES];
    private final TextView[] txtTitle = new TextView[AMOUNT_OF_MAIN_ATTRIBUTES];
    private final String[] attributes;
    private final Cursor tuple;
    private final SelectionDisplay selectionDisplay;
    public static boolean isOpen = false;

    DgDeleteTupleConfirmation(SelectionDisplay display, String[] attributes, Cursor tuple) {
        this.selectionDisplay = display;
        this.attributes = attributes;
        this.tuple = tuple;
    }

    @SuppressWarnings("ConstantConditions")
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        isOpen = true;
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View confirmationView = inflater.inflate(R.layout.dg_delete_tuple, null);
        initialiseTextViews(confirmationView);

        for (int i = 0; i < AMOUNT_OF_MAIN_ATTRIBUTES; ++i)
            setTextView(txtAttribute[i], txtTitle[i],attributes[i]);

        builder.setView(confirmationView)
                .setTitle("Are you sure to delete this entry?")
                .setNegativeButton("Cancel", (dialog, which) -> {})
                .setPositiveButton("Delete", (dialog, which) -> {
                    if (isDisplayed())
                        selectionDisplay.onDisplayUpdate(new String[] {EMPTY, EMPTY, EMPTY, EMPTY, EMPTY});
                    deleteTuple(tuple.getInt(tuple.getColumnIndex(_ID)));
                    Toast.makeText(getContext(), String.format("%s deleted", attributes[INDEX_NAME]), Toast.LENGTH_SHORT).show();
                });

        return builder.create();
    }

    @Override
    public void onDismiss(@NonNull DialogInterface dialog) {
        super.onDismiss(dialog);
        isOpen = false;
    }

    private boolean isDisplayed() {
        int displayCount = 0;
        for (int i = 0; i < AMOUNT_OF_MAIN_ATTRIBUTES; ++i) {
            if (i != INDEX_PASSWORD) {
                if (selectionDisplay.txtAttribute[i].getText().equals(tuple.getString(tuple.getColumnIndex(MAIN_ATTRIBUTES[i]))))
                    ++displayCount;
            } else
                if (selectionDisplay.currentPassword[ACTUAL_PASSWORD].equals(tuple.getString(tuple.getColumnIndex(MAIN_ATTRIBUTES[i]))))
                    ++displayCount;
        }
        return displayCount == AMOUNT_OF_MAIN_ATTRIBUTES;
    }

    private void setTextView(TextView attribute, TextView title, String text) {
        if (attribute == null || title == null)
            return;

        if (text == null || text.isEmpty()) {
            title.setVisibility(View.GONE);
            attribute.setVisibility(View.GONE);
        } else
            attribute.setText(text);
    }

    private void initialiseTextViews(View confirmationView) {
        initTxtAttribute(confirmationView);
        initTxtTitle(confirmationView);
    }

    private void initTxtTitle(View confirmationView) {
        txtTitle[INDEX_NAME]     = confirmationView.findViewById(R.id.txt_deleteTitleName);
        txtTitle[INDEX_PASSWORD] = confirmationView.findViewById(R.id.txt_deleteTitlePassword);
        txtTitle[INDEX_EMAIL]    = confirmationView.findViewById(R.id.txt_deleteTitleEmail);
        txtTitle[INDEX_USERNAME] = confirmationView.findViewById(R.id.txt_deleteTitleUsername);
        txtTitle[INDEX_NOTES]    = confirmationView.findViewById(R.id.txt_deleteTitleNotes);
    }

    private void initTxtAttribute(View confirmationView) {
        txtAttribute[INDEX_NAME]     = confirmationView.findViewById(R.id.txt_deleteName);
        txtAttribute[INDEX_PASSWORD] = confirmationView.findViewById(R.id.txt_deletePassword);
        txtAttribute[INDEX_EMAIL]    = confirmationView.findViewById(R.id.txt_deleteEmail);
        txtAttribute[INDEX_USERNAME] = confirmationView.findViewById(R.id.txt_deleteUsername);
        txtAttribute[INDEX_NOTES]    = confirmationView.findViewById(R.id.txt_deleteNotes);
    }
}
