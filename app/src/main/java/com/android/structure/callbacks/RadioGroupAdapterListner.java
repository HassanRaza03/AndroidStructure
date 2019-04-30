package com.android.structure.callbacks;

import android.widget.RadioGroup;

public interface RadioGroupAdapterListner {
    void onSwitch(RadioGroup radioGroup, int i, int adapterPosition);
}
