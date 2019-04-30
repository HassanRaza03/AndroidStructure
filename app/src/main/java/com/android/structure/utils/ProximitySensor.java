package com.android.structure.utils;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Build;
import android.os.PowerManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

/**
 * Created by muhammadhumzakhan on 10/24/2017.
 */

public class ProximitySensor implements SensorEventListener {

    public interface Delegate {
        void onProximitySensorNear();

        void onProximitySensorFar();
    }

    private final SensorManager mSensorManager;
    private Sensor mSensor;
    private PowerManager.WakeLock mScreenLock;
    private final Delegate mDelegate;

    public ProximitySensor(final AppCompatActivity context, final Delegate delegate) {
        if (context == null || delegate == null)
            throw new IllegalArgumentException("You must pass a non-null context and delegate");

        final Context appContext = context.getApplicationContext();
        mSensorManager = (SensorManager) appContext.getSystemService(Context.SENSOR_SERVICE);
        mDelegate = delegate;

        mSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY);

        if (mSensor == null) {
            StringBuilder sensorList = new StringBuilder();
            for (Sensor sensor : mSensorManager.getSensorList(Sensor.TYPE_ALL)) {
                sensorList.append(sensor.getName()).append(",");
            }

            throw new UnsupportedOperationException("Proximity sensor is not supported on this device! Sensors available: " + sensorList);
        }

        PowerManager powerManager = (PowerManager) appContext.getSystemService(Context.POWER_SERVICE);

        int screenLockValue;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            screenLockValue = PowerManager.PROXIMITY_SCREEN_OFF_WAKE_LOCK;
        } else {
            try {
                screenLockValue = PowerManager.class.getField("PROXIMITY_SCREEN_OFF_WAKE_LOCK").getInt(null);
            } catch (Exception exc) {
                screenLockValue = 32; // default integer value of PROXIMITY_SCREEN_OFF_WAKE_LOCK
            }
        }

        mScreenLock = powerManager.newWakeLock(screenLockValue, getClass().getSimpleName());
        if (!mScreenLock.isHeld()) {
            mScreenLock.acquire();
        }
    }

    public void onActivityResumed() {
        if (mSensorManager != null && mSensor != null) {
            mSensorManager.registerListener(this, mSensor, SensorManager.SENSOR_DELAY_UI);
        }
    }

    public void onActivityPaused() {
        if (mSensorManager != null && mSensor != null) {
            mSensorManager.unregisterListener(this);
        }
    }

    public void onActivityDestroyed() {
        if (mScreenLock.isHeld()) {
            mScreenLock.release();
        }
    }

    @Override
    public void onAccuracyChanged(final Sensor arg0, final int arg1) {
    }

    @Override
    public void onSensorChanged(final SensorEvent event) {
        try {
            if (event.values != null && event.values.length > 0) {
                if (event.values[0] < mSensor.getMaximumRange()) {
                    mDelegate.onProximitySensorNear();
                } else {
                    mDelegate.onProximitySensorFar();
                }
            }
        } catch (final Exception exc) {
            Log.e(getClass().getSimpleName(), "onSensorChanged exception", exc);
        }
    }

}