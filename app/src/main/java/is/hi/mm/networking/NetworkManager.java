package is.hi.mm.networking;

import android.content.Context;
import android.util.Log;

import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.List;

import is.hi.mm.entities.Recipe;
import is.hi.mm.entities.RecipeUser;

public class NetworkManager {
    private static final String BASE_URL = "https://hugbo-production.up.railway.app";
    private static NetworkManager sInstance;
    private static RequestQueue sQueue;
    private final Context mContext;

    public static synchronized NetworkManager getInstance(Context context) {
        if (sInstance == null) {
            sInstance = new NetworkManager(context);
        }
        return sInstance;
    }

    private NetworkManager(Context context) {
        mContext = context;
        sQueue = getRequestQueue();
    }

    public RequestQueue getRequestQueue() {
        if (sQueue == null) {
            sQueue = Volley.newRequestQueue(mContext.getApplicationContext());
        }
        return sQueue;
    }

    //TODO: Þurfum við ekki að setja inn comments og ratings hér?
    public void getRecipeById(Long recipeID, NetworkCallback<Recipe> callback) {
        JsonObjectRequest request = new JsonObjectRequest(
                Request.Method.GET, BASE_URL + "/api/" + recipeID, null,
                response -> {
                    Gson gson = new Gson();
                    Recipe recipe = gson.fromJson(response.toString(), Recipe.class);
                    Log.d("NetworkManager", "response: " + response);
                    System.out.println(response);
                    callback.onSuccess(recipe);
                    System.out.println(response);
                },
                error -> {
                    NetworkResponse response = error.networkResponse;
                    if (response != null) {
                        int statusCode = response.statusCode;
                        Log.e("NetworkManager", "Error response status code: " + statusCode);
                    }
                    callback.onFailure(error.getMessage());
                }
        );
        getRequestQueue().add(request);
    }

    //TODO: Þurfum við ekki að setja inn comments og ratings hér líka?

    public void getRecipes(NetworkCallback<List<Recipe>> callback) {
        StringRequest request = new StringRequest(
                //þetta mapping er ekki rétt. Þurfum að laga það: + "/index"
                Request.Method.GET, BASE_URL + "/api/", response -> {
                    Gson gson = new Gson();
                    Type listType = new TypeToken<List<Recipe>>(){}.getType();
                    System.out.println(response);
                    List<Recipe> recipe = gson.fromJson(response, listType);
                    callback.onSuccess(recipe);
                }, error -> callback.onFailure(error.toString())
        );
        sQueue.add(request);
    }

    public void createRecipe(String title, String description, String difficultyLevel, String allergyFactors, String image, String numbOfPeople, String prepTime, NetworkCallback<Recipe> callback) {
        String url = BASE_URL + "/api/create";

        JSONObject jsonBody = new JSONObject();
        try {
            jsonBody.put("title", title);
            jsonBody.put("description", description);
            jsonBody.put("difficultyLevel", difficultyLevel);
            jsonBody.put("allergyFactors", allergyFactors);
            jsonBody.put("image", image);
            jsonBody.put("numbOfPeople", numbOfPeople);
            jsonBody.put("prepTime", prepTime);

        } catch (JSONException e) {
            e.printStackTrace();
            callback.onFailure("Error creating JSON body: " + e.getMessage());
            return;
        }

        JsonObjectRequest request = new JsonObjectRequest(
                Request.Method.POST, url, jsonBody,
                response -> {
                    Gson gson = new Gson();
                    Recipe createdRecipe = gson.fromJson(response.toString(), Recipe.class);
                    callback.onSuccess(createdRecipe);
                },
                error -> callback.onFailure("Error: " + error.getMessage())
        );
        getRequestQueue().add(request);
    }

    public void signup(String recipeUsername, String recipeUserPassword, NetworkCallback<String> callback) {
        String url = BASE_URL + "/api/signup/";

        JSONObject jsonBody = new JSONObject();
        try {
            jsonBody.put("recipeUsername", recipeUsername);
            jsonBody.put("recipeUserPassword", recipeUserPassword);
        } catch (JSONException e) {
            e.printStackTrace();
            Log.e("NetworkManager", "eeeeeeeeeeeeeeeeError creating JSON body: " + e.getMessage());
            callback.onFailure("helloimtheerrorError creating JSON body: " + e.getMessage());
            return;
        }

        JsonObjectRequest request = new JsonObjectRequest(
                Request.Method.POST, url, jsonBody,
                response -> {
                    if (response == null || response.length() == 0) {
                        callback.onFailure("Received an empty response from the server.");
                        return;
                    }
                    System.out.println("onresponse: " + response);
                    callback.onSuccess(response.toString());
                },
                error -> {
                    if (error.networkResponse != null) {
                        int statusCode = error.networkResponse.statusCode;
                        if (statusCode == 409) {
                            callback.onFailure("The chosen username is already in use. Please choose a different one.");
                        } else {
                            callback.onFailure("Error: " + statusCode + " " + error.getMessage());
                        }
                    } else {
                        callback.onFailure("An unknown error occurred: " + error.getMessage());
                    }
                    Log.e("NetworkManager", "Error in signup: " + error);
                }
        );
        getRequestQueue().add(request);
    }

    public void login(String username, String password, NetworkCallback<RecipeUser> callback) {
        String url = BASE_URL + "/api/login/";

        JSONObject requestBody = new JSONObject();
        try {
            requestBody.put("recipeUsername", username);
            requestBody.put("recipeUserPassword", password);
        } catch (Exception e) {
            callback.onFailure("Error creating request body: " + e.getMessage());
            return;
        }

        JsonObjectRequest request = new JsonObjectRequest(
                Request.Method.POST, url, requestBody,
                response -> {
                    Gson gson = new Gson();
                    RecipeUser user = gson.fromJson(response.toString(), RecipeUser.class);
                    callback.onSuccess(user);
                },
                error -> {
                    if (error.networkResponse != null) {
                        int statusCode = error.networkResponse.statusCode;
                        if (statusCode == 409) {
                            callback.onFailure("The chosen username is already in use. Please choose a different one.");
                        } else {
                            callback.onFailure("Error: " + statusCode + " " + error.getMessage());
                        }
                    } else {
                        callback.onFailure("An unknown error occurred: " + error.getMessage());
                    }
                    Log.e("NetworkManager", "Error in signup: " + error);
                }
        );
        getRequestQueue().add(request);
    }
}

