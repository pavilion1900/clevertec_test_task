package ru.clevertec.validator;

import java.io.*;
import java.nio.charset.Charset;

public class ValidateCardWithRegEx implements Validate {
    @Override
    public void validate() {
        try (BufferedReader in = new BufferedReader(
                new FileReader("src/main/resources/inputCardData.txt",
                        Charset.forName("UTF-8")));
             PrintWriter outInvalidData = new PrintWriter(
                     new FileWriter("src/main/resources/invalidCardData.txt",
                             Charset.forName("UTF-8")));
             PrintWriter outRightData = new PrintWriter(
                     new FileWriter("src/main/resources/rightCardData.txt",
                             Charset.forName("UTF-8")))) {
            String line;
            while ((line = in.readLine()) != null) {
                String itemRegex = "\\d{3}[1-9];(20|1\\d|[1-9])\\b";
                if (!line.matches(itemRegex)) {
                    outInvalidData.println(line);
                } else {
                    outRightData.println(line);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
