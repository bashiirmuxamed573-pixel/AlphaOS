package com.alpha.os;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MemoryActivity extends Activity {

    private EditText memoryInput;
    private TextView memoryList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_memory);

        memoryInput = findViewById(R.id.memoryInput);
        memoryList = findViewById(R.id.memoryList);
        Button saveMemory = findViewById(R.id.saveMemory);

        saveMemory.setOnClickListener(v -> {
            String text = memoryInput.getText().toString().trim();

            if (text.isEmpty()) {
                Toast.makeText(
                    this,
                    "Fadlan geli xog aad rabto Alpha inuu xasuusto.",
                    Toast.LENGTH_SHORT
                ).show();
                return;
            }

            memoryList.setText("• " + text);
            memoryInput.setText("");

            Toast.makeText(
                this,
                "Waa la xafiday.",
                Toast.LENGTH_SHORT
            ).show();
        });
    }
}
