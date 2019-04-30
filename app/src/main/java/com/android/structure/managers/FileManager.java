package com.android.structure.managers;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.net.Uri;
import android.util.Base64;
import android.webkit.MimeTypeMap;
import android.widget.Toast;


import com.android.structure.constatnts.AppConstants;
import com.android.structure.helperclasses.ui.helper.UIHelper;
import com.android.structure.utils.LogUtil;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.channels.FileChannel;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;


/**
 * Created by muhammadmuzammil on 4/26/2017.
 */

public class FileManager {

    private static void createDirectory(String directory) {
        /*First check if root directory not created then create*/
        File rootDirectory = new File(AppConstants.ROOT_PATH);
        if (!rootDirectory.exists())
            rootDirectory.mkdirs();

        File innerDirectory = new File(directory);
        if (!innerDirectory.exists())
            innerDirectory.mkdir();

    }
//
//    /**
//     * Avatar is the location on which it saved picture
//     *
//     * @param avatar
//     * @param thumbnail
//     * @return
//     */
//    public static File createProfileImage(String avatar, boolean thumbnail, Context context) {
//        if (avatar == null || avatar.equals(""))
//            return null;
//
//        try {
//            avatar = avatar.substring(0, avatar.lastIndexOf(".")) + ".j";
//        } catch (Exception e) {
////            e.printStackTrace();
//            return null;
//        }
//        ContextWrapper cw = new ContextWrapper(context);
//        File directory;
//
//        if (thumbnail)
//            directory = cw.getDir("userProfile", Context.MODE_PRIVATE);
//        else
//            directory = cw.getCacheDir();
//
//        if (!directory.exists()) {
//            directory.mkdir();
//        }
//
//        String filename = URLUtil.guessFileName(avatar, null, null);
//        File imageFile = new File(directory, filename);
//        if (!imageFile.exists())
//            try {
//                imageFile.createNewFile();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        return imageFile;
//    }
//
//    public static File getUserImage(String avatar, boolean thumbnail, Context context) {
//        if (avatar == null || avatar.equals(""))
//            return null;
//
//        // FIXME: 8/18/2017  :  REMOVE NULL POINTER EXCEPTION // CONVERT TO JPG
//        try {
//            avatar = avatar.substring(0, avatar.lastIndexOf(".")) + ".j";
//        } catch (Exception e) {
//            e.printStackTrace();
//            return null;
//        }
//        ContextWrapper cw = new ContextWrapper(context);
//        File directory;
//
//        if (thumbnail) {
//            directory = cw.getDir(AppConstants.USER_PROFILE_PICTURE_FOLDER_DIRECTORY, Context.MODE_PRIVATE);
//        } else {
//            directory = cw.getCacheDir();
//            avatar = avatar.replace(AppConstants.SUFFIX_THUMB_IMAGE, "");
//        }
//
//        if (!directory.exists()) {
//            return null;
//        }
//        String filename = URLUtil.guessFileName(avatar, null, null);
//        File imageFile = new File(directory, filename);
//        if (!imageFile.exists())
//            return null;
//        return imageFile;
//    }
//
//    public static File getMyImage(boolean isThumbnail, Context context) {
//        ContextWrapper cw = new ContextWrapper(context);
//        if (isThumbnail)
//            return new File(cw.getDir(AppConstants.USER_PROFILE_PICTURE_FOLDER_DIRECTORY, Context.MODE_PRIVATE), AppConstants.USER_PROFILE_THUMBNAIL_NAME);
//        else
//            return new File(cw.getDir(AppConstants.USER_PROFILE_PICTURE_F OLDER_DIRECTORY, Context.MODE_PRIVATE), AppConstants.USER_PROFILE_PICTURE_NAME);
//    }


