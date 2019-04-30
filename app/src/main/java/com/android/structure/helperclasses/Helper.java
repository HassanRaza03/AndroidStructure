package com.android.structure.helperclasses;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.ActivityManager;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.PorterDuff;
import android.graphics.Rect;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.TransitionDrawable;
import android.media.AudioManager;
import android.media.MediaMetadataRetriever;
import android.media.RingtoneManager;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.Build;
import android.os.Parcelable;
import android.provider.BaseColumns;
import android.provider.ContactsContract;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.regex.Pattern;

import com.android.structure.R;
import io.realm.RealmList;
import io.realm.RealmModel;

/**
 * Created by muhammadmuzammil on 4/24/2017.
 */

public class Helper {
    public static boolean isFragmentOpened(FragmentActivity activity, String tag) {
        Fragment fragment = activity.getSupportFragmentManager().findFragmentByTag(tag);
        return (fragment != null && fragment.isVisible());
    }

    public static Fragment getFragmentByTag(FragmentActivity activity, String tag) {
        return activity.getSupportFragmentManager().findFragmentByTag(tag);
    }

    public static void hideKeyboard(Activity activity) {
        if (activity.getCurrentFocus() != null) {
            InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), 0);
        }
    }

    public static void showToast(Context context, String message) {
        Toast toast = Toast.makeText(context, message, Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
    }

    public static void changeStrokeColor(View view, int color) {
        GradientDrawable gd = (GradientDrawable) view.getBackground();
        gd.setStroke(2, color);
    }

    public static void changeTransitionDrawableColor(TransitionDrawable td, int color, int index) {
        td.getDrawable(index).setColorFilter(color, PorterDuff.Mode.SRC_ATOP);
    }

    public static ProgressDialog getLoader(Context context) {
        ProgressDialog progressDialog = new ProgressDialog(context);
        progressDialog.setMessage("Loading...");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setCancelable(false);
        return progressDialog;
    }

    public static ProgressDialog getLoader(Context context, String message) {
        ProgressDialog progressDialog = new ProgressDialog(context);
        progressDialog.setMessage(message);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setCancelable(false);
        return progressDialog;
    }


    public static boolean isMyServiceRunning(Context context, Class<?> serviceClass) {
        ActivityManager manager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (serviceClass.getName().equals(service.service.getClassName())) {
                Log.i("Service already", "running");
                return true;
            }
        }
        Log.i("Service not", "running");
        return false;
    }


    public static String feetToCentimeter(String feet) {
        double dCentimeter = 0d;
        if (!TextUtils.isEmpty(feet)) {
            if (feet.contains("'")) {
                String tempfeet = feet.substring(0, feet.indexOf("'"));
                if (!TextUtils.isEmpty(tempfeet)) {
                    dCentimeter += ((Double.valueOf(tempfeet)) * 30.48);
                }
            }
            if (feet.contains("\"")) {
                String tempinch = feet.substring(feet.indexOf("'") + 1, feet.indexOf("\""));
                if (!TextUtils.isEmpty(tempinch)) {
                    dCentimeter += ((Double.valueOf(tempinch)) * 2.54);
                }
            }
        }
        return String.valueOf(dCentimeter);
        //Format to decimal digit as per your requirement
    }

    public static String centimeterToFeet(String centemeter) {
        int feetPart = 0;
        int inchesPart = 0;
        if (!TextUtils.isEmpty(centemeter)) {
            double dCentimeter = Double.valueOf(centemeter);
            feetPart = (int) Math.floor((dCentimeter / 2.54) / 12);
            System.out.println((dCentimeter / 2.54) - (feetPart * 12));
            inchesPart = (int) Math.ceil((dCentimeter / 2.54) - (feetPart * 12));
        }
        return String.format("%d' %d''", feetPart, inchesPart);
    }


