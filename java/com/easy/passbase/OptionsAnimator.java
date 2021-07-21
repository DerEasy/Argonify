package com.easy.passbase;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.view.View;

import androidx.appcompat.content.res.AppCompatResources;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class OptionsAnimator {
    private final MainActivity mainActivity;
    private FloatingActionButton fabOptions;
    private static final int AMOUNT_OF_OPTIONS = 1;
    private static final float ANIM_OFFSET = 150;
    private static final FloatingActionButton[] OPTIONS = new FloatingActionButton[AMOUNT_OF_OPTIONS];
    private final float posY;
    private boolean optionsOpen = false;

    private FloatingActionButton fabOptionAddEntry;



    public OptionsAnimator(MainActivity mainActivity, float posY) {
        this.mainActivity = mainActivity;
        fabOptions = mainActivity.findViewById(R.id.fab_options);

        fabOptionAddEntry = mainActivity.findViewById(R.id.fab_optionAddEntry);
        this.posY = posY;

        OPTIONS[0] = fabOptionAddEntry;
    }



    protected void options() {
        if (!optionsOpen)
            openOptionsPanel();
        else
            closeOptionsPanel();
    }

    private void openOptionsPanel() {
        for (int i = 0; i < AMOUNT_OF_OPTIONS; ++i)
            animateOptionFABOpen(OPTIONS[i], i);
    }

    private void animateOptionFABOpen(FloatingActionButton fab, int optionsIndex) {
        int finalOptionsIndex = ++optionsIndex;

        fab.animate().y(posY - ANIM_OFFSET * optionsIndex).setListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationStart(Animator animation) {
                optionsOpen = true;
                fab.setVisibility(View.VISIBLE);
                fabOptions.setImageDrawable(AppCompatResources.getDrawable(fabOptions.getContext(), R.drawable.ic_round_expand_less_24));
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                if (optionsOpen) {
                    fab.setVisibility(View.VISIBLE);
                    fab.setY(posY - ANIM_OFFSET * finalOptionsIndex);
                }
            }

            @Override
            public void onAnimationCancel(Animator animation) {
                optionsOpen = false;
                fab.setVisibility(View.INVISIBLE);
                fab.setY(posY);
                fabOptions.setImageDrawable(AppCompatResources.getDrawable(fabOptions.getContext(), R.drawable.ic_round_expand_more_24));
            }
        }).start();
    }

    private void closeOptionsPanel() {
        for (int i = 0; i < AMOUNT_OF_OPTIONS; ++i)
            animateOptionFABClose(OPTIONS[i], i);
    }

    private void animateOptionFABClose(FloatingActionButton fab, int optionsIndex) {
        int finalOptionsIndex = ++optionsIndex;

        fab.animate().y(posY).setListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationStart(Animator animation) {
                optionsOpen = false;
                fab.setVisibility(View.VISIBLE);
                fabOptions.setImageDrawable(AppCompatResources.getDrawable(fabOptions.getContext(), R.drawable.ic_round_expand_more_24));
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                if (!optionsOpen) {
                    fab.setVisibility(View.INVISIBLE);
                    fab.setY(posY);
                }
            }

            @Override
            public void onAnimationCancel(Animator animation) {
                optionsOpen = true;
                fab.setVisibility(View.VISIBLE);
                fab.setY(posY - ANIM_OFFSET * finalOptionsIndex);
            }
        }).start();
    }
}