    //    public static File getUserImage(String avatar, boolean thumbnail, Context context) {
//        if (avatar == null || avatar.equals(""))
//            return null;
//
//        // FIXME: 8/18/2017  :  REMOVE NULL POINTER EXCEPTION // CONVERT TO JPG
//        try {
//            avatar = avatar.substring(0, avatar.lastIndexOf(".")) + ".j";
//        } catch (Exception e) {
//            e.printStackTrace();
//            return null;
//        }
//        ContextWrapper cw = new ContextWrapper(context);
//        File directory;
//
//        if (thumbnail) {
//            directory = cw.getDir(AppConstants.USER_PROFILE_PICTURE_FOLDER_DIRECTORY, Context.MODE_PRIVATE);
//        } else {
//            directory = cw.getCacheDir();
//            avatar = avatar.replace(AppConstants.SUFFIX_THUMB_IMAGE, "");
//        }
//
//        if (!directory.exists()) {
//            return null;
//        }
//        String filename = URLUtil.guessFileName(avatar, null, null);
//        File imageFile = new File(directory, filename);
//        if (!imageFile.exists())
//            return null;
//        return imageFile;
//    }

    public static String writeResponseBodyToDisk(Context context, String body, String fileName, String directoryPath, boolean isSaveInCache, boolean isSaveInExternalCache) {
        File file;
        if (isSaveInCache) {
            ContextWrapper cw = new ContextWrapper(context);
            File directory;
            if (isSaveInExternalCache) {
                directory = cw.getExternalCacheDir();
            } else {
                directory = cw.getCacheDir();
            }
            if (!directory.exists()) {
                directory.mkdir();
            }

            file = new File(directory, fileName);

        } else {
            createDirectory(directoryPath);
            file = new File(directoryPath
                    + "/" + fileName);
        }


        byte[] pdfAsBytes = Base64.decode(body, 0);
        FileOutputStream os;
        try {
            os = new FileOutputStream(file, false);
            os.write(pdfAsBytes);
            os.flush();
            os.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return file.getAbsolutePath();
    }

    public static boolean isFileExits(String path) {
        return new File(path).exists();
    }

    public static void copyFile(String sourcePath, File destFile) {
        try {
            File sourceFile = new File(sourcePath);
//        File destFile = new File(destPath);

            if (!sourceFile.exists()) {
                return;
            }

            FileChannel source = null;
            FileChannel destination = null;

            source = new FileInputStream(sourceFile).getChannel();

            destination = new FileOutputStream(destFile).getChannel();
            if (source != null) {
                destination.transferFrom(source, 0, source.size());
            }
            if (source != null) {
                source.close();
            }
            destination.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public static File copyImage(String sourcePath, String targetPath) {
        try {
            InputStream in = new FileInputStream(sourcePath);
            OutputStream out = new FileOutputStream(targetPath);
            byte[] buf = new byte[1024];
            int len;
            while ((len = in.read(buf)) > 0) {
                out.write(buf, 0, len);
            }
            in.close();
            out.close();
            return new File(targetPath);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String getFileNameFromPath(String path) {
        int index = path.lastIndexOf("/");
        if (index > path.length())
            return "Unknown File";

        return path.substring(index + 1);
    }

    public static void copyFile(String inputPath, String outputPath) {
        InputStream in = null;
        OutputStream out = null;
        try {

            //create output directory if it doesn't exist
            File dir = new File(outputPath);
            if (!dir.exists()) {
                dir.mkdirs();
            }


            in = new FileInputStream(inputPath);
            out = new FileOutputStream(outputPath);

            byte[] buffer = new byte[1024];
            int read;
            while ((read = in.read(buffer)) != -1) {
                out.write(buffer, 0, read);
            }
            in.close();
            in = null;

            // write the output file (You have now copied the file)
            out.flush();
            out.close();
            out = null;

        } catch (FileNotFoundException fnfe1) {
            LogUtil.e("tag", fnfe1.getMessage());
        } catch (Exception e) {
            LogUtil.e("tag", e.getMessage());
        }

    }


    public static String getFileSize(String path) {
        File file = new File(path);
        if (!file.exists())
            return "0 KB";

        return formatFileSize(file.length());
    }


    public static String formatFileSize(long size) {
        String hrSize = null;

        double b = size;
        double k = size / 1024.0;
        double m = ((size / 1024.0) / 1024.0);
        double g = (((size / 1024.0) / 1024.0) / 1024.0);
        double t = ((((size / 1024.0) / 1024.0) / 1024.0) / 1024.0);

        DecimalFormat dec = new DecimalFormat("0.0");

        if (t > 1) {
            hrSize = dec.format(t).concat(" TB");
        } else if (g > 1) {
            hrSize = dec.format(g).concat(" GB");
        } else if (m > 1) {
            hrSize = dec.format(m).concat(" MB");
        } else if (k > 1) {
            hrSize = dec.format(k).concat(" KB");
        } else {
            hrSize = dec.format(b).concat(" Bytes");
        }

        return hrSize;
    }


    public static String getExtension(String fileName) {
        String encoded;
        try {
            encoded = URLEncoder.encode(fileName, "UTF-8").replace("+", "%20");
        } catch (UnsupportedEncodingException e) {
            encoded = fileName;
        }

        return MimeTypeMap.getFileExtensionFromUrl(encoded).toLowerCase();
    }

    public static File createFileInAppDirectory(Context context, String folderName, String fileName) {
        ContextWrapper cw = new ContextWrapper(context);
        File directory = cw.getDir(folderName, Context.MODE_PRIVATE);
        return new File(directory, fileName);
    }


    public static void openFile(Context context, File url) {
        if (url == null) {
            UIHelper.showToast(context, "File is null");
            return;
        }

        try {

            Uri uri = Uri.fromFile(url);

            Intent intent = new Intent(Intent.ACTION_VIEW);
            if (url.toString().contains(".doc") || url.toString().contains(".docx")) {
                // Word document
                intent.setDataAndType(uri, "application/msword");
            } else if (url.toString().contains(".pdf")) {
                // PDF file
                intent.setDataAndType(uri, "application/pdf");
            } else if (url.toString().contains(".ppt") || url.toString().contains(".pptx")) {
                // Powerpoint file
                intent.setDataAndType(uri, "application/vnd.ms-powerpoint");
            } else if (url.toString().contains(".xls") || url.toString().contains(".xlsx")) {
                // Excel file
                intent.setDataAndType(uri, "application/vnd.ms-excel");
            } else if (url.toString().contains(".zip") || url.toString().contains(".rar")) {
                // WAV audio file
                intent.setDataAndType(uri, "application/x-wav");
            } else if (url.toString().contains(".rtf")) {
                // RTF file
                intent.setDataAndType(uri, "application/rtf");
            } else if (url.toString().contains(".wav") || url.toString().contains(".mp3")) {
                // WAV audio file
                intent.setDataAndType(uri, "audio/x-wav");
            } else if (url.toString().contains(".gif")) {
                // GIF file
                intent.setDataAndType(uri, "image/gif");
            } else if (url.toString().contains(".jpg") || url.toString().contains(".jpeg") || url.toString().contains(".png")) {
                // JPG file
                intent.setDataAndType(uri, "image/jpeg");
            } else if (url.toString().contains(".txt")) {
                // Text file
                intent.setDataAndType(uri, "text/plain");
            } else if (url.toString().contains(".3gp") || url.toString().contains(".mpg") ||
                    url.toString().contains(".mpeg") || url.toString().contains(".mpe") || url.toString().contains(".mp4") || url.toString().contains(".avi")) {
                // Video files
                intent.setDataAndType(uri, "video/*");
            } else {
                intent.setDataAndType(uri, "*/*");
            }

            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);
        } catch (ActivityNotFoundException e) {
            Toast.makeText(context, "No application found which can open the file", Toast.LENGTH_SHORT).show();
        }
    }

    public static ArrayList<File> getFiles(String DirectoryPath) {
        File f = new File(DirectoryPath);
        f.mkdirs();
        ArrayList<File> arrayList = new ArrayList<>();
        arrayList.addAll(Arrays.asList(f.listFiles()));
        return arrayList;
    }


    public static String getReplacedSlash(String Value) {

        return Value.replace("\\", "/");

    }
}