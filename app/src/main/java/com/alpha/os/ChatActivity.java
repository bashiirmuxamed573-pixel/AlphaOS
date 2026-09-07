package com.alpha.os;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

public class ChatActivity extends Activity {

    private EditText input;
    private LinearLayout messages;
    private ScrollView chatScroll;
    private Button send;
    private AlphaCore alphaCore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        input = findViewById(R.id.chatInput);
        messages = findViewById(R.id.chatMessages);
        chatScroll = findViewById(R.id.chatScroll);
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

        addMessage("Adiga: " + text, false);

        String answer = alphaCore.process(text);

        addMessage("Alpha: " + answer, true);

        input.setText("");

        chatScroll.post(() ->
                chatScroll.fullScroll(ScrollView.FOCUS_DOWN)
        );
    }

    private void addMessage(String text, boolean alpha) {
        TextView message = new TextView(this);

        message.setText(text);
        message.setTextColor(0xFFFFFFFF);
        message.setTextSize(17);
        message.setPadding(16, 16, 16, 16);

        if (alpha) {
            message.setBackgroundColor(0xFF151E31);
        } else {
            message.setBackgroundColor(0xFF101827);
        }

        LinearLayout.LayoutParams params =
                new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT
                );

        params.setMargins(0, 0, 0, 12);

        messages.addView(message, params);
    }

    @Override
    protected void onDestroy() {
        alphaCore = null;
        super.onDestroy();
    }
}
