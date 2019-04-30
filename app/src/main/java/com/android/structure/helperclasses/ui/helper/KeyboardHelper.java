package com.android.structure.helperclasses.ui.helper;

import android.content.Context;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

/**
 * Created by khanhamza on 06-Mar-17.
 */

public class KeyboardHelper {

    public static void hideSoftKeyboard(Context context, View view) {
        if (context == null || view == null) {
            return;
        }

        InputMethodManager imm = (InputMethodManager) context
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);

    }


    public static void hideSoftKeyboardForced(Context context, View view) {
        if (context == null) {
            return;
        }

        InputMethodManager imm = (InputMethodManager) context
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromInputMethod(view.getWindowToken(), 0);

    }

    public static void hideSoftKeyboard(Context context, EditText editText) {
        if (context == null) {
            return;
        }
        InputMethodManager imm = (InputMethodManager) context
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(editText.getWindowToken(), InputMethodManager.HIDE_IMPLICIT_ONLY);
    }

    public static void showSoftKeyboard(Context context, EditText editText) {

        if (context == null) {
            return;
        }

        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(editText, InputMethodManager.SHOW_IMPLICIT);
        editText.requestFocus();
    }

    public static void showSoftKeyboardForcefully(Context context, EditText editText) {

        if (context == null) {
            return;
        }

        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(editText, InputMethodManager.SHOW_FORCED);
        editText.requestFocus();
    }




}
