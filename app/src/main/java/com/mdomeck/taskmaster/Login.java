package com.mdomeck.taskmaster;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;

import com.amplifyframework.auth.AuthUserAttributeKey;
import com.amplifyframework.auth.options.AuthSignUpOptions;
import com.amplifyframework.core.Amplify;

public class Login extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        ((Button) findViewById(R.id.login_button)).setOnClickListener(view -> {
            EditText username = findViewById(R.id.username_login);
            EditText password = findViewById(R.id.password_login);

            Amplify.Auth.signIn(
                    username.getText().toString(),
                    password.getText().toString(),
                    result -> {
                        Log.i("Amplify.Login", result.isSignInComplete() ? "Sign in successful" : "Sign in not successful");
                        startActivity(new Intent(Login.this, MainActivity.class));
                    },
                    error -> Log.e("Amplify.Login", error.toString())
            );

            Intent i = new Intent(Login.this, MainActivity.class);
            this.startActivity(i);
        });
    }

}