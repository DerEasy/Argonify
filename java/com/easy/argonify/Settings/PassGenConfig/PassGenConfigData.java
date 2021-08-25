package com.easy.argonify.Settings.PassGenConfig;

public interface PassGenConfigData {
    //General
    String GEN_CONFIG = "GENERATOR_CONFIG";
    int AMOUNT_OF_CHAR_SWITCHES = 4;

    //Indices
    int INDEX_LOWERCASE = 0;
    int INDEX_UPPERCASE = 1;
    int INDEX_NUMBERS = 2;
    int INDEX_SYMBOLS = 3;

    //Options
    String CONFIG_LENGTH = "CONFIG_LENGTH";
    String CONFIG_LOWERCASE = "CONFIG_LOWERCASE";
    String CONFIG_UPPERCASE = "CONFIG_UPPERCASE";
    String CONFIG_NUMBERS = "CONFIG_NUMBERS";
    String CONFIG_SYMBOLS = "CONFIG_SYMBOLS";
    String CONFIG_EXCLUSIONS = "CONFIG_EXCLUSIONS";
    //Categories in an array
    String[] CONFIG_CHAR_CATS = {
            CONFIG_LOWERCASE,
            CONFIG_UPPERCASE,
            CONFIG_NUMBERS,
            CONFIG_SYMBOLS
    };
}
