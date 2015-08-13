package com.group2.swinghelper;

import android.app.Activity;
import android.os.Bundle;

/**
 * Created by Stefano on 8/11/15.
 */
public class SettingsActivity extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Display the fragment as the main content.
        getFragmentManager().beginTransaction()
                .replace(android.R.id.content, new SettingsFragment())
                .commit();


    }
}