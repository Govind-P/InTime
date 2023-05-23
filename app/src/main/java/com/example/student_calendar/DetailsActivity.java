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
import com.squareup.picasso.Picasso;

public class DetailsActivity extends AppCompatActivity {
    TextView titledetail;
    ImageView notify,timedue,notifydate;
    TextView duetime,notifydetails,duedate,desc,sub,priority;
    ImageView uploadImage;

    Button edit,del;
    String imageurl="",key="";
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        titledetail=findViewById(R.id.titledetail);
        notifydate=findViewById(R.id.notifydate);
        timedue=findViewById(R.id.notifytimedue);
        notify=findViewById(R.id.timenotify);
        uploadImage=findViewById(R.id.uploadImage);
        edit=findViewById(R.id.editButton);
        desc=findViewById(R.id.decdetails);
        sub=findViewById(R.id.subdetails);
        priority=findViewById(R.id.priodetails);
        duedate=findViewById(R.id.duedetails);
        duetime=findViewById(R.id.duetimedetails);
        notifydetails=findViewById(R.id.notifydetails);
        del=findViewById(R.id.delButton);



        Bundle bundle=getIntent().getExtras();
        if(bundle!=null){
            titledetail.setText(bundle.getString("title"));
            desc.setText(bundle.getString("desc"));
            sub.setText(bundle.getString("sub"));
            priority.setText(bundle.getString("prio"));
            imageurl = bundle.getString("image");
            key=bundle.getString("key");
            Glide.with(this).load(bundle.getString("image")).into(uploadImage);
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
            edit.setOnClickListener(view -> {
                Intent intent=new Intent(DetailsActivity.this, UpdateActivity.class);
                intent.putExtra("title",titledetail.getText());
                intent.putExtra("desc",desc.getText());
                intent.putExtra("sub",sub.getText());
                intent.putExtra("prio",priority.getText());
                intent.putExtra("due",duedate.getText());
                intent.putExtra("duetime",duetime.getText());
                intent.putExtra("notify",notifydetails.getText());
                intent.putExtra("image",imageurl);
                startActivity(intent);
            });
            del.setOnClickListener(view -> {
                final DatabaseReference reference = FirebaseDatabase.getInstance().getReference("TaskDetails");
                if(imageurl==null){
                    reference.child(key).removeValue();
                    Toast.makeText(DetailsActivity.this, "Deleted", Toast.LENGTH_SHORT).show();
                    finish();
                }
                else{
                    FirebaseStorage storage = FirebaseStorage.getInstance();
                    StorageReference storageReference = storage.getReferenceFromUrl(imageurl);
                    storageReference.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {
                            reference.child(key).removeValue();
                            Toast.makeText(DetailsActivity.this, "Deleted", Toast.LENGTH_SHORT).show();
                            finish();
                        }
                    });
                }

            });

        }
    }
}