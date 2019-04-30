package com.android.structure.libraries.imageresizer.utils;

import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class ImageWriter {

    public static boolean writeToFile(Bitmap image, File file) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        image.compress(CompressFormat.JPEG, 100, bytes);

        try {
            FileOutputStream fos = new FileOutputStream(file);
            fos.write(bytes.toByteArray());
            fos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return false;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }

        return true;
    }

}
