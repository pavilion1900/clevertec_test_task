package ru.clevertec.logic;

import ru.clevertec.format.*;
import ru.clevertec.model.Item;
import ru.clevertec.output.Output;
import ru.clevertec.task.collection.CustomList;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.math.*;
import java.nio.charset.Charset;

public class Logics {
    private CustomList<Item> list;
    private int discount;
    private BigDecimal value;
    private Output out;

    public Logics(CustomList<Item> list, int discount, Output out) {
        this.list = list;
        this.discount = discount;
        this.out = out;
        countValue();
    }

    private void countValue() {
        value = BigDecimal.valueOf(0);
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).isPromotion() && list.get(i).getQuantity() > 5) {
                value = value.add(list.get(i).getPrice()
                        .multiply(BigDecimal.valueOf(0.9))
                        .setScale(2, RoundingMode.HALF_UP)
                        .multiply(BigDecimal.valueOf(list.get(i).getQuantity()))
                );
            } else {
                value = value.add(list.get(i).getPrice()
                        .multiply(new BigDecimal(100)
                                .subtract(new BigDecimal(discount))
                                .divide(new BigDecimal(100)))
                        .setScale(2, RoundingMode.HALF_UP)
                        .multiply(BigDecimal.valueOf(list.get(i).getQuantity()))
                );
            }
        }
    }

    public BigDecimal getValue() {
        return value;
    }

    public void printTxt() {
        new FormatTxt(list, discount, value).setFormat();
    }

    public void printConsole() {
        try (BufferedReader in = new BufferedReader(new FileReader(
                "src/main/resources/Check.txt", Charset.forName("WINDOWS-1251")))) {
            in.lines().forEach(out::println);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void printConsoleFixedSettings() {
        new FormatConsoleFixedSettings(list, discount, value, out).setFormat();
    }
}