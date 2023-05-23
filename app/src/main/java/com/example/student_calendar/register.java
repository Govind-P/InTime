package com.example.student_calendar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class register extends AppCompatActivity {
    TextView text,tv,signin;
    EditText name,email,phone,pass,confirm;
    Button reg;
    private FirebaseAuth auth;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);
        text=findViewById(R.id.textView2);
        tv=findViewById(R.id.textView4);
        signin=findViewById(R.id.textView5);
        reg=findViewById(R.id.button);
        name=findViewById(R.id.editTextTextPersonName);
        email=findViewById(R.id.email);
        pass=findViewById(R.id.password);
        confirm=findViewById(R.id.confirm);
        phone=findViewById(R.id.editTextPhone);
        auth = FirebaseAuth.getInstance();
        FirebaseApp.initializeApp(this);

        reg.setOnClickListener(view ->{
            String Name=name.getText().toString().trim();
            String Phone=phone.getText().toString().trim();
            String Email=email.getText().toString().trim();
            String password=pass.getText().toString().trim();
            String Passconfirm=confirm.getText().toString().trim();
            if(Name.equals("")){
                name.setError("Enter a valid name");
            }
            else if(Phone.equals("") || Phone.length()!=10){
                phone.setError("Enter a valid phone number");
            }
            else if(Email.equals("")){
                email.setError("Enter a valid email");
            }
            else if(password.equals("") || !isvalid(password)){
                pass.setError("Enter a valid password.Password contain minimum 8 characters(include upper and lower alphabet,special character and digits.No space character.");
            }
            else if(!Passconfirm.equals(password)){
                confirm.setError("Enter correct password");
            }
            else{
                Data data=new Data(Name,Email,Phone,password);
                Task<AuthResult> userWithEmailAndPassword = auth.createUserWithEmailAndPassword(Email,password);
                FirebaseDatabase.getInstance().getReference().child("RegisterDetails").push()
                        .setValue(data).addOnCompleteListener(task -> {
                            if(task.isSuccessful()){
                                Toast.makeText(register.this, "Registration Successful", Toast.LENGTH_SHORT).show();
                                finish();
                            }
                        }).addOnFailureListener(e -> Toast.makeText(register.this, e.getMessage(),Toast.LENGTH_SHORT).show());
            }
        });
        signin.setOnClickListener(view -> startActivity(new Intent(register.this,login.class)));
    }

    public boolean isvalid(String s){
        if(s.length()<8){
            return false;
        }
        int u=0,l=0,c=0,d = 0;
        for(int i=0;i<s.length();i++){
            if(s.charAt(i)<='Z' && 'A' <= s.charAt(i) && u==0){
                u=1;
            }
            else if(s.charAt(i)<='z' && 'a' <= s.charAt(i) && l==0){
                l=1;
            }
            else if(s.charAt(i)<='9' && '0'<=s.charAt(i) && d==0){
                d=1;
            }
            /*else if(s.charAt(i)=){
                return false;
            }*/
            else{
                c=1;
            }
        }
        return u == 1 && l == 1 && c == 1 && d == 1;
    }

}