package com.easy.argonify.Settings.DBManipulation;

import static com.easy.argonify.Settings.DBManipulation.DBTransfer.exportDatabase;
import static com.easy.argonify.Utility.ArgonifyDialog.argonifyDialog;

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
import androidx.fragment.app.FragmentActivity;

import com.easy.argonify.R;

import java.io.IOException;

public class DgExport extends AppCompatDialogFragment {
    public static boolean isOpen = false;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        isOpen = true;

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View confirmView = inflater.inflate(R.layout.dg_export, null);

        builder.setView(confirmView)
                .setTitle("Export a decrypted copy?")
                .setNegativeButton("Cancel", (dialog, which) -> {})
                .setPositiveButton("Export", (dialog, which) -> tryExport(getActivity()));

        return argonifyDialog(builder, getContext());
    }

    @Override
    public void onDismiss(@NonNull DialogInterface dialog) {
        super.onDismiss(dialog);
        isOpen = false;
    }

    private void tryExport(FragmentActivity activity) {
        exportDatabase(activity);
    }
}
