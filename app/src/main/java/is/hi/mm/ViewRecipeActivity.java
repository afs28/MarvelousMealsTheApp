package is.hi.mm;

import androidx.appcompat.app.AppCompatActivity;

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
import com.android.volley.toolbox.JsonObjectRequest;


public class ViewRecipeActivity extends AppCompatActivity {

    private TextView mRecipeTitleTextView;
    private TextView mRecipeDescriptionTextView;
    private TextView mRecipeDifficultyLevelTextView;
    private TextView mRecipeRecipeAllergyTextView;
    private TextView mRecipeCommentsTextView;
    private TextView mRecipeRatingsTextView;

    private Long mRecipeID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_recipe);

        Intent intent = getIntent();
        mRecipeID = intent.getLongExtra("recipe_id", -1);

        //Log.d("ViewRecipeActivity", "mRecipeID: " + mRecipeID);
        //Log.e("ViewRecipeActivity", "mRecipeID: " + mRecipeID);

        mRecipeTitleTextView = findViewById(R.id.recipe_title_text_view);
        mRecipeDescriptionTextView = findViewById(R.id.recipe_description_text_view);
        mRecipeDifficultyLevelTextView = findViewById(R.id.recipe_difficulty_level_text_view);
        mRecipeRecipeAllergyTextView = findViewById(R.id.recipe_allergy_text_view);
        mRecipeCommentsTextView = findViewById(R.id.recipe_comments_text_view);
        mRecipeRatingsTextView = findViewById(R.id.recipe_ratings_text_view);

        //Log.e("ViewRecipeActivityView", "mRecipeTitleTextView: " + mRecipeTitleTextView);

        Context context = getApplicationContext();
        NetworkManager networkManager = NetworkManager.getInstance(context);
        networkManager.getRecipeById(mRecipeID, new NetworkCallback<Recipe>() {
            @Override
            public void onSuccess(Recipe recipe) {
                //Log.e("ViewRecipeActivity", "onSuccess method called");
                mRecipeTitleTextView.setText(recipe.getTitle());
                mRecipeDescriptionTextView.setText(recipe.getDescription());
                mRecipeDifficultyLevelTextView.setText(recipe.getDifficultyLevel());
                mRecipeRecipeAllergyTextView.setText(recipe.getAllergyFactors());
                mRecipeCommentsTextView.setText(recipe.getComments());
                mRecipeRatingsTextView.setText(String.valueOf(recipe.getRatings()));
                //Log.e("ViewRecipeActivityTitle", "mRecipeTitleTextView: " + mRecipeTitleTextView);
                //Log.e("ViewRecipeActivityDes", "mRecipeDescriptionTextView: " + mRecipeDescriptionTextView);
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
