package org.rocs.osd.controller.sms;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Service class responsible for handling SMS
 * communications via an external gateway.
 * This class provides methods to send SMS messages
 * both synchronously and asynchronously,
 * utilizing a REST-based GET API.
 */
public final class SmsService {
    /**
     * Logger instance of this class.
     */
    private static final Logger LOGGER =
            LoggerFactory.getLogger(SmsService.class);
    /**
     * Private constructor to prevent instantiation.
     * */
    private SmsService() {

    }
    /**
     * Base URL for the SMS gateway provider.
     * @return The configured base URL string.
     * */
    private static String getBaseUrl() {
        return ConfigLoader.get("sms.base.url"); }

    /**
     * Authentication username for the SMS API.
     * @return The configured username string.
     * */
    private static String getUsername() {
        return ConfigLoader.get("sms.username"); }
    /**
     * Authentication password for the SMS API.
     * @return The configured password string.
     * */
    private static String getPassword() {
        return ConfigLoader.get("sms.password"); }

    /**
     * Sends an SMS message synchronously.
     * This method formats the phone number,
     * encodes the message, and performs an
     * HTTP GET request to the configured gateway.
     * It logs the response or errors to the console.
     *
     * @param pPhone   The recipient's phone number (e.g., "09123456789").
     * @param pMessage The text content of the SMS.
     */
    public static void sendSMS(String pPhone, String pMessage) {
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("Attempting to send SMS to: {} "
                    + "with message length: {}",
                    pPhone, pMessage.length());
        }
        try {
            /*
              Standardize phone format before sending.
             * */
            pPhone = formatPhone(pPhone);

            /*
              URL encode the message to handle special characters and spaces.
              */
            String encodedMessage = URLEncoder.encode(
                    pMessage, StandardCharsets.UTF_8);
              /*
              Checks if getBaseUrl is null and returns a message if it is null
              */
            if (getBaseUrl() == null) {
                throw new IllegalStateException(
                        "Configuration missing! "
                        + "Check config.properties file.");
            }

            String urlString = getBaseUrl()
                    + "?username="
                    + getUsername()
                    + "&password="
                    + getPassword()
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
            StringBuilder response = new StringBuilder();

            try (BufferedReader reader = new BufferedReader(
                    new InputStreamReader(
                            responseCode == 200
                                    ? conn.getInputStream()
                                    : conn.getErrorStream()
                    ))) {

                String line;
                while ((line = reader.readLine()) != null) {
                    response.append(line);
                }

            } catch (IOException e) {
                if (LOGGER.isErrorEnabled()) {
                    LOGGER.error("Error reading SMS gateway response: {}",
                            e.getMessage());
                }
            }

            if (LOGGER.isDebugEnabled()) {
                LOGGER.debug("SMS gateway response body: {}", response);
            }

            if (responseCode == 200) {
                if (LOGGER.isInfoEnabled()) {
                    LOGGER.info("SMS SENT SUCCESSFULLY: {}", response);
                }
            } else {
                if (LOGGER.isErrorEnabled()) {
                    LOGGER.error("SMS FAILED: {}", response);
                }
            }

        } catch (java.net.SocketTimeoutException e) {
            if (LOGGER.isErrorEnabled()) {
                LOGGER.error("SMS TIMEOUT: The gateway took "
                        + "too long to respond.");
            }
        } catch (java.net.UnknownHostException e) {
            if (LOGGER.isErrorEnabled()) {
                LOGGER.error("SMS NETWORK ERROR: Could not resolve gateway "
                        + "host. Check internet connection.");
            }
        } catch (IOException e) {
            if (LOGGER.isErrorEnabled()) {
                LOGGER.error("SMS I/O ERROR: Problem connecting to the "
                        + "SMS service: {}", e.getMessage());
            }
        } catch (IllegalStateException e) {
            if (LOGGER.isErrorEnabled()) {
                LOGGER.error("SMS CONFIG ERROR: {}", e.getMessage());
            }
        } catch (Exception e) {
            if (LOGGER.isErrorEnabled()) {
                LOGGER.error("SMS UNEXPECTED ERROR: ", e);
            }
        }
    }

    /**
     * Sends an SMS message asynchronously by spawning a new thread.
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
