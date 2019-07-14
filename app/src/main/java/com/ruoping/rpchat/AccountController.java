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

/**
 * Class for handling saving and fetching User account info.
 */
public class AccountController {
    private final String TAG = "AccountController";

    protected FirebaseFirestore db;
    SharedPreferences sharedPrefs;

    public AccountController() {
//        sharedPrefs = getPreferences(MODE_PRIVATE);
    }

    /**
     * Store user's data object on device
     *
     * @param username
     * @param snapshot
     */
    protected void storeUserDataOnDevice(String username, QueryDocumentSnapshot snapshot) {
        // TODO: Find where to put the data, if needed
    }

    /**
     * Save a new user to Firebase
     * Does nothing if user already exists
     *
     * @param db:   instance of database being used
     * @param user: map object containing all user data
     */
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

    /**
     * Fetch user's data object from Firebase
     *
     * @param username
     */
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
