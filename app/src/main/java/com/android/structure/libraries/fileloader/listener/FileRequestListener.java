package com.android.structure.libraries.fileloader.listener;

import com.android.structure.libraries.fileloader.pojo.FileResponse;
import com.android.structure.libraries.fileloader.request.FileLoadRequest;

/**
 * Created by krishna on 12/10/17.
 */

public interface FileRequestListener<T> {
    void onLoad(FileLoadRequest request, FileResponse<T> response);

    void onError(FileLoadRequest request, Throwable t);
}
