package com.android.structure.libraries.maskformatter;

import android.text.InputType;

public class CharInputType {

    public static int getKeyboardTypeForNextChar(char nextChar) {
        if (Character.isDigit(nextChar)) {
            return InputType.TYPE_CLASS_NUMBER;
        }

        switch (nextChar) {
            case 'd':
                return InputType.TYPE_CLASS_NUMBER;
            case 'A':
            case 'Z':
            case '%':
                return InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_FLAG_CAP_CHARACTERS;
        }

        return InputType.TYPE_CLASS_TEXT;
    }

}