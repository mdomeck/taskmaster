package com.mdomeck.taskmaster;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import com.amplifyframework.api.graphql.model.ModelMutation;
import com.amplifyframework.core.Amplify;
import com.amplifyframework.datastore.generated.model.Task;

public class AddTask extends AppCompatActivity implements TaskAdapter.OnInteractingWithTaskListener{

    Database database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addtask);

        database = Room.databaseBuilder(getApplicationContext(), Database.class, "mdomeck_tasks")
                .allowMainThreadQueries()
                .build();

        final TextView taskTitleTV = findViewById(R.id.editTextMyTask);
        final TextView taskDescriptionTV = findViewById(R.id.editTextDoSomething);
        final TextView statusAddTask = findViewById(R.id.editTextStatus);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //Thanks David
        //https://developer.android.com/guide/topics/ui/notifiers/toasts.html
        Context context = getApplicationContext();
        CharSequence text = "Submitted!";
        int duration = Toast.LENGTH_SHORT;

        final Toast toast = Toast.makeText(context, text, duration);

        Button addTaskButton = AddTask.this.findViewById(R.id.buttonAddTaskSubmit);
        addTaskButton.setOnClickListener(new View.OnClickListener() {



            @Override
            public void onClick(View view) {

                toast.show();
                // Add a task
            Task addTask = Task.builder()
                    .title(taskTitleTV.getText().toString())
                    .body(taskDescriptionTV.getText().toString())
                    .state(statusAddTask.getText().toString()).build();

            Amplify.API.mutate(ModelMutation.create(addTask),
                    response -> Log.i("Amplify", "successfully added " + addTask.getTitle()),
                    error -> Log.e("amplify", error.toString()));

                database.taskDao().saveTask(addTask);


            Intent goToMainActivity = new Intent(AddTask.this, MainActivity.class);
            AddTask.this.startActivity(goToMainActivity);
            }
        });

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
