package com.example.notespro;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.format.Time;
import android.widget.EditText;
import android.widget.ImageButton;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.DocumentReference;


public class NoteDetailActivity extends AppCompatActivity {

    EditText titleEditText, contentEditText ;
    ImageButton saveNoteBtn ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_detail);

        titleEditText = findViewById(R.id.notes_title_text) ;
        contentEditText = findViewById(R.id.notes_content_text) ;
        saveNoteBtn = findViewById(R.id.save_note_btn) ;

        saveNoteBtn.setOnClickListener((v) -> saveNote() );



    }

    void saveNote()
    {
        String noteTitle = titleEditText.getText().toString() ;
        String noteContent = contentEditText.getText().toString() ;
        if(noteTitle == null || noteTitle.isEmpty() )
        {
            titleEditText.setError("Title is required");
            return ;
        }


        Note note = new Note() ;
        note.setTitle(noteTitle) ;
        note.setContent(noteContent) ;
        note.setTimestamp(Timestamp.now());

        saveNoteToFirebase(note);

    }

    void saveNoteToFirebase(Note note)
    {
        DocumentReference documentReference ;
        documentReference = Utility.getCollectionReferenceForNotes().document() ;
        documentReference.set(note).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {

                if(task.isSuccessful())
                {
                    // note added successfully
                    Utility.showToast(NoteDetailActivity.this, "Note added successfully");
                }
                else
                {   // Failed to add note
                    Utility.showToast(NoteDetailActivity.this, "Failed while adding note");

                }

            }
        });


    }






}