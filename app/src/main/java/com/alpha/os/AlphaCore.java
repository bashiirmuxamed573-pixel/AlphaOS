package com.alpha.os;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.provider.Settings;

import java.util.Locale;

public class AlphaCore {

    private final Context context;
    private final SharedPreferences memory;

    public AlphaCore(Context context) {
        this.context = context;
        memory = context.getSharedPreferences(
                "alpha_memory",
                Context.MODE_PRIVATE
        );
    }

    public String process(String input) {

        if (input == null || input.trim().isEmpty()) {
            return "Fadlan ii sheeg waxa aad rabto.";
        }

        String text = input.trim();
        String command = text.toLowerCase(Locale.ROOT);

        // XUSUUS: "xusuuso ..."
        if (command.startsWith("xusuuso ")) {

            String value = text.substring(8).trim();

            if (!value.isEmpty()) {
                memory.edit()
                        .putString("last_memory", value)
                        .apply();

                return "Waan xusuustay: " + value;
            }

            return "Maxaad rabtaa inaan xusuusto?";
        }

        // XUSUUSTA AKHRI
        if (command.contains("maxaad xusuusataa") ||
                command.contains("xusuustayda")) {

            String saved = memory.getString("last_memory", null);

            if (saved == null) {
                return "Weli wax xusuus ah lama kaydin.";
            }

            return "Waxaan xusuustaa: " + saved;
        }

        // GOOGLE
        if (command.contains("fur google")) {
            return openUrl("https://www.google.com",
                    "Google ayaan furay.");
        }

        // YOUTUBE
        if (command.contains("fur youtube")) {
            return openUrl("https://www.youtube.com",
                    "YouTube ayaan furay.");
        }

        // ANDROID SETTINGS
        if (command.contains("fur settings") ||
                command.contains("fur dejinta")) {

            try {
                Intent intent = new Intent(
                        Settings.ACTION_SETTINGS
                );

                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);

                return "Android Settings ayaan furay.";

            } catch (Exception e) {
                return "Ma furi karin Android Settings.";
            }
        }

        // APP SETTINGS
        if (command.contains("dejinta alpha") ||
                command.contains("alpha settings")) {

            try {
                Intent intent = new Intent(
                        Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
                        Uri.parse("package:" + context.getPackageName())
                );

                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);

                return "Dejinta ALPHA OS ayaan furay.";

            } catch (Exception e) {
                return "Ma furi karin dejinta ALPHA OS.";
            }
        }

        // SALAAN
        if (command.equals("salaan") ||
                command.contains("iska warran")) {

            return "Waad salaaman tahay. Alpha waa diyaar.";
        }

        // MAGACA
        if (command.contains("yaa tahay")) {
            return "Waxaan ahay Alpha, kaaliyaha ALPHA OS.";
        }

        // HELP
        if (command.equals("help") ||
                command.contains("maxaad qaban kartaa")) {

            return "Waxaan hadda qaban karaa: "
                    + "xusuus kaydin, xusuus akhrin, "
                    + "Google furid, YouTube furid, "
                    + "iyo Android Settings furid.";
        }

        return "Waan fahmay codsigaaga, laakiin amarkan weli lama darin.";
    }

    private String openUrl(String url, String success) {

        try {
            Intent intent = new Intent(
                    Intent.ACTION_VIEW,
                    Uri.parse(url)
            );

            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);

            return success;

        } catch (Exception e) {
            return "Ma heli karo app-ka lagu furo link-kan.";
        }
    }
}
