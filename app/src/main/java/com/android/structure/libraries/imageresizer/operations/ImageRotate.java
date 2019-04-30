package com.android.structure.libraries.imageresizer.operations;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Matrix;

import com.android.structure.libraries.imageresizer.utils.ImageDecoder;

import java.io.File;

public class ImageRotate {

    public static Bitmap rotate(Resources resources, int resId, ImageRotation rotation) {
        Bitmap sampledSrcBitmap = ImageDecoder.decodeResource(resources, resId);
        if (sampledSrcBitmap == null) {
            return null;
        }

        return rotate(sampledSrcBitmap, rotation);
    }

    public static Bitmap rotate(byte[] original, ImageRotation rotation) {
        Bitmap sampledSrcBitmap = ImageDecoder.decodeByteArray(original);
        if (sampledSrcBitmap == null) {
            return null;
        }

        return rotate(sampledSrcBitmap, rotation);
    }

    public static Bitmap rotate(File original, ImageRotation rotation) {
        Bitmap sampledSrcBitmap = ImageDecoder.decodeFile(original);
        if (sampledSrcBitmap == null) {
            return null;
        }

        return rotate(sampledSrcBitmap, rotation);
    }

    public static Bitmap rotate(Bitmap sampledSrcBitmap, ImageRotation rotation) {
        int width = sampledSrcBitmap.getWidth();
        int height = sampledSrcBitmap.getHeight();

        Matrix matrix = new Matrix();
        if (rotation == ImageRotation.FLIP_HORIZONTAL) {
            matrix.setScale(-1, 1);
            matrix.postTranslate(width, 0);
        } else if (rotation == ImageRotation.FLIP_VERTICAL) {
            matrix.setScale(1, -1);
            matrix.postTranslate(0, height);
        } else {
            matrix.setRotate(ImageRotation.inDegrees(rotation), width / 2, height / 2);
        }

        return Bitmap.createBitmap(sampledSrcBitmap, 0, 0, sampledSrcBitmap.getWidth(), sampledSrcBitmap.getHeight(), matrix, true);
    }

}
