package com.example.moodsmells;

import android.net.Uri;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.auth.User;
import com.google.firebase.storage.FirebaseStorage;

import java.util.ArrayList;

public class FirebaseServices
{

    private static FirebaseServices instance;
    private FirebaseAuth auth;
    private FirebaseFirestore firestore;
    private FirebaseStorage storage;

    public FirebaseServices(){
        auth = FirebaseAuth.getInstance();
        firestore = FirebaseFirestore.getInstance();
        storage = FirebaseStorage.getInstance();

    }

    public FirebaseAuth getAuth() {
        return auth;
    }

    public FirebaseStorage getStorage() {
        return storage;
    }


    public FirebaseFirestore getFirestore() {
        return firestore;
    }


    public static FirebaseServices getInstance(){
        if (instance == null){
            instance = new FirebaseServices();
        }
        return instance;
    }

}