package com.android.structure.utils;

import android.os.CountDownTimer;

/**
 * Created by muhammadhumzakhan on 8/3/2017.
 */

public abstract class Timer extends CountDownTimer {
    /**
     * @param millisInFuture    The number of millis in the future from the call
     * to {@link #start()} until the countdown is done and {@link #onFinish()}
     * is called.
     * @param countDownInterval The interval along the way to receive
     * {@link #onTick(long)} callbacks.
     */
    Timer timer;

    public Timer(long millisInFuture, long countDownInterval) {
        super(millisInFuture, countDownInterval);
    }


    @Override
    public void onFinish() {

    }
}
