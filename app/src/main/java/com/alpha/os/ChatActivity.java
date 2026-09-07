package com.alpha.os;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

public class ChatActivity extends Activity {

    private LinearLayout messages;
    private EditText input;
    private ScrollView scroll;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        messages = findViewById(R.id.chatMessages);
        input = findViewById(R.id.chatInput);
        scroll = findViewById(R.id.chatScroll);

        Button send = findViewById(R.id.chatSend);
        Button mic = findViewById(R.id.chatMic);

        send.setOnClickListener(v -> sendMessage());

        mic.setOnClickListener(v -> {
            addMessage("Alpha", "Dhageyso... 🎙️");
        });
    }

    private void sendMessage() {
        String text = input.getText().toString().trim();

        if (text.isEmpty()) return;

        addMessage("Adiga", text);

        input.setText("");

        addMessage(
            "Alpha",
            "Haa, waan ku fahmay. Waxaan diyaar u ahay inaan kaa caawiyo."
        );
    }

    private void addMessage(String sender, String text) {
        TextView message = new TextView(this);

        message.setText(sender + ": " + text);
        message.setTextColor(0xFFFFFFFF);
        message.setTextSize(17);
        message.setPadding(16, 16, 16, 16);

        LinearLayout.LayoutParams params =
            new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            );

        params.setMargins(0, 8, 0, 8);
        message.setLayoutParams(params);

        messages.addView(message);

        scroll.post(() -> scroll.fullScroll(ScrollView.FOCUS_DOWN));
    }
}
