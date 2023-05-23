package com.example.student_calendar;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class Status extends AppCompatActivity {
    TextView status;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_status);
        status=findViewById(R.id.status);

        status.setText("TO BE \n IMPLEMENTED");
    }
}