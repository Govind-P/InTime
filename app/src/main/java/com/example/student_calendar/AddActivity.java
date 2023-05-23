package com.example.student_calendar;

import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.timepicker.MaterialTimePicker;
import com.google.android.material.timepicker.TimeFormat;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class AddActivity extends AppCompatActivity {
    ImageView timenotify,timedue,notifydate;
    EditText topic,desc,sub,priority;
    ImageView uploadImage;
    Button save;
    Uri imageUri;
    TextView duetime,notify,duedate;
    FirebaseStorage storage = FirebaseStorage.getInstance();
    StorageReference storageRef = storage.getReference();
    private MaterialTimePicker timePicker;
    private MaterialDatePicker datePicker;
    String downloadUrl,imageurl;
    private int requestCode;
    private int resultCode;

    @SuppressLint({"Range", "MissingInflatedId"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = firebaseAuth.getCurrentUser();
        duetime=findViewById(R.id.Duetime);
        notify=findViewById(R.id.notify);
        duedate=findViewById(R.id.Duedate);
        timenotify=findViewById(R.id.notifytime);
        timedue=findViewById(R.id.notifytimedue);
        notifydate=findViewById(R.id.notifydate);
        topic=findViewById(R.id.uploadTopic);
        desc=findViewById(R.id.uploadDesc);
        sub=findViewById(R.id.uploadsub);
        priority=findViewById(R.id.priority);
        uploadImage=findViewById(R.id.uploadImage);
        save=findViewById(R.id.saveButton);
        FirebaseApp.initializeApp(this);



        timenotify.setOnClickListener(view -> {
            timePicker = new MaterialTimePicker.Builder()
                    .setTimeFormat(TimeFormat.CLOCK_12H)
                    .setHour(12)
                    .setMinute(0)
                    .setTitleText("Select Notify time")
                    .build();
            timePicker.show(getSupportFragmentManager(), "androidknowledge");
            timePicker.addOnPositiveButtonClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    notify.setText(timePicker.getHour()+":" + timePicker.getMinute());
                }
            });
        });
        timedue.setOnClickListener(view -> {
            timePicker = new MaterialTimePicker.Builder()
                    .setTimeFormat(TimeFormat.CLOCK_12H)
                    .setHour(12)
                    .setMinute(0)
                    .setTitleText("Select Notify time")
                    .build();
            timePicker.show(getSupportFragmentManager(), "androidknowledge");
            timePicker.addOnPositiveButtonClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    duetime.setText(timePicker.getHour()+":" + timePicker.getMinute());
                }
            });
        });
        final Calendar calendar = Calendar.getInstance();
        final int year = calendar.get(Calendar.YEAR);
        final int month = calendar.get(Calendar.MONTH);
        final int day = calendar.get(Calendar.DAY_OF_MONTH);
        notifydate.setOnClickListener(view -> {
            DatePickerDialog dialog = new DatePickerDialog(AddActivity.this, new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                    month = month+1;
                    String date ="";
                    if(month<10){
                        date = dayOfMonth+"/"+"0"+month+"/"+year;
                    }
                    else{
                        date = dayOfMonth+"/"+month+"/"+year;
                    }

                    duedate.setText(date);
                }
            },year, month,day);
            dialog.show();
        });

        uploadImage.setOnClickListener(view -> {
            Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
            intent.setType("image/*");
            startActivityForResult(intent, 2);
        });

        save.setOnClickListener(view -> {
            String email= currentUser.getEmail();
            String Topic=topic.getText().toString().trim();
            String Desc=desc.getText().toString().trim();
            String Sub=sub.getText().toString().trim();
            String Prio=priority.getText().toString().trim();
            String dueDate=duedate.getText().toString().trim();
            String dueTime=duetime.getText().toString().trim();
            String Notify=notify.getText().toString().trim();
            String status="Pending";

            if(Topic.equals("") || Desc.equals("") || Sub.equals("") || Prio.equals("") ){
                Toast.makeText(AddActivity.this,"Enter Topic,Description,Subject and Priority",Toast.LENGTH_SHORT).show();
            }
            else{
                Taskclass taskclass;
                taskclass = new Taskclass(email,Topic,Desc,Sub,Prio,dueDate,dueTime,Notify,imageurl,status);
                DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("TaskDetails");
                FirebaseDatabase.getInstance().getReference().child("TaskDetails").push()
                        .setValue(taskclass).addOnCompleteListener(task -> {
                            if(task.isSuccessful()){
                                Toast.makeText(AddActivity.this, "Data Saved", Toast.LENGTH_SHORT).show();
                                        Date date=null;
                                        try {
                                            date= new SimpleDateFormat("dd/MM/yyyy").parse(dueDate);
                                        } catch (ParseException e) {
                                            throw new RuntimeException(e);
                                        }
                                        int days= date.getDay()-2;
                                        int months=date.getMonth()+1;
                                        int years=date.getYear();
                                        DateFormat dateFormat = new SimpleDateFormat("hh:mm");
                                        Date d=null;
                                        try {
                                             d= dateFormat.parse(dueTime);
                                        } catch (ParseException e) {
                                            throw new RuntimeException(e);
                                        }
                                        int hour=d.getHours();
                                        int minute=d.getMinutes();
                                        finish();
                                        setNotificationAlarm(hour, minute, days,months,years);
                            }
                        }).addOnFailureListener(e -> Toast.makeText(AddActivity.this, e.getMessage(),Toast.LENGTH_SHORT).show());
            }
        });

    }

    private void setNotificationAlarm(int hour, int minute, int day,int month,int year) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_MONTH,day);
        calendar.set(Calendar.MONTH, month);
        calendar.set(Calendar.YEAR,year);
        calendar.set(Calendar.HOUR_OF_DAY, hour);
        calendar.set(Calendar.MINUTE, minute);
        calendar.set(Calendar.SECOND, 0);

        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(this,AlarmReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0, intent, 0);
        alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
    }


    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 2 && resultCode == RESULT_OK && data != null && data.getData() != null) {
            imageUri = data.getData();
            uploadImageToFirebase(imageUri);
        }
    }
    private void uploadImageToFirebase(Uri imageUri) {
        String filename = "image_" + System.currentTimeMillis() + ".jpg";
        StorageReference imageRef = storageRef.child("images/" + filename);
        UploadTask uploadTask = imageRef.putFile(imageUri);
        uploadTask.addOnSuccessListener(taskSnapshot -> {
            imageRef.getDownloadUrl().addOnSuccessListener(uri -> {

                imageurl = uri.toString();
                Toast.makeText(AddActivity.this, "Upload Successful", Toast.LENGTH_SHORT).show();
            });
        }).addOnFailureListener(e -> {
            Toast.makeText(AddActivity.this, "Upload Unsuccessful", Toast.LENGTH_SHORT).show();
        }).addOnProgressListener(snapshot -> {
            double progress = (100.0 * snapshot.getBytesTransferred()) / snapshot.getTotalByteCount();

        });
    }


}