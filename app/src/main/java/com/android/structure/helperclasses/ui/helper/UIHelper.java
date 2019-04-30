package com.android.structure.helperclasses.ui.helper;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.DialogInterface;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;
import android.media.ExifInterface;
import android.os.Build;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.graphics.ColorUtils;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.WindowManager;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.structure.adapters.SpinnerDialogAdapter;
import com.android.structure.models.IntWrapper;
import com.android.structure.models.SpinnerModel;
import com.gdacciaro.iOSDialog.iOSDialogBuilder;
import com.gdacciaro.iOSDialog.iOSDialogClickListener;
import com.google.common.base.Strings;
import com.google.gson.reflect.TypeToken;
import com.kaopiz.kprogresshud.KProgressHUD;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;

import com.android.structure.R;
import com.android.structure.activities.BaseActivity;
import com.android.structure.callbacks.GenericClickableInterface;
import com.android.structure.callbacks.OnSpinnerOKPressedListener;
import com.android.structure.callbacks.OnSpinnerItemClickListener;
import com.android.structure.fragments.abstracts.BaseFragment;
import com.android.structure.fragments.abstracts.GenericDialogFragment;
import com.android.structure.fragments.dialogs.SpinnerDialogFragment;
import com.android.structure.managers.retrofit.GsonFactory;

/**
 * Created by khanhamza on 09-Mar-17.
 */

public class UIHelper {

    public static void showToast(Context context, String message) {

        if (context == null) {
            return;
        }

        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }

    public static void showLongToastInCenter(Context ctx, int messageId) {
        Toast toast = Toast.makeText(ctx, messageId, Toast.LENGTH_LONG);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
    }

    public static void showLongToastInCenter(Context ctx, String message) {
        message = Strings.nullToEmpty(message);
        Toast toast = Toast.makeText(ctx, message, Toast.LENGTH_LONG);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
    }

    public static void showShortToastInCenter(Context ctx, String message) {
        if (ctx == null) return;
        message = Strings.nullToEmpty(message);
        Toast toast = Toast.makeText(ctx, message, Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
    }

    public static void showShortToastInCenter(Context ctx, int message) {
        Toast toast = Toast.makeText(ctx, message, Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
    }

    public static void showConnectionFailedToast(Context ctx) {
        //showLongToastInCenter(ctx, R.string.msg_connection_failed);
    }

    public static void showConnectionErrorToast(Context ctx) {
        // showLongToastInCenter(ctx, R.string.msg_connection_error);
    }

    public static void showAlertDialog(String message, CharSequence title,
                                       Context context) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setMessage(message)
                .setTitle(title)
                .setCancelable(true)
                .setNegativeButton("OK",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });

        AlertDialog alert = builder.create();
        alert.show();
    }

