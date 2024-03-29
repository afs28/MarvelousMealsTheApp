package is.hi.mm;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import is.hi.mm.entities.Recipe;
import is.hi.mm.networking.NetworkCallback;
import is.hi.mm.networking.NetworkManager;

public class RecipeAdapter extends RecyclerView.Adapter<RecipeAdapter.RecipeViewHolder> {

    private final Context mContext;
    private List<Recipe> mRecipeList;

    public RecipeAdapter(Context context, List<Recipe> recipeList) {
        mContext = context;
        mRecipeList = recipeList;
    }

    private void loadRecipes() {
        NetworkManager.getInstance(mContext).getRecipes(new NetworkCallback<List<Recipe>>() {
            @Override
            public void onSuccess(List<Recipe> result) {
                mRecipeList = result;
                notifyDataSetChanged();
            }

            @Override
            public void onFailure(String errorString) {
                // handle error
            }
        });
    }

    @NonNull
    @Override
    public RecipeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recipe_item, parent, false);
        return new RecipeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecipeViewHolder holder, int position) {
        Recipe recipe = mRecipeList.get(position);
        holder.bindRecipe(recipe);
    }

    @Override
    public int getItemCount() {
        if (mRecipeList == null) {
            return 0;
        } else {
            return mRecipeList.size();
        }
    } public void setRecipeList(List<Recipe> recipeList) {
        mRecipeList = recipeList;
        notifyDataSetChanged();
    }

    class RecipeViewHolder extends RecyclerView.ViewHolder {

        private final TextView mRecipeNameTextView;
        private Long mRecipeID;

        public RecipeViewHolder(@NonNull View itemView) {
            super(itemView);
            mRecipeNameTextView = itemView.findViewById(R.id.recipe_name_text);
            Button recipeButton = itemView.findViewById(R.id.recipe_button);

            recipeButton.setOnClickListener(v -> {
                Intent intent = new Intent(mContext, ViewRecipeActivity.class);
                intent.putExtra("recipe_id", mRecipeID); // pass the recipe ID to the activity
                mContext.startActivity(intent);
            });
        }

        public void bindRecipe(Recipe recipe) {
            mRecipeID = recipe.getRecipeID(); // set the recipe ID
            mRecipeNameTextView.setText(recipe.getTitle());
        }
    }


}