package com.alpha.os;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class HomeActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        EditText input = findViewById(R.id.messageInput);
        Button send = findViewById(R.id.sendButton);
        Button voice = findViewById(R.id.voiceButton);
        TextView message = findViewById(R.id.alphaMessage);

        send.setOnClickListener(v -> {
            String text = input.getText().toString().trim();

            if (text.isEmpty()) {
                return;
            }

            message.setText(
                "Adiga: " + text +
                "\n\nAlpha: Haa, waan ku fahmay. Waxaan kuu diyaar ahay."
            );

            input.setText("");
        });

        voice.setOnClickListener(v -> {
            Toast.makeText(
                this,
                "🎙️ Codka Alpha waa diyaar.",
                Toast.LENGTH_SHORT
            ).show();
        });
    }
}
