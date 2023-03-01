package is.hi.mm.entities;

import com.google.gson.annotations.SerializedName;

public class RecipeRatings {
    @SerializedName("ratingID")
    private Long ratingID;
    @SerializedName("myRating")
    private Double myRating;
    @SerializedName("nickname")
    private String nickname;
    @SerializedName("recipeID")
    private Long recipeID;

    public RecipeRatings(Long ratingID, Double myRating, String nickname, Long recipeID) {
        this.ratingID = ratingID;
        this.myRating = myRating;
        this.nickname = nickname;
        this.recipeID = recipeID;
    }

    public Long getRatingID() {
        return ratingID;
    }

    public void setRatingID(Long ratingID) {
        this.ratingID = ratingID;
    }

    public Double getMyRating() {
        return myRating;
    }

    public void setMyRating(Double myRating) {
        this.myRating = myRating;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public Long getRecipeID() {
        return recipeID;
    }

    public void setRecipeID(Long recipeID) {
        this.recipeID = recipeID;
    }
}
