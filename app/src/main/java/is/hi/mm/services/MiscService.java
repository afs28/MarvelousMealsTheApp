package is.hi.mm.services;

import androidx.appcompat.app.ActionBar;

import is.hi.mm.R;

/**
 * Used for misc. needs around the app.
 */
public class MiscService {

    /**
     * Use this when a Fragment will cover the entire Activity, this will return the 'back arrow' to the Activity.
     * @param actionBar Create this with: 'ActionBar actionBar = getSupportActionBar();'
     * @param title (Optional) Title to show on the ActionBar, by default it will be the app name ('app_name' in strings.xml).
     */
    public static void CreateActionBarForFragments(ActionBar actionBar, String title) {

        // Show the back button on the toolbar
        actionBar.setDisplayHomeAsUpEnabled(true);
        // show the name
        actionBar.setTitle(title);
    }
    /**
     * Use this when a Fragment will cover the entire Activity, this will return the 'back arrow' to the Activity.
     * @param actionBar Create this with: 'ActionBar actionBar = getSupportActionBar();', it's the ActionBar of the Activity.
     */
    public static void CreateActionBarForFragments(ActionBar actionBar) {

        // Show the back button on the toolbar
        actionBar.setDisplayHomeAsUpEnabled(true);
        // show the app_name
        actionBar.setTitle(R.string.app_name);

    }
}
