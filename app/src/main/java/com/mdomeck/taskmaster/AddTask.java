package com.mdomeck.taskmaster;

import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.os.Build;
import android.os.Bundle;
import android.os.FileUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import com.amplifyframework.api.graphql.model.ModelMutation;
import com.amplifyframework.api.graphql.model.ModelQuery;
import com.amplifyframework.core.Amplify;
import com.amplifyframework.datastore.generated.model.Task;
import com.amplifyframework.datastore.generated.model.Team;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;

public class AddTask extends AppCompatActivity implements TaskAdapter.OnInteractingWithTaskListener {

    //  Database database;
    //int teamWeAreOnIndex = 0;
    String lastFileIUploadedKey;


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

        addListenersToButtons();
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


    @RequiresApi(api = Build.VERSION_CODES.Q)
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 4018) {
            Log.i("Amplify.pickImage", "Got the imge back from the activity");

            File fileCopy = new File(getFilesDir(), "test file");

            try {
                InputStream inStream = getContentResolver().openInputStream(data.getData());
                FileOutputStream out = new FileOutputStream(fileCopy);
                copyStream(inStream, out);
               // FileUtils.copy(inStream, new FileOutputStream(fileCopy));
            } catch (Exception e) {
                e.printStackTrace();
                Log.e("Amplify.pickImage", e.toString());
            }
            uploadFile(fileCopy, fileCopy.getName() + Math.random());
        } else if (requestCode == 2) {
            Log.i("Amplify.doesnotexist", "this does not exist");
        } else {
            Log.i("Amplify.pickImage", "You picked an image");
        }
    }


    public void retrieveFile() {
        Intent getPicIntent = new Intent(Intent.ACTION_GET_CONTENT);
        getPicIntent.setType("*/*");
        startActivityForResult(getPicIntent, 4018);
    }

    private void downloadFile(String fileKey) {
        Amplify.Storage.downloadFile(
                fileKey,
                new File(getApplicationContext().getFilesDir() + "/" + fileKey + ".txt"),
                result -> {
                    Log.i("Amplify.s3down", "Successfully downloaded: " + result.getFile().getName());
                    ImageView image = findViewById(R.id.imageLastUploaded);
                    image.setImageBitmap(BitmapFactory.decodeFile(result.getFile().getPath()));
                },
                error -> Log.e("Amplify.s3down", "Download Failure", error)
        );
    }


    public void uploadFile(File f, String key) {
        lastFileIUploadedKey = key;
        Amplify.Storage.uploadFile(
                key,
                f,
                result -> {
                    Log.i("Amplify.s3", "Successfully uploaded: " + result.getKey());
                    downloadFile(key);
                },
                storageFailure -> Log.e("Amplify.s3", "Upload failed", storageFailure)
        );
    }

    //https://stackoverflow.com/questions/9292954/how-to-make-a-copy-of-a-file-in-android
    public static void copyStream(InputStream in, OutputStream out) throws Exception {
        byte[] buffer = new byte[1024];
        int read;
        while((read = in.read(buffer)) != -1){
            out.write(buffer, 0, read);
        }
    }

    // https://stackoverflow.com/questions/9292954/how-to-make-a-copy-of-a-file-in-android
//    public static void copy(File src, File dst) throws IOException {
//        InputStream in = new FileInputStream(src);
//        try {
//            OutputStream out = new FileOutputStream(dst);
//            try {
//                // Transfer bytes from in to out
//                byte[] buf = new byte[1024];
//                int len;
//                while ((len = in.read(buf)) > 0) {
//                    out.write(buf, 0, len);
//                }
//            } finally {
//                out.close();
//            }
//        } finally {
//            in.close();
//        }
//    }


    public void addListenersToButtons() {
        Button addPic = findViewById(R.id.add_photo_addtask);
        addPic.setOnClickListener((view -> retrieveFile()));
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