//    public static Map<String, String[]> readContacts(Context context) {
//        Map<String, String[]> contacts = new HashMap<>();
//
//        ContentResolver cr = context.getContentResolver();
//        Cursor cur = cr.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
//                new String[]{
//                        ContactsContract.CommonDataKinds.Phone.CONTACT_ID,
//                        //ContactsContract.Contacts._ID,
//                        ContactsContract.Data.DISPLAY_NAME,
//                        ContactsContract.CommonDataKinds.Phone.NUMBER,
//                }, null, null, null);
//
//        if (cur.getCount() > 0) {
//            String currentUserPhoneNo = SharedPreferenceManager.getInstance(context).getCurrentUser().userPhoneNumber;
//
//            while (cur.moveToNext()) {
//                int id = cur.getInt(0);
//                String displayName = cur.getString(1);
//                String phone = cur.getString(2);
//
//                phone = phone.startsWith("0") ? phone.replaceFirst("0", "92").replaceAll(" ", "") : phone.replaceAll(" ", "");
//                if (phone.equals(currentUserPhoneNo))
//                    continue;
//
//                contacts.put(phone, new String[]{id != 0 ? String.valueOf(id) : "-1", displayName});
//            }
//            cur.close();
//        }
//        Log.e("abc", "Contact sync complete");
//        return contacts;
//    }

    public static boolean isNumberExistonPhone(Context context, String number) {
        Uri uri = Uri.withAppendedPath(ContactsContract.PhoneLookup.CONTENT_FILTER_URI, Uri.encode(number));
        String name = "?";

        ContentResolver contentResolver = context.getContentResolver();
        Cursor contactLookup = contentResolver.query(uri, new String[]{BaseColumns._ID,
                ContactsContract.PhoneLookup.DISPLAY_NAME}, null, null, null);

        if (contactLookup != null && contactLookup.getCount() > 0) {
            contactLookup.close();
            return true;
        }

        return false;
    }

    public static boolean isJSONValid(String test) {
        try {
            new JSONObject(test);
        } catch (JSONException ex) {
            // edited, to include @Arthur's comment
            // e.g. in case JSONArray is valid as well...
            try {
                new JSONArray(test);
            } catch (JSONException ex1) {
                return false;
            }
        }
        return true;
    }


    public static boolean isNetworkConnected(Context context, boolean showToast) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (cm.getActiveNetworkInfo() == null) {
            if (showToast)
                Helper.showToast(context, "Check your phone's internet connection and try again");
            return false;
        }
        return true;
    }

    public static boolean isNetworkConnected(Context context, String message) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (cm.getActiveNetworkInfo() == null) {
            showToast(context, message);
            return false;
        }
        return true;
    }


    public static Bitmap byteArrayToBitmap(byte[] byteArray) {
        return BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
    }

    public static void setCallMode(boolean onCall, Context context) {
        AudioManager audioManager = ((AudioManager) context.getSystemService(Context.AUDIO_SERVICE));
        audioManager.setMode(onCall ? AudioManager.MODE_IN_CALL : AudioManager.MODE_NORMAL);
        audioManager.setSpeakerphoneOn(!onCall);
    }

    public static void loudSpeaker(boolean enable, Context context) {
        AudioManager audioManager = ((AudioManager) context.getSystemService(Context.AUDIO_SERVICE));
        audioManager.setSpeakerphoneOn(enable);
    }

    public static void microphone(boolean mute, Context context) {
        AudioManager audioManager = ((AudioManager) context.getSystemService(Context.AUDIO_SERVICE));
        audioManager.setMicrophoneMute(mute);
    }


    public static void inviteFriends(Context context, String to, String message) {
        Uri sms_uri = Uri.parse("smsto:+" + to);
        Intent share = new Intent(Intent.ACTION_SENDTO, sms_uri);
        share.putExtra("sms_body", message);
        share.putExtra(Intent.EXTRA_EMAIL, new String[]{to});
        context.startActivity(share);
    }

    public static byte[] encodeFileInBase64(String filePath) {
        Bitmap bm = BitmapFactory.decodeFile(filePath);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        return baos.toByteArray();
    }

    public static void openContactCard(Activity activity, String contactID) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
