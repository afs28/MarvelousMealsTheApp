package is.hi.mm.entities;

import com.google.gson.annotations.SerializedName;

public class Recipe {

    @SerializedName("recipeID")
    private Long recipeID;
    @SerializedName("title")
    private String title;
    @SerializedName("description")
    private String description;
    @SerializedName("difficultyLevel")
    private String difficultyLevel;
    @SerializedName("allergyFactors")
    private String allergyFactors;
    @SerializedName("comments")
    private String comments;
    @SerializedName("ratings")
    private Double ratings;
    @SerializedName("imageOfRecipe")
    private String imageOfRecipe;
    @SerializedName("forNumberOfPeople")
    private Integer forNumberOfPeople;
    @SerializedName("prepTime")
    private Integer prepTime;

    public Recipe(Long recipeID, String title, String description) {
        this.recipeID = recipeID;
        this.title = title;
        this.description = description;
    }

    public Recipe(Long recipeID){
        this.recipeID = recipeID;
    }
    public Long getRecipeID() {
        return recipeID;
    }
    public String getImageOfRecipe() {
        return imageOfRecipe;
    }
    public void setImageOfRecipe(String imageOfRecipe) {
        this.imageOfRecipe = imageOfRecipe;
    }
    public void setRecipeID(Long recipeID) {
        this.recipeID = recipeID;
    }
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public String getDifficultyLevel() {
        return difficultyLevel;
    }
    public void setDifficultyLevel(String difficultyLevel) {
        this.difficultyLevel = difficultyLevel;
    }
    public String getAllergyFactors() {
        return allergyFactors;
    }
    public void setAllergyFactors(String allergyFactors) {
        this.allergyFactors = allergyFactors;
    }
    public String getComments() {
        return comments;
    }
    public void setComments(String comment) {
        this.comments = comment;
    }
    public Double getRatings() {
        return ratings;
    }
    public void setRatings(Double rating) {
        this.ratings = rating;
    }
    public Integer getForNumberOfPeople() {
        return forNumberOfPeople;
    }
    public void setForNumberOfPeople(Integer forNumberOfPeople) {
        this.forNumberOfPeople = forNumberOfPeople;
    }
    public Integer getPrepTime() {
        return prepTime;
    }
    public void setPrepTime(Integer prepTime) {
        this.prepTime = prepTime;
    }
}
