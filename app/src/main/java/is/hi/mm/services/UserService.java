package is.hi.mm.services;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.text.TextUtils;
import android.util.Log;
import android.widget.EditText;
import android.widget.Toast;

import is.hi.mm.LoginActivity;
import is.hi.mm.MainActivity;
import is.hi.mm.entities.RecipeUser;
import is.hi.mm.networking.NetworkCallback;
import is.hi.mm.networking.NetworkManager;

public class UserService {

    public static void signup(Context context, EditText mSignupName, EditText mSignupPassword) {
        String name = mSignupName.getText().toString();
        String password = mSignupPassword.getText().toString();

        Log.e("signup", "mSignupName: " + name);

        if (TextUtils.isEmpty(name) || TextUtils.isEmpty(password)) {
            Toast.makeText(context, "Please enter all the required fields", Toast.LENGTH_SHORT).show();
            return;
        }

        //Context context = getApplicationContext();
        NetworkManager networkManager = NetworkManager.getInstance(context);
        networkManager.signup(name, password, new NetworkCallback<String>() {
            @Override
            public void onSuccess(String response) {
                Toast.makeText(context, "Signup successful", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(String error) {
                Toast.makeText(context, "Signup failed: " + error, Toast.LENGTH_SHORT).show();
            }
        });
    }

    public static void login(Activity activity, EditText mName, EditText mPassword) {
        String username = mName.getText().toString();
        String password = mPassword.getText().toString();

        Log.e("login", "mLoginName: " + username);

        if (TextUtils.isEmpty(username) || TextUtils.isEmpty(password)) {
            Toast.makeText(activity, "Please enter all the required fields", Toast.LENGTH_SHORT).show();
            return;
        }

        NetworkManager networkManager = NetworkManager.getInstance(activity);
        networkManager.login(username, password, new NetworkCallback<RecipeUser>() {
            @Override
            public void onSuccess(RecipeUser user) {
                Toast.makeText(activity, "Login successful", Toast.LENGTH_SHORT).show();
                PreferencesService.SetUserPreference(activity, username, password);
                // Navigate to another activity after successful login, e.g., MainActivity
                Intent intent = new Intent(activity, MainActivity.class);
                activity.startActivity(intent);
                ((Activity) activity).finish();
            }

            @Override
            public void onFailure(String error) {
                Toast.makeText(activity, "Login failed: " + error, Toast.LENGTH_SHORT).show();
            }
        });
    }

    public static void logout(Activity activity) {
        PreferencesService.ClearUserPreference(activity);

        // starting mainactivity after
        // clearing values in shared preferences.
        Intent i = new Intent(activity, MainActivity.class);
        activity.startActivity(i);
        ((Activity) activity).finish();
    }
}
