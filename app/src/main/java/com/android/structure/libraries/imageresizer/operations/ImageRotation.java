package com.android.structure.libraries.imageresizer.operations;

public enum ImageRotation {

    CW_90,
    CW_180,
    CW_270,
    FLIP_HORIZONTAL,
    FLIP_VERTICAL;

    public static int inDegrees(ImageRotation rotation) {
        if (rotation == CW_90) return 90;
        if (rotation == CW_180) return 180;
        if (rotation == CW_270) return 270;

        return 0;
    }

}
