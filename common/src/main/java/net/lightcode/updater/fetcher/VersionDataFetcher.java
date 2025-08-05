package net.lightcode.updater.fetcher;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;

public class VersionDataFetcher {
    public static String fetch(Logger logger,String urlString) {
        if (urlString == null) return "0.0";

        try {
            URL url = URI.create(urlString).toURL();

            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            connection.setRequestMethod("GET");

            int responseCode = connection.getResponseCode();

            if (responseCode != 200) {
                logger.log(Level.SEVERE, "Received non-OK response " + responseCode);
                return "0.0";
            }

            try (InputStream inputStream = connection.getInputStream();
                 InputStreamReader reader = new InputStreamReader(inputStream);
                 BufferedReader bufferedReader = new BufferedReader(reader)) {

                return bufferedReader.readLine();
            }
        } catch (IOException exception) {
            logger.log(Level.SEVERE, "Fetching version of newest plugin failed " + exception.getMessage());
            return "0.0";
        }
    }
}
