package is.hi.mm;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.SearchView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import is.hi.mm.entities.Recipe;
import is.hi.mm.entities.RecipeUser;
import is.hi.mm.networking.NetworkCallback;
import is.hi.mm.networking.NetworkManager;
public class MainActivity extends AppCompatActivity implements SearchView.OnQueryTextListener {

    private static final String TAG = "MainActivity";

    private RecyclerView mRecipeListRecyclerView;
    private RecipeAdapter mAdapter;

    private List<Recipe> mRecipeList;

    private List<Recipe> mFilteredRecipeList;

    // creating constant keys for shared preferences.
    public static final String SHARED_PREFS = "shared_prefs";

    // key for storing email.
    public static final String USERNAME_KEY = "username_key";

    // key for storing password.
    public static final String PASSWORD_KEY = "password_key";

    // variable for shared preferences.
    SharedPreferences sharedpreferences;
    String pref_username, pref_password;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // initializing our shared preferences.
        sharedpreferences = getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE);

        // getting data from shared prefs and
        // storing it in our string variable.
        pref_username = sharedpreferences.getString(USERNAME_KEY, null);
        pref_password = sharedpreferences.getString(PASSWORD_KEY, null);


        Button loginButton = findViewById(R.id.login_button);
        Button settingsButton = findViewById(R.id.settings_button);
        Button createRecipeButton = findViewById(R.id.create_recipe_button);

        // if logged in
        Log.e("MainCreate", "pref_username: " + pref_username);
        if (pref_username != null && pref_password != null) {
            // change login to logout
            loginButton.setText(R.string.logout_button);
            loginButton.setOnClickListener(v -> {
                // calling method to edit values in shared prefs.
                SharedPreferences.Editor editor = sharedpreferences.edit();

                // below line will clear
                // the data in shared prefs.
                editor.clear();

                // below line will apply empty
                // data to shared prefs.
                editor.apply();

                // restarting mainactivity after
                // clearing values in shared preferences.
                Intent i = new Intent(MainActivity.this, MainActivity.class);
                startActivity(i);
                finish();
            });
            // create recipe button
            createRecipeButton.setOnClickListener(view -> {
                Intent intent = new Intent(MainActivity.this, CreateRecipeActivity.class);
                startActivity(intent);
            });
        } else {
            // login button
            loginButton.setOnClickListener(v -> {
                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(intent);
            });
            // make recipe button redirect to login
            createRecipeButton.setOnClickListener(view -> {
                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(intent);
            });
        }



        // settings button
        settingsButton.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, SettingsActivity.class);
            startActivity(intent);
        });



        mRecipeListRecyclerView = findViewById(R.id.mRecipeList);
        mRecipeListRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        NetworkManager networkManager = NetworkManager.getInstance(this);
        networkManager.getRecipes(new NetworkCallback<List<Recipe>>() {
            @Override
            public void onSuccess(List<Recipe> result) {
                mRecipeList = result;
                mFilteredRecipeList = new ArrayList<>(mRecipeList);
                mAdapter = new RecipeAdapter(MainActivity.this, mFilteredRecipeList);
                mRecipeListRecyclerView.setAdapter(mAdapter);
            }

            @Override
            public void onFailure(String errorString) {
                Log.e(TAG, errorString);
            }
        });

        // Set up SearchView
        SearchView searchView = findViewById(R.id.mRecipeSearch);
        searchView.setOnQueryTextListener(this);

        // set Light/Dark Mode
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        SettingsActivity.setTheme(sharedPreferences);
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        // Not needed for this implementation
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        // Split the input text into search query and difficulty level
        String query = newText.trim();
        String difficulty = "";
        Pattern pattern = Pattern.compile("\\b(easy|medium|hard)\\b", Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(query);
        if (matcher.find()) {
            difficulty = Objects.requireNonNull(matcher.group(1)).toLowerCase();
            query = query.replaceFirst("(?i)" + difficulty, "").trim();
        }

        // Update the query field
        String query1 = query;

        // Call the filter method with the updated query and difficulty level
        filter(query1, difficulty);
        return true;
    }

    private void filter(String query, String difficulty) {
        Log.d(TAG, "Filtering with query: " + query + " and difficulty: " + difficulty);
        List<Recipe> filteredList = new ArrayList<>();
        if (!(mRecipeList == null)) {
            for (Recipe recipe : mRecipeList) {
                if (query.isEmpty() || recipe.getTitle().toLowerCase().contains(query.toLowerCase())) {
                    if (difficulty.isEmpty() || recipe.getDifficultyLevel().equalsIgnoreCase(difficulty)) {
                        filteredList.add(recipe);
                    }
                }
            }
            mAdapter.setRecipeList(filteredList);
        }


    }
/*
    private SharedPreferences getSharedPrefs() {
        return getSharedPreferences("MyAppPrefs", Context.MODE_PRIVATE);
    }

    private void onLoginSuccess(RecipeUser user) {
        SharedPreferences.Editor editor = getSharedPrefs().edit();
        editor.putLong("loggedInUserId", user.getRecipeUserID());
        // Store other information if needed
        editor.apply();
        // Continue with any other login success actions
    }

 */

}

