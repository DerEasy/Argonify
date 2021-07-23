package com.easy.passbase;

import android.view.View;
import android.view.ViewTreeObserver;

public class GlobalLayoutListenerAdapter {
    private final MainActivity mainActivity;
    private final View animSetup;
    private ViewTreeObserver.OnGlobalLayoutListener listener;

    /**
     * Attaches a GlobalLayoutListener to receive the y-position of the closed-state
     * options menu, then automatically instantiates the MainActivity OptionsAnimator
     * and detaches the listener.
     * @param mainActivity The MainActivity instance
     */
    public GlobalLayoutListenerAdapter(MainActivity mainActivity) {
        this.mainActivity = mainActivity;
        animSetup = mainActivity.findViewById(R.id.fab_optionAddTuple);
        attach();
    }

    private void attach() {

        animSetup.getViewTreeObserver().addOnGlobalLayoutListener(listener = () -> {
            mainActivity.setOptionsAnimator(new OptionsAnimator(mainActivity, animSetup.getY()));
            detach();
        });
    }

    private void detach() {
        animSetup.getViewTreeObserver().removeOnGlobalLayoutListener(listener);
    }
}
