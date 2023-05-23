package com.example.student_calendar;


import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
public class MainActivity extends AppCompatActivity {
    CardView addtask,task,completed,addnote,notes,status,account;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = firebaseAuth.getCurrentUser();
        addtask=findViewById(R.id.addtask);
        task=findViewById(R.id.task);
        completed=findViewById(R.id.completed);
        addnote=findViewById(R.id.addnote);
        notes=findViewById(R.id.notes);
        account=findViewById(R.id.account);
        status=findViewById(R.id.status);
        if (currentUser != null){
            addtask.setOnClickListener(view -> {
                startActivity(new Intent(MainActivity.this,AddActivity.class));
            });
            task.setOnClickListener(view -> {
                startActivity(new Intent(MainActivity.this,AddedTask.class));
            });
            completed.setOnClickListener(view -> {
                startActivity(new Intent(MainActivity.this, Completed.class));
            });
            addnote.setOnClickListener(view -> {
                startActivity(new Intent(MainActivity.this,Notes.class));
            });
            notes.setOnClickListener(view -> {
                startActivity(new Intent(MainActivity.this,AddedNotes.class));
            });
            status.setOnClickListener(view -> {
                startActivity(new Intent(MainActivity.this,Status.class));
            });
            account.setOnClickListener(view -> {
                startActivity(new Intent(MainActivity.this,MyAccount.class));
            });
        }
        else{
            finish();
            startActivity(new Intent(MainActivity.this,login.class));
        }

    }


}