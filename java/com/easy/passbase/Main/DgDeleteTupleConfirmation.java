package com.easy.passbase;

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

import static com.easy.passbase.PasswordDB.deleteTuple;
import static com.easy.passbase.PasswordDBHelper.AMOUNT_OF_MAIN_ATTRIBUTES;
import static com.easy.passbase.PasswordDBHelper.INDEX_EMAIL;
import static com.easy.passbase.PasswordDBHelper.INDEX_NAME;
import static com.easy.passbase.PasswordDBHelper.INDEX_NOTES;
import static com.easy.passbase.PasswordDBHelper.INDEX_PASSWORD;
import static com.easy.passbase.PasswordDBHelper.INDEX_USERNAME;
import static com.easy.passbase.PasswordDBHelper._ID;

public class DgDeleteTupleConfirmation extends AppCompatDialogFragment {
    private final TextView[] txtAttribute = new TextView[AMOUNT_OF_MAIN_ATTRIBUTES];
    private final TextView[] txtTitle = new TextView[AMOUNT_OF_MAIN_ATTRIBUTES];
    private final String[] attributes;
    private final Cursor tuple;
    public static boolean isOpen = false;

    DgDeleteTupleConfirmation(String[] attributes, Cursor tuple) {
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
