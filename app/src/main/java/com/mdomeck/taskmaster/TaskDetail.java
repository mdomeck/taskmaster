package com.mdomeck.taskmaster;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

public class TaskDetail extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_detail);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        TextView taskName = TaskDetail.this.findViewById(R.id.taskDetailsTitle);
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        String displayTaskTitle = preferences.getString("taskName", "taskDetails");
        taskName.setText(displayTaskTitle);
    }

//    public void taskDisplay(View view) {
//        TextView taskName = TaskDetail.this.findViewById(R.id.taskDetailsTitle);
//        String customTaskName = taskName.getText().toString();
//        taskName.setText(customTaskName);
//    }

    public boolean onOptionsItemSelected(MenuItem item) {
        Intent mtIntent = new Intent(getApplicationContext(), MainActivity.class);
        startActivityForResult(mtIntent, 0);
        return true;

    }

}