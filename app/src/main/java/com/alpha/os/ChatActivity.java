package com.alpha.os;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class ChatActivity extends Activity {

    private EditText input;
    private TextView messages;
    private Button send;
    private AlphaCore alphaCore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        input = findViewById(R.id.chatInput);
        messages = findViewById(R.id.chatMessages);
        send = findViewById(R.id.chatSend);

        alphaCore = new AlphaCore(this);

        send.setOnClickListener(v -> sendMessage());

        input.setOnEditorActionListener((v, actionId, event) -> {
            sendMessage();
            return true;
        });
    }

    private void sendMessage() {

        String text = input.getText().toString().trim();

        if (text.isEmpty()) {
            return;
        }

        String answer = alphaCore.process(text);

        String oldMessages = messages.getText().toString();

        String newMessages;

        if (oldMessages.isEmpty()) {
            newMessages =
                    "Adiga: " + text +
                    "\n\nAlpha: " + answer;
        } else {
            newMessages =
                    oldMessages +
                    "\n\nAdiga: " + text +
                    "\n\nAlpha: " + answer;
        }

        messages.setText(newMessages);
        input.setText("");
    }

    @Override
    protected void onDestroy() {
        alphaCore = null;
        super.onDestroy();
    }
}
