package is.hi.mm.entities;

import com.google.gson.annotations.SerializedName;

public class RecipeComments {
    @SerializedName("commentID")
    private Long commentID;
    @SerializedName("myComment")
    private String myComment;
    @SerializedName("nickname")
    private String nickname;
    @SerializedName("recipeID")
    private Long recipeID;

    public RecipeComments(Long commentID, String myComment, String nickname, Long recipeID) {
        this.commentID = commentID;
        this.myComment = myComment;
        this.nickname = nickname;
        this.recipeID = recipeID;
    }

    public Long getCommentID() {
        return commentID;
    }

    public void setCommentID(Long commentID) {
        this.commentID = commentID;
    }

    public String getMyComment() {
        return myComment;
    }

    public void setMyComment(String myComment) {
        this.myComment = myComment;
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
