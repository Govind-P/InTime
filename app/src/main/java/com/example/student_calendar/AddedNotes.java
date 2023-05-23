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

public class AddedNotes extends AppCompatActivity {
    RecyclerView rv;
    List<Note> notelist;
    DatabaseReference databaseReference;
    ValueEventListener valueEventListener;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = firebaseAuth.getCurrentUser();
        setContentView(R.layout.activity_added_notes);
        rv=findViewById(R.id.rvnotes);
        GridLayoutManager gridLayoutManager=new GridLayoutManager(AddedNotes.this,1);
        rv.setLayoutManager(gridLayoutManager);
        AlertDialog.Builder builder=new AlertDialog.Builder(AddedNotes.this);
        builder.setCancelable(false);
        builder.setView(R.layout.progessbar);
        AlertDialog dialog= builder.create();
        dialog.show();

        notelist=new ArrayList<>();
        NoteAdapter adapter=new NoteAdapter(AddedNotes.this,notelist);
        rv.setAdapter(adapter);
        databaseReference= FirebaseDatabase.getInstance().getReference("NoteDetails");
        dialog.show();

        valueEventListener=databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                notelist.clear();
                for(DataSnapshot itemSnapshot:snapshot.getChildren()){
                    String data = itemSnapshot.child("email").getValue(String.class);
                    if(data!=null && data.equals(currentUser.getEmail())) {
                        Note note=itemSnapshot.getValue(Note.class);
                        note.setKey(itemSnapshot.getKey());
                        notelist.add(note);
                    }
                }
                adapter.notifyDataSetChanged();
                if(notelist.isEmpty()){
                    setContentView(R.layout.activity_emptynote);
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