package com.android.structure.callbacks;

import com.android.structure.adapters.SpinnerDialogAdapter;

public interface OnSpinnerItemClickListener {
    void onItemClick(int position, Object object, SpinnerDialogAdapter adapter);
}
