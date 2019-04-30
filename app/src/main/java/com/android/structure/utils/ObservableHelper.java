package com.android.structure.utils;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by muhammadhumzakhan on 8/2/2017.
 */

public class ObservableHelper {

    public static Observable<Long> createIntervalObserver(int initialDelay, int period, TimeUnit timerUnit) {

        return Observable.interval(initialDelay, period, timerUnit, Schedulers.io())
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread());
    }

}
