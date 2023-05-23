package com.example.student_calendar;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class CompleteDetails extends AppCompatActivity {
    TextView titledetail;
    ImageView notify,timedue,notifydate;
    TextView duetime,notifydetails,duedate,desc,sub,priority;
    ImageView uploadImage;

    TextView pdf;
    Button restore,delete;
    String imageurl="";
    String key="";
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complete_details);
        titledetail=findViewById(R.id.ctitledetail);
        notifydate=findViewById(R.id.cnotifydate);
        timedue=findViewById(R.id.cnotifytimedue);
        notify=findViewById(R.id.ctimenotify);
        uploadImage=findViewById(R.id.cuploadImage);
        restore=findViewById(R.id.crestore);
        delete=findViewById(R.id.cdelete);
        desc=findViewById(R.id.cdecdetails);
        sub=findViewById(R.id.csubdetails);
        priority=findViewById(R.id.cpriodetails);
        duedate=findViewById(R.id.cduedetails);
        duetime=findViewById(R.id.cduetimedetails);
        notifydetails=findViewById(R.id.cnotifydetails);




        Bundle bundle=getIntent().getExtras();
        if(bundle!=null){
            key=bundle.getString("title");
            titledetail.setText(bundle.getString("title"));
            desc.setText(bundle.getString("desc"));
            sub.setText(bundle.getString("sub"));
            priority.setText(bundle.getString("prio"));
            imageurl = bundle.getString("Image");
            key= bundle.getString("key");
            Glide.with(this).load(bundle.getString("Image")).into(uploadImage);
            if(bundle.getString("due").equals("")){
                duedate.setText("Not Set");
            }
            else{
                duedate.setText(bundle.getString("due"));
            }
            if(bundle.getString("duetime").equals("")){
                duetime.setText("Not Set");
            }
            else{
                duetime.setText(bundle.getString("duetime"));
            }
            if(bundle.getString("notify").equals("")){
                notifydetails.setText("Not Set");
            }
            else{
                notifydetails.setText(bundle.getString("notify"));
            }

        }
        delete.setOnClickListener(view -> {
            final DatabaseReference reference = FirebaseDatabase.getInstance().getReference("TaskDetails");
            if(imageurl==null){
                reference.child(key).removeValue();
                Toast.makeText(CompleteDetails.this, "Deleted", Toast.LENGTH_SHORT).show();
                finish();
            }
            else{
                FirebaseStorage storage = FirebaseStorage.getInstance();
                StorageReference storageReference = storage.getReferenceFromUrl(imageurl);
                storageReference.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        reference.child(key).removeValue();
                        Toast.makeText(CompleteDetails.this, "Deleted", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                });
            }

        });
        }
    }