//        Uri uri = Uri.withAppendedPath(ContactsContract.Contacts.CONTENT_URI, String.valueOf(contactID));
        Uri uri = Uri.withAppendedPath(ContactsContract.Contacts.CONTENT_URI, String.valueOf(contactID));
        intent.setData(uri);
        activity.startActivity(intent);
    }


    public static boolean StringComparator(String s, String s1) {
        return s.equals(s1);
    }

    public static void showRingtonePicker(Activity activity, String title, int requestCode, String ringtone, int ringtoneType, Uri defaultUri) {
        Intent intent = new Intent(RingtoneManager.ACTION_RINGTONE_PICKER);
        intent.putExtra(RingtoneManager.EXTRA_RINGTONE_TITLE, title);
        intent.putExtra(RingtoneManager.EXTRA_RINGTONE_TYPE,
                ringtoneType);

        if (ringtone != null) {
            if (ringtone.isEmpty())
                intent.putExtra(RingtoneManager.EXTRA_RINGTONE_DEFAULT_URI,
                        defaultUri);
//            intent.putExtra(RingtoneManager.EXTRA_RINGTONE_DEFAULT_URI,
//                        RingtoneManager
//                                .getDefaultUri(RingtoneManager.TYPE_NOTIFICATION));
            else {
                RingtoneManager
                        .getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
                intent.putExtra(RingtoneManager.EXTRA_RINGTONE_EXISTING_URI, Uri.parse(ringtone));
                intent.putExtra(RingtoneManager.EXTRA_RINGTONE_DEFAULT_URI,
                        defaultUri);
//                intent.putExtra(RingtoneManager.EXTRA_RINGTONE_DEFAULT_URI,
//                        RingtoneManager
//                                .getDefaultUri(RingtoneManager.TYPE_NOTIFICATION));
            }
        } else {
            intent.putExtra(RingtoneManager.EXTRA_RINGTONE_DEFAULT_URI,
                    defaultUri);
//            RingtoneManager
//                            .getDefaultUri(RingtoneManager.TYPE_NOTIFICATION));
        }

        intent.putExtra(RingtoneManager.EXTRA_RINGTONE_SHOW_DEFAULT, true);

        activity.startActivityForResult(intent, requestCode);
    }

    public static void showRingtonePicker(Fragment fragment, String title,
                                          int requestCode, String ringtone, int ringtoneType, Uri defaultUri) {
        Intent intent = new Intent(RingtoneManager.ACTION_RINGTONE_PICKER);
        intent.putExtra(RingtoneManager.EXTRA_RINGTONE_TITLE, title);
        intent.putExtra(RingtoneManager.EXTRA_RINGTONE_TYPE,
                ringtoneType);

        if (ringtone != null) {
            if (ringtone.isEmpty())
                intent.putExtra(RingtoneManager.EXTRA_RINGTONE_DEFAULT_URI,
                        defaultUri);
            else {
                intent.putExtra(RingtoneManager.EXTRA_RINGTONE_EXISTING_URI, Uri.parse(ringtone));
                intent.putExtra(RingtoneManager.EXTRA_RINGTONE_DEFAULT_URI,
                        defaultUri);
            }
        } else {
            intent.putExtra(RingtoneManager.EXTRA_RINGTONE_DEFAULT_URI,
                    defaultUri);
        }

        intent.putExtra(RingtoneManager.EXTRA_RINGTONE_SHOW_DEFAULT, true);

        fragment.startActivityForResult(intent, requestCode);
    }


    public static void createShortCut(Context mContext, String shortcutname) {
        Intent shortcutintent = new Intent("com.android.launcher.action.INSTALL_SHORTCUT");
        shortcutintent.putExtra("duplicate", false);
        shortcutintent.putExtra(Intent.EXTRA_SHORTCUT_NAME, shortcutname);
        Parcelable icon = Intent.ShortcutIconResource.fromContext(mContext, R.mipmap.ic_launcher);
        shortcutintent.putExtra(Intent.EXTRA_SHORTCUT_ICON_RESOURCE, icon);
        shortcutintent.putExtra(Intent.EXTRA_SHORTCUT_INTENT, new Intent("edu.aku.family_hifazat"));
        mContext.sendBroadcast(shortcutintent);
    }

    public static void openViewIntent(Context context, File file, String mimeType) {
        Intent newIntent = new Intent(Intent.ACTION_VIEW);
        newIntent.setDataAndType(Uri.fromFile(file), mimeType);
        newIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        try {
            context.startActivity(newIntent);
        } catch (ActivityNotFoundException e) {
            Toast.makeText(context, "No handler for this type of file.", Toast.LENGTH_LONG).show();
        }
    }

    public static void addShortcut(Context mContext, String shortcutname, int chatId, Class<?> cls) {
        //Adding shortcut for MainActivity
        //on Home screen
//        Intent shortcutIntent = new Intent(mContext.getApplicationContext(), cls);
//
//
//        shortcutIntent.setAction(Intent.ACTION_MAIN);
//        shortcutIntent.putExtra(KEY_CHATMODEL_ID, chatId);
//        Intent addIntent = new Intent();
//
//        addIntent.putExtra(Intent.EXTRA_SHORTCUT_INTENT, shortcutIntent);
//        addIntent.putExtra(Intent.EXTRA_SHORTCUT_NAME, shortcutname);
//        addIntent.putExtra(Intent.EXTRA_SHORTCUT_ICON_RESOURCE,
//                Intent.ShortcutIconResource.fromContext(mContext,
//                        R.mipmap.ic_launcher));
//        addIntent.putExtra("duplicate", false);
//        addIntent.setAction("com.android.launcher.action.INSTALL_SHORTCUT");
//        mContext.sendBroadcast(addIntent);
    }

    public static JSONObject getJsonObject(String[] keys, Object[] values) {
        JSONObject jsonObject = new JSONObject();
        for (int i = 0; i < keys.length; i++) {
            try {
                jsonObject.put(keys[i], values[i]);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return jsonObject;
    }

    public static JSONObject getJsonObject(String key, Object value) {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put(key, value);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject;
    }


    public static String serializeRealmObject(RealmList<? extends RealmModel> realmList) {
        Gson gson = new Gson();
        return gson.toJson(realmList);
    }

    public static String getDuration(File file) {
        if (!file.exists())
            return "Voice";

        try {
            MediaMetadataRetriever mediaMetadataRetriever = new MediaMetadataRetriever();
            mediaMetadataRetriever.setDataSource(file.getAbsolutePath());
            String durationStr = mediaMetadataRetriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_DURATION);
            return formateMilliSeccond(Long.parseLong(durationStr));
        } catch (Exception e) {
        }

        return formateMilliSeccond(0);
    }

    public static long getDurationLong(File file) {
        if (!file.exists())
            return 0;

        MediaMetadataRetriever mediaMetadataRetriever = new MediaMetadataRetriever();
        try {
            mediaMetadataRetriever.setDataSource(file.getAbsolutePath());
            String durationStr = mediaMetadataRetriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_DURATION);
            return Long.parseLong(durationStr);
        } catch (Exception e) {
            //java.lang.RuntimeException: setDataSource failed: status = 0xFFFFFFED
            return 0;
        }

    }


    private static String formateMilliSeccond(long milliseconds) {
        String finalTimerString = "";
        String secondsString = "";

        // Convert total duration into time
        int hours = (int) (milliseconds / (1000 * 60 * 60));
        int minutes = (int) (milliseconds % (1000 * 60 * 60)) / (1000 * 60);
        int seconds = (int) ((milliseconds % (1000 * 60 * 60)) % (1000 * 60) / 1000);

        // Add hours if there
        if (hours > 0) {
            finalTimerString = hours + ":";
        }

        // Prepending 0 to seconds if it is one digit
        if (seconds < 10) {
            secondsString = "0" + seconds;
        } else {
            secondsString = "" + seconds;
        }

        finalTimerString = finalTimerString + minutes + ":" + secondsString;

        return finalTimerString;
    }

    public static int getStatusBarHeight(Window window, Context context) {
        Rect rectangle = new Rect();
        window.getDecorView().getWindowVisibleDisplayFrame(rectangle);
        int statusBarHeight = rectangle.top;
        int contentViewTop =
                window.findViewById(Window.ID_ANDROID_CONTENT).getTop();
        int titleBarHeight = contentViewTop - statusBarHeight;

        Log.i("*** Elenasys :: ", "StatusBar Height= " + statusBarHeight + " , TitleBar Height = " + titleBarHeight);
        if (titleBarHeight <= 0)
            return getStatusBarHeightOlder(context);

        return titleBarHeight;

    }

    public static int getStatusBarHeightOlder(Context context) {
        int result = 0;
        int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = context.getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }


    public static void setTranslucentStatusBar(Window window) {
        if (window == null) return;
        int sdkInt = Build.VERSION.SDK_INT;
        if (sdkInt >= Build.VERSION_CODES.LOLLIPOP) {
            setTranslucentStatusBarLollipop(window);
        } else if (sdkInt >= Build.VERSION_CODES.KITKAT) {
            setTranslucentStatusBarKiKat(window);
        }
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private static void setTranslucentStatusBarLollipop(Window window) {
        window.setStatusBarColor(
                window.getContext()
                        .getResources()
                        .getColor(R.color.colorPrimary));
    }

    @TargetApi(Build.VERSION_CODES.KITKAT)
    private static void setTranslucentStatusBarKiKat(Window window) {
        window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
    }

    public static void shareText(Context context, String text) {
        Intent sharingIntent = new Intent(Intent.ACTION_SEND);
        sharingIntent.setType("text/plain");
        sharingIntent.putExtra(Intent.EXTRA_TEXT, text);
        context.startActivity(Intent.createChooser(sharingIntent, "Share using"));
    }

    public static boolean validEmail(String email) {
        Pattern pattern = Patterns.EMAIL_ADDRESS;
        return pattern.matcher(email).matches();
    }

    public static void openPlayStore(Activity activity) {
        final String appPackageName = activity.getPackageName(); // getPackageName() from Context or Activity object
        try {
            activity.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + appPackageName)));
        } catch (android.content.ActivityNotFoundException anfe) {
            activity.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + appPackageName)));
        }
    }


    public static void addTextView(Context context, LinearLayout linearLayout, String text, int size, int color) {

        TextView textView = new TextView(context);
        LayoutParams layoutParams = new LayoutParams(LayoutParams.WRAP_CONTENT,
                LayoutParams.WRAP_CONTENT);
//        layoutParams.gravity = Gravity.RIGHT;
//        layoutParams.setMargins(10, 10, 10, 10); // (left, top, right, bottom)
        textView.setLayoutParams(layoutParams);
        textView.setText(text);
        textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, size);
//        textView.setBackgroundColor(color); // hex color 0xAARRGGBB
        textView.setTextColor(color);
        linearLayout.addView(textView);
    }
}
