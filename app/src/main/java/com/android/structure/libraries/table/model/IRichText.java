package com.android.structure.libraries.table.model;


public interface IRichText {
    CharSequence getText();
    int getRunCount();
    ITextRun getRun(int index);
}
