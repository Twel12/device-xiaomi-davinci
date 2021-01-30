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

import com.android.internal.os.DeviceKeyHandler;

public class KeyHandler implements DeviceKeyHandler {
    private static final boolean DEBUG = true;
    private static final String TAG = "KeyHandler";
    private static final String DOZE_INTENT = "com.android.systemui.doze.pulse";
    private static final int KEYCODE_FOD = 338;
    private static final String SOF_STATE = "sof_state";
    private static Context mContext;

    public KeyHandler(Context context) {
        mContext = context;
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
        if (scanCode != KEYCODE_FOD)
            return event;

        if (DEBUG)
            Log.d(TAG, "Got FOD BTN_INFO event");
        if (sofEnable) {
        mContext.sendBroadcastAsUser(
                new Intent(DOZE_INTENT), new UserHandle(UserHandle.USER_CURRENT));
        }
        return null;
    }
}
