package com.android.structure.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.KeyEvent;

import com.andreabaccega.widget.FormEditText;

import com.android.structure.utils.utility.Utils;

public class AnyEditTextView extends FormEditText {

    public AnyEditTextView(Context context) {
        super(context);
    }

    public AnyEditTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        Utils.setTypeface(attrs, this);
    }

    public AnyEditTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        Utils.setTypeface(attrs, this);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event)
    {
        if (keyCode== KeyEvent.KEYCODE_ENTER)
        {
            // Just ignore the [Enter] key
            return true;
        }
        // Handle all other keys in the default way
        return super.onKeyDown(keyCode, event);
    }


    public String getStringTrimmed(){
        return  getText().toString().trim() ;
    }
}
