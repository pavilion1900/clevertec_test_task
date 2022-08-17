package ru.clevertec.validator;

import java.io.*;
import java.nio.charset.Charset;

public class ValidateItemWithRegEx implements Validate {

    private static final String INPUT_ITEM = "src/main/resources/inputItemData.txt";
    private static final String INVALID_ITEM = "src/main/resources/invalidItemData.txt";
    private static final String RIGHT_ITEM = "src/main/resources/rightItemData.txt";
    private static final String ENCODING = "UTF-8";
    private static final String REGEX = "^(100|[1-9]\\d|0[1-9]|[1-9]);(([A-Z][a-z]{2,29})|"
            + "([А-ЯЁ][а-яё]{2,29}));((100\\.00)|([1-9]\\d|0[1-9]|[1-9])\\.\\d{2});"
            + "(true|false)\\b";

    @Override
    public void validate() {
        try (BufferedReader in = new BufferedReader(
                new FileReader(INPUT_ITEM, Charset.forName(ENCODING)));
             PrintWriter outInvalidData = new PrintWriter(
                     new FileWriter(INVALID_ITEM, Charset.forName(ENCODING)));
             PrintWriter outRightData = new PrintWriter(
                     new FileWriter(RIGHT_ITEM, Charset.forName(ENCODING)))) {
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
