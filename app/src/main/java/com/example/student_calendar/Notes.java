package com.example.student_calendar;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class Notes extends AppCompatActivity {
    EditText topic,desc,sub;
    TextView pdf;
    Button upload;
    ProgressBar progress;
    Uri pdfUri;
    FirebaseStorage storage = FirebaseStorage.getInstance();
    StorageReference storageRef = storage.getReference();
    String downloadUrl="";


    @SuppressLint({"Range", "MissingInflatedId", "SuspiciousIndentation"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notes);
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = firebaseAuth.getCurrentUser();

        topic=findViewById(R.id.uploadnote);
        desc=findViewById(R.id.uploadDescri);
        sub=findViewById(R.id.uploadsubj);
        pdf=findViewById(R.id.pdfnotes);
        upload=findViewById(R.id.upload);
        FirebaseApp.initializeApp(this);
        AlertDialog.Builder builder=new AlertDialog.Builder(Notes.this);
        builder.setCancelable(false);
        builder.setView(R.layout.progressbar_save);

        pdf.setOnClickListener(view -> {
            Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
            intent.setType("application/pdf");
            startActivityForResult(intent, 1);
        });

        upload.setOnClickListener(view -> {
            String email= currentUser.getEmail();
            String Topic=topic.getText().toString().trim();
            String Desc=desc.getText().toString().trim();
            String Sub=sub.getText().toString().trim();


            if(Topic.equals("") || Desc.equals("") || Sub.equals("") || downloadUrl.equals("") ){
                Toast.makeText(Notes.this,"Enter Topic,Description and Subject",Toast.LENGTH_SHORT).show();
            }

            else{
                AlertDialog dialog= builder.create();
                dialog.show();
                Note note;
                note = new Note(email,Topic,Desc,Sub,downloadUrl);
                    FirebaseDatabase.getInstance().getReference().child("NoteDetails").push()
                        .setValue(note).addOnCompleteListener(task -> {
                            if(task.isSuccessful()){
                                dialog.dismiss();
                                Toast.makeText(Notes.this, "Data saved", Toast.LENGTH_SHORT).show();
                                finish();
                            }
                        }).addOnFailureListener(e -> Toast.makeText(Notes.this, e.getMessage(),Toast.LENGTH_SHORT).show());
            }
        });

    }
    private void uploadPdfToFirebase(Uri pdfUri) {
        String filename = "pdf_" + System.currentTimeMillis() + ".pdf";
        StorageReference pdfRef = storageRef.child("pdfs/" + filename);
        UploadTask uploadTask = pdfRef.putFile(pdfUri);
        uploadTask.addOnSuccessListener(taskSnapshot -> {
            pdfRef.getDownloadUrl().addOnSuccessListener(uri -> {
                downloadUrl = uri.toString();
                Toast.makeText(Notes.this, "Upload Successful", Toast.LENGTH_SHORT).show();
            });
        }).addOnFailureListener(e -> {
            Toast.makeText(Notes.this, "Upload Unsuccessful", Toast.LENGTH_SHORT).show();
        }).addOnProgressListener(snapshot -> {
            int progress = (int) ((100.0 * snapshot.getBytesTransferred()) / snapshot.getTotalByteCount());

        });
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK && data != null && data.getData() != null) {
            pdfUri = data.getData();
            uploadPdfToFirebase(pdfUri);
        }
    }

}