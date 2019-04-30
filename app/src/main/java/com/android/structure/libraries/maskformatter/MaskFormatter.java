package com.android.structure.libraries.maskformatter;

import android.os.Build;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.widget.EditText;


public class MaskFormatter implements TextWatcher {

    private static final char SPACE = ' ';

    private final String mask;
    private final EditText maskedField;
    private final char maskCharacter;

    private boolean editTextChange;
    private int newIndex;
    private String textBefore;
    private int selectionBefore;
    private int passwordMask;

    public MaskFormatter(String mask, EditText maskedField) {
        this(mask, maskedField, SPACE);
    }

    public MaskFormatter(String mask, EditText maskedField, char maskCharacter) {
        this.mask = mask;
        this.maskedField = maskedField;
        this.maskCharacter = maskCharacter;
        this.passwordMask = getPasswordMask(maskedField);
        setInputTypeBasedOnMask();
    }

    private int getPasswordMask(EditText maskedField) {
        int inputType = maskedField.getInputType();
        int maskedFieldPasswordMask = (inputType & InputType.TYPE_TEXT_VARIATION_PASSWORD
            | inputType & InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            maskedFieldPasswordMask |= inputType & InputType.TYPE_NUMBER_VARIATION_PASSWORD;
            maskedFieldPasswordMask |= inputType & InputType.TYPE_TEXT_VARIATION_WEB_PASSWORD;
        }

        return maskedFieldPasswordMask;
    }

    @Override
    public void beforeTextChanged(CharSequence s, int index, int toBeReplacedCount, int addedCount) {
        textBefore = s.toString();
        selectionBefore = maskedField.getSelectionEnd();
    }

    @Override
    public void onTextChanged(CharSequence s, int index, int replacedCount, int addedCount) {
        if (editTextChange) {
            maskedField.setSelection(newIndex);
            editTextChange = false;
            return;
        }

        try {
            String appliedMaskString = applyMask(s.toString());
            if (!appliedMaskString.equals(s.toString())) {
                editTextChange = true;
                newIndex = countNewIndex(addedCount, appliedMaskString);
                maskedField.setText(appliedMaskString);
            }
        } catch (InvalidTextException ie) {
            editTextChange = true;
            newIndex = selectionBefore;
            maskedField.setText(textBefore);
        }
    }

    private int countNewIndex(int addedCount, String appliedMaskString) {
        if (appliedMaskString.length() == 0) {
            return 0;
        }

        if (addedCount < 1) {
            return newIndexForRemovingCharacters(appliedMaskString);
        }

        return newIndexForAddingCharacters(appliedMaskString);
    }

    private int newIndexForRemovingCharacters(String appliedMaskString) {
        if (selectionBefore > appliedMaskString.length()) {
            selectionBefore = appliedMaskString.length();
        } else {
            selectionBefore = selectionBefore - 1;
        }

        if (selectionBefore < 0) {
            return 0;
        }

        if (selectionBefore - 1 >= 0
            && appliedMaskString.charAt(selectionBefore - 1) == maskCharacter) {
            return selectionBefore - 1;
        }

        return selectionBefore;
    }

    private int newIndexForAddingCharacters(String appliedMaskString) {
        if (selectionBefore >= appliedMaskString.length()) {
            return appliedMaskString.length();
        }

        if (appliedMaskString.charAt(selectionBefore) == maskCharacter) {
            return selectionBefore + 2;
        }

        return selectionBefore + 1;
    }

    @Override
    public void afterTextChanged(Editable s) {
        if (editTextChange) {
            maskedField.setSelection(newIndex);
        }
        setInputTypeBasedOnMask();
    }

    private String applyMask(String newValue) throws InvalidTextException {
        String newValueWithoutSpaces = newValue.replaceAll(String.valueOf(maskCharacter), "");
        StringBuilder sb = new StringBuilder();
        int index = 0;
        for (char c : newValueWithoutSpaces.toCharArray()) {
            if (index >= mask.length()) {
                throw new InvalidTextException();
            }
            while (mask.charAt(index) == maskCharacter) {
                sb.append(maskCharacter);
                index++;
            }
            sb.append(applyMaskToChar(c, index));
            index++;
        }

        return sb.toString();
    }

    private char applyMaskToChar(char c, int maskIndex) throws InvalidTextException {
        return CharTransforms.transformChar(c, mask.charAt(maskIndex));
    }

    private void setInputTypeBasedOnMask() {
        int selection = maskedField.getSelectionEnd();
        if (selection >= mask.length()) {
            return;
        }

        char maskChar = getFirstNotWhiteCharFromMask();
        if (maskChar == maskCharacter) {
            return;
        }

        maskedField.setInputType(passwordMask | CharInputType.getKeyboardTypeForNextChar(maskChar));
    }

    private char getFirstNotWhiteCharFromMask() {
        int maskIndex = maskedField.getSelectionEnd();
        while (maskIndex < mask.length() && mask.charAt(maskIndex) == maskCharacter) {
            maskIndex++;
        }
        return mask.charAt(maskIndex);
    }

    public String getRawTextValue() {
        return maskedField.getText().toString().replaceAll(String.valueOf(maskCharacter), "");
    }

}