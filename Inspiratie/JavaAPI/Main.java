package com.kbs.main;

import com.racersystems.jracer.RacerClient;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public class Main {

    private static String getParamsString(Map<String, String> params) {
        StringBuilder result = new StringBuilder();

        for (Map.Entry<String, String> entry : params.entrySet()) {
            result.append(URLEncoder.encode(entry.getKey(), StandardCharsets.UTF_8));
            result.append("=");
            result.append(URLEncoder.encode(entry.getValue(), StandardCharsets.UTF_8));
            result.append("&");
        }

        String resultString = result.toString();
        return resultString.length() > 0
                ? resultString.substring(0, resultString.length() - 1)
                : resultString;
    }

    public static void main(String[] args) {

        String input = "\"C:/Users/andre/Poli/KBS/Project/CarConfig/src/com/kbs/racer/CarOntology.racer\"";
        String queries = "\"C:/Users/andre/Poli/KBS/Project/CarConfig/src/com/kbs/racer/OntologyEvaluation.racer\"";

        try {
            URL url = new URL("http://example.com");
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");

            Map<String, String> parameters = new HashMap<>();
            parameters.put("param1", "val");
            con.setDoOutput(true);
            DataOutputStream out = new DataOutputStream(con.getOutputStream());
            out.writeBytes(getParamsString(parameters));
            out.flush();
            out.close();

            int status = con.getResponseCode();
            System.out.println(status);
            if (status <= 299) {
                BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
                StringBuffer content = new StringBuffer();
                String inputLine;
                while ((inputLine = in.readLine()) != null) {
                    content.append(inputLine);
                }
                in.close();

                System.out.println(content);
            }

            con.disconnect();


        } catch (Exception e) {
            e.printStackTrace();
        }

        String ip = "127.0.0.1";
        int port = 8088;
        RacerClient racer = new RacerClient(ip, port);
        try {
            racer.openConnection();
            System.out.println(racer.sendRaw("(racer-read-file " + input + ")"));

            // obtain all individuals
            String answer = racer.sendRaw("(concept-instances *top*)");
            String[] result = answer.replaceAll("[()]", "").split(" ");

            // if their categories are disjoint, they are compatible
            for (String item1 : result) {
                for (String item2 : result) {
                    if (!item1.equals(item2) &&
                            !item1.equals("MACAN") &&
                            !item1.equals("TAYCAN") &&
                            !item2.equals("MACAN") &&
                            !item2.equals("TAYCAN")) {
                        boolean disjoint = racer.returnBoolean(racer.sendRaw("(evaluate (concept-disjoint-p (car (car (most-specific-instantiators '"
                                + item1 + " (current-abox)))) (car (car (most-specific-instantiators '"
                                + item2 + " (current-abox)))) (current-tbox)))"));
                        if (disjoint) {
                            racer.sendRaw("(related " + item1 + " " + item2 + " ISCOMPATIBLEITEMITEM)");
                        }
                    }
                }
            }

            System.out.println(racer.sendRaw("(racer-read-file " + queries + ")"));

            racer.closeConnection();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
