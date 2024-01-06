package com.example.notespro;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.google.firebase.Timestamp;
import java.util.Date;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.sql.Time;
import java.text.SimpleDateFormat;

public class Utility {

    static void showToast(Context context, String message) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show() ;

    }

    static CollectionReference getCollectionReferenceForNotes()
    {
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        return FirebaseFirestore.getInstance().collection("notes").document(currentUser.getUid()).collection("my notes") ;


    }

    @SuppressLint("SimpleDateFormat")
    static String timestampToString(Timestamp timestamp)
    {

        return new SimpleDateFormat("dd/MM/yyyy").format(timestamp.toDate()) ;

    }



}
