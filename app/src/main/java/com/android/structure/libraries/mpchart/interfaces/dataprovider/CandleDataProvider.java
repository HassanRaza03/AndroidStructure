package com.android.structure.libraries.mpchart.interfaces.dataprovider;

import com.android.structure.libraries.mpchart.data.CandleData;

public interface CandleDataProvider extends BarLineScatterCandleBubbleDataProvider {

    CandleData getCandleData();
}
