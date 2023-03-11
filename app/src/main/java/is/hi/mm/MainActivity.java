package is.hi.mm;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SearchView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import is.hi.mm.entities.Recipe;
import is.hi.mm.networking.NetworkCallback;
import is.hi.mm.networking.NetworkManager;

public class MainActivity extends AppCompatActivity implements SearchView.OnQueryTextListener {

    private static final String TAG = "MainActivity";

    private RecyclerView mRecipeListRecyclerView;
    private RecipeAdapter mAdapter;

    private List<Recipe> mRecipeList;

    private List<Recipe> mFilteredRecipeList;

    private String mQuery = "";

    private Button mLoginButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mLoginButton = findViewById(R.id.login_button);

        mLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(intent);
            }
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
            difficulty = matcher.group(1).toLowerCase();
            query = query.replaceFirst("(?i)" + difficulty, "").trim();
        }

        // Update the query field
        mQuery = query;

        // Call the filter method with the updated query and difficulty level
        filter(mQuery, difficulty);
        return true;
    }

    private void filter(String query, String difficulty) {
        Log.d(TAG, "Filtering with query: " + query + " and difficulty: " + difficulty);
        List<Recipe> filteredList = new ArrayList<>();
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

