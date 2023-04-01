package is.hi.mm;

import android.content.Context;
import android.content.Intent;
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

public class LoginActivity extends AppCompatActivity {

    private EditText mName;
    private EditText mPassword;
    private EditText mSignupName;
    private EditText mSignupPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //signup:
        mSignupName = findViewById(R.id.signup_username);
        mSignupPassword = findViewById(R.id.signup_password);
        Button signupButton = findViewById(R.id.signup_button);

        signupButton.setOnClickListener(v -> signup());

        //login:
        mName = findViewById(R.id.login_username);
        mPassword = findViewById(R.id.login_password);
        Button loginButton = findViewById(R.id.login_button);

        loginButton.setOnClickListener(v -> login());
    }

    private void signup() {
        String name = mSignupName.getText().toString();
        String password = mSignupPassword.getText().toString();

        Log.e("signup", "mSignupName: " + name);

        if (TextUtils.isEmpty(name) || TextUtils.isEmpty(password)) {
            Toast.makeText(this, "Please enter all the required fields", Toast.LENGTH_SHORT).show();
            return;
        }

        Context context = getApplicationContext();
        NetworkManager networkManager = NetworkManager.getInstance(context);
        networkManager.signup(name, password, new NetworkCallback<String>() {
            @Override
            public void onSuccess(String response) {
                Toast.makeText(LoginActivity.this, "Signup successful", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(String error) {
                Toast.makeText(LoginActivity.this, "Signup failed: " + error, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void login() {
        String username = mName.getText().toString();
        String password = mPassword.getText().toString();

        Log.e("login", "mLoginName: " + username);

        if (TextUtils.isEmpty(username) || TextUtils.isEmpty(password)) {
            Toast.makeText(this, "Please enter all the required fields", Toast.LENGTH_SHORT).show();
            return;
        }

        NetworkManager networkManager = NetworkManager.getInstance(getApplicationContext());
        networkManager.login(username, password, new NetworkCallback<RecipeUser>() {
            @Override
            public void onSuccess(RecipeUser user) {
                Toast.makeText(LoginActivity.this, "Login successful", Toast.LENGTH_SHORT).show();
                // Navigate to another activity after successful login, e.g., MainActivity
                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(intent);
            }

            @Override
            public void onFailure(String error) {
                Toast.makeText(LoginActivity.this, "Login failed: " + error, Toast.LENGTH_SHORT).show();
            }
        });
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
