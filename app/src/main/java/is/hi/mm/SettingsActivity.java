package is.hi.mm;

import static is.hi.mm.services.MiscService.CreateActionBarForFragments;

import android.content.SharedPreferences;
import android.os.Bundle;
import androidx.preference.ListPreference;

import android.view.MenuItem;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;

import is.hi.mm.services.MiscService;
import is.hi.mm.services.PreferencesService;

public class SettingsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        // Show the back button on the toolbar
        ActionBar actionBar = getSupportActionBar();
        MiscService.CreateActionBarForFragments(actionBar);

        // Load the preference screen fragment
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.settings, new PreferencesService.SettingsFragment())
                .commit();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }





}
