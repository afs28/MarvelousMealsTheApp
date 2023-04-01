package is.hi.mm;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import is.hi.mm.entities.Recipe;
import is.hi.mm.networking.NetworkCallback;
import is.hi.mm.networking.NetworkManager;

public class CreateRecipeActivity extends AppCompatActivity {
    private EditText mRecipeTitle;
    private EditText mRecipeDescription;
    private EditText mRecipeDifficultyLevel;
    private EditText mRecipeAllergyFactors;
    private EditText mRecipeImage;
    private EditText mRecipeNumbOfPeople;
    private EditText mRecipePrepTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_recipe);

        mRecipeTitle = findViewById(R.id.recipe_title);
        mRecipeDescription = findViewById(R.id.recipe_description);
        mRecipeDifficultyLevel = findViewById(R.id.recipe_difficulty_level);
        mRecipeAllergyFactors = findViewById(R.id.recipe_allergy_factors);
        mRecipeImage = findViewById(R.id.recipe_image);
        mRecipeNumbOfPeople = findViewById(R.id.recipe_fornumberofpeople);
        mRecipePrepTime = findViewById(R.id.recipe_prep_time);

        Button submitRecipeButton = findViewById(R.id.submit_recipe_button);
        submitRecipeButton.setOnClickListener(view -> submitRecipe());
    }

    private void submitRecipe() {
        String recipeTitle = mRecipeTitle.getText().toString();
        String recipeDescription = mRecipeDescription.getText().toString();
        String recipeDifficultyLevel = mRecipeDifficultyLevel.getText().toString();
        String recipeAllergyFactors = mRecipeAllergyFactors.getText().toString();
        String recipeImage = mRecipeImage.getText().toString();
        String recipeNumbOfPeople = mRecipeNumbOfPeople.getText().toString();
        String recipePrepTime = mRecipePrepTime.getText().toString();

        // Use NetworkManager to submit the recipe data to your backend
        NetworkManager.getInstance(this).createRecipe(recipeTitle, recipeDescription, recipeDifficultyLevel, recipeAllergyFactors, recipeImage, recipeNumbOfPeople, recipePrepTime, new NetworkCallback<Recipe>() {
            @Override
            public void onSuccess(Recipe result) {
                // Show a success message and finish the activity
                Toast.makeText(CreateRecipeActivity.this, "Recipe added successfully!", Toast.LENGTH_SHORT).show();
                finish();
            }

            @Override
            public void onFailure(String error) {
                // Show an error message
                Toast.makeText(CreateRecipeActivity.this, "Failed to add recipe: " + error, Toast.LENGTH_SHORT).show();
            }
        });
    }
}
