package com.mdomeck.taskmaster;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import com.amplifyframework.api.graphql.model.ModelMutation;
import com.amplifyframework.api.graphql.model.ModelQuery;
import com.amplifyframework.core.Amplify;
import com.amplifyframework.datastore.generated.model.Task;
import com.amplifyframework.datastore.generated.model.Team;

import java.util.ArrayList;

public class AddTask extends AppCompatActivity implements TaskAdapter.OnInteractingWithTaskListener {

    //  Database database;
    //int teamWeAreOnIndex = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addtask);

        ArrayList<Team> teams = new ArrayList<>();

        Amplify.API.query(
                ModelQuery.list(Team.class),
                response -> {
                    for (Team team : response.getData()) {
                        teams.add(team);
                    }
                    //handler.sendEmptyMessage(1);
                },
                error -> Log.e("Amplify", "failed to retrieve team")
        );

//        database = Room.databaseBuilder(getApplicationContext(), Database.class, "mdomeck_tasks")
//                .fallbackToDestructiveMigration()
//                .allowMainThreadQueries()
//                .build();

        Context context = getApplicationContext();
        CharSequence text = "Submitted!";
        int duration = Toast.LENGTH_SHORT;

        final Toast toast = Toast.makeText(context, text, duration);

        Button addTaskButton = AddTask.this.findViewById(R.id.buttonAddTaskSubmit);
        addTaskButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                RadioGroup getBox = AddTask.this.findViewById(R.id.radioGroup);
                RadioButton selectedTeam = AddTask.this.findViewById(getBox.getCheckedRadioButtonId());
                String teamName = selectedTeam.getText().toString();
                Team chosenTeam = null;
                for (int i = 0; i < teams.size(); i++) {
                    if (teams.get(i).getName().equals(teamName)) {
                        chosenTeam = teams.get(i);
                    }
                }

                TextView taskTitleTV = findViewById(R.id.editTextMyTask);
                TextView taskDescriptionTV = findViewById(R.id.editTextDoSomething);

                Task addTask = Task.builder()
                        .title(taskTitleTV.getText().toString())
                        .body(taskDescriptionTV.getText().toString())
                        .state("new").apartOf(chosenTeam).build();

                Amplify.API.mutate(ModelMutation.create(addTask),
                        response -> Log.i("Amplify", "successfully added " + addTask.getTitle()),
                        error -> Log.e("amplify", error.toString()));

                //    database.taskDao().saveTask(addTask);

                toast.show();
                onBackPressed();
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        Intent mtIntent = new Intent(getApplicationContext(), MainActivity.class);
        startActivityForResult(mtIntent, 0);
        return true;
    }

    @Override
    public void taskListener(Task task) {
    }
}
