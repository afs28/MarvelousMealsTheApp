package is.hi.mm;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
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

    private Button mLoginButton;

    private EditText mSignupName;

    private EditText mSignupPassword;

    private Button mSignupButton;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //signup:
        mSignupName = findViewById(R.id.signup_username);
        mSignupPassword = findViewById(R.id.signup_password);
        mSignupButton = findViewById(R.id.signup_button);

        mSignupButton = findViewById(R.id.signup_button);

        mSignupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signup();
            }
        });

        //login:
        mName = findViewById(R.id.login_username);
        mPassword = findViewById(R.id.login_password);
        mLoginButton = findViewById(R.id.login_button);

        mLoginButton = findViewById(R.id.login_button);

        mLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
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
