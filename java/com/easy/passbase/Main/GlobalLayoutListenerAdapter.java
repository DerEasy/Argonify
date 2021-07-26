package com.easy.passbase.Main;

import android.view.View;
import android.view.ViewTreeObserver;

import com.easy.passbase.R;

public class GlobalLayoutListenerAdapter {
    private final MainActivity mainActivity;
    private final View setupY;
    private ViewTreeObserver.OnGlobalLayoutListener listener;

    /**
     * Attaches a GlobalLayoutListener to receive the y-position of the closed-state
     * options menu, then automatically instantiates the MainActivity OptionsAnimator
     * and detaches the listener.
     * @param mainActivity The MainActivity instance
     */
    public GlobalLayoutListenerAdapter(MainActivity mainActivity) {
        this.mainActivity = mainActivity;
        setupY = mainActivity.findViewById(R.id.fab_optionAddTuple);
        attach();
    }

    private void attach() {
        setupY.getViewTreeObserver().addOnGlobalLayoutListener(listener = () -> {
            mainActivity.setOptionsAnimator(new OptionsAnimator(mainActivity, setupY.getY()));
            detach();
        });
    }

    private void detach() {
        setupY.getViewTreeObserver().removeOnGlobalLayoutListener(listener);
    }
}
