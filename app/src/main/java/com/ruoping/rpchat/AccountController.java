package com.ruoping.rpchat;

import android.content.SharedPreferences;
import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.Map;

import static android.content.Context.MODE_PRIVATE;

public class AccountController {
    private final String TAG = "AccountController";

    protected FirebaseFirestore db;
    SharedPreferences sharedPrefs;

    public AccountController() {
//        sharedPrefs = getPreferences(MODE_PRIVATE);
    }

    protected void storeUserDataOnDevice(String username, QueryDocumentSnapshot snapshot) {

    }


    protected void addNewUser(FirebaseFirestore db, Map<String, Object> user) {
        db.collection("users")
                .add(user)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Log.d(TAG, "Document snapshot added with ID: " + documentReference.getId());
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Could not add document", e);
                    }
                });
    }

    protected void getUserData(String username) {
        QueryDocumentSnapshot data;
        Task<QuerySnapshot> query = db.collection("users").whereEqualTo("username", username).get();
        query.addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
//                data = queryDocumentSnapshots.iterator().next();
            }
        });

    }
}
