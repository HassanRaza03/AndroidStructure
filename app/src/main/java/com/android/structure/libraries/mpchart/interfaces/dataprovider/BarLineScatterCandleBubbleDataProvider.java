package com.android.structure.libraries.mpchart.interfaces.dataprovider;

import com.android.structure.libraries.mpchart.components.YAxis.AxisDependency;
import com.android.structure.libraries.mpchart.data.BarLineScatterCandleBubbleData;
import com.android.structure.libraries.mpchart.utils.Transformer;

public interface BarLineScatterCandleBubbleDataProvider extends ChartInterface {

    Transformer getTransformer(AxisDependency axis);
    boolean isInverted(AxisDependency axis);
    
    float getLowestVisibleX();
    float getHighestVisibleX();

    BarLineScatterCandleBubbleData getData();
}
