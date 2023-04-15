package is.hi.mm;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import is.hi.mm.entities.RecipeUser;
import is.hi.mm.networking.NetworkCallback;
import is.hi.mm.networking.NetworkManager;
import is.hi.mm.services.UserService;

public class LoginActivity extends AppCompatActivity {

    private EditText mName;
    private EditText mPassword;
    private EditText mSignupName;
    private EditText mSignupPassword;



    // variable for shared preferences.
    SharedPreferences sharedpreferences;
    String pref_username, pref_password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //signup:
        mSignupName = findViewById(R.id.signup_username);
        mSignupPassword = findViewById(R.id.signup_password);
        Button signupButton = findViewById(R.id.signup_button);

        signupButton.setOnClickListener(v -> UserService.signup(this, mSignupName, mSignupPassword));

        //login:
        mName = findViewById(R.id.login_username);
        mPassword = findViewById(R.id.login_password);
        Button loginButton = findViewById(R.id.login_button);

        loginButton.setOnClickListener(v -> UserService.login(this, mName, mPassword));
    }





    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == android.R.id.home) {
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}
