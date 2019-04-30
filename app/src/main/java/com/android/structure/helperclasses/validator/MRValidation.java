package com.android.structure.helperclasses.validator;

import android.widget.EditText;

import com.andreabaccega.formedittextvalidator.Validator;

/**
 * Created by khanhamza on 08-Mar-17.
 */

public class MRValidation extends Validator {

    private EditText edtConfirmPassword;

    public MRValidation() {
        super("MR number should contain 7 digits");
    }


    @Override
    public boolean isValid(EditText et) {
        return et.getText().toString().length() == 9;
    }


}
