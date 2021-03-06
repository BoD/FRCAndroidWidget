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
package ca.rmen.android.frcwidget.prefs;

import java.util.Locale;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import ca.rmen.lfrc.FrenchRevolutionaryCalendar.CalculationMethod;

/**
 * Preference names and values used by this app.
 * 
 * @author calvarez
 */
public class FRCPreferences {

    static final String PREF_METHOD = "setting_method";
    static final String PREF_DETAILED_VIEW = "setting_detailed_view";
    static final String PREF_LANGUAGE = "setting_language";
    private static final int FREQUENCY_MINUTES = 86400;
    public static final int FREQUENCY_DAYS = 86400000;

    private static FRCPreferences me = null;

    private final SharedPreferences sharedPrefs;

    public static enum DetailedView {
        NONE, TIME, DAY_OF_YEAR
    };

    public synchronized static FRCPreferences getInstance(Context context) {
        if (me == null) me = new FRCPreferences(context);
        return me;
    }

    private FRCPreferences(Context context) {
        sharedPrefs = PreferenceManager.getDefaultSharedPreferences(context);
    }

    public Locale getLocale() {
        String language = sharedPrefs.getString(PREF_LANGUAGE, "fr");
        return new Locale(language);
    }

    public CalculationMethod getCalculationMethod() {
        String methodPrefStr = sharedPrefs.getString(FRCPreferences.PREF_METHOD, "0");
        int calculationMethodInt = Integer.parseInt(methodPrefStr);
        CalculationMethod calculationMethod = CalculationMethod.values()[calculationMethodInt];
        return calculationMethod;
    }

    public int getUpdateFrequency() {
        DetailedView detailedView = getDetailedView();
        final int frequency;
        if (detailedView == DetailedView.TIME) frequency = FRCPreferences.FREQUENCY_MINUTES;
        else
            frequency = FRCPreferences.FREQUENCY_DAYS;
        return frequency;
    }

    public DetailedView getDetailedView() {
        String detailedViewValue = sharedPrefs.getString(FRCPreferences.PREF_DETAILED_VIEW, DetailedView.DAY_OF_YEAR.name().toLowerCase(Locale.US));
        return DetailedView.valueOf(detailedViewValue.toUpperCase(Locale.US));
    }
}
