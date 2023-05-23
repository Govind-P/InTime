package com.example.student_calendar;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class NoteDetails extends AppCompatActivity {
    TextView pdf;
    String pdfUrl;
    TextView desc,sub,titledetail;
    Button delete;
    String key;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_details);
        delete=findViewById(R.id.deletenote);
        desc=findViewById(R.id.notedescdetails);
        sub=findViewById(R.id.notesubdetails);
        titledetail=findViewById(R.id.notetitledetail);
        pdf=findViewById(R.id.pdfnote);

        Bundle bundle=getIntent().getExtras();

        if(bundle!=null){
            titledetail.setText(bundle.getString("title"));
            desc.setText(bundle.getString("desc"));
            sub.setText(bundle.getString("sub"));
            key=bundle.getString("key");
            pdfUrl= bundle.getString("url");
        }

        pdf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(NoteDetails.this,PDFViewerActivity.class);
                intent.putExtra("filePath",bundle.getString("url"));
                startActivity(intent);
            }
        });
        delete.setOnClickListener(view -> {
            final DatabaseReference reference = FirebaseDatabase.getInstance().getReference("NoteDetails");
            if(pdfUrl==null){
                reference.child(key).removeValue();
                Toast.makeText(NoteDetails.this, "Deleted", Toast.LENGTH_SHORT).show();
                finish();
            }
            else{
                FirebaseStorage storage = FirebaseStorage.getInstance();
                StorageReference storageReference = storage.getReferenceFromUrl(pdfUrl);
                storageReference.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        reference.child(key).removeValue();
                        Toast.makeText(NoteDetails.this, "Deleted", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                });
            }

        });
    }
}