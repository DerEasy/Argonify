package com.easy.passbase.Settings;

import android.view.ViewTreeObserver;
import android.widget.TableLayout;

import androidx.appcompat.app.AppCompatActivity;

import com.easy.passbase.R;

class GlobalLayoutListenerAdapter {
    private SetOLPatternActivity setPatternActivity;
    private RequestOLPatternActivity requestPatternActivity;
    private final TableLayout table;
    private ViewTreeObserver.OnGlobalLayoutListener listener;

    public GlobalLayoutListenerAdapter(AppCompatActivity patternActivity) {
        if (patternActivity instanceof SetOLPatternActivity) {
            setPatternActivity = (SetOLPatternActivity) patternActivity;
            table = setPatternActivity.findViewById(R.id.table_setOLPattern);
        } else {
            requestPatternActivity = (RequestOLPatternActivity) patternActivity;
            table = requestPatternActivity.findViewById(R.id.table_requestOLPattern);
        }

        attach();
    }

    private void attach() {
        table.getViewTreeObserver().addOnGlobalLayoutListener(listener = () -> {
            if (setPatternActivity != null)
                setPatternActivity.onTableInitialisation(table.getWidth());
            else
                requestPatternActivity.onTableInitialisation(table.getWidth());

            detach();
        });
    }

    private void detach() {
        table.getViewTreeObserver().removeOnGlobalLayoutListener(listener);
    }
}
