package com.example.student_calendar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.os.Bundle;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class Completed extends AppCompatActivity {
    RecyclerView rv;
    List<Taskclass> ctask;
    DatabaseReference databaseReference;
    ValueEventListener valueEventListener;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_completed);
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = firebaseAuth.getCurrentUser();
        rv=findViewById(R.id.crv);
        GridLayoutManager gridLayoutManager=new GridLayoutManager(Completed.this,1);
        rv.setLayoutManager(gridLayoutManager);
        AlertDialog.Builder builder=new AlertDialog.Builder(Completed.this);
        builder.setCancelable(false);
        builder.setView(R.layout.progessbar);
        AlertDialog dialog= builder.create();
        dialog.show();

        ctask=new ArrayList<>();
        CompletedAdapter adapter=new CompletedAdapter(Completed.this,ctask);
        rv.setAdapter(adapter);
        databaseReference= FirebaseDatabase.getInstance().getReference("TaskDetails");
        dialog.show();

        valueEventListener=databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ctask.clear();
                for(DataSnapshot itemSnapshot:snapshot.getChildren()){
                    String data = itemSnapshot.child("email").getValue(String.class);
                    String datastatus = itemSnapshot.child("status").getValue(String.class);
                    if((data!=null && data.equals(currentUser.getEmail())) && (datastatus!=null && datastatus.equals("Completed"))) {
                        Taskclass taskc = itemSnapshot.getValue(Taskclass.class);
                        taskc.setKey(itemSnapshot.getKey());
                        ctask.add(taskc);
                    }
                }
                adapter.notifyDataSetChanged();
                if(ctask.isEmpty()){
                    setContentView(R.layout.activity_empty);
                }
                dialog.dismiss();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                dialog.dismiss();
            }
        });
    }
}