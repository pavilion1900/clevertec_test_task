package ru.clevertec.validator;

import java.io.*;
import java.nio.charset.Charset;

public class ValidateCardWithRegEx implements Validate {

    private static final String INPUT_CARD = "src/main/resources/inputCardData.txt";
    private static final String INVALID_CARD = "src/main/resources/invalidCardData.txt";
    private static final String RIGHT_CARD = "src/main/resources/rightCardData.txt";
    private static final String ENCODING = "UTF-8";
    private static final String REGEX = "\\d{3}[1-9];(20|1\\d|[1-9])\\b";

    @Override
    public void validate() {
        try (BufferedReader in = new BufferedReader(
                new FileReader(INPUT_CARD, Charset.forName(ENCODING)));
             PrintWriter outInvalidData = new PrintWriter(
                     new FileWriter(INVALID_CARD, Charset.forName(ENCODING)));
             PrintWriter outRightData = new PrintWriter(
                     new FileWriter(RIGHT_CARD, Charset.forName(ENCODING)))) {
            String line;
            while ((line = in.readLine()) != null) {
                if (!line.matches(REGEX)) {
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
