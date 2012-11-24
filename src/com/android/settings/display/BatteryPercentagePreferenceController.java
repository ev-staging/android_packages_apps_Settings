/*
 * Copyright (C) 2016 The Android Open Source Project
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
package com.android.settings.display;

import android.content.Context;
import android.provider.Settings;
import android.support.v7.preference.ListPreference;
import android.support.v7.preference.Preference;

import com.android.settings.R;
import com.android.settings.core.PreferenceControllerMixin;
import com.android.settingslib.core.AbstractPreferenceController;

import static android.provider.Settings.System.SHOW_BATTERY_PERCENT;

/**
 * A controller to manage the switch for showing battery percentage in the status bar.
 */

public class BatteryPercentagePreferenceController extends AbstractPreferenceController implements
        PreferenceControllerMixin, Preference.OnPreferenceChangeListener {

    private static final String KEY_BATTERY_PERCENTAGE = "battery_percentage";

    public BatteryPercentagePreferenceController(Context context) {
        super(context);
    }

    @Override
    public boolean isAvailable() {
        return true;
    }

    @Override
    public String getPreferenceKey() {
        return KEY_BATTERY_PERCENTAGE;
    }

    @Override
    public void updateState(Preference preference) {
        int setting = Settings.System.getInt(mContext.getContentResolver(),
                SHOW_BATTERY_PERCENT, 0);
        ((ListPreference) preference).setValue(Integer.toString(setting));
        updateSummary(preference, setting);
    }

    private void updateSummary(Preference preference, int value) {
        int summary = R.string.battery_percentage_disabled;
        if (value == 1) {
            summary = R.string.battery_percentage_outside_summary;
        } else if (value == 2) {
            summary = R.string.battery_percentage_inside_summary;
        }
        ((ListPreference) preference).setSummary(summary);
    }

    @Override
    public boolean onPreferenceChange(Preference preference, Object newValue) {
        Settings.System.putInt(mContext.getContentResolver(), SHOW_BATTERY_PERCENT,
                Integer.valueOf((String) newValue));
        updateSummary(preference, Integer.valueOf((String) newValue));
        return true;
    }
}
