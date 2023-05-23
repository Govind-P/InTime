package com.example.student_calendar;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class MyAccount extends AppCompatActivity {
    Button logout;
    List<Data> register;
    DatabaseReference databaseReference;
    ValueEventListener valueEventListener;
    TextView navName,navEmail,navPhone;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_account);
        navEmail=findViewById(R.id.navEmail);
        navName=findViewById(R.id.navName);
        navPhone=findViewById(R.id.navPhone);
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = firebaseAuth.getCurrentUser();
        logout= findViewById(R.id.logout);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                firebaseAuth.signOut();
                finish();
                Intent intent = new Intent(MyAccount.this, login.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);

            }
        });
        register=new ArrayList<>();
        databaseReference= FirebaseDatabase.getInstance().getReference("RegisterDetails");
        navEmail.setText(currentUser.getEmail());
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // Retrieve the data
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    // Access individual data objects
                    String data = snapshot.child("email").getValue(String.class);
                    if(data!=null && data.equals(currentUser.getEmail())){
                        navName.setText((CharSequence) snapshot.child("name").getValue(String.class));
                        navPhone.setText((CharSequence) snapshot.child("phone").getValue(String.class));
                    }
                    //register.add(data);
                    // Do something with the retrieved data
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Handle any errors that occur
            }
        });
        //navName.setText(register.get(0).getName());
        //navPhone.setText(register.get(0).getPhone());



    }
}