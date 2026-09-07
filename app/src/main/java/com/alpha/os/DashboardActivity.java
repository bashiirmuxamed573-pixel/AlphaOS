package com.alpha.os;

import android.app.Activity;
import android.os.Bundle;
import android.content.Intent;
import android.widget.Button;

public class DashboardActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        Button permissions = findViewById(R.id.permissionsButton);

        permissions.setOnClickListener(v -> {
            startActivity(
                new Intent(this, PermissionsActivity.class)
            );
        });
    }
}
