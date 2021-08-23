package com.easy.argonify.Utility;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;


import androidx.core.content.ContextCompat;

import com.easy.argonify.R;

public class ArgonifyDialog {
    public static AlertDialog argonifyDialog(AlertDialog.Builder builder, Context context) {
        AlertDialog dialog = builder.create();
        dialog.show();

        dialog.getButton(DialogInterface.BUTTON_POSITIVE).setTextColor(ContextCompat.getColor(context, R.color.green));
        dialog.getButton(DialogInterface.BUTTON_NEGATIVE).setTextColor(ContextCompat.getColor(context, R.color.green));
        dialog.getWindow().setBackgroundDrawableResource(R.color.darkGrey);

        return dialog;
    }
}
