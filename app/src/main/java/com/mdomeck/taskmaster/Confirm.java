package com.mdomeck.taskmaster;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;

import com.amplifyframework.core.Amplify;

public class Confirm extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm);

        ((Button) findViewById(R.id.submit_signup)).setOnClickListener(view -> {

        EditText username = findViewById(R.id.user_signup);
        EditText confirmation = findViewById(R.id.confirm_signup);

        Amplify.Auth.confirmSignUp(
                username.getText().toString(),
                confirmation.getText().toString(),
                results -> Log.i("Amplify.confirm", results.isSignUpComplete() ? "Confirm signup successful" : "Confirm signup incomplete"),
                error -> Log.e("Amplify.confirm", error.toString())
        );
            Intent i = new Intent(Confirm.this, Login.class);
            this.startActivity(i);
        });
    }

}

