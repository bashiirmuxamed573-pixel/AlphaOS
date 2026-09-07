package com.alpha.os;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.speech.tts.TextToSpeech;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Locale;

public class VoiceAssistantActivity extends Activity {

    private SpeechRecognizer recognizer;
    private TextView state;
    private TextView transcript;
    private TextView response;
    private TextToSpeech tts;

    private static final int MIC_PERMISSION = 200;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_voice_assistant);

        state = findViewById(R.id.voiceState);
        transcript = findViewById(R.id.transcript);
        response = findViewById(R.id.alphaResponse);

        Button mic = findViewById(R.id.micButton);

        if (checkSelfPermission(Manifest.permission.RECORD_AUDIO)
                != PackageManager.PERMISSION_GRANTED) {

            requestPermissions(
                    new String[]{Manifest.permission.RECORD_AUDIO},
                    MIC_PERMISSION
            );
        }

        tts = new TextToSpeech(this, status -> {
            if (status == TextToSpeech.SUCCESS) {
                tts.setLanguage(new Locale("so", "SO"));
            }
        });

        if (!SpeechRecognizer.isRecognitionAvailable(this)) {
            state.setText("❌ Speech Recognition lagama heli karo Android-kan.");
            mic.setEnabled(false);
            return;
        }

        recognizer = SpeechRecognizer.createSpeechRecognizer(this);
        recognizer.setRecognitionListener(listener);

        mic.setOnClickListener(v -> startListening());
    }

    private void startListening() {

        if (checkSelfPermission(Manifest.permission.RECORD_AUDIO)
                != PackageManager.PERMISSION_GRANTED) {

            requestPermissions(
                    new String[]{Manifest.permission.RECORD_AUDIO},
                    MIC_PERMISSION
            );
            return;
        }

        state.setText("🎙️ Dhageysanayaa...");
        transcript.setText("");
        response.setText("");

        Intent intent = new Intent(
                RecognizerIntent.ACTION_RECOGNIZE_SPEECH
        );

        intent.putExtra(
                RecognizerIntent.EXTRA_LANGUAGE,
                "so-SO"
        );

        intent.putExtra(
                RecognizerIntent.EXTRA_LANGUAGE_PREFERENCE,
                "so-SO"
        );

        intent.putExtra(
                RecognizerIntent.EXTRA_PARTIAL_RESULTS,
                true
        );

        recognizer.startListening(intent);
    }

    private final RecognitionListener listener =
            new RecognitionListener() {

        @Override
        public void onReadyForSpeech(Bundle params) {
            state.setText("🎙️ Hadal hadda...");
        }

        @Override
        public void onBeginningOfSpeech() {
            state.setText("🎙️ Waan ku dhageysanayaa...");
        }

        @Override
        public void onRmsChanged(float rmsdB) {}

        @Override
        public void onBufferReceived(byte[] buffer) {}

        @Override
        public void onEndOfSpeech() {
            state.setText("⏳ Falanqeynaya...");
        }

        @Override
        public void onError(int error) {
            state.setText(
                    "❌ Speech recognition error: " + error
            );
        }

        @Override
        public void onResults(Bundle results) {

            ArrayList<String> matches =
                    results.getStringArrayList(
                            SpeechRecognizer.RESULTS_RECOGNITION
                    );

            if (matches == null || matches.isEmpty()) {
                state.setText("❌ Hadalka lama aqrin.");
                return;
            }

            String text = matches.get(0);

            transcript.setText(
                    "Adiga: " + text
            );

            String alphaAnswer = processCommand(text);

            response.setText(
                    "Alpha: " + alphaAnswer
            );

            state.setText("✅ Waa la fahmay.");

            speak(alphaAnswer);
        }

        @Override
        public void onPartialResults(Bundle partialResults) {}

        @Override
        public void onEvent(int eventType, Bundle params) {}
    };

    private String processCommand(String text) {

        String command = text.toLowerCase(Locale.ROOT).trim();

        if (command.contains("salaan") ||
                command.contains("iska warran")) {

            return "Waad salaaman tahay. Waxaan ahay Alpha.";
        }

        if (command.contains("yaa tahay")) {

            return "Waxaan ahay Alpha, kaaliyaha ALPHA OS.";
        }

        if (command.contains("waad mahadsan tahay")) {

            return "Adigaa mudan.";
        }

        return "Waan maqlay waxaad tiri: " + text;
    }

    private void speak(String text) {

        if (tts == null) {
            return;
        }

        tts.speak(
                text,
                TextToSpeech.QUEUE_FLUSH,
                null,
                "alpha_voice"
        );
    }

    @Override
    protected void onDestroy() {

        if (recognizer != null) {
            recognizer.destroy();
        }

        if (tts != null) {
            tts.stop();
            tts.shutdown();
        }

        super.onDestroy();
    }
}
