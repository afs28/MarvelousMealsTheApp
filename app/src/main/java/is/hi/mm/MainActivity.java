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



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button loginButton = findViewById(R.id.login_button);
        Button settingsButton = findViewById(R.id.settings_button);
        Button createRecipeButton = findViewById(R.id.create_recipe_button);

        // login button
        loginButton.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(intent);
        });


        // settings button
        settingsButton.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, SettingsActivity.class);
            startActivity(intent);
        });

        // create recipe button
        createRecipeButton.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, CreateRecipeActivity.class);
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

}

