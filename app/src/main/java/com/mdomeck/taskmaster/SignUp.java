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

public class SignUp extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        ((Button) findViewById(R.id.submit_signup)).setOnClickListener(view -> {
            EditText userSignup = findViewById(R.id.user_signup);
            EditText email = findViewById(R.id.confirm_signup);
            EditText password = findViewById(R.id.password_create);
            Log.i("expected password", password.getText().toString());
            Amplify.Auth.signUp(
                    userSignup.getText().toString(),
                    password.getText().toString(),
                    AuthSignUpOptions.builder().userAttribute(AuthUserAttributeKey.email(),
                            email.getText().toString()).build(),
                    result -> Log.i("Amplify.signup", "Results: " + result.toString()),
                    error -> Log.e("Applify.signup", "SignUp Failed", error)
            );

            Intent i = new Intent(SignUp.this, Confirm.class);
            this.startActivity(i);

        });
    }
}