    public static void showAlertDialog(String message, String title,
                                       DialogInterface.OnClickListener onClickListener, Context context) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder
                .setMessage(message)
                .setCancelable(true)
                .setNegativeButton("Cancel",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        })
                .setPositiveButton("OK", onClickListener);

        if (!title.isEmpty())
            builder.setTitle(title);

        AlertDialog alert = builder.create();
        alert.show();
    }


    /**
     * get Color lightness, if you have color in HEX, then parse it using Color.parse(hexColor)
     *
     * @param color
     * @return
     */
    public static float getColorLightness(int color) {
        int red = Color.red(color);
        int green = Color.green(color);
        int blue = Color.blue(color);

        float[] hsl = new float[3];
        ColorUtils.RGBToHSL(red, green, blue, hsl);
        return hsl[2];
    }


    public static void showDialogWithView(View view, String message, String title,
                                          String positiveText, DialogInterface.OnClickListener onPositiveClickListener,
                                          Context context) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder
                .setMessage(message)
                .setCancelable(true)
                .setView(view)
                .setNegativeButton("Cancel",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        })
                .setPositiveButton(positiveText, onPositiveClickListener);

        if (!title.isEmpty())
            builder.setTitle(title);

        AlertDialog alert = builder.create();
        alert.show();
    }

    public static void showAlertDialogThrice(boolean addNetralButton,
                                             String message, String title,
                                             DialogInterface.OnClickListener onNeutralClickListener,
                                             DialogInterface.OnClickListener onPositiveClickListener,
                                             String neutralText, String positiveText,
                                             Context context) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder
                .setMessage(message)
                .setCancelable(true)
                .setNegativeButton("Cancel",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        })
                .setPositiveButton(positiveText, onPositiveClickListener);

        if (addNetralButton)
            builder.setNeutralButton(neutralText, onNeutralClickListener);

        if (!title.isEmpty())
            builder.setTitle(title);

        AlertDialog alert = builder.create();
        alert.show();
    }


    public static void showAlertDialog(String message, String title,
                                       DialogInterface.OnClickListener onPositiveClickListener, String positiveText,
                                       DialogInterface.OnClickListener onNegativeClickListener, String negativeText,
                                       Context context) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder
                .setMessage(message)
                .setCancelable(true)
                .setNegativeButton(negativeText, onNegativeClickListener)
                .setPositiveButton(positiveText, onPositiveClickListener);

        if (!title.isEmpty())
            builder.setTitle(title);

        AlertDialog alert = builder.create();
        alert.show();
    }

    public static void showCheckedDialogBox(Context mContext, String mTitle,
                                            String[] privacyArray, int selectedIndex,
                                            DialogInterface.OnClickListener onClickListener) {

        AlertDialog dialog = new AlertDialog.Builder(mContext)
                .setTitle(mTitle)
                .setSingleChoiceItems(privacyArray,
                        selectedIndex, null)
                .setPositiveButton("OK", onClickListener
                ).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        //  Your code when user clicked on Cancel
                    }
                }).create();
        dialog.show();
    }

    public static void showListDialogBox(Context mContext, String mTitle,
                                         String[] Array,
                                         final DialogInterface.OnClickListener onClickListener) {

        AlertDialog dialog = new AlertDialog.Builder(mContext)
                .setTitle(mTitle)
                .setItems(Array, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        onClickListener.onClick(dialog, which);
                        dialog.dismiss();
                    }
                })
                .setPositiveButton("OK", onClickListener
                ).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        //  Your code when user clicked on Cancel
                    }
                }).create();


        dialog.show();
    }

    public static void showListDialogBox(Context mContext, String[] Array,
                                         final DialogInterface.OnClickListener onClickListener) {

        AlertDialog dialog = new AlertDialog.Builder(mContext)
                .setItems(Array, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        onClickListener.onClick(dialog, which);
                        dialog.dismiss();
                    }
                })
                .create();
        dialog.show();
    }


    public static Rect locateView(View v) {
        int[] loc_int = new int[2];
        if (v == null)
            return null;
        try {
            v.getLocationOnScreen(loc_int);
        } catch (NullPointerException npe) {
            // Happens when the view doesn't exist on screen anymore.
            return null;
        }
        Rect location = new Rect();
        location.left = loc_int[0];
        location.top = loc_int[1];
        location.right = location.left + v.getWidth();
        location.bottom = location.top + v.getHeight();
        return location;
    }

    public static void textMarquee(TextView txtView) {
        txtView.setEllipsize(TextUtils.TruncateAt.END);
    }

//    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
//    public static int getScreenWidth(Activity ctx) {
//        Display display = ctx.getWindowManager().getDefaultDisplay();
//
//        if (OSHelper.hasHoneycombMR2()) {
//            Point size = new Point();
//            display.getSize(size);
//            return size.x;
//        } else {
//            return display.getWidth();
//        }
//
//    }

    public static void dimBehind(Dialog dialog) {
        WindowManager.LayoutParams lp = dialog.getWindow().getAttributes();
        lp.dimAmount = 0.9f;
        dialog.getWindow()
                .addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        dialog.setCancelable(false);
    }

