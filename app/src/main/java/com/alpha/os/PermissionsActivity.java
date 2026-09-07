package com.alpha.os;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.speech.tts.TextToSpeech;
import android.widget.Button;
import android.widget.TextView;

import java.util.Locale;

public class PermissionsActivity extends Activity {

    private static final int MIC_REQUEST = 100;
    private static final int NOTIFICATION_REQUEST = 101;

    private TextView microphoneStatus;
    private TextView notificationStatus;
    private TextView filesStatus;
    private TextView ttsStatus;
    private TextView securityStatus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_permissions);

        securityStatus = findViewById(R.id.securityStatus);
        microphoneStatus = findViewById(R.id.microphoneStatus);
        notificationStatus = findViewById(R.id.notificationStatus);
        filesStatus = findViewById(R.id.filesStatus);
        ttsStatus = findViewById(R.id.ttsStatus);

        Button mic = findViewById(R.id.microphoneButton);
        Button notification = findViewById(R.id.notificationButton);
        Button files = findViewById(R.id.filesButton);
        Button tts = findViewById(R.id.ttsButton);
        Button settings = findViewById(R.id.settingsButton);

        mic.setOnClickListener(v -> requestMicrophone());
        notification.setOnClickListener(v -> requestNotifications());
        files.setOnClickListener(v -> openFileSettings());
        tts.setOnClickListener(v -> checkTTS());

        settings.setOnClickListener(v ->
                startActivity(new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
                        android.net.Uri.parse("package:" + getPackageName()))));

        refreshStatus();
    }

    private void requestMicrophone() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.RECORD_AUDIO)
                    != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(
                        new String[]{Manifest.permission.RECORD_AUDIO},
                        MIC_REQUEST
                );
            } else {
                microphoneStatus.setText("✅ Makarafoonka waa la oggolaaday.");
            }
        }
    }

    private void requestNotifications() {
        if (Build.VERSION.SDK_INT >= 33) {
            if (checkSelfPermission(Manifest.permission.POST_NOTIFICATIONS)
                    != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(
                        new String[]{Manifest.permission.POST_NOTIFICATIONS},
                        NOTIFICATION_REQUEST
                );
            } else {
                notificationStatus.setText("✅ Ogeysiisyada waa la oggolaaday.");
            }
        } else {
            notificationStatus.setText("✅ Android-kan notification permission gaar ah uma baahna.");
        }
    }

    private void openFileSettings() {
        Intent intent = new Intent(
                Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
                android.net.Uri.parse("package:" + getPackageName())
        );
        startActivity(intent);
        filesStatus.setText("ℹ️ Faylasha waxaa lagu maamuli karaa Android App Settings.");
    }

    private void checkTTS() {
        TextToSpeech tts = new TextToSpeech(this, status -> {
            if (status == TextToSpeech.SUCCESS) {
                int result = tts.setLanguage(new Locale("so", "SO"));

                if (result == TextToSpeech.LANG_MISSING_DATA ||
                        result == TextToSpeech.LANG_NOT_SUPPORTED) {
                    ttsStatus.setText(
                            "⚠️ TTS waa shaqaynayaa, laakiin cod Soomaali ah lama helin."
                    );
                } else {
                    ttsStatus.setText(
                            "✅ TTS iyo luqadda Soomaaliga waa la helay."
                    );
                }

                tts.shutdown();
            } else {
                ttsStatus.setText("❌ TTS engine lama bilaabi karin.");
            }
        });
    }

    private void refreshStatus() {
        if (checkSelfPermission(Manifest.permission.RECORD_AUDIO)
                == PackageManager.PERMISSION_GRANTED) {
            microphoneStatus.setText("✅ Makarafoonka waa la oggolaaday.");
        } else {
            microphoneStatus.setText("🔒 Makarafoonka wali lama oggolaan.");
        }

        if (Build.VERSION.SDK_INT >= 33) {
            if (checkSelfPermission(Manifest.permission.POST_NOTIFICATIONS)
                    == PackageManager.PERMISSION_GRANTED) {
                notificationStatus.setText("✅ Ogeysiisyada waa la oggolaaday.");
            } else {
                notificationStatus.setText("🔒 Ogeysiisyada wali lama oggolaan.");
            }
        } else {
            notificationStatus.setText("✅ Ogeysiisyadu waxay ku shaqeeyaan Android-kan.");
        }

        filesStatus.setText("ℹ️ Xakamaynta faylasha waxay raacaysaa Android permissions.");

        securityStatus.setText(
                "🛡️ Amniga ALPHA OS: ACTIVE"
        );
    }

    @Override
    protected void onResume() {
        super.onResume();
        refreshStatus();
    }
}
