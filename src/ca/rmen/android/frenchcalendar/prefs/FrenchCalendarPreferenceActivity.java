/*
 * French Revolutionary Calendar Android Widget
 * Copyright (C) 2011 - 2014 Carmen Alvarez
 * 
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
 */
package ca.rmen.android.frenchcalendar.prefs;

import android.appwidget.AppWidgetManager;
import android.content.Intent;
import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.util.Log;
import ca.rmen.android.frenchcalendar.FrenchCalendarScheduler;
import ca.rmen.android.frenchcalendar.R;

/**
 * Configuration screen. The settings in this screen will apply to all widgets: both narrow and wide.
 * 
 * @author calvarez
 */
public class FrenchCalendarPreferenceActivity extends PreferenceActivity { // NO_UCD (use default)

    private static final String TAG = FrenchCalendarPreferenceActivity.class.getSimpleName();

    @SuppressWarnings("deprecation")
    @Override
    protected void onCreate(Bundle icicle) {
        Log.v(TAG, "onCreate: bundle = " + icicle);
        super.onCreate(icicle);
        addPreferencesFromResource(R.xml.widget_settings);
        /*
         * From the documentation: https://developer.android.com/guide/topics/appwidgets/index.html
         * The App Widget host calls the configuration Activity and the configuration
         * Activity should always return a result. The result should include the App Widget ID
         * passed by the Intent that launched the Activity (saved in the Intent extras as EXTRA_APPWIDGET_ID).
         */
        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        int appWidgetId = AppWidgetManager.INVALID_APPWIDGET_ID;
        if (extras != null) appWidgetId = extras.getInt(AppWidgetManager.EXTRA_APPWIDGET_ID, AppWidgetManager.INVALID_APPWIDGET_ID);
        Intent resultValue = new Intent();
        if (appWidgetId > -1) resultValue.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId);
        setResult(RESULT_OK, resultValue);
    }

    @Override
    protected void onDestroy() {
        Log.v(TAG, "onDestroy");
        super.onDestroy();
        // When we leave the preference screen, reupdate all our widgets
        FrenchCalendarScheduler.getInstance(this).schedule();
    }
}
