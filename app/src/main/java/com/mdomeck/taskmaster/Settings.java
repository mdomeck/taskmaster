package com.mdomeck.taskmaster;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

public class Settings extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        final SharedPreferences.Editor preferenceEditor = preferences.edit();
        findViewById(R.id.saveUsernameButton).setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View view) {

                RadioGroup getBox = Settings.this.findViewById(R.id.radioGroup);
                RadioButton selectedButton = Settings.this.findViewById(getBox.getCheckedRadioButtonId());
                String userTeam = selectedButton.getText().toString();


                EditText username = findViewById(R.id.usernameInputSettings);
                preferenceEditor.putString("savedUsername", username.getText().toString());
                preferenceEditor.putString("teamChosen", userTeam);
                preferenceEditor.apply();

                onBackPressed();
            }
        });
            //TODO save the team here
    }
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent mtIntent = new Intent(getApplicationContext(), MainActivity.class);
        startActivityForResult(mtIntent, 0);
        return true;

    }
}