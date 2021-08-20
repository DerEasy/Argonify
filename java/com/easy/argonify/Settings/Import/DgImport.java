package com.easy.argonify.Settings.Import;

import static com.easy.argonify.Utility.PasswordDBHelper.importDatabase;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;
import androidx.core.content.ContextCompat;

import com.easy.argonify.R;

import java.io.IOException;

public class DgImport extends AppCompatDialogFragment {
    public static boolean isOpen = false;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        isOpen = true;

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View confirmView = inflater.inflate(R.layout.dg_import, null);

        builder.setView(confirmView)
                .setTitle("Import entries from a database?")
                .setNegativeButton("Cancel", (dialog, which) -> {})
                .setPositiveButton("Import", (dialog, which) -> {
                    try {
                        importDatabase(getActivity());
                        Toast toast = Toast.makeText(getContext(), "Task successful.", Toast.LENGTH_SHORT);
                        TextView v = toast.getView().findViewById(R.id.message);
                        if (v != null) v.setGravity(Gravity.CENTER); toast.show();
                    } catch (IOException e) {
                        e.printStackTrace();
                        Toast toast = Toast.makeText(getContext(), "Task failed. Did you grant read permissions?", Toast.LENGTH_SHORT);
                        TextView v = toast.getView().findViewById(R.id.message);
                        if (v != null) v.setGravity(Gravity.CENTER); toast.show();
                    }
                });

        AlertDialog dialog = builder.create();
        dialog.show();

        dialog.getButton(DialogInterface.BUTTON_POSITIVE).setTextColor(ContextCompat.getColor(getContext(), R.color.green));
        dialog.getButton(DialogInterface.BUTTON_NEGATIVE).setTextColor(ContextCompat.getColor(getContext(), R.color.green));
        dialog.getWindow().setBackgroundDrawableResource(R.color.darkGrey);

        return dialog;
    }

    @Override
    public void onDismiss(@NonNull DialogInterface dialog) {
        super.onDismiss(dialog);
        isOpen = false;
    }
}
