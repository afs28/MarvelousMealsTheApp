package is.hi.mm.networking;

import android.content.Context;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;
import is.hi.mm.entities.Recipe;

public class NetworkManager {
    private static final String BASE_URL = "https://hugbo-production.up.railway.app/";
    private static NetworkManager mInstance;
    private static RequestQueue mQueue;
    private Context mContext;

    public static synchronized NetworkManager getInstance(Context context) {
        if(mInstance == null) {
            mInstance = new NetworkManager(context);
        }
        return mInstance;
    }

    private NetworkManager(Context context) {
        mContext = context;
        mQueue = getRequestQueue();
    }

    public RequestQueue getRequestQueue() {
        if (mQueue == null) {
            mQueue = Volley.newRequestQueue(mContext.getApplicationContext());
        }
        return mQueue;
    }

    public void getRecipes(NetworkCallback<List<Recipe>> callback) {
        StringRequest request = new StringRequest(
                Request.Method.GET, BASE_URL + "index", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Gson gson = new Gson();
                Type listType = new TypeToken<List<Recipe>>(){}.getType();
                //List<Question> questionBank = gson.fromJson(response, listType);
                //List<Recipe> findAll() = gson.fromJson(response, listType);
                //callback.onSuccess(questionBank);
                //callback.onSuccess();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                callback.onFailure(error.toString());
            }
        }
        );
        mQueue.add(request);
    }
}
