package com.mdomeck.taskmaster;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.BitmapFactory;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
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
import androidx.core.app.ActivityCompat;

import com.amplifyframework.analytics.AnalyticsEvent;
import com.amplifyframework.api.graphql.model.ModelMutation;
import com.amplifyframework.api.graphql.model.ModelQuery;
import com.amplifyframework.core.Amplify;
import com.amplifyframework.datastore.generated.model.Task;
import com.amplifyframework.datastore.generated.model.Team;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class AddTask extends AppCompatActivity implements TaskAdapter.OnInteractingWithTaskListener {

    String lastFileIUploadedKey;
    Uri imageFromIntent;

    FusedLocationProviderClient locationProviderClient;
    Location currentLocation;
    String addressString;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addtask);

        ArrayList<Team> teams = new ArrayList<>();
        askForPermissionToUseLocation();
        configureLocationServices();
        askForLocation();


        Intent intent = getIntent();
        if (intent.getType() != null) {
            Log.i("Amplify.addPic", "Within image/ this came out" + intent.toString());
            //https://developer.android.com/guide/components/intents-common thanks Paul
            //https://www.programcreek.com/java-api-examples/?class=android.content.Intent&method=getClipData thanks Bade
            imageFromIntent = intent.getClipData().getItemAt(0).getUri();
            Log.i("Amplify.uri", "this is the uri" + imageFromIntent.toString());

            ImageView image = findViewById(R.id.imageLastUploaded);
            image.setImageURI(imageFromIntent);
        }

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
                TextView statusTV = findViewById(R.id.statusText);

                if (lastFileIUploadedKey == null) {

                    File fileCopy = new File(getFilesDir(), "image file");

                    try {
                        InputStream inStream = getContentResolver().openInputStream(imageFromIntent);
                        FileOutputStream out = new FileOutputStream(fileCopy);
                        copyStream(inStream, out);
                    } catch (Exception e) {
                        e.printStackTrace();
                        Log.e("Amplify.pickImage", e.toString());
                    }
                    //File newFileToUpload = new File(imageFromIntent.getPath());
                    uploadFile(fileCopy, fileCopy.getName() + Math.random());
                }

                Task addTask = Task.builder()
                        .title(taskTitleTV.getText().toString())
                        .body(taskDescriptionTV.getText().toString())
                        .state(statusTV.getText().toString())
                        .apartOf(chosenTeam)
                        .location(addressString)
                        .filekey(lastFileIUploadedKey)
                        .build();

                Amplify.API.mutate(ModelMutation.create(addTask),
                        response -> Log.i("Amplify", "successfully added " + addTask.getTitle()),
                        error -> Log.e("amplify", error.toString()));

                toast.show();

                AnalyticsEvent event = AnalyticsEvent.builder()
                        .name("addTask")
                        .addProperty("time", Long.toString(new Date().getTime()))
                        .addProperty("addTask", "added a task")
                        .build();
                Amplify.Analytics.recordEvent(event);


                onBackPressed();

            }

        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }


    public void askForLocation() {

        LocationRequest locationRequest;
        LocationCallback locationCallback;

        locationRequest = LocationRequest.create();
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        locationRequest.setInterval(60000);

        locationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(LocationResult locationResult) {
                if (locationResult == null) {
                    return;
                }
                currentLocation = locationResult.getLastLocation();
                Log.i("Amplify_location", currentLocation.toString());

                Geocoder geocoder = new Geocoder(AddTask.this, Locale.getDefault());
                try {
                    List<Address> addresses = geocoder.getFromLocation(currentLocation.getLatitude(), currentLocation.getLongitude(), 10);
                    Log.i("Amplify_location", addresses.get(0).toString());
                    addressString = addresses.get(0).getAddressLine(0);
                    Log.i("Amplify_location", addressString);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        };

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        locationProviderClient.requestLocationUpdates(locationRequest, locationCallback, getMainLooper());
    }

    public void askForPermissionToUseLocation(){
        requestPermissions(new String[]{Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION}, 2);
    }

    public void configureLocationServices(){
        locationProviderClient = LocationServices.getFusedLocationProviderClient(this);
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
        while ((read = in.read(buffer)) != -1) {
            out.write(buffer, 0, read);
        }
    }

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
