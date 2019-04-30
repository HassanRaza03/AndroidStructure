package com.android.structure.libraries.mpchart.custom;


import android.content.Context;

import com.android.structure.libraries.mpchart.components.AxisBase;
import com.android.structure.libraries.mpchart.formatter.IAxisValueFormatter;

import com.android.structure.R;

public class CustomStringFormatter implements IAxisValueFormatter {


    private final Context mContext;

    public CustomStringFormatter(Context mContext) {
        this.mContext = mContext;
    }

    @Override
    public String getFormattedValue(float value, AxisBase axis) {
        int v = (int) value;
        if (mContext.getResources().getStringArray(R.array.stats_xAxis).length > v) {
            return mContext.getResources().getStringArray(R.array.stats_xAxis)[v];
        } else {
            return "Unknown";
        }
    }
}