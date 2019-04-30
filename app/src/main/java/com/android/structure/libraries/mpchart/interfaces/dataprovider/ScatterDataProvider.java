package com.android.structure.libraries.mpchart.interfaces.dataprovider;

import com.android.structure.libraries.mpchart.data.ScatterData;

public interface ScatterDataProvider extends BarLineScatterCandleBubbleDataProvider {

    ScatterData getScatterData();
}
