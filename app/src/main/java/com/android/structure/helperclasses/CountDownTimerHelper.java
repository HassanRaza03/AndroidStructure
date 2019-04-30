package com.android.structure.helperclasses;

import android.os.CountDownTimer;

/**
 * Created by khanhamza on 01-Jun-17.
 */

public class CountDownTimerHelper {


    /**
     * Count down timer values in seconds
     *
     * @param timeInSeconds         total timer time in seconds
     * @param timeIntervalInSeconds per interval of time
     * @param onCountDownTimer      OnCountDown Timer Listener with two methods.
     */

    public static CountDownTimer runCountDownTime(int timeInSeconds, int timeIntervalInSeconds, final OnCountDownTimer onCountDownTimer) {
        CountDownTimer countDownTimer = new CountDownTimer(timeInSeconds * 1000, timeIntervalInSeconds * 1000) {

            public void onTick(long millisUntilFinished) {
                onCountDownTimer.onTick((int) (millisUntilFinished / 1000));
            }

            public void onFinish() {
                onCountDownTimer.onFinish();
            }

        }.start();

        return countDownTimer;
    }

    public interface OnCountDownTimer {
        void onTick(int time);

        void onFinish();
    }

}
