package com.alpha.os;

import android.app.Activity;
import android.os.Bundle;
import android.content.Intent;
import android.widget.Button;

public class LauncherActivity extends Activity {

    private void open(Class<?> screen) {
        startActivity(new Intent(this, screen));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launcher);

        Button ai = findViewById(R.id.ai);
        Button voice = findViewById(R.id.voice);
        Button memory = findViewById(R.id.memory);
        Button android = findViewById(R.id.android);
        Button security = findViewById(R.id.security);
        Button scheduler = findViewById(R.id.scheduler);
        Button plugins = findViewById(R.id.plugins);
        Button settings = findViewById(R.id.settings);

        ai.setOnClickListener(v -> open(ChatActivity.class));
        voice.setOnClickListener(v -> open(VoiceAssistantActivity.class));
        memory.setOnClickListener(v -> open(MemoryActivity.class));
        android.setOnClickListener(v -> open(AndroidControlActivity.class));
        security.setOnClickListener(v -> open(PermissionsActivity.class));
        scheduler.setOnClickListener(v -> open(SchedulerActivity.class));
        plugins.setOnClickListener(v -> open(PluginsActivity.class));
        settings.setOnClickListener(v -> open(SettingsActivity.class));
    }
}
