package com.clevertap.lib;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class MyClass {

    public static void main(String as[]) {
        parseJson();
    }

    public static void parseJson() {
        BufferedReader reader = null;
        String file = "/Users/chetan.a/Downloads/1610457216-1621255808-1644523200-0.json";
        try {
            reader = new BufferedReader(new InputStreamReader(new FileInputStream(file), StandardCharsets.UTF_8));
            String inputLine;
            JsonParser parser = new JsonParser(); // com.google.gson.JsonParser
            int counter = 0;
            HashMap<String, Integer> data = new HashMap<>();

            while ((inputLine = reader.readLine()) != null) {
                Set<Map.Entry<String, JsonElement>> entries = parser.parse(inputLine).getAsJsonObject().entrySet();//will return members of your object
                for (Map.Entry<String, JsonElement> entry : entries) {
                    if (data.get(entry.getKey()) != null) {
                        data.put(entry.getKey(), data.get(entry.getKey()) + 1);
                    } else {
                        data.put(entry.getKey(), 1);
                    }
                }
                counter++;
            }
            for (String name : data.keySet()) {
                System.out.println(name + ", " + data.get(name));
            }

            System.out.println("File processed with " + counter + " records");
        } catch (Exception e) {
            System.err.println("Error - " + e.getMessage());
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}