package is.hi.mm.networking;

import android.annotation.SuppressLint;
import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;
import is.hi.mm.entities.Recipe;

public class NetworkManager {
    private static final String BASE_URL = "https://hugbo-production.up.railway.app";
    @SuppressLint("StaticFieldLeak")
    private static NetworkManager sInstance;
    private static RequestQueue sQueue;
    private final Context mContext;

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
}
