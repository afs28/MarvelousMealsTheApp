package is.hi.mm.services;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;

import androidx.appcompat.app.AppCompatDelegate;
import androidx.preference.ListPreference;
import androidx.preference.PreferenceFragmentCompat;

import is.hi.mm.R;

/**
 * Used for SettingsActivity as well as app settings in general.
 */
public class PreferencesService {
    // creating constant keys for shared preferences.
    public static final String SHARED_PREFS = "shared_prefs";

    // key for storing username.
    public static final String USERNAME_KEY = "username_key";

    // key for storing password.
    public static final String PASSWORD_KEY = "password_key";
    /**
     * Use this to set the settings.
     * @param context put 'this' in here.
     */
    public static void SetSettings(Context context) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        setTheme(sharedPreferences);
    }

    public static void SetUserPreference(Context context, String username, String password) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        // below two lines will put values for
        // email and password in shared preferences.
        editor.putString(USERNAME_KEY, username);
        editor.putString(PASSWORD_KEY, password);
        editor.apply();

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
                PreferencesService.setTheme(sharedPreferences);

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
