package com.mdomeck.taskmaster;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.amplifyframework.core.Amplify;

import java.io.File;

public class TaskDetail extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_detail);

       // getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Intent intent = getIntent();
        TextView titleTask = TaskDetail.this.findViewById(R.id.taskName);
        TextView bodyTask = TaskDetail.this.findViewById(R.id.loremIpsumTaskDetails);
        TextView stateTask = TaskDetail.this.findViewById(R.id.descriptionTaskDetails);
        TextView locationTask = TaskDetail.this.findViewById(R.id.taskdetail_address);

        titleTask.setText(intent.getExtras().getString("title"));
        bodyTask.setText(intent.getExtras().getString("body"));
        stateTask.setText(intent.getExtras().getString("state"));
        locationTask.setText(intent.getExtras().getString("location"));

        if(intent.getExtras().containsKey("fileKey")){
            downloadFile(intent.getExtras().getString("fileKey"));
        }
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        Intent mtIntent = new Intent(getApplicationContext(), MainActivity.class);
        startActivityForResult(mtIntent, 0);
        return true;
    }

    public void downloadFile(String fileKey) { // fileKey will be coming from intent atm } // code direction from Jack Nelson https://github.com/jnelsonjava/taskmaster/blob/main/app/src/main/java/com/jnelsonjava/taskmaster/AddTask.java
        Amplify.Storage.downloadFile(
                fileKey,
                new File(getApplicationContext().getFilesDir() + "/" + fileKey + ".txt"),
                result -> {
                    Log.i("Amplify.s3dl", "Successful download: " + result.getFile().getName());
                    ImageView image = findViewById(R.id.imageVDetailPage);
                    image.setImageBitmap(BitmapFactory.decodeFile(result.getFile().getPath()));
                },
                error -> Log.e("Amplify.s3down", "Download Fail", error)
        );
    }

}