package is.hi.mm;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.widget.SearchView;
import java.util.ArrayList;
import java.util.List;
import is.hi.mm.entities.Recipe;
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
        // Update the query field
        // Call the filter method with the updated query
        filter(newText);
        return true;
    }

    @SuppressLint("NotifyDataSetChanged")
    private void filter(String query) {
        mFilteredRecipeList.clear();
        for (Recipe recipe : mRecipeList) {
            if (recipe.getTitle().toLowerCase().contains(query.toLowerCase())) {
                mFilteredRecipeList.add(recipe);
            }
        }
        mAdapter.notifyDataSetChanged();
    }
}

