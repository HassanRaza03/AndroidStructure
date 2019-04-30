package com.android.structure.libraries.table.model.action;

import com.android.structure.libraries.table.model.ICellData;

public interface Action {
    boolean onAction(ICellData cell);
}
