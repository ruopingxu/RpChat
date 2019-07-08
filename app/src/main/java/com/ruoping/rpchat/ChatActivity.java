package com.ruoping.rpchat;
import static com.ruoping.rpchat.Constants.USERNAME_KEY;
import static com.ruoping.rpchat.Constants.USERS_COLLECTION;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;

import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ChatActivity extends AppCompatActivity {
    private FirebaseFirestore db;

    private final AccountController accountController = new AccountController();
    SharedPreferences sharedPreferences;
    String username;
    List<String> messages = new ArrayList<>();

    TextView chatTitle;
    RecyclerView chatHistory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // show content
        setContentView(R.layout.activity_chat);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        chatTitle = findViewById(R.id.chat_title);

        // load previous data from Firebase
        db = FirebaseFirestore.getInstance();
        Intent intent = getIntent();
        username = intent.getStringExtra(Constants.USERNAME_KEY);

        fetchUserInfo(username);


    }

    private void fetchUserInfo(final String username) {
        Task<QuerySnapshot> query = db.collection(USERS_COLLECTION).whereEqualTo(USERNAME_KEY, username).get();
        query.addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                if (queryDocumentSnapshots.isEmpty()) {
                    Map<String, Object> newUser = new HashMap<>();
                    newUser.put(USERNAME_KEY, username);
                    db.collection(USERS_COLLECTION).add(newUser);

                    accountController.addNewUser(db, newUser);
                    chatTitle.setText("Created new user" + username);
                    return;
                }
                QueryDocumentSnapshot query = queryDocumentSnapshots.iterator().next();
                String userData = query.getData().get(USERNAME_KEY).toString();
                chatTitle.setText(userData);
            }
        });

    }

}
