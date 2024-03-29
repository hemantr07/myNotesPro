package com.example.notespro;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.widget.ImageButton;
import android.widget.TextView;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.Query;

public class MainActivity extends AppCompatActivity {

    FloatingActionButton addNoteBtn ;

    RecyclerView recyclerView ;
    ImageButton menuBtn ;
    NoteAdapter noteAdapter ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        menuBtn = findViewById(R.id.menu_btn) ;
        recyclerView = findViewById(R.id.recycler_view) ;



        addNoteBtn = findViewById(R.id.add_note_btn) ;
        addNoteBtn.setOnClickListener((v) -> startActivity(new Intent(MainActivity.this, NoteDetailActivity.class)));

        menuBtn.setOnClickListener((v) -> showMenu() );

        setUpRecyclerView() ;

    }

    void showMenu()
    {
        // Display Menu

    }

    void setUpRecyclerView()
    {
        Query query = Utility.getCollectionReferenceForNotes().orderBy("timestamp", Query.Direction.DESCENDING) ;
        FirestoreRecyclerOptions<Note> options = new FirestoreRecyclerOptions.Builder<Note>().setQuery(query,Note.class).build() ;
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        noteAdapter = new NoteAdapter(options, this) ;
        recyclerView.setAdapter(noteAdapter);


    }

//    @Override
//    protected void onStart() {
//        super.onStart();
//        noteAdapter.startListening();
//    }
//
//    @Override
//    protected void onStop() {
//        super.onStop();
//        noteAdapter.stopListening();
//
//    }
//
//    @Override
//    protected void onResume() {
//        super.onResume();
//        noteAdapter.notifyDataSetChanged();
//    }
}