package com.android.structure.libraries.imageloader;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.widget.ImageView;

import com.android.structure.constatnts.WebServiceConstants;
import com.android.structure.managers.SharedPreferenceManager;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;

import java.util.Map;

import com.android.structure.R;
import com.android.structure.constatnts.AppConstants;

/**
 * Created by khanhamza on 08-Mar-17.
 */

public class ImageLoaderHelper {


    public static void loadImageWithConstantHeadersWithoutAnimation(Context context, ImageView imageView, String path) {
        ImageLoader.getInstance().displayImage(ImageLoaderHelper.getUserImageURL(path),
                imageView,
                ImageLoaderHelper.getOptionsSimple(WebServiceConstants
                        .getHeaders(SharedPreferenceManager.getInstance(context).getString(AppConstants.KEY_TOKEN))));
    }

    public static void loadImageWithConstantHeaders(Context context, ImageView imageView, String path) {
        ImageLoader.getInstance().displayImage(ImageLoaderHelper.getUserImageURL(path),
                imageView,
                ImageLoaderHelper.getOptionsWithAnimation(WebServiceConstants
                        .getHeaders(SharedPreferenceManager.getInstance(context).getString(AppConstants.KEY_TOKEN))));
    }


    public static void loadBase64Image(Context context, ImageView imageView, String base64) {
        byte[] decodedString = Base64.decode(base64, Base64.DEFAULT);
        Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);

        if (decodedByte != null) {
            imageView.setImageBitmap(decodedByte);
        } else {
            imageView.setImageResource(R.drawable.female_icon_filled);
        }
    }

    public static String getImageURL(String path, String requestMethod) {
        return WebServiceConstants.GETIMAGE_BASE_URL + path + "&requestmethod=" + requestMethod;
    }

    public static String getUserImageURL(String path) {
        return WebServiceConstants.GETIMAGE_BASE_URL + path + "&requestmethod=" + WebServiceConstants.METHOD_USER_GET_USER_IMAGE;
    }

    public static DisplayImageOptions getOptionsSimple() {

        return new DisplayImageOptions.Builder().cacheInMemory(true).cacheOnDisk(true)
                .showImageForEmptyUri(R.color.base_dark_gray)
                .showImageOnFail(R.drawable.profile_placeholder)
                .showImageOnLoading(R.drawable.profile_placeholder)
                .build();
    }


    public static DisplayImageOptions getOptionsSimple(Map<String, String> headers) {

        return new DisplayImageOptions.Builder().cacheInMemory(true).cacheOnDisk(true)
                .showImageForEmptyUri(R.color.base_dark_gray)
                .showImageOnFail(R.drawable.profile_placeholder)
                .showImageOnLoading(R.drawable.profile_placeholder)
                .extraForDownloader(headers)
                .build();
    }

    public static DisplayImageOptions getOptionsWithAnimation() {

        return new DisplayImageOptions.Builder().cacheInMemory(true).cacheOnDisk(true)
                .showImageForEmptyUri(R.color.base_dark_gray)
                .showImageOnFail(R.drawable.profile_placeholder)
                .showImageOnLoading(R.drawable.profile_placeholder)
                .imageScaleType(ImageScaleType.EXACTLY).displayer(new FadeInBitmapDisplayer(500)).build();
    }


    public static DisplayImageOptions getOptionsWithAnimation(Map<String, String> headers) {

        return new DisplayImageOptions.Builder().cacheInMemory(true).cacheOnDisk(true)
                .showImageForEmptyUri(R.color.base_dark_gray)
                .showImageOnFail(R.drawable.profile_placeholder)
                .showImageOnLoading(R.drawable.profile_placeholder)
                .extraForDownloader(headers)
                .imageScaleType(ImageScaleType.EXACTLY).displayer(new FadeInBitmapDisplayer(500)).build();
    }
}
