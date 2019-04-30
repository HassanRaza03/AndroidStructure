package com.android.structure.helperclasses.validator;

import android.widget.EditText;

import com.andreabaccega.formedittextvalidator.Validator;

/**
 * Created by khanhamza on 08-Mar-17.
 */

public class MobileNumberValidation extends Validator {

    public MobileNumberValidation() {
        super("Mobile number length must be greater than 9 ");
    }

    @Override
    public boolean isValid(EditText mobileNumrber) {
        return mobileNumrber.getText() != null
                && mobileNumrber.getText().toString().length() >= 10;
    }
}
