package io.github.fajzu.shared.updater;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import org.jetbrains.annotations.NotNull;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;

public class VersionDataFetcher {
    public static String fetch(final @NotNull Logger logger,
                               final String urlString) {
        if (urlString == null) {
            return "0.0";
        }

        try {
            final URL url = new URL(urlString);
            final HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("Accept", "application/vnd.github.v3+json");

            final int responseCode = connection.getResponseCode();
            if (responseCode != 200) {
                logger.log(Level.SEVERE, "Received non-OK response " + responseCode);
                return "0.0";
            }

            try (final InputStream inputStream = connection.getInputStream();
                 final InputStreamReader reader = new InputStreamReader(inputStream);
                 final BufferedReader bufferedReader = new BufferedReader(reader)) {

                final String line = bufferedReader.readLine();
                if (line == null || line.isEmpty()) {
                    return "0.0";
                }

                final JsonParser parser = new JsonParser();
                final JsonElement element = parser.parse(line);

                if (!element.isJsonArray()) {
                    return "0.0";
                }

                final JsonArray array = element.getAsJsonArray();
                if (array.size() == 0) {
                    return "0.0";
                }

                final JsonElement firstTag = array.get(0);
                if (!firstTag.isJsonObject()) {
                    return "0.0";
                }

                final JsonElement nameElem = firstTag.getAsJsonObject().get("name");
                if (nameElem == null) {
                    return "0.0";
                }

                return nameElem.getAsString();
            }
        } catch (IOException exception) {
            logger.log(Level.SEVERE, "Fetching latest GitHub tag failed: " + exception.getMessage());
            return "0.0";
        }
    }
}
