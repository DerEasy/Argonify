package com.easy.passbase;

import android.view.View;
import android.view.ViewTreeObserver;

public class GlobalLayoutListenerAdapter {
    MainActivity mainActivity;
    ViewTreeObserver.OnGlobalLayoutListener listener;
    View animSetup;

    /**
     * Attaches a GlobalLayoutListener to receive the y-position of the closed-state
     * options menu, then automatically instantiates the MainActivity OptionsAnimator
     * and detaches the listener.
     * @param mainActivity The MainActivity instance
     */
    public GlobalLayoutListenerAdapter(MainActivity mainActivity) {
        this.mainActivity = mainActivity;
        attach();
    }

    private void attach() {
        animSetup = mainActivity.findViewById(R.id.fab_optionAddTuple);
        animSetup.getViewTreeObserver().addOnGlobalLayoutListener(listener = () -> {
            mainActivity.setOptionsAnimator(new OptionsAnimator(mainActivity, animSetup.getY()));
            detach();
        });
    }

    private void detach() {
        animSetup.getViewTreeObserver().removeOnGlobalLayoutListener(listener);
    }
}
