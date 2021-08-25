package com.easy.argonify.Settings.DBManipulation;

import static com.easy.argonify.Utility.ArgonifyDialog.argonifyDialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;

import com.easy.argonify.R;

public class DgExportLoading extends AppCompatDialogFragment {
    private UnencryptedDB caller;
    private ProgressBar progressBar;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        setCancelable(false);

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View loadView = inflater.inflate(R.layout.dg_loading_im_export, null);
        progressBar = loadView.findViewById(R.id.load_im_export);

        new GlobalLayoutListenerAdapter(loadView);

        builder.setView(loadView)
                .setTitle("Loading…");

        return argonifyDialog(builder, getContext());
    }

    private class GlobalLayoutListenerAdapter {
        private ViewTreeObserver.OnGlobalLayoutListener listener;

        private GlobalLayoutListenerAdapter(View loadView) {
            View view = loadView.findViewById(R.id.load_im_export);

            view.getViewTreeObserver().addOnGlobalLayoutListener(listener = () -> {
                caller.exportDB(DgExportLoading.this);
                view.getViewTreeObserver().removeOnGlobalLayoutListener(listener);
            });
        }
    }

    void initialise(UnencryptedDB parent) {
        caller = parent;
    }

    void setMaxProgress(int max) {
        progressBar.setMax(max);
    }

    void incrementProgress() {
        progressBar.incrementProgressBy(1);
    }
}
