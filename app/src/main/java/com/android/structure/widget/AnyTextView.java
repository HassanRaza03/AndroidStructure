package com.android.structure.widget;

import android.content.Context;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;


import com.android.structure.utils.utility.Utils;

public class AnyTextView extends AppCompatTextView {



    public AnyTextView(Context context) {
        super(context);
    }

    public AnyTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        Utils.setTypeface(attrs, this);
    }

    public AnyTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        Utils.setTypeface(attrs, this);
    }



    public String getStringTrimmed(){
        return  getText().toString().trim() ;
    }


}
