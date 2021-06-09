/*
 * Copyright (C) 2021 The LineageOS Project
 *
 * SPDX-License-Identifier: Apache-2.0
 */

package org.lineageos.keyhandler;

import android.content.Context;
import android.content.Intent;
import android.os.UserHandle;
import android.util.Log;
import android.view.KeyEvent;
import android.os.SystemProperties;
import android.provider.Settings;
import android.content.ContentResolver;
import android.os.SystemClock;
import android.os.PowerManager;
import android.os.PowerManager.WakeLock;

import com.android.internal.os.DeviceKeyHandler;

public class KeyHandler implements DeviceKeyHandler {
    private static final boolean DEBUG = true;
    private static final String TAG = "KeyHandler";
    private static final String DOZE_INTENT = "com.android.systemui.doze.pulse";
    private static final int KEYCODE_FOD = 338;
    private static final String SOF_STATE = "sof_state";
    private static Context mContext;
    private static final int WAKELOCK_TIMEOUT_MS = 300;
    private PowerManager mPowerManager;
    private WakeLock mWakeLock;

    public KeyHandler(Context context) {
        mContext = context;
        mPowerManager = (PowerManager) mContext.getSystemService(Context.POWER_SERVICE);
        mWakeLock = mPowerManager.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, TAG);
        if (DEBUG)
            Log.i(TAG, "KeyHandler constructor called");
    }

    public boolean isSOFOn() {
        return Settings.System.getIntForUser(mContext.getContentResolver(),
                SOF_STATE, 0,
                UserHandle.USER_CURRENT) == 1;
    }

    public KeyEvent handleKeyEvent(KeyEvent event) {
        int scanCode = event.getScanCode();
        boolean sofEnable = isSOFOn();
        if (scanCode != KEYCODE_FOD) return event;
        int action = event.getAction();
        if(action == KeyEvent.ACTION_UP) return event;

        if (DEBUG)
            Log.d(TAG, "Got FOD BTN_INFO event");
        if (sofEnable) {
            mWakeLock.acquire(WAKELOCK_TIMEOUT_MS);
            mPowerManager.wakeUp(SystemClock.uptimeMillis(), PowerManager.WAKE_REASON_GESTURE,
                            "android.policy:BIOMETRIC");
        }
        return null;
    }
}
