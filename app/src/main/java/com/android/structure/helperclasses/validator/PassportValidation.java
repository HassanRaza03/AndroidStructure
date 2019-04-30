package com.android.structure.helperclasses.validator;

import android.widget.EditText;

import com.andreabaccega.formedittextvalidator.Validator;

/**
 * Created by khanhamza on 08-Mar-17.
 */

public class PassportValidation extends Validator {

    private EditText edtConfirmPassword;

    public PassportValidation() {
        super("Passport number should contain 9 letters");
    }


    @Override
    public boolean isValid(EditText et) {
        return et.getText().toString().length() == 9;
    }


}
