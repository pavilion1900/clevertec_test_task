package ru.clevertec.output;

import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.*;

class ConsoleOutputTest {

    @Test
    void println() {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        System.setOut(new PrintStream(out));
        String str = "hello";
        Output consoleOutput = new ConsoleOutput();
        consoleOutput.println(str);
        assertEquals(out.toString(), "hello" + System.lineSeparator());
    }
}