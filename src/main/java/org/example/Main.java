package org.example;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;

import java.io.FileWriter;
import java.io.IOException;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        try {
            // Connect to the website
            Document doc = Jsoup.connect("https://en.wikipedia.org/wiki/MS_Dhoni").get();

            // Parse and print the title
            String title = doc.title();
            System.out.println("Title: " + title);

            // Get all text elements
            Elements elements = doc.getAllElements();

            // Prepare a JSON object
            JsonObject jsonObject = new JsonObject();
            JsonArray textArray = new JsonArray();

            // Iterate over each element and get the text
            for (Element element : elements) {
                String text = element.ownText().trim();
                if (!text.isEmpty()) {
                    textArray.add(new JsonPrimitive(text));
                }
            }

            // Add the text array to the JSON object
            jsonObject.add("texts", textArray);

            // Write JSON to a file
            try (FileWriter file = new FileWriter("output.json")) {
                file.write(jsonObject.toString());
                System.out.println("Successfully written JSON to output.json");
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}