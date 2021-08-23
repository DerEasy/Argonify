package com.easy.argonify.Settings.Clear;

import static com.easy.argonify.Settings.Applock.ApplockStrings.CLEAR_DATA;
import static com.easy.argonify.Settings.Applock.ApplockStrings.REASON_CLEAR_DATA;
import static com.easy.argonify.Utility.ArgonifyDialog.argonifyDialog;
import static com.easy.argonify.Utility.RequestApplock.requestApplock;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;

import com.easy.argonify.R;

public class DgClear extends AppCompatDialogFragment {
    public static boolean isOpen = false;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        isOpen = true;

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View confirmView = inflater.inflate(R.layout.dg_clear, null);

        builder.setView(confirmView)
                .setTitle("Irreversibly delete ALL entries?")
                .setNegativeButton("Cancel", ((dialog, which) -> {}))
                .setPositiveButton("Continue", ((dialog, which) -> confirmClear()));

        return argonifyDialog(builder, getContext());
    }

    @Override
    public void onDismiss(@NonNull DialogInterface dialog) {
        super.onDismiss(dialog);
        isOpen = false;
    }

    private void confirmClear() {
        requestApplock(getContext(), REASON_CLEAR_DATA, CLEAR_DATA);
    }
}
