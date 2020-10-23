package com.mdomeck.taskmaster;

import android.app.Notification;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.room.Room;

import java.nio.channels.Channel;

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
            Task newTask = new Task(taskTitleTV.getText().toString(), taskDescriptionTV.getText().toString(), statusAddTask.getText().toString());
            database.taskDao().saveTask(newTask);
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
