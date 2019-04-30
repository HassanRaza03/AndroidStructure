package com.android.structure.utils;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

/**
 * Created by muhammadhumzakhan on 7/19/2017.
 */

public class AsyncHelper extends AsyncTask<String, Void, Void> {

    Runnable runnable;
    Context mContext;
    AysncCycle aysncCycle;
    boolean isSuccess;

    public AsyncHelper(Context mContext, Runnable runnable, AysncCycle aysncCycle) {
        this.mContext = mContext;
        this.runnable = runnable;
        this.aysncCycle = aysncCycle;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        showLoader();
    }

    @Override
    protected Void doInBackground(String... params) {
        runnable.run();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }

    public interface AysncCycle {
        void onPostExecute(boolean isSucces);
    }

    private ProgressDialog progressDialog;

    private void showLoader() {
        if (progressDialog == null) {
            progressDialog = new ProgressDialog(mContext);
        }

        progressDialog.setCancelable(false);
        progressDialog.setMessage("Loading please wait... ");
        progressDialog.setIndeterminate(true);
        progressDialog.show();
    }

    private void hideLoader() {
        if (progressDialog != null) {
            progressDialog.hide();
            progressDialog = null;
        }

    }

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            Bundle b = msg.getData();
            isSuccess = b.getBoolean("KEY");
            aysncCycle.onPostExecute(isSuccess);
            hideLoader();

        }
    };


    public Handler getHandler() {
        return handler;
    }
}
