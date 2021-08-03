package com.easy.passbase.Settings;

import android.view.ViewTreeObserver;
import android.widget.TableLayout;

import com.easy.passbase.R;

class GlobalLayoutListenerAdapter {
    private final SetOLPatternActivity patternActivity;
    private final TableLayout table;
    private ViewTreeObserver.OnGlobalLayoutListener listener;

    public GlobalLayoutListenerAdapter(SetOLPatternActivity patternActivity) {
        this.patternActivity = patternActivity;
        table = patternActivity.findViewById(R.id.table_setOLPattern);
        attach();
    }

    private void attach() {
        table.getViewTreeObserver().addOnGlobalLayoutListener(listener = () -> {
            patternActivity.onTableInitialisation(table.getWidth());
            detach();
        });
    }

    private void detach() {
        table.getViewTreeObserver().removeOnGlobalLayoutListener(listener);
    }
}
