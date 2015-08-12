package com.group2.swinghelper;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.EditTextPreference;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;

/**
 * Created by Stefano on 8/11/15.
 */
public class SettingsFragment extends PreferenceFragment implements SharedPreferences.OnSharedPreferenceChangeListener {

    private SharedPreferences prefs;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Load the preferences from an XML resource
        addPreferencesFromResource(R.xml.preferences);

        prefs = PreferenceManager.getDefaultSharedPreferences(getActivity());

        String new_summary = prefs.getString("name", "NULL");
        EditTextPreference etp = (EditTextPreference)findPreference("name");
        etp.setSummary(new_summary);

    }


    @Override
    public void onResume() {
        super.onResume();

        prefs.registerOnSharedPreferenceChangeListener(this);
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences preferences, String key) {
        if (key.equals("name")) {

            Preference pref = findPreference(key);
            EditTextPreference etp = (EditTextPreference) pref;
            pref.setSummary(etp.getText());

        }
    }


    @Override
    public void onPause() {
        prefs.unregisterOnSharedPreferenceChangeListener(this);

        super.onPause();
    }




}