package com.android.structure.libraries.mpchart.interfaces.dataprovider;

import com.android.structure.libraries.mpchart.data.BubbleData;

public interface BubbleDataProvider extends BarLineScatterCandleBubbleDataProvider {

    BubbleData getBubbleData();
}
