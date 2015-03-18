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

import evervolv.provider.EVSettings;

import com.android.settings.R;
import com.android.settings.core.PreferenceControllerMixin;
import com.android.settingslib.core.AbstractPreferenceController;
import com.android.settingslib.graph.BatteryMeterDrawableBase;

import static evervolv.provider.EVSettings.System.STATUS_BAR_BATTERY_STYLE;

/**
 * A controller to manage the switch for showing battery percentage in the status bar.
 */

public class BatteryMeterStylePreferenceController extends AbstractPreferenceController implements
        PreferenceControllerMixin, Preference.OnPreferenceChangeListener {

    private static final String KEY_BATTERY_PERCENTAGE = "battery_meter_style";

    public BatteryMeterStylePreferenceController(Context context) {
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
        int setting = EVSettings.System.getInt(mContext.getContentResolver(),
                STATUS_BAR_BATTERY_STYLE, 0);
        ((ListPreference) preference).setValue(Integer.toString(setting));
        updateSummary(preference, setting);
    }

    private void updateSummary(Preference preference, int value) {
        final int summary;
        switch (value) {
            case BatteryMeterDrawableBase.BATTERY_STYLE_CIRCLE:
                summary = R.string.battery_meter_style_circle;
                break;
            case BatteryMeterDrawableBase.BATTERY_STYLE_DOTTED_CIRCLE:
                summary = R.string.battery_meter_style_dotted_circle;
                break;
            case BatteryMeterDrawableBase.BATTERY_STYLE_HIDDEN:
                summary = R.string.battery_meter_style_hidden;
                break;
            case BatteryMeterDrawableBase.BATTERY_STYLE_TEXT:
                summary = R.string.battery_meter_style_text;
                break;
            case BatteryMeterDrawableBase.BATTERY_STYLE_PORTRAIT:
            default:
                summary = R.string.battery_meter_style_portrait;
                break;
        }

        ((ListPreference) preference).setSummary(summary);
    }

    @Override
    public boolean onPreferenceChange(Preference preference, Object newValue) {
        EVSettings.System.putInt(mContext.getContentResolver(), STATUS_BAR_BATTERY_STYLE,
                Integer.valueOf((String) newValue));
        updateSummary(preference, Integer.valueOf((String) newValue));
        return true;
    }
}
