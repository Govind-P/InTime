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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

public class AddedTask extends AppCompatActivity {
    RecyclerView rv;
    List<Taskclass> datalist;
    DatabaseReference databaseReference;
    ValueEventListener valueEventListener;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_added_task);
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = firebaseAuth.getCurrentUser();
        rv=findViewById(R.id.recyclerview);
        GridLayoutManager gridLayoutManager=new GridLayoutManager(AddedTask.this,1);
        rv.setLayoutManager(gridLayoutManager);
        AlertDialog.Builder builder=new AlertDialog.Builder(AddedTask.this);
        builder.setCancelable(false);
        builder.setView(R.layout.progessbar);
        AlertDialog dialog= builder.create();
        dialog.show();

        datalist=new ArrayList<>();
        MyAdapter adapter=new MyAdapter(AddedTask.this,datalist);
        rv.setAdapter(adapter);
        databaseReference= FirebaseDatabase.getInstance().getReference("TaskDetails");
        dialog.show();

        valueEventListener=databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                datalist.clear();
                for(DataSnapshot itemSnapshot:snapshot.getChildren()){
                    String data = itemSnapshot.child("email").getValue(String.class);
                    String datastatus = itemSnapshot.child("status").getValue(String.class);
                    if((data!=null && data.equals(currentUser.getEmail())) && (datastatus!=null && datastatus.equals("Pending"))) {
                        Taskclass taskclass = itemSnapshot.getValue(Taskclass.class);
                        taskclass.setKey(itemSnapshot.getKey());
                        datalist.add(taskclass);
                    }
                }
                Collections.sort(datalist, new Comparator<Taskclass>() {
                    @Override
                    public int compare(Taskclass obj1, Taskclass obj2) {
                        Date date1= null;
                        try {
                            date1 = new SimpleDateFormat("dd/MM/yyyy").parse(obj1.getDueDate());
                        } catch (ParseException e) {
                            throw new RuntimeException(e);
                        }
                        Date date2= null;
                        try {
                            date2 = new SimpleDateFormat("dd/MM/yyyy").parse(obj2.getDueDate());
                        } catch (ParseException e) {
                            throw new RuntimeException(e);
                        }
                        return date1.compareTo(date2);
                    }
                });
                adapter.notifyDataSetChanged();
                if(datalist.isEmpty()){
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