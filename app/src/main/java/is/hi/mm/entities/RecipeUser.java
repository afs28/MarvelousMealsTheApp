package is.hi.mm.entities;

import com.google.gson.annotations.SerializedName;

public class RecipeUser {
    @SerializedName("recipeUserID")
    private long recipeUserID;
    @SerializedName("recipeUsername")
    private String recipeUsername;
    @SerializedName("recipeUserPassword")
    private String recipeUserPassword;

    public RecipeUser(long recipeUserID, String recipeUsername, String recipeUserPassword) {
        this.recipeUserID = recipeUserID;
        this.recipeUsername = recipeUsername;
        this.recipeUserPassword = recipeUserPassword;
    }

    public long getRecipeUserID() {
        return recipeUserID;
    }

    public void setRecipeUserID(long recipeUserID) {
        this.recipeUserID = recipeUserID;
    }

    public String getRecipeUsername() {
        return recipeUsername;
    }

    public void setRecipeUsername(String recipeUsername) {
        this.recipeUsername = recipeUsername;
    }

    public String getRecipeUserPassword() {
        return recipeUserPassword;
    }

    public void setRecipeUserPassword(String recipeUserPassword) {
        this.recipeUserPassword = recipeUserPassword;
    }
}
