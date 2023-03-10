package is.hi.mm.networking;

import android.content.Context;
import android.util.Log;

import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.List;
import is.hi.mm.entities.Recipe;
import is.hi.mm.networking.NetworkCallback;


public class NetworkManager {
    private static final String BASE_URL = "https://hugbo-production.up.railway.app";
    private static NetworkManager sInstance;
    private static RequestQueue sQueue;
    private Context mContext;

    public static synchronized NetworkManager getInstance(Context context) {
        if(sInstance == null) {
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

    public void getRecipeById(Long recipeID, NetworkCallback<Recipe> callback) {
        JsonObjectRequest request = new JsonObjectRequest(
                Request.Method.GET, BASE_URL + "/api/" + recipeID, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Gson gson = new Gson();
                        Recipe recipe = gson.fromJson(response.toString(), Recipe.class);
                        Log.d("NetworkManager", "response: " + response.toString());
                        System.out.println(response.toString());
                        callback.onSuccess(recipe);
                        System.out.println(response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        NetworkResponse response = error.networkResponse;
                        if (response != null) {
                            int statusCode = response.statusCode;
                            Log.e("NetworkManager", "Error response status code: " + statusCode);
                        }
                        callback.onFailure(error.getMessage());
                    }
                }
        );
        getRequestQueue().add(request);
    }

    public void getRecipes(NetworkCallback<List<Recipe>> callback) {
        StringRequest request = new StringRequest(
                //??etta mapping er ekki r??tt. ??urfum a?? laga ??a??: + "/index"
                Request.Method.GET, BASE_URL + "/api/", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Gson gson = new Gson();
                Type listType = new TypeToken<List<Recipe>>(){}.getType();
                System.out.println(response);
                List<Recipe> recipe = gson.fromJson(response, listType);
                callback.onSuccess(recipe);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                callback.onFailure(error.toString());
            }
        }
        );
        sQueue.add(request);
    }
}
