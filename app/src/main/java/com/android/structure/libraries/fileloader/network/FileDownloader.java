package com.android.structure.libraries.fileloader.network;

import android.content.Context;
import android.support.annotation.WorkerThread;


import com.android.structure.BuildConfig;
import com.android.structure.libraries.fileloader.utility.AndroidFileManager;
import com.android.structure.managers.retrofit.WebServices;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import okio.BufferedSink;
import okio.Okio;

/**
 * Created by krishna on 12/10/17.
 */

public class FileDownloader {
    private String uri;
    private String dirName;
    private int dirType;
    private static OkHttpClient httpClient;
    private Context context;

    public FileDownloader(Context context, String uri, String dirName, int dirType) {
        this.context = context.getApplicationContext();
        this.uri = uri;
        this.dirName = dirName;
        this.dirType = dirType;
        initHttpClient();
    }

    private void initHttpClient() {
        if (httpClient == null) {
            HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
            if (BuildConfig.DEBUG)
                interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            else
                interceptor.setLevel(HttpLoggingInterceptor.Level.NONE);
            httpClient = new OkHttpClient.Builder()
                    .connectTimeout(10, TimeUnit.SECONDS)
                    .writeTimeout(10, TimeUnit.SECONDS)
                    .readTimeout(30, TimeUnit.SECONDS)
                    .addInterceptor(interceptor)
                    .build();
        }
    }

    @WorkerThread
    public File download() throws Exception {
        Request request = new Request.Builder()
                .addHeader("Authorization", "Bearer " + WebServices.getBearerToken())
                .addHeader("Requestor", "aku.edu")
                .addHeader("Accept", "image/jpeg")
                .url(uri).build();
        Response response = httpClient.newCall(request).execute();
        if (!response.isSuccessful() || response.body() == null) {
            throw new IOException("Failed to download file: " + response);
        }
        File downloadedFile = AndroidFileManager.getFileForRequest(context, uri, dirName, dirType);
        if (downloadedFile.exists()) {
            if (downloadedFile.delete())
                downloadedFile = AndroidFileManager.getFileForRequest(context, uri, dirName, dirType);
        }
        BufferedSink sink = Okio.buffer(Okio.sink(downloadedFile));
        sink.writeAll(response.body().source());
        sink.close();
        return downloadedFile;
    }
}
