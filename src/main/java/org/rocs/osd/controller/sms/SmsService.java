package org.rocs.osd.controller.sms;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

    public class SmsService {

        private static final String USERNAME = "test";
        private static final String PASSWORD = "test";
        /**
         * Change IP according to what GSM Modem(SMS) is displaying
         * */
        private static final String BASE_URL = "http://10.63.93.239:8090/SendSMS";

        public static void sendSMS(String phone, String message) {

            try {
                String encodedMessage = URLEncoder.encode(message, "UTF-8");
                /**
                 * USERNAME and PASSWORD are for the accounts on the GSM Modem(SMS), our third-party app that
                 * acts as a middleman between the actual phone used for sending SMS and the java app.
                 * */
                String urlString = BASE_URL +
                        "?username=" + USERNAME +
                        "&password=" + PASSWORD +
                        "&phone=" + phone +
                        "&message=" + encodedMessage;

                URL url = new URL(urlString);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();


                conn.setConnectTimeout(5000);
                conn.setReadTimeout(5000);

                conn.setRequestMethod("GET");

                int responseCode = conn.getResponseCode();

                BufferedReader reader = new BufferedReader(
                        new InputStreamReader(
                                responseCode == 200 ?
                                        conn.getInputStream() :
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

        public static void sendSMSAsync(String phone, String message) {

            new Thread(() -> sendSMS(phone, message)).start();
        }

        public static String formatPhone(String phone) {
            if (phone.startsWith("0")) {
                return "+63" + phone.substring(1);
            }
            return phone;
        }
    }
