package com.android.structure.libraries.mpchart.custom;


import android.content.Context;

import com.android.structure.libraries.mpchart.components.AxisBase;
import com.android.structure.libraries.mpchart.formatter.IAxisValueFormatter;
import com.android.structure.models.TupleModel;

public class DivisionStringFormatter implements IAxisValueFormatter {


    private final Context mContext;
    private final TupleModel statsModel;

    public DivisionStringFormatter(Context mContext, TupleModel statsModel) {
        this.mContext = mContext;
        this.statsModel = statsModel;
    }

    @Override
    public String getFormattedValue(float value, AxisBase axis) {
        int v = (int) value;
//        if (v == 0) {
//            return mContext.getResources().getStringArray(R.array.stats_xAxis)[v];
//        } else if (statsModel.getListDivisionCount().size() >= v) {
//            return statsModel.getListDivisionCount().get(v - 1).getDivisionName();
//        } else {
            return "Unknown";
//        }
    }
}