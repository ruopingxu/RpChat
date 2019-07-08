package com.ruoping.rpchat;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.ruoping.rpchat.Constants.USERNAME_KEY;
import static com.ruoping.rpchat.Constants.USERS_COLLECTION;

public class ChatActivity extends AppCompatActivity {
    private FirebaseFirestore db;

    private final AccountController accountController = new AccountController();
    String username;
    List<String> messages = new ArrayList<>();

    TextView chatTitle;
    RecyclerView chatHistoryView;
    RecyclerView.Adapter chatAdapter;
    RecyclerView.LayoutManager chatLayoutManager;
    Button sendButton;
    EditText messageInput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // setup content
        setContentView(R.layout.activity_chat);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        chatTitle = findViewById(R.id.chat_title);

        setupChatHistory();

        messageInput = findViewById(R.id.input_text_msg);
        sendButton = findViewById(R.id.send_msg_button);

        setupListeners();

        // load previous data from Firebase
        db = FirebaseFirestore.getInstance();
        Intent intent = getIntent();
        username = intent.getStringExtra(Constants.USERNAME_KEY);

        fetchUserInfo(username);

    }

    private void setupChatHistory() {
        chatHistoryView = findViewById(R.id.chat_history);
        chatHistoryView.setHasFixedSize(true);

        chatLayoutManager = new LinearLayoutManager(this);
        chatHistoryView.setLayoutManager(chatLayoutManager);

        chatAdapter = new ConversationAdapter(messages);
        chatHistoryView.setAdapter(chatAdapter);
    }
    private void setupListeners(){
        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendNewMessage();
            }
        });

        messageInput.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int i, KeyEvent keyEvent) {
                if (keyEvent.getAction() == KeyEvent.ACTION_DOWN && keyEvent.getKeyCode() == KeyEvent.KEYCODE_ENTER){
                    sendNewMessage();
                    return true;
                }
                return false;
            }
        });
    }

    private void sendNewMessage() {
        String newMessage = messageInput.getText().toString();
        messages.add(newMessage);
        chatAdapter.notifyItemInserted(messages.size()-1);
        messageInput.getText().clear();
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
