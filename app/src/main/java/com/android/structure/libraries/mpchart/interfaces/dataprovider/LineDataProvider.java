package com.android.structure.libraries.mpchart.interfaces.dataprovider;

import com.android.structure.libraries.mpchart.components.YAxis;
import com.android.structure.libraries.mpchart.data.LineData;

public interface LineDataProvider extends BarLineScatterCandleBubbleDataProvider {

    LineData getLineData();

    YAxis getAxis(YAxis.AxisDependency dependency);
}
