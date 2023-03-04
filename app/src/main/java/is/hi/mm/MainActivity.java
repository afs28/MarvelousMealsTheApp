package is.hi.mm;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;

import java.util.List;

import is.hi.mm.entities.Recipe;
import is.hi.mm.networking.NetworkCallback;
import is.hi.mm.networking.NetworkManager;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    private RecyclerView mRecipeListRecyclerView;
    private RecipeAdapter mAdapter;

    private List<Recipe> mRecipeList;

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
                mAdapter = new RecipeAdapter(MainActivity.this, mRecipeList);
                mRecipeListRecyclerView.setAdapter(mAdapter);

                // Accessing the first recipe in the list
                Recipe firstRecipe = mRecipeList.get(0);

                // Printing the description of the first recipe
                Log.d(TAG, "Description of the first recipe: " + firstRecipe.getDescription());
            }

            @Override
            public void onFailure(String errorString) {
                Log.e(TAG, errorString);
            }
        });

        // TODO: Add code for button onClick listener and other TODO items.
    }
}
