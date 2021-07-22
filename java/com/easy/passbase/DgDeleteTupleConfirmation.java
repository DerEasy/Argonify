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
import static com.easy.passbase.PasswordDBHelper.COLUMN_EMAIL;
import static com.easy.passbase.PasswordDBHelper.COLUMN_NAME;
import static com.easy.passbase.PasswordDBHelper.COLUMN_NOTES;
import static com.easy.passbase.PasswordDBHelper.COLUMN_PASSWORD;
import static com.easy.passbase.PasswordDBHelper.COLUMN_USERNAME;
import static com.easy.passbase.PasswordDBHelper.MAIN_ATTRIBUTES;
import static com.easy.passbase.PasswordDBHelper._ID;

public class DgDeleteTupleConfirmation extends AppCompatDialogFragment {
    private final TextView[] attributeTextView = new TextView[AMOUNT_OF_MAIN_ATTRIBUTES];
    private final TextView[] titleTextView = new TextView[AMOUNT_OF_MAIN_ATTRIBUTES];
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
            setTextView(MAIN_ATTRIBUTES[i], attributes[i]);

        builder.setView(confirmationView)
                .setTitle("Are you sure to delete this entry?")
                .setNegativeButton("Cancel", (dialog, which) -> {})
                .setPositiveButton("Delete", (dialog, which) -> {
                    deleteTuple(tuple.getInt(tuple.getColumnIndex(_ID)));
                    Toast.makeText(getContext(), String.format("%s deleted", attributes[0]), Toast.LENGTH_SHORT).show();
                });

        return builder.create();
    }

    @Override
    public void onDismiss(@NonNull DialogInterface dialog) {
        super.onDismiss(dialog);
        isOpen = false;
    }

    private void setTextView(String column, String text) {
        TextView title = getTitleTextViewByColumn(column);
        TextView attribute = getTextViewByColumn(column);
        if (attribute == null || title == null)
            return;

        if (text == null || text.isEmpty()) {
            title.setVisibility(View.GONE);
            attribute.setVisibility(View.GONE);
        } else
            attribute.setText(text);
    }

    private TextView getTextViewByColumn(String column) {
        switch (column) {
            case COLUMN_NAME:
                return attributeTextView[0];
            case COLUMN_PASSWORD:
                return attributeTextView[1];
            case COLUMN_EMAIL:
                return attributeTextView[2];
            case COLUMN_USERNAME:
                return attributeTextView[3];
            case COLUMN_NOTES:
                return attributeTextView[4];
            default:
                return null;
        }
    }

    private TextView getTitleTextViewByColumn(String column) {
        switch (column) {
            case COLUMN_NAME:
                return titleTextView[0];
            case COLUMN_PASSWORD:
                return titleTextView[1];
            case COLUMN_EMAIL:
                return titleTextView[2];
            case COLUMN_USERNAME:
                return titleTextView[3];
            case COLUMN_NOTES:
                return titleTextView[4];
            default:
                return null;
        }
    }

    private void initialiseTextViews(View confirmationView) {
        setAttributeTextView(confirmationView);
        setTitleTextView(confirmationView);
    }

    private void setTitleTextView(View confirmationView) {
        titleTextView[0] = confirmationView.findViewById(R.id.txt_deleteTitleName);
        titleTextView[1] = confirmationView.findViewById(R.id.txt_deleteTitlePassword);
        titleTextView[2] = confirmationView.findViewById(R.id.txt_deleteTitleEmail);
        titleTextView[3] = confirmationView.findViewById(R.id.txt_deleteTitleUsername);
        titleTextView[4] = confirmationView.findViewById(R.id.txt_deleteTitleNotes);
    }

    private void setAttributeTextView(View confirmationView) {
        attributeTextView[0] = confirmationView.findViewById(R.id.txt_deleteName);
        attributeTextView[1] = confirmationView.findViewById(R.id.txt_deletePassword);
        attributeTextView[2] = confirmationView.findViewById(R.id.txt_deleteEmail);
        attributeTextView[3] = confirmationView.findViewById(R.id.txt_deleteUsername);
        attributeTextView[4] = confirmationView.findViewById(R.id.txt_deleteNotes);
    }
}
