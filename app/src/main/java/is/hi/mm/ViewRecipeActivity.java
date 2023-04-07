package is.hi.mm;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.util.Pair;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.TextView;

import java.util.List;

import is.hi.mm.entities.Recipe;
import is.hi.mm.networking.NetworkCallback;
import is.hi.mm.networking.NetworkManager;

public class ViewRecipeActivity extends AppCompatActivity {

    private TextView mRecipeTitleTextView;
    private TextView mRecipeDescriptionTextView;
    private TextView mRecipeDifficultyLevelTextView;
    private TextView mRecipeRecipeAllergyTextView;
    private TextView mRecipeCommentsTextView;
    private TextView mRecipeRatingsTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_recipe);

        Intent intent = getIntent();
        Long recipeID = intent.getLongExtra("recipe_id", -1);

        mRecipeTitleTextView = findViewById(R.id.recipe_title_text_view);
        mRecipeDescriptionTextView = findViewById(R.id.recipe_description_text_view);
        mRecipeDifficultyLevelTextView = findViewById(R.id.recipe_difficulty_level_text_view);
        mRecipeRecipeAllergyTextView = findViewById(R.id.recipe_allergy_text_view);
        mRecipeCommentsTextView = findViewById(R.id.recipe_comments_text_view);
        mRecipeRatingsTextView = findViewById(R.id.recipe_ratings_text_view);

        Context context = getApplicationContext();
        NetworkManager networkManager = NetworkManager.getInstance(context);
        networkManager.getRecipeWithCommentsById(recipeID, new NetworkCallback<Pair<Recipe, List<String>>>() {
            @Override
            public void onSuccess(Pair<Recipe, List<String>> result) {
                Recipe recipe = result.first;
                List<String> comments = result.second;

                mRecipeTitleTextView.setText(recipe.getTitle());
                mRecipeDescriptionTextView.setText(recipe.getDescription());
                mRecipeDifficultyLevelTextView.setText(recipe.getDifficultyLevel());
                mRecipeRecipeAllergyTextView.setText(recipe.getAllergyFactors());
                mRecipeRatingsTextView.setText(String.valueOf(recipe.getRatings()));

                // Display comments
                StringBuilder commentsText = new StringBuilder();
                for (String comment : comments) {
                    commentsText.append(comment).append("\n\n");
                }
                mRecipeCommentsTextView.setText(commentsText.toString());
            }

            @Override
            public void onFailure(String errorString) {
                Log.e("ViewRecipeActivity", "Error loading recipe: " + errorString);
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        // Handle the back button click event
        if (id == android.R.id.home) {
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
