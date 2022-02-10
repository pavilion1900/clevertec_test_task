package ru.clevertec.logics;

import ru.clevertec.format.*;
import ru.clevertec.models.Item;
import ru.clevertec.output.Output;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.math.*;
import java.util.List;

public class Logics {
    private List<Item> list;
    private int discount;
    private BigDecimal value;
    private Output out;

    public Logics(List<Item> list, int discount, Output out) {
        this.list = list;
        this.discount = discount;
        this.out = out;
        countValue();
    }

    private void countValue() {
        value = BigDecimal.valueOf(0);
        for (Item item : list) {
            if (item.isPromotion() && item.getQuantity() > 5) {
                value = value.add(item.getPrice()
                        .multiply(BigDecimal.valueOf(0.9))
                        .setScale(2, RoundingMode.HALF_UP)
                        .multiply(BigDecimal.valueOf(item.getQuantity()))
                );
            } else {
                value = value.add(item.getPrice()
                        .multiply(new BigDecimal(100)
                                .subtract(new BigDecimal(discount))
                                .divide(new BigDecimal(100)))
                        .setScale(2, RoundingMode.HALF_UP)
                        .multiply(BigDecimal.valueOf(item.getQuantity()))
                );
            }
        }
    }

    public void printTxt() {
        new FormatTxt(list, discount, value).setFormat();
    }

    public void printConsole() {
        try (BufferedReader in = new BufferedReader(new FileReader(
                "src/main/java/ru/clevertec/doc/Check.txt"))) {
            in.lines().forEach(out::println);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void printConsoleFixedSettings() {
        new FormatConsoleFixedSettings(list, discount, value, out).setFormat();
    }
}