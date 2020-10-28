package com.mdomeck.taskmaster;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.amplifyframework.AmplifyException;
import com.amplifyframework.api.aws.AWSApiPlugin;
import com.amplifyframework.api.graphql.model.ModelMutation;
import com.amplifyframework.core.Amplify;
import com.amplifyframework.datastore.generated.model.Task;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity implements TaskAdapter.OnInteractingWithTaskListener {

    Database database;


//    @Override
//    public void onResume() {
//        super.onResume();
//        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
//        TextView myTaskTitle = findViewById(R.id.myTaskTitle);
//        String greeting = String.format("%s's tasks", preferences.getString("savedUsername", "userTasks"));
//        myTaskTitle.setText(greeting);
//        SharedPreferences.Editor preferenceEditor = preferences.edit();
//    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        try {
            Amplify.addPlugin(new AWSApiPlugin());
            Amplify.configure(getApplicationContext());

            // Add a task
            Task addTask = Task.builder()
                    .title("Dog Bath")
                    .body("Wash with Soap")
                    .state("Not done").build();

            Amplify.API.mutate(ModelMutation.create(addTask),
                    response -> Log.i("Amplify", "successfully added " + addTask.getTitle()),
                    error -> Log.e("amplify", error.toString()));

            database.taskDao().saveTask(addTask);

            Log.i("MyAmplifyApp", "Initialized Amplify");
        } catch (AmplifyException error) {
            Log.e("MyAmplifyApp", "Could not initialize Amplify", error);
        }

        database = Room.databaseBuilder(getApplicationContext(), Database.class, "mdomeck_tasks")
                .fallbackToDestructiveMigration()
                .allowMainThreadQueries()
                .build();


        //ArrayList<TaskLocal> taskLocals = (ArrayList<TaskLocal>) database.taskDao().getAllTasks();

//        RecyclerView recyclerView = findViewById(R.id.taskRecycler);
//        recyclerView.setLayoutManager(new LinearLayoutManager(this));
//        recyclerView.setAdapter(new TaskAdapter(task, this));

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
    }


    @Override
    public void taskListener(Task task) {
        Intent intent = new Intent(MainActivity.this, TaskDetail.class);
        intent.putExtra("title", task.getTitle());
        intent.putExtra("body", task.getBody());
        intent.putExtra("state", task.getState());
        this.startActivity(intent);

    }
}