//    public static DisplayImageOptions getRoundedCornerImage() {
//        DisplayImageOptions displayOptions;
//        displayOptions = new DisplayImageOptions.Builder()
//                .showImageForEmptyUri(R.drawable.img_fav_pic)
//                .showImageOnFail(R.drawable.img_fav_pic)
//                .showStubImage(R.drawable.img_fav_pic)
//                .cacheInMemory(true)
//                .displayer(new RoundedBitmapDisplayer(10))
//                .cacheOnDisc(true)
//                .build();
//        return displayOptions;
//    }

    public static void releaseFocus(View view) {
        ViewParent parent = view.getParent();
        ViewGroup group = null;
        View child = null;
        while (parent != null) {
            if (parent instanceof ViewGroup) {
                group = (ViewGroup) parent;
                for (int i = 0; i < group.getChildCount(); i++) {
                    child = group.getChildAt(i);
                    if (child != view && child.isFocusable())
                        child.requestFocus();
                }
            }
            parent = parent.getParent();
        }
    }

    public static String compressImage(String filePath) {

        Bitmap scaledBitmap = null;
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        Bitmap bmp = BitmapFactory.decodeFile(filePath, options);

        int actualHeight = options.outHeight;
        int actualWidth = options.outWidth;

//      max Height and width values of the compressed image is taken as 816x612

        float maxHeight = 816.0f;
        float maxWidth = 612.0f;
        float imgRatio = actualWidth / actualHeight;
        float maxRatio = maxWidth / maxHeight;

//      width and height values are set maintaining the aspect ratio of the image

        if (actualHeight > maxHeight || actualWidth > maxWidth) {
            if (imgRatio < maxRatio) {
                imgRatio = maxHeight / actualHeight;
                actualWidth = (int) (imgRatio * actualWidth);
                actualHeight = (int) maxHeight;
            } else if (imgRatio > maxRatio) {
                imgRatio = maxWidth / actualWidth;
                actualHeight = (int) (imgRatio * actualHeight);
                actualWidth = (int) maxWidth;
            } else {
                actualHeight = (int) maxHeight;
                actualWidth = (int) maxWidth;

            }
        }

//      setting inSampleSize value allows to load a scaled down version of the original image

        options.inSampleSize = calculateInSampleSize(options, actualWidth, actualHeight);

//      inJustDecodeBounds set to false to load the actual bitmap
        options.inJustDecodeBounds = false;

//      this options allow android to claim the bitmap memory if it runs low on memory
        options.inPurgeable = true;
        options.inInputShareable = true;
        options.inTempStorage = new byte[16 * 1024];

        try {
//          load the bitmap from its path
            bmp = BitmapFactory.decodeFile(filePath, options);
        } catch (OutOfMemoryError exception) {
            exception.printStackTrace();

        }
        try {
            scaledBitmap = Bitmap.createBitmap(actualWidth, actualHeight, Bitmap.Config.ARGB_8888);
        } catch (OutOfMemoryError exception) {
            exception.printStackTrace();
        }

        float ratioX = actualWidth / (float) options.outWidth;
        float ratioY = actualHeight / (float) options.outHeight;
        float middleX = actualWidth / 2.0f;
        float middleY = actualHeight / 2.0f;

        Matrix scaleMatrix = new Matrix();
        scaleMatrix.setScale(ratioX, ratioY, middleX, middleY);

        Canvas canvas = new Canvas(scaledBitmap);
        canvas.setMatrix(scaleMatrix);
        canvas.drawBitmap(bmp, middleX - bmp.getWidth() / 2, middleY - bmp.getHeight() / 2, new Paint(Paint.FILTER_BITMAP_FLAG));

//      check the rotation of the image and display it properly
        ExifInterface exif;
        try {
            exif = new ExifInterface(filePath);

            int orientation = exif.getAttributeInt(
                    ExifInterface.TAG_ORIENTATION, 0);
            Log.d("EXIF", "Exif: " + orientation);
            Matrix matrix = new Matrix();
            if (orientation == 6) {
                matrix.postRotate(90);
                Log.d("EXIF", "Exif: " + orientation);
            } else if (orientation == 3) {
                matrix.postRotate(180);
                Log.d("EXIF", "Exif: " + orientation);
            } else if (orientation == 8) {
                matrix.postRotate(270);
                Log.d("EXIF", "Exif: " + orientation);
            }
            scaledBitmap = Bitmap.createBitmap(scaledBitmap, 0, 0,
                    scaledBitmap.getWidth(), scaledBitmap.getHeight(), matrix,
                    true);
        } catch (IOException e) {
            e.printStackTrace();
        }

        FileOutputStream out = null;
        String filename = getFilename();
        try {
            out = new FileOutputStream(filename);

//          write the compressed bitmap at the destination specified by filename.
            scaledBitmap.compress(Bitmap.CompressFormat.JPEG, 80, out);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return filename;

    }

    private static String getFilename() {
        File file = new File(Environment.getExternalStorageDirectory().getPath(), "MyFolder/Images");
        if (!file.exists()) {
            file.mkdirs();
        }
        String uriSting = (file.getAbsolutePath() + "/" + System.currentTimeMillis() + ".jpg");
        return uriSting;

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

    public static String addSpaces(int count) {
        String s = "";
        for (int i = 0; i < count; i++) {
            s += " ";
        }
        return s;
    }


    public static int statusBarHeight(Resources resources) {
        int statusBarHeight = 0;
        int resourceId = resources.getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            statusBarHeight = resources.getDimensionPixelSize(resourceId);
        }
        return statusBarHeight;
    }

    public static int actionBarHeight(Activity activity) {
        // action bar height
        int actionBarHeight = 0;
        final TypedArray styledAttributes = activity.getTheme().obtainStyledAttributes(
                new int[]{android.R.attr.actionBarSize}
        );
        actionBarHeight = (int) styledAttributes.getDimension(0, 0);
        styledAttributes.recycle();
        return actionBarHeight;
    }

    public static int navigationBarHeight(Resources resources) {
        // navigation bar height
        int navigationBarHeight = 0;
        int resourceId = resources.getIdentifier("navigation_bar_height", "dimen", "android");
        if (resourceId > 0) {
            navigationBarHeight = resources.getDimensionPixelSize(resourceId);
        }
        return navigationBarHeight;
    }

    public static void cropImagePicker(@NonNull Context context, @NonNull Fragment fragment) {
        CropImage.activity()
                .setGuidelines(CropImageView.Guidelines.ON)
                .setAspectRatio(1, 1)
                .setFixAspectRatio(true)
                .setCropShape(CropImageView.CropShape.RECTANGLE)
                .setMinCropWindowSize(192, 192)
                .setMinCropResultSize(192, 192)
                .setMultiTouchEnabled(false)
                .setOutputCompressFormat(Bitmap.CompressFormat.JPEG)
                // FIXME: 15-Jul-17 Fix Quality if required
                .setRequestedSize(640, 640, CropImageView.RequestSizeOptions.RESIZE_FIT)
                .setOutputCompressQuality(100)
                .start(context, fragment);
    }

    public static void cropImagePicker(@NonNull Activity activity) {
        CropImage.activity()
                .setGuidelines(CropImageView.Guidelines.ON)
                .setAspectRatio(1, 1)
                .setFixAspectRatio(true)
                .setCropShape(CropImageView.CropShape.RECTANGLE)
                .setMinCropWindowSize(192, 192)
                .setMinCropResultSize(192, 192)
                .setMultiTouchEnabled(false)
                .setOutputCompressFormat(Bitmap.CompressFormat.JPEG)
                // FIXME: 15-Jul-17 Fix Quality if required
                .setRequestedSize(640, 640, CropImageView.RequestSizeOptions.RESIZE_FIT)
                .setOutputCompressQuality(100)
                .start(activity);
    }

    public static String TAG_PIC_INFO = "PIC INFO";

    public static File saveToInternalSorage(Context context, Bitmap bitmapImage, String folderName, String fileName) {
        ContextWrapper cw = new ContextWrapper(context);
        File directory = cw.getDir(folderName, Context.MODE_PRIVATE);
        File mypath = new File(directory, fileName);
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(mypath);

            // Use the compress method on the BitMap object to write image to the OutputStream
            bitmapImage.compress(Bitmap.CompressFormat.JPEG, 100, fos);
            fos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        Log.d(TAG_PIC_INFO, "saveToInternalSorage: directory.getPath()         ->" + directory.getPath());
        Log.d(TAG_PIC_INFO, "saveToInternalSorage: mypath.getPath()            ->" + mypath.getPath());
        Log.d(TAG_PIC_INFO, "saveToInternalSorage: mypath.getAbsolutePath()    ->" + mypath.getAbsolutePath());

        return mypath;
    }


    public static void showSpinnerDialog(BaseFragment fragment, final ArrayList<SpinnerModel> arrData, String title, final TextView textView,
                                         OnSpinnerItemClickListener onSpinnerItemClick, OnSpinnerOKPressedListener onSpinnerOKPressedListener, final IntWrapper positionToScroll) {
        final SpinnerDialogFragment dialogFragment;

        String s = GsonFactory.getSimpleGson().toJson(arrData);
        Type type = new TypeToken<ArrayList<SpinnerModel>>() {
        }.getType();
        final ArrayList<SpinnerModel> listCopy = GsonFactory.getSimpleGson().fromJson(s, type);

        if (onSpinnerItemClick == null) {
            dialogFragment = SpinnerDialogFragment.newInstance(title, listCopy, new OnSpinnerItemClickListener() {
                @Override
                public void onItemClick(int position, Object object, SpinnerDialogAdapter adapter) {
                    if (object instanceof SpinnerModel) {
                        textView.setText(((SpinnerModel) object).getText());
                        for (SpinnerModel arrDatum : listCopy) {
                            arrDatum.setSelected(false);
                        }
                        listCopy.get(position).setSelected(true);
//                        adapter.getArrData().get(position).setSelected(true);

                        for (SpinnerModel arrDatum : arrData) {
                            arrDatum.setSelected(false);
                        }
                        arrData.get(position).setSelected(true);
//                        adapter.getArrData().get(position).setSelected(true);


                        adapter.notifyDataSetChanged();
                        positionToScroll.value = position;
                    }
                }
            }, onSpinnerOKPressedListener, positionToScroll.value);
        } else {
            dialogFragment = SpinnerDialogFragment.newInstance(title, listCopy, onSpinnerItemClick, onSpinnerOKPressedListener, positionToScroll.value);
        }
        dialogFragment.show(fragment.getFragmentManager(), null);
    }


    public static GenericDialogFragment genericPopUp(BaseActivity activity, GenericDialogFragment genericDialogFragment, String title, String message, String btn1Text, String btn2Text, GenericClickableInterface btn1Interface, GenericClickableInterface btnbtn2Interface, boolean isCancelable, boolean isShow) {
        genericDialogFragment.setTitle(title);
        genericDialogFragment.setMessage(message);
        genericDialogFragment.setButton1(btn1Text, btn1Interface);
        genericDialogFragment.setButton2(btn2Text, btnbtn2Interface);
        genericDialogFragment.setCancelable(isCancelable);
        if (isShow) {
            genericDialogFragment.show(activity.getSupportFragmentManager(), "generic_popup");
        }
        return genericDialogFragment;
    }

    public static void showOnlyTextPopup(BaseActivity baseActivity, String text) {
        GenericDialogFragment genericDialogFragment = GenericDialogFragment.newInstance();
        UIHelper.genericPopUp(baseActivity, genericDialogFragment, "", text,
                "OK", null, new GenericClickableInterface() {
                    @Override
                    public void click() {
                        genericDialogFragment.dismiss();
                    }
                }, null, false, true);
    }

    /**
     * library dependednt
     */
    public static KProgressHUD getProgressHUD(Context context) {
        ProgressBar progressBar = new ProgressBar(context, null, android.R.attr.progressBarStyle);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            progressBar.setIndeterminateTintList(ColorStateList.valueOf(context.getResources().getColor(R.color.c_white)));
        }


        KProgressHUD progressHUD = KProgressHUD.create(context);
//                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
        progressHUD
                .setCancellable(false)
                .setCustomView(progressBar)
                .setLabel("Loading...", context.getResources().getColor(R.color.c_white));
//                .setBackgroundColor(getContext().getResources().getColor(R.color.c_white))
//                .setAnimationSpeed(2)
//                .setDimAmount(0.8f)
//                .setGraceTime(500)

        return progressHUD;

    }


    public static void showIOSPopup(Context context, String title, String subTitle, String positiveLabel, String negativeLabel, iOSDialogClickListener positiveListener, iOSDialogClickListener negativeListener) {

        new iOSDialogBuilder(context)
                .setTitle(title)
                .setSubtitle(subTitle)
                .setBoldPositiveLabel(false)
                .setCancelable(false)
                .setPositiveListener(positiveLabel, dialog -> {
                    positiveListener.onClick(dialog);
                    dialog.dismiss();

                })
                .setNegativeListener(negativeLabel, dialog -> {
                    negativeListener.onClick(dialog);
                    dialog.dismiss();
                })
                .build().show();


    }


}
