package com.android.structure.managers;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.renderscript.Allocation;
import android.renderscript.Element;
import android.renderscript.RenderScript;
import android.renderscript.ScriptIntrinsicBlur;
import android.util.Base64;
import android.util.Log;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.NumberFormat;

import static android.support.media.ExifInterface.ORIENTATION_ROTATE_180;
import static android.support.media.ExifInterface.ORIENTATION_ROTATE_270;
import static android.support.media.ExifInterface.ORIENTATION_ROTATE_90;

/**
 * Created by muhammadmuzammil on 7/18/2017.
 */

public class ImageManager {


    /**
     * @param context
     * @param data
     * @param width
     * @param quality
     * @param isFront
     * @param output
     * @param blur
     * @param blurRadius
     * @param isVideo
     */


    public static final float BLUR_RADIUS = 5f;

    public static void compress(Context context, byte[] data, int width, int quality, boolean isFront, File output, boolean blur, float blurRadius, boolean isVideo) {
        Bitmap original = BitmapFactory.decodeByteArray(data, 0, data.length);
        compressInternal(context, original, width, quality, isFront, output, blur, blurRadius, isVideo);
    }

    /**
     * @param context
     * @param original
     * @param width
     * @param quality
     * @param isFront
     * @param output
     * @param blur
     * @param blurRadius
     * @param isVideo
     */
    public static void compress(Context context, Bitmap original, int width, int quality, boolean isFront, File output, boolean blur, float blurRadius, boolean isVideo) {
        compressInternal(context, original, width, quality, isFront, output, blur, blurRadius, isVideo);
    }


    private static void compressInternal(Context context, Bitmap original, int width, int quality, boolean isFront, File output, boolean blur, float blurRadius, boolean isNotRotate) {
        float height = ((float) original.getHeight() / (float) original.getWidth()) * width;

        Matrix matrix = new Matrix();
        if (isNotRotate && isFront) {
            matrix.postScale(-1, 1);
        } else {
            if (isFront) {
                matrix.postRotate(270);
                matrix.postScale(-1, 1);
            } else {
                matrix.postRotate(90);
            }
        }


        Bitmap scaledBitmap = Bitmap.createScaledBitmap(original, width, (int) height, true);
        scaledBitmap = Bitmap.createBitmap(scaledBitmap, 0, 0, scaledBitmap.getWidth(), scaledBitmap.getHeight(), matrix, true);

        try {
            if (blur)
                scaledBitmap = blur(context, scaledBitmap, blurRadius);

            FileOutputStream out = new FileOutputStream(output);
            scaledBitmap.compress(Bitmap.CompressFormat.JPEG, quality, out);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Matrix getRotation(Uri selectedImage, Bitmap b) {

        Matrix matrix = new Matrix();
        ExifInterface exif;
        try {

            exif = new ExifInterface(selectedImage.getPath());

            int orientation = exif.getAttributeInt(
                    ExifInterface.TAG_ORIENTATION,
                    ExifInterface.ORIENTATION_NORMAL);
            Log.d("orientation", orientation + "");
            if (orientation == ORIENTATION_ROTATE_90) {
                matrix.postRotate(90);
            } else if (orientation == ORIENTATION_ROTATE_180) {
                matrix.postRotate(180);
                matrix.postScale((float) b.getWidth(), (float) b.getHeight());
            } else if (orientation == ORIENTATION_ROTATE_270) {
                matrix.postRotate(270);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return matrix;

    }


    private static int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {
            final int heightRatio = Math.round((float) height / (float) reqHeight);
            final int widthRatio = Math.round((float) width / (float) reqWidth);
            inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;
        }
        final float totalPixels = width * height;
        final float totalReqPixelsCap = reqWidth * reqHeight * 2;
        while (totalPixels / (inSampleSize * inSampleSize) > totalReqPixelsCap) {
            inSampleSize++;
        }

        return inSampleSize;
    }


    public static void showImageSizeLog(File file, Bitmap bitmap, String nameToShow) {
        if (bitmap == null) return;
        double length = file.length();
        NumberFormat formatter = new DecimalFormat("#0.00");

        length = length / 1024;
        Log.d("Image Size - " + nameToShow, nameToShow + " " + formatter.format(length) + " Kb" + " -dimensions " + bitmap.getWidth() + " x " + bitmap.getHeight());

    }

    public static String bitmapToBase64(Bitmap bitmap) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 80, byteArrayOutputStream);
        byte[] byteArray = byteArrayOutputStream.toByteArray();
        return Base64.encodeToString(byteArray, Base64.DEFAULT);
    }

    public static Bitmap base64ToBitmap(String base64) {
        byte[] decodedString = Base64.decode(base64, Base64.DEFAULT);
        return BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
    }

    private static Bitmap blur(Context context, Bitmap src, float blurRadius) {


        Bitmap U8_4Bitmap;
        if (src.getConfig() == Bitmap.Config.ARGB_8888) {
            U8_4Bitmap = src;
        } else {
            U8_4Bitmap = src.copy(Bitmap.Config.ARGB_8888, true);
        }

        //==============================

//        Bitmap bitmap = Bitmap.createBitmap(U8_4Bitmap.getWidth(), U8_4Bitmap.getHeight(), U8_4Bitmap.getConfig());


        RenderScript rs = RenderScript.create(context);
        final Allocation input = Allocation.createFromBitmap(rs, U8_4Bitmap); //use this constructor for best performance, because it uses USAGE_SHARED mode which reuses memory
        final Allocation output = Allocation.createTyped(rs, input.getType());
        final ScriptIntrinsicBlur script = ScriptIntrinsicBlur.create(rs, Element.U8_4(rs));
        script.setRadius(blurRadius);
        script.setInput(input);
        script.forEach(output);
        output.copyTo(U8_4Bitmap);
        rs.destroy();
        return U8_4Bitmap;
    }


    public static boolean isVideo(String fileName) {
        String mimeType = FileManager.getExtension(fileName);

        switch (mimeType) {
            case "mp4":
            case "3gp":
            case "mkv":
            case "mov":
            case "flv":
            case "avi":
            case "wmv":
                return true;
//            case "jpg":
//            case "jpeg":
//            case "png":
//            case "":

        }

        return false;
    }

}
