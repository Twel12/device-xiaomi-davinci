/*
 * Copyright (C) 2018 The LineageOS Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.lineageos.keyhandler;

import android.content.Context;
import android.os.Bundle;
import android.view.MenuItem;
import androidx.preference.Preference;
import androidx.preference.Preference.OnPreferenceChangeListener;
import androidx.preference.PreferenceFragment;
import androidx.preference.SwitchPreference;
import android.os.SystemProperties;
import android.provider.Settings;
import android.os.UserHandle;

import org.lineageos.keyhandler.R;

public class KeyHandlerSettingsFragment extends PreferenceFragment implements
        OnPreferenceChangeListener {

    private SwitchPreference mSwitch;
    private static final String SOF_ENABLE_KEY = "sof_enable";
    private static final String SOF_STATE = "sof_state";

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        addPreferencesFromResource(R.xml.keyhandler_settings);
        getActivity().getActionBar().setDisplayHomeAsUpEnabled(true);
        mSwitch = (SwitchPreference) findPreference(SOF_ENABLE_KEY);
        mSwitch.setEnabled(true);
        mSwitch.setOnPreferenceChangeListener(this);
    }

    @Override
    public boolean onPreferenceChange(Preference preference, Object newValue) {
        if (preference instanceof SwitchPreference) {
            Settings.System.putIntForUser(getActivity().getContentResolver(),
                    SOF_STATE, !mSwitch.isChecked() ? 1 : 0, UserHandle.USER_CURRENT);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            getActivity().onBackPressed();
            return true;
        }
        return false;
    }

}