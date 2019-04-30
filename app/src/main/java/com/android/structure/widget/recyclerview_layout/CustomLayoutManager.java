package com.android.structure.widget.recyclerview_layout;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;

public class CustomLayoutManager extends LinearLayoutManager {
    private boolean isScrollEnabled = true;

    // orientation should be LinearLayoutManager.VERTICAL or HORIZONTAL
    public CustomLayoutManager(Context context, int orientation, boolean isScrollEnabled) {
        super(context, orientation, false);
        this.isScrollEnabled = isScrollEnabled;
    }

    @Override
    public boolean canScrollVertically() {
        //Similarly you can customize "canScrollHorizontally()" for managing horizontal scroll
        return isScrollEnabled && super.canScrollVertically();
    }
}