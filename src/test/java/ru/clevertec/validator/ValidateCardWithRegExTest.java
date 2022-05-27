package ru.clevertec.validator;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;

class ValidateCardWithRegExTest {

    @Test
    void validateCards() {
        Validate validate = new ValidateCardWithRegEx();
        validate.validate();
        String itemRegex = "\\d{3}[1-9];(20|1\\d|[1-9])\\b";
        String validCards;
        try {
            validCards = Files.readString(Path.of("src/main/resources/rightCardData.txt"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        assertTrue(validCards.lines()
                .allMatch(str -> str.matches(itemRegex)));
    }
}