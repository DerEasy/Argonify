package com.easy.argonify.Main;

import android.os.Handler;
import android.os.Looper;
import android.widget.ImageView;
import android.widget.TextView;

import com.easy.argonify.R;

public class AppIconAnimator {
    private final ImageView icon;
    private final TextView txt;
    private final TextDecryptor decryptor;

    AppIconAnimator(MainActivity parentActivity) {
        icon = parentActivity.findViewById(R.id.img_mainAppIcon);
        txt = parentActivity.findViewById(R.id.txt_mainAppIcon);
        decryptor = new TextDecryptor(txt, "ARGONIFY");

        animateIntro();
    }

    private void animateIntro() {
        new Handler(Looper.getMainLooper()).postDelayed((Runnable) this::animateIcon, 200);
        new Handler(Looper.getMainLooper()).postDelayed((Runnable) this::animateText, 600);
    }

    private void animateIcon() {
        icon.animate().alpha(1).setDuration(600);
        icon.animate().y(200);
    }

    private void animateText() {
        decryptor.startAnimation();
        txt.animate().alpha(1).setDuration(400);
        txt.animate().y(80);
    }
}
