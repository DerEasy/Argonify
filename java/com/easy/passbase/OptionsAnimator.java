package com.easy.passbase;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class OptionsAnimator {
    private final int AMOUNT_OF_OPTIONS = 4;
    private final float POS_Y;
    private final Animation ANIM_ROTATE;
    private final Animation ANIM_ROTATE_REVERSE;
    private final FloatingActionButton FAB_OPTIONS;
    private final FloatingActionButton[] OPTION = new FloatingActionButton[AMOUNT_OF_OPTIONS];
    private boolean optionsOpen;

    public OptionsAnimator(MainActivity mainActivity, float posY) {
        FAB_OPTIONS = mainActivity.findViewById(R.id.fab_options);
        FAB_OPTIONS.bringToFront();
        POS_Y = posY;

        ANIM_ROTATE = AnimationUtils.loadAnimation(FAB_OPTIONS.getContext(), R.anim.fab_rotate_icon);
        ANIM_ROTATE_REVERSE = AnimationUtils.loadAnimation(FAB_OPTIONS.getContext(), R.anim.fab_rotate_icon_reverse);
        ANIM_ROTATE.setFillAfter(true);
        ANIM_ROTATE_REVERSE.setFillAfter(true);

        OPTION[0] = mainActivity.findViewById(R.id.fab_optionAddTuple);
        OPTION[1] = mainActivity.findViewById(R.id.fab_optionRemoveTuple);
        OPTION[2] = mainActivity.findViewById(R.id.fab_optionEditTuple);
        OPTION[3] = mainActivity.findViewById(R.id.fab_optionPasswordGenerator);
    }

    /**
     * Switches the state of the options panel to open or closed
     */
    public void switchState() {
        if (!optionsOpen)
            openOptionsPanel();
        else
            closeOptionsPanel();
    }

    /**
     * Sets the state of the options panel to open
     */
    public void open() {
        if (!optionsOpen)
            openOptionsPanel();
    }

    /**
     * Sets the state of the options panel to closed
     */
    public void close() {
        if (optionsOpen)
            closeOptionsPanel();
    }

    private void openOptionsPanel() {
        for (int i = 0; i < AMOUNT_OF_OPTIONS; ++i)
            animateOptionFABOpen(OPTION[i], i);
    }

    private void animateOptionFABOpen(FloatingActionButton fab, int optionIndex) {
        float ANIM_OFFSET = 140;

        fab.animate().y(POS_Y - ANIM_OFFSET * ++optionIndex).setListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationStart(Animator animation) {
                optionsOpen = true;
                fab.setVisibility(View.VISIBLE);
                FAB_OPTIONS.startAnimation(ANIM_ROTATE);
            }
        }).start();
    }

    private void closeOptionsPanel() {
        for (int i = 0; i < AMOUNT_OF_OPTIONS; ++i)
            animateOptionFABClose(OPTION[i]);
    }

    private void animateOptionFABClose(FloatingActionButton fab) {
        fab.animate().y(POS_Y).setListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationStart(Animator animation) {
                optionsOpen = false;
                fab.setVisibility(View.VISIBLE);
                FAB_OPTIONS.startAnimation(ANIM_ROTATE_REVERSE);
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                if (!optionsOpen) {
                    fab.setVisibility(View.INVISIBLE);
                    fab.setY(POS_Y);
                }
            }
        }).start();
    }
}
