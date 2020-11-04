package com.mdomeck.taskmaster;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.amplifyframework.AmplifyException;
import com.amplifyframework.api.aws.AWSApiPlugin;
import com.amplifyframework.api.graphql.model.ModelMutation;
import com.amplifyframework.api.graphql.model.ModelQuery;
import com.amplifyframework.auth.cognito.AWSCognitoAuthPlugin;
import com.amplifyframework.core.Amplify;
import com.amplifyframework.datastore.generated.model.Task;
import com.amplifyframework.datastore.generated.model.Team;
import com.amplifyframework.storage.s3.AWSS3StoragePlugin;

import java.time.chrono.MinguoChronology;
import java.util.ArrayList;


public class MainActivity extends AppCompatActivity implements TaskAdapter.OnInteractingWithTaskListener {

    // Database database;
    ArrayList<Task> tasks;
    Handler handler;
    int teamWeAreOnIndex = 0;
    Handler handleCheckedLogin;

    @Override
    public void onResume() {
        super.onResume();
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        TextView myTaskTitle = findViewById(R.id.myTaskTitle);
//        String greeting = String.format("%s's tasks", preferences.getString("savedUsername", "userTasks"));
//        myTaskTitle.setText(greeting);

        getIsSignedIn();

        //String teamChosen = preferences.getString("teamChosen", "No team chosen");
        TextView myTeamTitle = findViewById(R.id.myTeamTitle);
        String teamChosen = String.format("Team %s", preferences.getString("teamChosen", "No team chosen"));
        myTeamTitle.setText(teamChosen);


        Amplify.API.query(
                ModelQuery.list(Task.class),
                response -> {
                    tasks.clear(); //keeps the same array list but empties it
                    for (Task task : response.getData()) {
                        if (preferences.contains("teamChosen")) {
                            if (task.apartOf.getName().equals(preferences.getString("teamChosen", " "))) {
                                tasks.add(task);
                            }
                        } else {
                            tasks.add(task);
                        }
                    }
                    handler.sendEmptyMessage(1);
                    Log.i("Amplify.queryItems", "received from Dynamo " + tasks.size());
                },
                error -> Log.i("Amplify.queryItems", "did not get items"));
    }


    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        try {
            Amplify.addPlugin(new AWSApiPlugin());
            Amplify.addPlugin(new AWSCognitoAuthPlugin());
            Amplify.addPlugin(new AWSCognitoAuthPlugin());
            Amplify.addPlugin(new AWSS3StoragePlugin());
            Amplify.configure(getApplicationContext());
            

            //setUpThreeTeams();

            //Log.i("MyAmplifyApp", "Initialized Amplify");
        } catch (AmplifyException error) {
            Log.e("MyAmplifyApp", "Could not initialize Amplify", error);
        }

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor preferenceEditor = preferences.edit();

        TextView myTaskTitle = findViewById(R.id.myTaskTitle);
        //String greeting = String.format("%s's tasks", preferences.getString("savedUsername", "userTasks"));
       // myTaskTitle.setText(greeting);

//        database = Room.databaseBuilder(getApplicationContext(), Database.class, "mdomeck_tasks")
//                .fallbackToDestructiveMigration()
//                .allowMainThreadQueries()
//                .build();

        tasks = new ArrayList<Task>();

        RecyclerView recyclerView = findViewById(R.id.taskRecycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(new TaskAdapter(tasks, this));

        handler = new Handler(Looper.getMainLooper(),
                new Handler.Callback() {

                    @Override
                    public boolean handleMessage(@NonNull Message message) {
                        recyclerView.getAdapter().notifyDataSetChanged();
                        return true;
                    }
                });

        handleCheckedLogin = new Handler(Looper.getMainLooper(), message -> {
            if (message.arg1 == 0) {
                Log.i("Amplify.login", "User not logged in");
            } else if (message.arg1 == 1) {
                Log.i("Amplify.login", "User logged in");
                Log.i("Amplify.user", Amplify.Auth.getCurrentUser().getUsername());

                String greeting = String.format("%s is logged in", Amplify.Auth.getCurrentUser().getUsername());
                myTaskTitle.setText(greeting);
            }
            return false;
        });


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

        Button signupUser = MainActivity.this.findViewById(R.id.signup_main);
        signupUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, SignUp.class);
                startActivity(intent);
            }
        });

        Button loginUser = MainActivity.this.findViewById(R.id.login_main);
        loginUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, Login.class);
                startActivity(intent);
            }
        });

        ((Button) findViewById(R.id.signout_main)).setOnClickListener(view -> {
            Amplify.Auth.signOut(
                    () -> Log.i("Amplify.Signout", "sign out successful"),
                    error -> Log.e("Amplify.Signout", error.toString())
            );
            Intent i = new Intent(MainActivity.this, MainActivity.class);
            startActivity(i);
        });



    }


    public void getIsSignedIn() {
        Amplify.Auth.fetchAuthSession(
                result -> {
                    Log.i("Amplify.Login", result.toString());
                    Message message = new Message();

                    if (result.isSignedIn()) {
                        message.arg1 = 1;
                        handleCheckedLogin.sendMessage(message);
                    } else {
                        message.arg1 = 0;
                        handleCheckedLogin.sendMessage(message);
                    }
                },
                error -> Log.e("Amplify.Login", error.toString())
        );
    }




    public void setUpThreeTeams() {
        Team team1 = Team.builder()
                .name("Mickey")
                .build();

        Team team2 = Team.builder()
                .name("Minnie")
                .build();
        Team team3 = Team.builder()
                .name("Daisy")
                .build();

        Amplify.API.mutate(ModelMutation.create(team1),
                response -> Log.i("Amplify", "added a team"),
                error -> Log.e("Amplify", "failed to add a team")
        );

        Amplify.API.mutate(ModelMutation.create(team2),
                response -> Log.i("Amplify", "added a team"),
                error -> Log.e("Amplify", "failed to add a team")
        );

        Amplify.API.mutate(ModelMutation.create(team3),
                response -> Log.i("Amplify", "added a team"),
                error -> Log.e("Amplify", "failed to add a team")
        );

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