package ru.clevertec.validator;

import java.io.*;
import java.nio.charset.Charset;

public class ValidateItemWithRegEx implements Validate {
    @Override
    public void validate() {
        try (BufferedReader in = new BufferedReader(
                new FileReader("src/main/resources/inputItemData.txt",
                        Charset.forName("UTF-8")));
             PrintWriter outInvalidData = new PrintWriter(
                     new FileWriter("src/main/resources/invalidItemData.txt",
                             Charset.forName("UTF-8")));
             PrintWriter outRightData = new PrintWriter(
                     new FileWriter("src/main/resources/rightItemData.txt",
                             Charset.forName("UTF-8")))) {
            String line;
            while ((line = in.readLine()) != null) {
                String itemRegex = "^(100|[1-9]\\d|0[1-9]|[1-9]);(([A-Z][a-z]{2,29})|"
                        + "([А-ЯЁ][а-яё]{2,29}));((100\\.00)|([1-9]\\d|0[1-9]|[1-9])\\.\\d{2});"
                        + "(true|false)\\b";
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
