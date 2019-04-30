package com.android.structure.widget;

/**
 * Created by khanhamza on 01-Mar-17.
 */

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

public class CustomViewPager extends ViewPager {

    private boolean enabled;

    public CustomViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.enabled = true;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (this.enabled) {
            return super.onTouchEvent(event);
        }

        return false;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent event) {
        if (this.enabled) {
            return super.onInterceptTouchEvent(event);
        }

        return false;
    }


    public void moveNext() {
        //it doesn't matter if you're already in the last item
        if (getCurrentItem() < getAdapter().getCount() - 1) {
            setCurrentItem(getCurrentItem() + 1);
        }
    }

    public void movePrevious() {
        //it doesn't matter if you're already in the first item
        if (getCurrentItem() > 0) {
            setCurrentItem(getCurrentItem() - 1);
        }
    }

    public void setPagingEnabled(boolean enabled) {
        this.enabled = enabled;
    }
}