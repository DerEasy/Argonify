package com.easy.argonify.Main;

import android.os.Handler;
import android.os.Looper;
import android.widget.TextView;

import java.util.Random;

public class TextDecryptor {
    private static final String allChars =
                    "abcdefghijklmnopqrstuvwxyz" +
                    "ABCDEFGHIJKLMNOPQRSTUVWXYZ" +
                    "789" +
                    "$*.[]{}()?~-\"!@#%&/\\,><':;|_";
    private static final char[] chars = allChars.toCharArray();
    private static final Random rng = new Random();

    private final TextView txt;
    private StringBuilder currentText;
    private String decryptionText;
    private int currentIndex;
    private int shufflingAmount = 6;
    private long shufflingTime = 30;

    public TextDecryptor(TextView textView) {
        txt = textView;
    }

    public TextDecryptor(TextView textView, String text) {
        txt = textView;
        setDecryptionText(text);
    }

    public void setDecryptionText(String text) {
        decryptionText = text;
        currentText = new StringBuilder(decryptionText.length());
    }

    public void setShufflingSettings(int amount, int msTime) {
        shufflingAmount = amount;
        shufflingTime = msTime;
    }

    public void startAnimation() {
        currentIndex = 0;
        currentText = new StringBuilder(decryptionText.length());
        shuffleChar();
    }

    private void shuffleChar() {
        for (int x = 0; x < shufflingAmount; ++x) {
            int i = x;

            new Handler(Looper.getMainLooper()).postDelayed((Runnable) () -> {
                deleteCharIfViable(i);
                randomCharAtCurrentIndex();
                tryUpdateText(i);
            }, shufflingTime * i);
        }
    }

    private void deleteCharIfViable(int index) {
        if (index > 0)
            currentText.deleteCharAt(currentIndex);
    }

    private void randomCharAtCurrentIndex() {
        currentText.insert(currentIndex, chars[rng.nextInt(chars.length)]);
        txt.setText(currentText.toString());
    }

    private void tryUpdateText(int index) {
        if (index == shufflingAmount - 1)
            updateText();
    }

    private void updateText() {
        currentText.deleteCharAt(currentIndex);
        new Handler(Looper.getMainLooper()).postDelayed((Runnable) this::insertActualChar, shufflingTime);
    }

    private void insertActualChar() {
        currentText.insert(currentIndex, decryptionText.charAt(currentIndex));
        txt.setText(currentText.toString());
        onCharPlaced();
    }

    private void onCharPlaced() {
        ++currentIndex;
        if (currentIndex < decryptionText.length())
            shuffleChar();
    }
}
