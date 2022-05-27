package ru.clevertec.validator;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;

class ValidateItemWithRegExTest {

    @Test
    void validateItems() {
        Validate validate = new ValidateItemWithRegEx();
        validate.validate();
        String itemRegex = "^(100|[1-9]\\d|0[1-9]|[1-9]);(([A-Z][a-z]{2,29})|"
                + "([А-ЯЁ][а-яё]{2,29}));((100\\.00)|([1-9]\\d|0[1-9]|[1-9])\\.\\d{2});"
                + "(true|false)\\b";
        String validItems;
        try {
            validItems = Files.readString(Path.of("src/main/resources/rightItemData.txt"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        assertTrue(validItems.lines()
                .allMatch(str -> str.matches(itemRegex)));
    }
}