package com.mdomeck.taskmaster;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {

    @Override
    public void onResume() {
        super.onResume();
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        TextView myTaskTitle = findViewById(R.id.myTaskTitle);
        String greeting = String.format("%s's tasks", preferences.getString("savedUsername", "userTasks"));
        myTaskTitle.setText(greeting);
        SharedPreferences.Editor preferenceEditor = preferences.edit();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ArrayList<Task> task = new ArrayList<>();
        task.add(new Task("Task the First", "body", "state"));
        task.add(new Task("Task the Second", "body", "state"));
        task.add(new Task("Task the Third", "body", "state"));
        task.add(new Task("Task the First", "body", "state"));
        task.add(new Task("Task the Second", "body", "state"));
        task.add(new Task("Task the Third", "body", "state"));
        task.add(new Task("Task the First", "body", "state"));
        task.add(new Task("Task the Second", "body", "state"));
        task.add(new Task("Task the Third", "body", "state"));
        task.add(new Task("Task the First", "body", "state"));
        task.add(new Task("Task the Second", "body", "state"));
        task.add(new Task("Task the Third", "body", "state"));
        task.add(new Task("Task the First", "body", "state"));
        task.add(new Task("Task the Second", "body", "state"));
        task.add(new Task("Task the Third", "body", "state"));
        task.add(new Task("Task the First", "body", "state"));
        task.add(new Task("Task the Second", "body", "state"));
        task.add(new Task("Task the Third", "body", "state"));
        task.add(new Task("Task the First", "body", "state"));
        task.add(new Task("Task the Second", "body", "state"));
        task.add(new Task("Task the Third", "body", "state"));
        task.add(new Task("Task the First", "body", "state"));
        task.add(new Task("Task the Second", "body", "state"));
        task.add(new Task("Task the Third", "body", "state"));

        RecyclerView recyclerView = findViewById(R.id.taskRecycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(new TaskAdapter(task));       // TODO add this later



        Button addTaskButton = MainActivity.this.findViewById(R.id.addTaskButton);
        addTaskButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this, AddTask.class);
                startActivity(i);
            }
        });

        Button addSettingsButton = MainActivity.this.findViewById(R.id.settingsButtonHome);
        addSettingsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this, Settings.class);
                startActivity(i);
            }
        });

        Button allTasksButton = MainActivity.this.findViewById(R.id.allTasksButton);
        allTasksButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this, AllTasks.class);
                startActivity(i);
            }
        });


        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        final SharedPreferences.Editor preferenceEditor = preferences.edit();

        Button selectTaskOneButton = MainActivity.this.findViewById(R.id.taskFirstButton);
        selectTaskOneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this, TaskDetail.class);
                Button firstButton = findViewById(R.id.taskFirstButton);
                preferenceEditor.putString("taskName", firstButton.getText().toString());
                preferenceEditor.apply();
               // i.putExtra("taskName",firstButton.getText());
                MainActivity.this.startActivity(i);
            }
        });

        Button selectTaskTwoButton = MainActivity.this.findViewById(R.id.taskSecondButton);
        selectTaskTwoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this, TaskDetail.class);
                Button secondButton = findViewById(R.id.taskSecondButton);
                preferenceEditor.putString("taskName", secondButton.getText().toString());
                preferenceEditor.apply();
                //i.putExtra("taskName",secondButton.getText());
                MainActivity.this.startActivity(i);
            }
        });

        Button selectTaskThreeButton = MainActivity.this.findViewById(R.id.taskThirdButton);
        selectTaskThreeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this, TaskDetail.class);
                Button thirdButton = findViewById(R.id.taskThirdButton);
                preferenceEditor.putString("taskName", thirdButton.getText().toString());
                preferenceEditor.apply();
                //i.putExtra("taskName",thirdButton.getText());
                MainActivity.this.startActivity(i);
            }
        });
    }


}