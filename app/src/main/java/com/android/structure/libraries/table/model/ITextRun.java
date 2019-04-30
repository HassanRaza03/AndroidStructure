package com.android.structure.libraries.table.model;


import com.android.structure.libraries.table.model.action.Action;

public interface ITextRun {
    int getStartPos();
    int getLength();
    int getFontIndex();
    int getBackgroundColor();
    Action getAction();
}
