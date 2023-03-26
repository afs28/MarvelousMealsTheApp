package is.hi.mm;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.preference.PreferenceActivity;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.preference.ListPreference;
import androidx.preference.PreferenceFragmentCompat;
import androidx.preference.PreferenceManager;

public class SettingsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Load the preference screen fragment
        getSupportFragmentManager()
                .beginTransaction()
                .replace(android.R.id.content, new SettingsFragment())
                .commit();
    }

    public static void setTheme(SharedPreferences sharedPreferences) {
        // Set the theme based on the saved preference or the system's night mode setting
        String themeValue = sharedPreferences.getString("theme", "system");
        if (themeValue.equals("dark")) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
            //setTheme(R.style.Theme_MarvelousMeals);
        } else if (themeValue.equals("light")) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
            //setTheme(R.style.Theme_MarvelousMeals);
        } else {
            // Follow the system's night mode setting
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM);
        }
    }


    public static class SettingsFragment extends PreferenceFragmentCompat implements SharedPreferences.OnSharedPreferenceChangeListener {

        @Override
        public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
            // Load the preferences from an XML resource
            setPreferencesFromResource(R.xml.preferences, rootKey);

            // Set the summary for the theme preference
            ListPreference themePreference = findPreference("theme");
            themePreference.setSummary(themePreference.getEntry());

            // Register the listener for preference changes
            getPreferenceScreen().getSharedPreferences().registerOnSharedPreferenceChangeListener(this);
        }

        @Override
        public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
            if (key.equals("theme")) {
                // Update the summary for the theme preference
                ListPreference themePreference = findPreference(key);
                themePreference.setSummary(themePreference.getEntry());


                // Recreate the activity to apply the new theme
                //getActivity().recreate();
                setTheme(sharedPreferences);

            }
        }

        @Override
        public void onDestroy() {
            super.onDestroy();

            // Unregister the listener for preference changes
            getPreferenceScreen().getSharedPreferences().unregisterOnSharedPreferenceChangeListener(this);
        }
    }
}


/*


public class SettingsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        if (savedInstanceState == null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.settings, new SettingsFragment())
                    .commit();
        }
    }

    public static class SettingsFragment extends PreferenceFragmentCompat {
        @Override
        public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
            setPreferencesFromResource(R.xml.preferences, rootKey);
        }
    }
}

 */