package org.rocs.osd.controller.sms;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

/**
 * Service class responsible for handling SMS
 * communications via an external gateway.
 * <p>
 * This class provides methods to send SMS messages
 * both synchronously and asynchronously,
 * utilizing a REST-based GET API.
 */
public final class SmsService {
    /**
     * Configuration loader instance used to retrieve API credentials.
     * */
    private static ConfigLoader cLoader;
    /**
     * Private constructor to prevent instantiation.
     * */
    private SmsService() {

    }
    /**
     * Base URL for the SMS gateway provider .
     * */
    private final static String baseUrl = cLoader.get("sms.base.url");

    /**
     * Authentication username for the SMS API.
     * */
    private final static String username = cLoader.get("sms.username");

    /**
     * Authentication password for the SMS API.
     * */
    private final static String password = cLoader.get("sms.password");

    /**
     * Sends an SMS message synchronously.
     * <p>
     * This method formats the phone number,
     * encodes the message, and performs an
     * HTTP GET request to the configured gateway.
     * It logs the response or errors to the console.
     *
     * @param pPhone   The recipient's phone number (e.g., "09123456789").
     * @param pMessage The text content of the SMS.
     */
    public static void sendSMS(String pPhone, String pMessage) {

        try {
            /*
              Standardize phone format before sending.
             * */
            pPhone = formatPhone(pPhone);

            /*
              URL encode the message to handle special characters and spaces.
              */
            String encodedMessage = URLEncoder.encode(pMessage, StandardCharsets.UTF_8);

            String urlString = baseUrl
                    + "?username="
                    + username
                    + "&password="
                    + password
                    + "&phone="
                    + pPhone
                    + "&message="
                    + encodedMessage;

            URL url = new URL(urlString);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            /*
             * Sets timeout limits to keep the
             * application from hanging
             * */
            conn.setConnectTimeout(5000);
            conn.setReadTimeout(5000);
            conn.setRequestMethod("GET");

            int responseCode = conn.getResponseCode();

            /*
             * Read the response stream (input if success,
             * error stream if failed)
             * */
            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(
                            responseCode == 200
                                    ?
                                    conn.getInputStream()
                                    :
                                    conn.getErrorStream()
                    )
            );

            String line;
            StringBuilder response = new StringBuilder();

            while ((line = reader.readLine()) != null) {
                response.append(line);
            }

            reader.close();

            if (responseCode == 200) {
                System.out.println("SMS SENT SUCCESSFULLY: " + response);
            } else {
                System.err.println("SMS FAILED: " + response);
            }

        } catch (Exception e) {
            System.err.println("SMS ERROR: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Sends an SMS message asynchronously by spawning a new thread.
     * <p>
     * Use this method to prevent the main execution thread from blocking
     * while waiting for the network response.
     *
     * @param pPhone   The recipient's phone number.
     * @param pMessage The text content of the SMS.
     */
    public static void sendSMSAsync(String pPhone, String pMessage) {
        new Thread(() -> sendSMS(pPhone, pMessage)).start();
    }

    /**
     * Formats a local Philippine phone number to international format.
     * <p>
     * Replaces the leading '0' with the '+63' country code.
     *
     * @param pPhone The raw phone number string.
     * @return The formatted phone number string.
     */
    public static String formatPhone(String pPhone) {
        if (pPhone.startsWith("0")) {
            return "+63" + pPhone.substring(1);
        }
        return pPhone;
    }
}
