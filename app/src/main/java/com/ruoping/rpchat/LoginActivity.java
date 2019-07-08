package com.ruoping.rpchat;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity {
    private final String TAG = "LoginActivity";


    EditText usernameInput;
    Button usernameInputButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_page);

        usernameInput = findViewById(R.id.username_input);
        usernameInputButton = findViewById(R.id.username_input_button);

        usernameInputButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent startChatIntent = new Intent(LoginActivity.this, ChatActivity.class);
                String usernameText = usernameInput.getText().toString();
                startChatIntent.putExtra(Constants.USERNAME_KEY, usernameText);
                startActivity(startChatIntent);
            }
        });


    }
}