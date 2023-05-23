package com.example.student_calendar;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;

public class login extends AppCompatActivity {
    TextView tv4,tv2,tv3;
    ImageView img;
    Button bt;
    EditText email,pass;
    private FirebaseAuth auth;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        img=findViewById(R.id.imageView);
        tv2=findViewById(R.id.tv2);
        tv3=findViewById(R.id.tv3);
        tv4=findViewById(R.id.tv4);
        bt=findViewById(R.id.bt);
        email=findViewById(R.id.email);
        pass=findViewById(R.id.password);
        auth = FirebaseAuth.getInstance();
        bt.setOnClickListener(view -> {
            String textemail=email.getText().toString();
            String textpass=pass.getText().toString();
            if(textpass.length()==0 && textemail.length()==0){
                pass.setError("Enter Password");
                email.setError("Enter Email");
            }
            else if(textpass.length()==0){
                pass.setError("Enter Password");
            }
            else if(textemail.length()==0 || !Patterns.EMAIL_ADDRESS.matcher(textemail).matches()){
                email.setError("Enter valid Email");
            }
            else{
                auth.signInWithEmailAndPassword(textemail,textpass)
                        .addOnSuccessListener(authResult -> {
                            Toast.makeText(login.this, "Login Successful", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(login.this, MainActivity.class));
                            finish();
                        }).addOnFailureListener(e -> Toast.makeText(login.this, "Login Failed", Toast.LENGTH_SHORT).show());
            }
        });
        tv4.setOnClickListener(view -> startActivity(new Intent(login.this, register.class)));
    }
}