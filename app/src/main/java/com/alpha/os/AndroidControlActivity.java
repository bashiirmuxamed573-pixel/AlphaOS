package com.alpha.os;

import android.app.Activity;
import android.os.Bundle;
import android.content.Intent;
import android.provider.Settings;
import android.widget.Button;
import android.widget.TextView;

public class AndroidControlActivity extends Activity {

    private TextView status;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_android_control);

        status = findViewById(R.id.controlStatus);

        Button apps = findViewById(R.id.openApps);
        Button settings = findViewById(R.id.openSettings);
        Button files = findViewById(R.id.openFiles);
        Button notifications = findViewById(R.id.notifications);
        Button browser = findViewById(R.id.browser);

        apps.setOnClickListener(v ->
            status.setText("Apps-ka telefoonka ayaa la diyaarinayaa.")
        );

        settings.setOnClickListener(v -> {
            startActivity(new Intent(Settings.ACTION_SETTINGS));
        });

        files.setOnClickListener(v ->
            status.setText("Maamulka Files-ka ayaa la diyaarinayaa.")
        );

        notifications.setOnClickListener(v ->
            status.setText("Maamulka ogeysiisyada ayaa la diyaarinayaa.")
        );

        browser.setOnClickListener(v ->
            status.setText("Browser-ka ayaa la diyaarinayaa.")
        );
    }
}
